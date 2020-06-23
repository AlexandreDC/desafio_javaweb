/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softfocus.server.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alexa
 */
@Entity
@Table(name = "categorias")
@NamedQueries({
    @NamedQuery(name = "Categoria.findAll", query = "SELECT p FROM Categoria p"),
    @NamedQuery(name = "Categoria.findByCodigo", query = "SELECT p FROM Categoria p WHERE p.codigo = :codigo"),
    @NamedQuery(name = "Categoria.findCategoriasTeste", query = "SELECT p FROM Categoria p WHERE (p.nome like 'T- Teste de insercao%' or p.nome like 'T- Teste de alteraca%')"),
    @NamedQuery(name = "Categoria.findByNome", query = "SELECT p FROM Categoria p WHERE p.nome = :nome")})
@XmlRootElement
public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo", nullable = false)
    private Integer codigo;
    
    
    @Basic(optional = false)
    @Column(name = "nome", nullable = false, length = 50)
    private String nome;
    

    public Categoria() {
    }

    public Categoria(Integer codigo) {
        this.codigo = codigo;
    }

    public Categoria(Integer codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categoria)) {
            return false;
        }
        Categoria other = (Categoria) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.softfocus.exam.model.Categoria[ codigo=" + codigo + " ]";
    }
    
}
