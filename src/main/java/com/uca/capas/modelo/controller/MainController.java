package com.uca.capas.modelo.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
	
	@RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView();
		//Lista quemada para efectos de ejemplo...
		List<String> lista = new ArrayList<>();
		lista.add("Rojo");
		lista.add("Verde");
		lista.add("Azul");
		
		mav.addObject("hora", Calendar.getInstance().getTime().toString());
		mav.addObject("colores", lista);
		mav.setViewName("commons/index");
		return mav;
	}

}
