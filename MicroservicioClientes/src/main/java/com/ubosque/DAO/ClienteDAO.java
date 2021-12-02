package com.ubosque.DAO;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.ubosque.DTO.Clientes;

public class ClienteDAO {

	MongoClient mongoClient;
	MongoDatabase database;
	MongoCollection<Document> clientes;
	ConnectionString connectionString;
	MongoClientSettings settings;

	public ClienteDAO() {
		try {
			connectionString = new ConnectionString(
					"mongodb+srv://admin:admin@cluster0.ykb33.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
			settings = MongoClientSettings.builder().applyConnectionString(connectionString).build();
			mongoClient = MongoClients.create(settings);
			database = mongoClient.getDatabase("db_clientes");

			clientes = database.getCollection("clientes");
			System.out.println("Conexión exitosa");
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public void cerrar() {
		try {
			mongoClient.close();

			System.out.println("Conexión cerrada");
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public ArrayList<Clientes> listarClientes() {
		ArrayList<Clientes> ListaClientes = new ArrayList<Clientes>();
		try {
			ArrayList<Document> docusuarios = clientes.find().into(new ArrayList<>());
			for (Document documento : docusuarios) {
				Clientes cliente = new Clientes();
				cliente.setCedulaCliente(documento.getInteger("cedulaCliente"));
				cliente.setEmailCliente(documento.getString("correoCliente"));
				cliente.setNombreCliente(documento.getString("nombreCliente"));
				cliente.setDireccionCliente(documento.getString("direccion"));
				cliente.setTelefonoCliente(documento.getString("telefono"));

				ListaClientes.add(cliente);
			}
			System.out.print("Listando Usuarios");
			this.cerrar();
		} catch (Exception e) {
			e.getMessage();
			this.cerrar();
		}
		return ListaClientes;
	}

	public boolean crear(Clientes cliente) {
		try {

			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("cedulaCliente", cliente.getCedulaCliente());

			ArrayList<Document> docusuarios = clientes.find(whereQuery).into(new ArrayList<>());

			if (docusuarios.size() == 0 || docusuarios.isEmpty()) {
				Document documento = new Document("_id", new ObjectId());
				documento.append("cedulaCliente", cliente.getCedulaCliente());
				documento.append("correoCliente", cliente.getEmailCliente());
				documento.append("nombreCliente", cliente.getNombreCliente());
				documento.append("direccion", cliente.getDireccionCliente());
				documento.append("telefono", cliente.getTelefonoCliente());

				clientes.insertOne(documento);
				System.out.println("Cliente Creado");
			} else {
				System.out.println("El Cliente ya existe");
				System.out.println("Cliente No Creado");
				this.cerrar();
				return false;
			}

			this.cerrar();

		} catch (Exception e) {
			e.getMessage();
			this.cerrar();
			return false;
		}
		return true;
	}

	public boolean delete(int cedula_cliente) {
		try {
			DeleteResult estado = clientes.deleteOne(new Document("cedulaCliente", cedula_cliente));

			if (estado != null) {
				System.out.println("Cliente Borrado");
			} else {
				System.out.println("Cliente No Borrado");
			}

			this.cerrar();
			System.out.print("Cliente Creado");
		} catch (Exception e) {
			e.getMessage();
			this.cerrar();
			return false;
		}
		return true;
	}

	public boolean update(Clientes cliente, int cedula) {
		try {
			Document documento = new Document();
			documento.append("cedulaCliente", cliente.getCedulaCliente());
			documento.append("correoCliente", cliente.getEmailCliente());
			documento.append("nombreCliente", cliente.getNombreCliente());
			documento.append("direccion", cliente.getDireccionCliente());
			documento.append("telefono", cliente.getTelefonoCliente());

			Document filtro = new Document("cedulaCliente", cedula);
			UpdateResult estado = clientes.replaceOne(filtro, documento);

			this.cerrar();
			System.out.println("Cliente actualizadp!");
		} catch (Exception e) {
			e.getMessage();
			this.cerrar();
			return false;
		}
		return true;
	}

	public ArrayList<Clientes> listarUsuario(int cedula_cliente) {
		ArrayList<Clientes> ListaClientes = new ArrayList<Clientes>();
		try {

			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("cedulaCliente", cedula_cliente);

			ArrayList<Document> docusuarios = clientes.find(whereQuery).into(new ArrayList<>());
			for (Document documento : docusuarios) {
				Clientes usuario = new Clientes();
				usuario.setCedulaCliente(documento.getInteger("cedulaCliente"));
				usuario.setEmailCliente(documento.getString("correoCliente"));
				usuario.setNombreCliente(documento.getString("nombreCliente"));
				usuario.setDireccionCliente(documento.getString("direccion"));
				usuario.setTelefonoCliente(documento.getString("telefono"));

				ListaClientes.add(usuario);
			}
			System.out.print("Listando Clientes");
			this.cerrar();
		} catch (Exception e) {
			e.getMessage();
			this.cerrar();
		}
		return ListaClientes;
	}
}