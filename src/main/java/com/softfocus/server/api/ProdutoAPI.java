/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softfocus.server.api;

import com.softfocus.server.util.ResourceNotFoundException;
import com.softfocus.server.model.Produto;
import com.softfocus.server.service.ProdutoService;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/v1/produtos")
public class ProdutoAPI {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<Produto>> findAll() {
        return ResponseEntity.ok(produtoService.findAll());
    }

    @PostMapping("/adicionar-produto")
    public ResponseEntity createProduto(@Valid @RequestBody Produto produto) {

        
         
        return ResponseEntity.ok(produtoService.salvar(produto));

    }
    
    

    @GetMapping(path = "/{produto_codigo}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Produto> findByCodigo(@PathVariable Integer produto_codigo) {
        Optional<Produto> stock = produtoService.findByCodigo(produto_codigo);
        if (!stock.isPresent()) {
            new ResourceNotFoundException("Produto not found with id " + produto_codigo);
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(stock.get());
    }

    @PutMapping("/{produto_codigo}")
    public ResponseEntity updateProduto(@PathVariable Integer produto_codigo,
            @Valid @RequestBody Produto produto) {

        if (!produtoService.findByCodigo(produto_codigo).isPresent()) {
            new ResourceNotFoundException("Produto not found with id " + produto_codigo);
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(produtoService.salvar(produto));

    }

    @DeleteMapping("/{produto_codigo}")
    public ResponseEntity deleteProduto(@PathVariable Integer produto_codigo) {
        if (!produtoService.findByCodigo(produto_codigo).isPresent()) {
            ResponseEntity.badRequest().build();
        }

        produtoService.deleteByCodigo(produto_codigo);

        return ResponseEntity.ok().build();
    }

}
