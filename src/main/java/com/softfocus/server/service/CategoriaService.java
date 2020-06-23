/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softfocus.server.service;

import com.softfocus.server.api.repository.CategoriaRepository;
import com.softfocus.server.model.Categoria;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class CategoriaService {
    private final CategoriaRepository categoriaRespository;
    
    
    public CategoriaService(CategoriaRepository categoriaRespository) {
        this.categoriaRespository = categoriaRespository;
    }
    
    
    
    public List<Categoria> findAll() {
        return categoriaRespository.findAll();
    }

    public Optional<Categoria> findByCodigo(Integer id) {
        return categoriaRespository.findById(id);
    }

    public Categoria salvar(Categoria stock) {
        return categoriaRespository.save(stock);
    }

    public void deleteByCodigo(Integer id) {
        categoriaRespository.deleteById(id);
    }
}