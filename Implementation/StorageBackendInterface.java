import java.util.List;

public interface StorageBackendInterface<T, U> {
    void insert(T key, U item);
    List<U> search(T key);
    void delete(T key);
}
