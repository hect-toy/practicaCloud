package com.example.practicaCloud;

import com.example.practicaCloud.entity.Categoria;
import com.example.practicaCloud.entity.Producto;
import com.example.practicaCloud.repository.ProductoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

@DataJpaTest
public class pacticaTestRepository {

    @Autowired
    private ProductoRepository productoRepository;


    @Test
    public void cuandobusquesXCategoria_yObtengasListaProdcutos(){
        Producto producto1 = Producto.builder()
                .name("computer")
                .categoria(Categoria.builder().id(1L).build())
                .description("")
                .stock(Double.parseDouble("10"))
                .price(Double.parseDouble("89"))
                .status("Created")
                .createat(new Date())
                .build();

        productoRepository.save(producto1);

        List<Producto> founds = productoRepository.findByCategoria(producto1.getCategoria());
        Assertions.assertThat(founds.size()).isEqualTo(3);

    }

}
