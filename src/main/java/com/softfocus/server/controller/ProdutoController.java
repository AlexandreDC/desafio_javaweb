/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softfocus.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 *
 * @author alexa
 */

@Controller
public class ProdutoController {
    @GetMapping("/produtos")
    public String list(){
        return "produtos";
    }
}