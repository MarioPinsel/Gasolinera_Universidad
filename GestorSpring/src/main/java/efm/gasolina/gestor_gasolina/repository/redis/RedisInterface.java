package efm.gasolina.gestor_gasolina.repository.redis;

public interface RedisInterface {
    public void save(String key, Object value);
    public void saveWithTTL(String key, Object value, Integer ttl);
    public Object getValue (String key);
}
