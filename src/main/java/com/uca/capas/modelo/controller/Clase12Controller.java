package com.uca.capas.modelo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Clase12Controller {
	
	@RequestMapping("/index12")
	public String index12() {
		return "clases/clase12/index";
	}

}
