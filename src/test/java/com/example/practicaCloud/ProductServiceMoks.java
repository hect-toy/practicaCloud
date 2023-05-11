package com.example.practicaCloud;

import com.example.practicaCloud.entity.Categoria;
import com.example.practicaCloud.entity.Producto;
import com.example.practicaCloud.repository.ProductoRepository;
import com.example.practicaCloud.service.ProductService;
import com.example.practicaCloud.service.serviceImplement.ProducServiceImplement;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

@SpringBootTest
public class ProductServiceMoks {

    @Mock
    private ProductoRepository productoRepository;
    @Autowired
    private ProductService productService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        productService = new ProducServiceImplement(productoRepository);
        Producto computer = Producto.builder()
                .id(1L)
                .name("computer")
                .categoria(Categoria.builder().id(1L).build())
                .description("")
                .stock(Double.parseDouble("5"))
                .price(Double.parseDouble("89"))
                .status("Created")
                .createat(new Date())
                .build();
        Mockito.when(productoRepository.findById(1L)).thenReturn(Optional.of(computer));
        Mockito.when(productoRepository.save(computer)).thenReturn(computer);
    }

    @Test
    public void cuandoValidaObtenerID_entoncesRetornarProducto(){
        Producto found = productService.getProducto(1L);
        Assertions.assertThat(found.getName()).isEqualTo("computer");
    }

    @Test
    public void cuandoActualicesStock_esntoncesRetornaNuevoStock(){
        Producto newStock = productService.updateStock(1L,Double.parseDouble("13"));
        Assertions.assertThat(newStock.getStock()).isEqualTo(13);
    }
}
