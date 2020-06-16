package com.uca.capas.modelo.controller;

import java.text.ParseException;
import java.util.List;

import com.uca.capas.modelo.dto.ClienteDTO;
import com.uca.capas.modelo.service.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Clase21Controller {

    @Autowired
    ClienteService clienteService;

    @RequestMapping("/index21")
    public String index21(){
        return "clases/clase21/index";
    }

    @RequestMapping("/getclientesestado")
    public @ResponseBody List<ClienteDTO> getClientesEstado(@RequestParam Integer estado) {
        List<ClienteDTO> clientes = clienteService.getClienteEstado(estado);

        return clientes;
    }

    @RequestMapping("/getclientesfecha")
    public @ResponseBody List<ClienteDTO> getClientesFecha(@RequestParam String fecha) throws ParseException {
        List<ClienteDTO> clientes = clienteService.getClienteFecha(fecha);

        return clientes;
    }

    @RequestMapping("/getclientesfechaEstado")
    public @ResponseBody List<ClienteDTO> getClientesFechaEstado(@RequestParam String fecha,
            @RequestParam Integer estado) throws ParseException {
        List<ClienteDTO> clientes = clienteService.getClienteFechaEstado(fecha, estado);

        return clientes;
    }

    @RequestMapping("/getclientesMarca")
    public @ResponseBody List<ClienteDTO> getClientesMarca(@RequestParam String marca) {
        List<ClienteDTO> clientes = clienteService.getClienteMarca(marca);

        return clientes;
    }
    
}