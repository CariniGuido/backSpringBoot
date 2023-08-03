/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestion.clientes.controller;

import com.gestion.clientes.model.Cliente;
import com.gestion.clientes.repository.ClienteRepository;
import com.gestion.clientes.exception.ResourceNotFoundException;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@CrossOrigin(origins = "http://localhost:3000")         
@RestController
@RequestMapping("/api/v1")
public class ClienteController {
    @Autowired
    private ClienteRepository clienteRepository;
   
    @GetMapping("/clientes")
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }
  
    @PostMapping("/clientes")
    public Cliente guardarCliente(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }
     
    @GetMapping("clientes/{id}")
    public ResponseEntity<Cliente> listarClientePorId(@PathVariable long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El cliente con ese id no existe: " + id));
        return ResponseEntity.ok(cliente);
    }
     
    @PutMapping("/clientes/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable long id, @RequestBody Cliente clienteRequest) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El cliente con ese id no existe: " + id));

        cliente.setNombre(clienteRequest.getNombre());
        cliente.setApellido(clienteRequest.getApellido());
        cliente.setEmail(clienteRequest.getEmail());

        final Cliente clienteActualizadoEnBD = clienteRepository.save(cliente);
        return ResponseEntity.ok(clienteActualizadoEnBD);
    }
    
    
    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarCliente(@PathVariable long id) {
        Map<String, Boolean> response = new HashMap<>();
        try {
            Cliente cliente = clienteRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("El cliente con ese id no existe: " + id));

            clienteRepository.delete(cliente);
            response.put("eliminado", true);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException ex) {
            response.put("eliminado", false);
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            response.put("eliminado", false);
            return ResponseEntity.status(500).build();
        }
    }

}