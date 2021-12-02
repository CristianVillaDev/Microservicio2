package com.ubosque.MicroservicioClientes;

import java.util.ArrayList;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ubosque.DAO.ClienteDAO;
import com.ubosque.DTO.Clientes;

@RestController
@CrossOrigin(origins = "*")
@ComponentScan(basePackages = { "com.ubosque.DAO" })
@RequestMapping("/clientes")
public class ClientesController {

	@PostMapping("guardar")
	public boolean crear(@RequestBody Clientes cliente) {
		boolean estado = false;
		ClienteDAO usuarioDAO = new ClienteDAO();
		estado = (boolean) usuarioDAO.crear(cliente);
		return estado;
	}

	@RequestMapping("listar")
	public ArrayList<Clientes> listar() {
		ClienteDAO usuarioDAO = new ClienteDAO();
		return usuarioDAO.listarClientes();
	}
	
	@RequestMapping("prueba")
	public String prueba(){
		return "probando";
	}

	@RequestMapping("listar/{cedula_cliente}")
	public ArrayList<Clientes> listar(@PathVariable("cedula_cliente") int cedula_cliente) {
		ClienteDAO usuarioDAO = new ClienteDAO();
		return usuarioDAO.listarUsuario(cedula_cliente);
	}

	@PutMapping("actualizar/{cedula_cliente}")
	public boolean actualizar(@PathVariable("cedula_cliente") int cedula_cliente, @RequestBody Clientes cliente) {
		boolean estado = false;
		ClienteDAO usuarioDAO = new ClienteDAO();
		estado = usuarioDAO.update(cliente, cedula_cliente);
		return estado;
	}

	@DeleteMapping("eliminar/{cedula_cliente}")
	public boolean delete(@PathVariable("cedula_cliente") int cedula_cliente) {
		boolean estado = false;
		ClienteDAO usuarioDAO = new ClienteDAO();
		estado = usuarioDAO.delete(cedula_cliente);
		return estado;
	}

}
