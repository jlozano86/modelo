package com.uca.capas.modelo.controller;

import com.uca.capas.modelo.domain.Cliente;
import com.uca.capas.modelo.service.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Clase20Controller {

    @Autowired
    ClienteService clienteService;

    @RequestMapping("/index20")
    public String index20(){
        return "clases/clase20/index";
    }

    @RequestMapping("/ejecutarprocedimiento")
    public ModelAndView ejecutarProcedimiento(@RequestParam Integer cliente, @RequestParam Boolean estado){
        ModelAndView mav = new ModelAndView();
        Integer resultado;
        resultado = clienteService.actualizarClientes(cliente, estado);
        mav.addObject("resultado", resultado);
        mav.setViewName("clases/clase20/resultado");
        return mav;
    }

    @RequestMapping("/buscarcliente20")
    public ModelAndView buscar16(@RequestParam Integer codigo) {
        ModelAndView mav = new ModelAndView();
        Cliente c = clienteService.findOne(codigo);
        mav.addObject("cliente", c);
        mav.setViewName("clases/clase20/cliente");
        return mav;
    }
    
}