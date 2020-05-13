package com.uca.capas.modelo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.uca.capas.modelo.domain.Cliente;
import com.uca.capas.modelo.service.ClienteService;

@Controller
public class Clase16Controller {
	
	@Autowired
	ClienteService clienteService;
	
	@RequestMapping("/index16")
	public String index13() {
		return "clases/clase16/index";
	}

}
