package com.uca.capas.modelo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Clase13Controller {
	
	@RequestMapping("/index13")
	public String index13() {
		return "clases/clase13/index";
	}

}
