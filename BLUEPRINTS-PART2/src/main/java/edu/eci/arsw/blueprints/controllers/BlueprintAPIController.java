/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hcadavid
 */
@RestController
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {

    @Autowired
    BlueprintsServices blueprintsServices;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> manejadorGetRecursoBlueprint() {
        Set<Blueprint> data = null;
        try{
            data = blueprintsServices.getAllBlueprints();
        }catch (Exception ex){
            return new ResponseEntity<>("ERROR 500",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
    }

    @RequestMapping(method = RequestMethod.GET, path = "{author}")
    public ResponseEntity<?> manejadorGetRecursoBlueprintAutor(@PathVariable("author") String authorName) {
        Set<Blueprint> data = null;
        try {
            data = blueprintsServices.getBlueprintsByAuthor(authorName);
        } catch (BlueprintNotFoundException e) {
            return new ResponseEntity<>("ERROR 404",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
    }

    @RequestMapping(method = RequestMethod.GET, path = "{author}/{bpname}")
    public ResponseEntity<?> manejadorGetRecursoBlueprintAutor(@PathVariable("author") String authorName,
                                                               @PathVariable("bpname") String bpName) {
        Blueprint data = null;
        try {
            data = blueprintsServices.getBlueprint(authorName,bpName);
        } catch (BlueprintNotFoundException e) {
            return new ResponseEntity<>("ERROR 404",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> newBlueprint(@RequestBody Blueprint b ) {
        try {
            blueprintsServices.addNewBlueprint(b);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (BlueprintPersistenceException e) {
            return new ResponseEntity<>("ERROR 403",HttpStatus.FORBIDDEN);
        }
    }
    
    @RequestMapping(method = RequestMethod.PUT, path = "{author}/{bpname}")
    public ResponseEntity<?> putBlueprint(
        @RequestBody Blueprint b,
        @PathVariable("author") String authorName,
        @PathVariable("bpname") String bpName)
    {
        try {
            blueprintsServices.update(b, authorName, bpName);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (BlueprintNotFoundException e) {
            return new ResponseEntity<>("ERROR 403",HttpStatus.FORBIDDEN);
        }
    }
    

}
