package com.example.practicaCloud.repository;

import com.example.practicaCloud.entity.Categoria;
import com.example.practicaCloud.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto,Long> {

    public List<Producto> findByCategoria(Categoria categoria);
}
