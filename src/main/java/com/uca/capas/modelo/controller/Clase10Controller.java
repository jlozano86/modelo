package com.uca.capas.modelo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Clase10Controller {
	
	@RequestMapping("/index10")
	public String index() {
		/* No es necesario poner la extension .html a index, ya que el metodo
		 * templateResolver.setSuffix(".html") sirve para asumir dicha extension.
		 * Como solo vamos a redirigir a la pagina index.html no es necesario un 
		 * objeto ModelAndView, al devolver un tipo de dato String, Spring automaticamente
		 * asume que es una vista la que se quiere devolver.
		 */
		return "clases/clase10/index";
	}
	
	@RequestMapping("/parametros1")
	public ModelAndView parametros1(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String usuario = request.getParameter("usuario");
		String password = request.getParameter("password");
		
		mav.addObject("user", usuario);
		mav.addObject("pass", password);
		mav.setViewName("clases/clase10/resultado");
		return mav;
		
	}
	
	@RequestMapping("/parametros2")
	public ModelAndView parametros2(@RequestParam String usuario, @RequestParam String password) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("user", usuario);
		mav.addObject("pass", password);
		mav.setViewName("clases/clase10/resultado");
		return mav;
	}
	
	@RequestMapping("/parametros3")
	public ModelAndView parametros3(@RequestParam(value="user") String usuario, 
			@RequestParam(value="pass") String password) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("user", usuario);
		mav.addObject("pass", password);
		mav.setViewName("clases/clase10/resultado");
		return mav;
	}

}
