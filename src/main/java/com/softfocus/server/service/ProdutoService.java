/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softfocus.server.service;

import com.softfocus.server.api.repository.ProdutoRepository;
import com.softfocus.server.model.Produto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class ProdutoService {
    private final ProdutoRepository produtoRespository;
    
    
    public ProdutoService(ProdutoRepository produtoRespository) {
        this.produtoRespository = produtoRespository;
    }
    
    
    
    public List<Produto> findAll() {
        return produtoRespository.findAll();
    }

    public Optional<Produto> findByCodigo(Integer id) {
        return produtoRespository.findById(id);
    }

    public Produto salvar(Produto stock) {
        return produtoRespository.save(stock);
    }

    public void deleteByCodigo(Integer id) {
        produtoRespository.deleteById(id);
    }
}