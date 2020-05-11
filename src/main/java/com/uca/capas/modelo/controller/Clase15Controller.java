package com.uca.capas.modelo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.uca.capas.modelo.domain.Cliente;
import com.uca.capas.modelo.service.ClienteService;

@Controller
public class Clase15Controller {
	
	@Autowired
	ClienteService clienteService;
	
	@RequestMapping("/index15")
	public String index13() {
		return "clases/clase15/index";
	}
	
	@RequestMapping("/guardar15")
	public ModelAndView guardarCliente(@ModelAttribute Cliente cliente) {
		ModelAndView mav = new ModelAndView();
		//Mando a llamar al servicio encargado de guardar a la entidad
		clienteService.save(cliente);
		mav.setViewName("clases/clase15/index");
		mav.addObject("resultado", 1);
		return mav;
	}

}
