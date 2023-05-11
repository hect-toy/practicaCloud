package com.example.practicaCloud.controller;

import com.example.practicaCloud.entity.Categoria;
import com.example.practicaCloud.entity.Producto;
import com.example.practicaCloud.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.sound.sampled.Port;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/Productos")
public class ProductoController {

    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<List<Producto>> obtenerProductosXCategoria(@RequestParam(name = "id")Long id){
        List<Producto> productos = new ArrayList<>();
        if (id == null){
            productos = service.listAllProduct();
            if (productos.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }
        productos = service.findByCategoria(Categoria.builder().id(id).build());
        if (productos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable("id")Long id){
        Producto producto = service.getProducto(id);
        if (producto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(producto);
    }

    @PostMapping
    public ResponseEntity<Producto> nuevoProducto(@Valid @RequestBody Producto producto, BindingResult result){
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Producto newProducto = service.createProducto(producto);
        if (newProducto == null){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(newProducto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable("id")Long id,@RequestBody Producto producto){
        producto.setId(id);
        Producto productoActualizado = service.updateProducto(producto);
        if (productoActualizado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(producto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Producto> borrarProducto(@PathVariable("id")Long id){
        Producto producto = service.deleteProducto(id);
        if(producto==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(producto);
    }

    @PutMapping(value = "/{id}/stock")
    public ResponseEntity<Producto> actualizarStock(@PathVariable("id")Long id,@RequestParam(name = "cantidad",required = true)Double cantidad){
        Producto producto = service.updateStock(id,cantidad);
        if (producto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(producto);
    }

    private String formatMessage(BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err->{
                    Map<String,String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .message(errors)
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String json="";
        try{
            json = mapper.writeValueAsString(errors);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return json;
    }
}
