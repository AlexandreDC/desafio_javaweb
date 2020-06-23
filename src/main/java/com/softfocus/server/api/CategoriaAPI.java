/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softfocus.server.api;

import com.softfocus.server.util.ResourceNotFoundException;
import com.softfocus.server.model.Categoria;
import com.softfocus.server.service.CategoriaService;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaAPI {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<Categoria>> findAll() {
        return ResponseEntity.ok(categoriaService.findAll());
    }

    @PostMapping("/adicionar-categoria")
    public ResponseEntity createCategoria(@Valid @RequestBody Categoria categoria) {

        return ResponseEntity.ok(categoriaService.salvar(categoria));

    }
    
    

    @GetMapping(path = "/{categoria_codigo}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Categoria> findByCodigo(@PathVariable Integer categoria_codigo) {
        Optional<Categoria> stock = categoriaService.findByCodigo(categoria_codigo);
        if (!stock.isPresent()) {
            new ResourceNotFoundException("Categoria not found with id " + categoria_codigo);
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(stock.get());
    }

    @PutMapping("/{categoria_codigo}")
    public ResponseEntity updateCategoria(@PathVariable Integer categoria_codigo,
            @Valid @RequestBody Categoria categoria) {

        if (!categoriaService.findByCodigo(categoria_codigo).isPresent()) {
            new ResourceNotFoundException("Categoria not found with id " + categoria_codigo);
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(categoriaService.salvar(categoria));

    }

    @DeleteMapping("/{categoria_codigo}")
    public ResponseEntity deleteCategoria(@PathVariable Integer categoria_codigo) {
        if (!categoriaService.findByCodigo(categoria_codigo).isPresent()) {
            ResponseEntity.badRequest().build();
        }

        categoriaService.deleteByCodigo(categoria_codigo);

        return ResponseEntity.ok().build();
    }

}
