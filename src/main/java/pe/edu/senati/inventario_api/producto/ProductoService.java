package pe.edu.senati.inventario_api.producto;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductoService {
    private final ProductoRepository repo;

    public ProductoService(ProductoRepository repo) {
        this.repo = repo;
    }

    public List<Producto> listar(String nombre) {
        if (nombre == null || nombre.isBlank()) return repo.findAll();
        return repo.findByNombreContainingIgnoreCase(nombre);
    }

    public Producto buscar(Long id) {
        return repo.findById(id).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }

    public Producto crear(ProductoDTO datos) {
        Producto producto = new Producto(datos.nombre().trim(), datos.precio(), datos.stock());
        return repo.save(producto);
    }

    public Producto actualizar(Long id, ProductoDTO datos) {
        Producto producto = buscar(id);
        producto.setNombre(datos.nombre().trim());
        producto.setPrecio(datos.precio());
        producto.setStock(datos.stock());
        return repo.save(producto);
    }

    public void eliminar(Long id) {
        Producto producto = buscar(id);
        repo.delete(producto);
    }
}
