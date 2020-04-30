package com.uca.capas.modelo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.uca.capas.modelo.dao.ClienteDAO;
import com.uca.capas.modelo.domain.Cliente;

@Controller
public class ClienteController {
	
	@Autowired
	ClienteDAO clienteDao;
	
	@RequestMapping("/buscarcliente")
	public ModelAndView buscar(@RequestParam Integer codigo) {
		ModelAndView mav = new ModelAndView();
		Cliente c = clienteDao.findOne(codigo);
		mav.addObject("cliente", c);
		mav.setViewName("clases/clase13/cliente");
		return mav;
	}

}
