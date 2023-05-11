package com.example.practicaCloud.service.serviceImplement;

import com.example.practicaCloud.entity.Categoria;
import com.example.practicaCloud.entity.Producto;
import com.example.practicaCloud.repository.ProductoRepository;
import com.example.practicaCloud.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ProducServiceImplement implements ProductService {

    private final ProductoRepository productoRepository;

    @Override
    public List<Producto> listAllProduct() {
        return productoRepository.findAll();
    }

    @Override
    public Producto getProducto(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    public Producto updateProducto(Producto producto) {
        Producto exist = getProducto(producto.getId());
        if (exist == null) {
            return null;
        }
        return productoRepository.save(producto);
    }

    @Override
    public Producto deleteProducto(Long id) {
        Producto delete = getProducto(id);
        if (delete == null) {
            return null;
        }
        delete.setStatus("DELETE");
        return productoRepository.save(delete);
    }

    @Override
    public Producto createProducto(Producto producto) {
        producto.setStatus("CREATED");
        producto.setCreateat(new Date());
        return productoRepository.save(producto);
    }

    @Override
    public List<Producto> findByCategoria(Categoria categoria) {
        return productoRepository.findByCategoria(categoria);
    }

    @Override
    public Producto updateStock(Long id, Double quantity) {
        Producto producto = getProducto(id);
        if(producto == null) {
            return null;
        }
        producto.setStock(quantity);
        return productoRepository.save(producto);
    }
}
