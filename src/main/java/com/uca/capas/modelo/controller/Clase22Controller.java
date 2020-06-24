package com.uca.capas.modelo.controller;

import com.uca.capas.modelo.domain.Cliente;
import com.uca.capas.modelo.service.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Clase22Controller {

    @Autowired
    ClienteService clienteService;

    @RequestMapping("/index22")
    public String index22(){
        return "clases/clase22/index";
    }

    @RequestMapping("/nuevoclientejdbc1")
    public ModelAndView indexNuevo1(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("cliente", new Cliente());
        mav.setViewName("clases/clase22/cliente1");
        return mav;
    }
    
    @RequestMapping("/nuevoclientejdbc2")
    public ModelAndView indexNuevo2() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("cliente", new Cliente());
        mav.setViewName("clases/clase22/cliente2");
        return mav;
    }

    @RequestMapping("/buscarcliente22")
    public ModelAndView buscar15(@RequestParam Integer codigo) {
        ModelAndView mav = new ModelAndView();
        Cliente c = clienteService.findOne(codigo);
        mav.addObject("cliente", c);
        mav.setViewName("clases/clase22/cliente3");
        return mav;
    }

    @RequestMapping("/guardarjdbc1")
    public ModelAndView guardarJdbc1(@ModelAttribute Cliente c){
        ModelAndView mav = new ModelAndView();
        clienteService.insertClienteNoAutoId(c);
        mav.addObject("resultado1", 1);
        mav.setViewName("clases/clase22/index");
        return mav;
    }

    @RequestMapping("/guardarjdbc2")
    public ModelAndView guardarJdbc2(@ModelAttribute Cliente c) {
        ModelAndView mav = new ModelAndView();
        clienteService.insertClienteNoAutoId(c);
        mav.addObject("resultado2", 1);
        mav.setViewName("clases/clase22/index");
        return mav;
    }

    @RequestMapping("/guardarjdbc3")
    public ModelAndView guardarJdbc3(@ModelAttribute Cliente c) {
        ModelAndView mav = new ModelAndView();
        clienteService.updateCliente(c);
        mav.addObject("resultado3", 1);
        mav.setViewName("clases/clase22/index");
        return mav;
    }

    @RequestMapping("/ejecutarprocedimientojdbc")
    public ModelAndView ejecutarProcedimiento(@RequestParam Integer cliente, @RequestParam Boolean estado) {
        ModelAndView mav = new ModelAndView();
        Integer resultado;
        resultado = clienteService.cambiarEstadoVehiculos(cliente, estado);
        mav.addObject("resultado4", resultado > 0 ? 1 : 0);
        mav.setViewName("clases/clase22/index");
        return mav;
    }
    
}