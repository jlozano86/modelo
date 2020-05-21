package com.uca.capas.modelo.controller;

import java.util.List;

import com.uca.capas.modelo.domain.Cliente;
import com.uca.capas.modelo.service.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Clase18Controller {

    @Autowired
    ClienteService clienteService;

    @RequestMapping("/index18")
    public String index18(){
        return "clases/clase18/index";
    }

    @RequestMapping("/clase18/clientes")
    public @ResponseBody List<Cliente> getClientesNombre(@RequestParam Integer tipo, 
    @RequestParam(required = false) String valor1, @RequestParam(required = false) String valor2)
            throws Exception {
        List<Cliente> clientes = clienteService.getClientesQueryMethod(tipo, valor1, valor2);
        return clientes;
    }
    
}