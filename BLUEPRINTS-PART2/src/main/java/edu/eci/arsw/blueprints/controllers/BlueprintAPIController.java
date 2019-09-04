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
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
        Set<Blueprint> data = blueprintsServices.getAllBlueprints();
        return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
    }

    @RequestMapping(method = RequestMethod.GET, path = "{author}")
    public ResponseEntity<?> manejadorGetRecursoBlueprintAutor(@PathVariable("author") String authorName) {
        Set<Blueprint> data = null;
        try {
            data = blueprintsServices.getBlueprintsByAuthor(authorName);
        } catch (BlueprintNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
    }

}
