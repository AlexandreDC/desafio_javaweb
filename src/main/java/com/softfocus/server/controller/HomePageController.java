/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softfocus.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {


    @GetMapping("/")
    public String index(){
        return "index";
    }
    
    @GetMapping("/requisitos")
    public String requisitos(){
        return "requisitos";
    }
}
