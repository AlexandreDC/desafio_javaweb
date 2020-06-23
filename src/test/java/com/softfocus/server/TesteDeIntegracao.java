/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softfocus.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softfocus.server.api.repository.CategoriaRepository;
import com.softfocus.server.api.repository.ProdutoRepository;
import com.softfocus.server.model.Categoria;
import com.softfocus.server.model.Produto;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 *
 * @author alexa
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class TesteDeIntegracao {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    @Order(1)
    void testeDeInsercaoCategoriaViaAPI() throws Exception {
        Categoria categoriaTestada = new Categoria();

        String nomeAleatorio = "T- Teste de insercao" 
                + new SimpleDateFormat("yy-MM-dd HH:mm:ss.SSS").format(new Date()).substring(0, 20)
                + new Random().ints(97, 123)
                        .limit(10)
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();

        assertThat(nomeAleatorio.length()).isEqualTo(50);

        categoriaTestada.setNome(nomeAleatorio);

        mockMvc.perform(post("/api/v1/categorias/adicionar-categoria")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(categoriaTestada)))
                .andExpect(status().isOk());

        List<Categoria> categoriasSalvas = categoriaRepository.findByNome(categoriaTestada.getNome());

        assertThat(!categoriasSalvas.isEmpty());

        assertThat(categoriasSalvas.size()).isEqualTo(1);

        assertThat(categoriasSalvas.get(0).getNome()).isEqualTo(categoriaTestada.getNome());

    }

    @Test
    @Order(2)
    void testeDeConsultaCategoriaViaAPI() throws Exception {

        List<Categoria> listaCategorias;
        Categoria categoriaTestada;

        listaCategorias = categoriaRepository.findCategoriasTeste();

        assertThat(!listaCategorias.isEmpty());

        categoriaTestada = listaCategorias.get(listaCategorias.size() - 1);

        mockMvc.perform(get("/api/v1/categorias/"+categoriaTestada.getCodigo().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value(categoriaTestada.getNome()));

    }
    
    @Test
    @Order(3)
    void testeDeAlteracaoCategoriaViaAPI() throws Exception {

        List<Categoria> listaCategorias;
        Categoria categoriaTestada;
        Optional<Categoria> categoriaRecuperada;

        String nomeAleatorio = "T- Teste de insercao" 
                + new SimpleDateFormat("yy-MM-dd HH:mm:ss.SSS").format(new Date()).substring(0, 20)
                + new Random().ints(97, 123)
                        .limit(10)
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();

        assertThat(nomeAleatorio.length()).isEqualTo(50);

        listaCategorias = categoriaRepository.findCategoriasTeste();

        assertThat(!listaCategorias.isEmpty());

        categoriaTestada = listaCategorias.get(listaCategorias.size() - 1);

        categoriaTestada.setNome(nomeAleatorio);
        

        mockMvc.perform(put("/api/v1/categorias/" + categoriaTestada.getCodigo().toString())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(categoriaTestada)))
                .andExpect(status().isOk());

        categoriaRecuperada = categoriaRepository.findById(categoriaTestada.getCodigo());

        assertThat(!categoriaRecuperada.isEmpty());

        assertThat(categoriaRecuperada.get().getNome()).isEqualTo(categoriaTestada.getNome());
    }
    
    @Test
    @Order(4)
    void testeDeInsercaoProdutoViaAPI() throws Exception {
        List<Produto> produtosSalvos;
        Produto produtoTestado = new Produto();
        List<Categoria> listaCategorias = categoriaRepository.findCategoriasTeste();

        assertThat(!listaCategorias.isEmpty());

        String descricaoAleatoria = new SimpleDateFormat("yy-MM-dd HH:mm:ss.SSS").format(new Date()).substring(0, 20)
                + new Random().ints(97, 123)
                        .limit(4000 - 20) //retira 20 da string inicial
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();

        String nomeAleatorio = "T- Teste de insercao"
                + new Random().ints(97, 123)
                        .limit(200 - 20) //retira 20 da string inicial
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();

        assertThat(nomeAleatorio.length()).isEqualTo(200);
        assertThat(descricaoAleatoria.length()).isEqualTo(4000);

        produtoTestado.setNome(nomeAleatorio);
        produtoTestado.setDescricao(descricaoAleatoria);

        produtoTestado.setCategoria(listaCategorias.get(listaCategorias.size() - 1));

        mockMvc.perform(post("/api/v1/produtos/adicionar-produto")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(produtoTestado)))
                .andExpect(status().isOk());

        produtosSalvos = produtoRepository.findByDescricao(descricaoAleatoria);

        assertThat(!produtosSalvos.isEmpty());

        assertThat(produtosSalvos.get(0).getNome()).isEqualTo(produtoTestado.getNome());
        assertThat(produtosSalvos.get(0).getDescricao()).isEqualTo(produtoTestado.getDescricao());
        assertThat(produtosSalvos.get(0).getCategoria()).isEqualTo(produtoTestado.getCategoria());

    }

    @Test
    @Order(5)
    void testeDeConsultaProdutoViaAPI() throws Exception {

        List<Produto> listaProdutos;
        Produto produtoTestado;

        listaProdutos = produtoRepository.findProdutosTeste();

        assertThat(!listaProdutos.isEmpty());

        produtoTestado = listaProdutos.get(listaProdutos.size() - 1);

        mockMvc.perform(get("/api/v1/produtos/"+produtoTestado.getCodigo().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value(produtoTestado.getNome()))
                .andExpect(jsonPath("$.descricao").value(produtoTestado.getDescricao()))
                .andExpect(jsonPath("$.categoria.nome").value(produtoTestado.getCategoria().getNome()));

    }

    @Test
    @Order(6)
    void testeDeAlteracaoProdutoViaAPI() throws Exception {

        List<Produto> listaProdutos;
        Produto produtoTestado;
        Optional<Produto> produtoRecuperado;

        String descricaoAleatoria = new SimpleDateFormat("yy-MM-dd HH:mm:ss.SSS").format(new Date()).substring(0, 20)
                + new Random().ints(97, 123)
                        .limit(4000 - 20) //retira 20 da string inicial
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();

        String nomeAleatorio = "T- Teste de alteraca"
                + new Random().ints(97, 123)
                        .limit(200 - 20) //retira 20 da string inicial
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();

        assertThat(nomeAleatorio.length()).isEqualTo(200);
        assertThat(descricaoAleatoria.length()).isEqualTo(4000);

        listaProdutos = produtoRepository.findProdutosTeste();

        assertThat(!listaProdutos.isEmpty());

        produtoTestado = listaProdutos.get(listaProdutos.size() - 1);

        produtoTestado.setNome(nomeAleatorio);
        produtoTestado.setDescricao(descricaoAleatoria);
        produtoTestado.setCategoria(categoriaRepository.findAll().get(0));

        mockMvc.perform(put("/api/v1/produtos/" + produtoTestado.getCodigo().toString())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(produtoTestado)))
                .andExpect(status().isOk());

        produtoRecuperado = produtoRepository.findById(produtoTestado.getCodigo());

        assertThat(!produtoRecuperado.isEmpty());

        assertThat(produtoRecuperado.get().getNome()).isEqualTo(produtoTestado.getNome());
        assertThat(produtoRecuperado.get().getDescricao()).isEqualTo(produtoTestado.getDescricao());
        assertThat(produtoRecuperado.get().getCategoria()).isEqualTo(produtoTestado.getCategoria());
    }
    
    @Test
    @Order(7)
    void testeDeExclusaoProdutoViaAPI() throws Exception {

        List<Produto> listaProdutos;
        Optional<Produto> produtoRecuperado;

        listaProdutos = produtoRepository.findProdutosTeste();

        assertThat(!listaProdutos.isEmpty());

        
        
        

        
        
        
        for (Produto produto : listaProdutos) {
            
            mockMvc.perform(delete("/api/v1/produtos/" + produto.getCodigo().toString())
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(produto)))
                    .andExpect(status().isOk());

            produtoRecuperado = produtoRepository.findById(produto.getCodigo());

            assertThat(produtoRecuperado.isEmpty());
        }
        
    }

    
    @Test
    @Order(8)
    void testeDeExclusaoCategoriaViaAPI() throws Exception {

      
        List<Categoria> listaCategoria;
        Optional<Categoria> categoriaRecuperada;

        listaCategoria = categoriaRepository.findCategoriasTeste();

        assertThat(!listaCategoria.isEmpty());

        
        
        for (Categoria categoria : listaCategoria) {
            
            mockMvc.perform(delete("/api/v1/categorias/" + categoria.getCodigo().toString())
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(categoria)))
                    .andExpect(status().isOk());

            categoriaRecuperada = categoriaRepository.findById(categoria.getCodigo());

            assertThat(categoriaRecuperada.isEmpty());
        }
    }

}
