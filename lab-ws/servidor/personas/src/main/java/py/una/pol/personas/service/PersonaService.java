/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package py.una.pol.personas.service;


import javax.ejb.Stateless;
import javax.inject.Inject;

import py.una.pol.personas.dao.PersonaDAO;
import py.una.pol.personas.model.Persona;
import py.una.pol.personas.model.Relacion;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class PersonaService {

    @Inject
    private Logger log;

    @Inject
    private PersonaDAO dao;
    
    public long asociar(Relacion r) throws Exception{
    	log.info("Asociando --> idPerosna:" + r.getId_persona() + " | idAsignatura" + r.getId_asignatura());
        long op;
    	try {
        	op = dao.asociar(r);
        }catch(Exception e) {
        	log.severe("Error Asociando --> idPerosna:" + r.getId_persona() + " | idAsignatura" + r.getId_asignatura());
        	throw e;
        }
    	if(op > 0) {
    		log.info("Relacion Exito --> idPerosna:" + r.getId_persona() + " | idAsignatura" + r.getId_asignatura());	
    	}else if( op == 0) {
    		log.info("Relacion ya existe --> idPerosna:" + r.getId_persona() + " | idAsignatura" + r.getId_asignatura());
    	}else {
    		log.info("Ocurrio un Error de Transaccion");
    	}
    	return op;
    }
    
    public long desasociar(Relacion r) throws Exception{
    	log.info("desAsociando --> idPerosna: " + r.getId_persona() + " | idAsignatura: " + r.getId_asignatura());
        long op;
    	try {
        	op = dao.desasociar(r);
        }catch(Exception e) {
        	log.severe("Error desAsociando --> idPerosna: " + r.getId_persona() + " | idAsignatura: " + r.getId_asignatura());
        	throw e;
        }
    	if(op > 0) {
    		log.info("desAsociando Exito --> idPerosna: " + r.getId_persona() + " | idAsignatura: " + r.getId_asignatura());	
    	}else if( op == 0) {
    		log.info("Relacion no existe --> idPerosna: " + r.getId_persona() + " | idAsignatura: " + r.getId_asignatura());
    	}else {
    		log.info("Ocurrio un Error de Transaccion");
    	}
    	return op;
    }

    public void crear(Persona p) throws Exception {
        log.info("Creando Persona: " + p.getNombre() + " " + p.getApellido());
        try {
        	dao.insertar(p);
        }catch(Exception e) {
        	log.severe("ERROR al crear persona: " + e.getLocalizedMessage() );
        	throw e;
        }
        log.info("Persona creada con éxito: " + p.getNombre() + " " + p.getApellido() );
    }
    
    public List<Persona> seleccionar() {
    	return dao.seleccionar();
    }
    
    public Persona seleccionarPorCedula(long cedula) {
    	return dao.seleccionarPorCedula(cedula);
    }
    
    public long borrar(long cedula) throws Exception {
    	return dao.borrar(cedula);
    }

	public List<String> listarAsignaturas(long cedula) {
		log.info("Listar Asignaturas | cedula: "+cedula);
        List<String> asignaturas;
		try {
        	asignaturas = dao.listarAsignaturas(cedula);
        }catch(Exception e) {
        	log.severe("ERROR al crear persona: " + e.getLocalizedMessage() );
        	throw e;
        }
        log.info("Lista asignatura creada | cedula: "+cedula);
		return asignaturas;
	}
}
