package com.uca.capas.modelo.controller;

import java.util.ArrayList;
import java.util.List;

import com.uca.capas.modelo.domain.Cliente;
import com.uca.capas.modelo.dto.TableDTO;
import com.uca.capas.modelo.service.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Clase19Controller {

    @Autowired
    ClienteService clienteService;

    @RequestMapping("/index19")
    public String index18(){
        return "clases/clase19/index";
    }

    @RequestMapping("/clientestable")
    public String clientesTable(){
        return "clases/clase19/clientes";
    }

    @RequestMapping("/editarcliente")
	public ModelAndView buscar(@RequestParam Integer id) {
		ModelAndView mav = new ModelAndView();
		Cliente c = clienteService.findOne(id);
		mav.addObject("cliente", c);
		mav.setViewName("clases/clase19/cliente");
		return mav;
	}
    
    @RequestMapping("/clase19/verclientes")
    public @ResponseBody List<Cliente> verClientes(){
		/**
		 * Traigo a todos los clientes ordenados por el objeto Sort
		 */
		Sort sort = Sort.by(Direction.ASC, "ccliente");
        return clienteService.findAll(sort);
    }

    @RequestMapping("/clase19/buscarclientes")
    public @ResponseBody List<Cliente> buscarClientes(@RequestParam(required = false) String nombres, 
        @RequestParam(required = false) String apellidos){
        return clienteService.findClientesNombreApel(nombres, apellidos);
    }

    @RequestMapping("/cargarclientes")
    public @ResponseBody TableDTO cargarUsuario(@RequestParam Integer draw,
			@RequestParam Integer start, @RequestParam Integer length, 
			@RequestParam(value="search[value]", required = false) String search) {
		
		Page<Cliente> clientes = clienteService.findAll(PageRequest.of(start/length, length, Sort.by(Direction.ASC, "ccliente")));
		
		List<String[]> data = new ArrayList<>();
		
		for(Cliente u : clientes) {
			data.add(new String[] {u.getCcliente().toString(), u.getSnombres(), 
				u.getSapellidos(), u.getFechaDelegate(), 
				u.getBactivo() == true ? "Activo" : "Inactivo"});
		}
		
		TableDTO dto = new TableDTO();
		dto.setData(data);
		dto.setDraw(draw);
		dto.setRecordsFiltered(clienteService.countAll().intValue());
		dto.setRecordsTotal(clienteService.countAll().intValue());	
		
		return dto;
    }
    
    @RequestMapping("/guardar19")
	public ModelAndView guardarCliente(@ModelAttribute Cliente cliente) {
		ModelAndView mav = new ModelAndView();
		//Mando a llamar al servicio encargado de guardar a la entidad
		clienteService.save(cliente);
		mav.setViewName("clases/clase19/clientes");
		mav.addObject("resultado", 1);
		return mav;
	}
}