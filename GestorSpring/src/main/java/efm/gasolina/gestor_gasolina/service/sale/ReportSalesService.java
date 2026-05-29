package efm.gasolina.gestor_gasolina.service.sale;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import efm.gasolina.gestor_gasolina.model.sale.Sale;
import efm.gasolina.gestor_gasolina.repository.sale.SaleRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class ReportSalesService {

    private final SaleRepository saleRepo;
    private final JavaMailSender mailSender;

    @Value("${spring.data.personalmail}")
    private String destinatario;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public ReportSalesService(SaleRepository saleRepo, JavaMailSender mailSender) {
        this.saleRepo = saleRepo;
        this.mailSender = mailSender;
    }

    // ─────────────────────────────────────────────────────────────
    // MÉTODO PÚBLICO: obtiene las ventas, genera el PDF y lo envía
    // ─────────────────────────────────────────────────────────────
    public void generateAndSend(Long id) throws Exception {
        List<Sale> ventas = saleRepo.findByStationId(id);
        byte[] pdfBytes = generarPDF(ventas);
        enviarCorreo(destinatario, pdfBytes);
    }

    // ─────────────────────────────────────────────────────────────
    // MÉTODO PRIVADO: construye el PDF en memoria → byte[]
    // ─────────────────────────────────────────────────────────────
    private byte[] generarPDF(List<Sale> ventas) throws Exception {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer   = new PdfWriter(baos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document  = new Document(pdfDoc);

        PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        PdfFont regular  = PdfFontFactory.createFont(StandardFonts.HELVETICA);

        DeviceRgb headerColor = new DeviceRgb(41, 128, 185);
        DeviceRgb grisClaro   = new DeviceRgb(240, 240, 240);
        DeviceRgb blanco      = new DeviceRgb(255, 255, 255);

        // ── Título ───────────────────────────────────────────────
        document.add(new Paragraph("Reporte de Ventas de Gasolina")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(20)
                .setFont(boldFont));

        document.add(new Paragraph("Generado el: " + LocalDateTime.now().format(FORMATTER))
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(10)
                .setFont(regular)
                .setFontColor(ColorConstants.GRAY));

        document.add(new Paragraph("\n"));

        // ── Tabla ────────────────────────────────────────────────
        float[] columnWidths = {0.5f, 1.5f, 1.5f, 1.5f, 1f, 1.5f, 1.5f, 2f, 2f, 2f};
        Table tabla = new Table(UnitValue.createPercentArray(columnWidths))
                .useAllAvailableWidth();

        String[] headers = {
            "ID", "Placa", "Combustible", "Vehículo",
            "Vol (gal)", "$/Galón", "Total", "Estación", "Operador", "Fecha"
        };

        // ── Encabezados ──────────────────────────────────────────
        for (String h : headers) {
            tabla.addHeaderCell(
                new Cell()
                    .add(new Paragraph(h).setFont(boldFont).setFontColor(ColorConstants.WHITE))
                    .setBackgroundColor(headerColor)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setPadding(5)
            );
        }

        // ── Filas ────────────────────────────────────────────────
        boolean alterna = false;
        for (Sale venta : ventas) {
            DeviceRgb bg = alterna ? grisClaro : blanco;

            // Usamos solo los getters que existen en Sale
            String operador = venta.getOperator() != null
                    ? venta.getOperator().getName() : "N/A";
            String estacion = venta.getStation() != null
                    ? String.valueOf(venta.getStation().getId()) : "N/A";

            String[] valores = {
                String.valueOf(venta.getId()),
                venta.getPlate(),
                venta.getFuelType(),
                venta.getVehicleType(),
                String.valueOf(venta.getVolume()),
                "$" + venta.getPricePerGallon(),
                "$" + venta.getTotalPrice(),
                estacion,
                operador,
                venta.getDate().format(FORMATTER)
            };

            for (String valor : valores) {
                tabla.addCell(
                    new Cell()
                        .add(new Paragraph(valor).setFont(regular))
                        .setBackgroundColor(bg)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setPadding(4)
                );
            }
            alterna = !alterna;
        }

        document.add(tabla);

        // ── Totales ──────────────────────────────────────────────
        int totalGeneral = ventas.stream().mapToInt(Sale::getTotalPrice).sum();

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Total de ventas: " + ventas.size())
                .setTextAlignment(TextAlignment.RIGHT)
                .setFont(boldFont));
        document.add(new Paragraph("Ingreso total: $" + totalGeneral)
                .setTextAlignment(TextAlignment.RIGHT)
                .setFont(boldFont)
                .setFontSize(13));

        document.close();
        return baos.toByteArray();
    }

    // ─────────────────────────────────────────────────────────────
    // MÉTODO PRIVADO: adjunta el byte[] y envía el correo
    // ─────────────────────────────────────────────────────────────
    private void enviarCorreo(String destinatario,
                               byte[] pdfBytes) throws MessagingException {

        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

        helper.setTo(destinatario);
        helper.setSubject("Reporte de Ventas de Gasolina");
        helper.setText("""
                <h2>Reporte de Ventas</h2>
                <p>Adjunto encontrarás el reporte de ventas generado automáticamente.</p>
                <p>Saludos.</p>
                """, true);

        helper.addAttachment("reporte_ventas.pdf", new ByteArrayResource(pdfBytes));
        mailSender.send(mensaje);
    }
}