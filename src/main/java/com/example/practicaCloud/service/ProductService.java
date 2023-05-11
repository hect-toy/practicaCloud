package com.example.practicaCloud.service;

import com.example.practicaCloud.entity.Categoria;
import com.example.practicaCloud.entity.Producto;

import java.util.List;

public interface ProductService {
    public List<Producto> listAllProduct();
    public Producto getProducto(Long id);
    public Producto createProducto(Producto producto);
    public Producto updateProducto(Producto producto);
    public Producto deleteProducto(Long id);
    public List<Producto> findByCategoria(Categoria categoria);
    public Producto updateStock(Long id, Double quantity);
}
