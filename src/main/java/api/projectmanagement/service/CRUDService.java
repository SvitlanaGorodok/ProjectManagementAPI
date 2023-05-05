package api.projectmanagement.service;

import java.util.List;
import java.util.UUID;

public interface CRUDService<T> {
    public T save(T object);

    public List<T> findAll();

    public T findById(UUID id);

    public void deleteById(UUID id);
}
