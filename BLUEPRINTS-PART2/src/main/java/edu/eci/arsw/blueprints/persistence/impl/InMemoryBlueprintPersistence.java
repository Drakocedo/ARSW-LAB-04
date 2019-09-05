/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

/**
 * @author hcadavid
 */
@Component("InMemoryBlueprintPersistence")
public class InMemoryBlueprintPersistence implements BlueprintsPersistence {

    private final Map<Tuple<String, String>, Blueprint> blueprints = new HashMap<>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts = new Point[]{new Point(140, 140), new Point(115, 115)};
        Point[] pts1 = new Point[]{new Point(1, 1), new Point(78, 487), new Point(12, 59), new Point(146, 156)};
        Point[] pts2 = new Point[]{new Point(2, 2), new Point(51, 0), new Point(14, 32), new Point(16, 15)};
        Point[] pts3 = new Point[]{new Point(7, 4), new Point(71, 47), new Point(146, 156)};
        Blueprint bp = new Blueprint("pepe", "la-estatuta", pts);
        Blueprint bp1 = new Blueprint("ronaldo", "la-pintura", pts1);
        Blueprint bp2 = new Blueprint("pepe", "la-escultura", pts2);
        Blueprint bp3 = new Blueprint("leonardo", "lisa", pts3);

        blueprints.put(new Tuple<>(bp.getAuthor(), bp.getName()), bp);
        blueprints.put(new Tuple<>(bp1.getAuthor(), bp1.getName()), bp1);
        blueprints.put(new Tuple<>(bp2.getAuthor(), bp2.getName()), bp2);
        blueprints.put(new Tuple<>(bp3.getAuthor(), bp3.getName()), bp3);
    }

    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(), bp.getName()))) {
            throw new BlueprintPersistenceException("The given blueprint already exists: " + bp);
        } else {
            blueprints.put(new Tuple<>(bp.getAuthor(), bp.getName()), bp);
        }
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        Blueprint bp = blueprints.get(new Tuple<>(author, bprintname));
        if (bp == null)
            throw new BlueprintNotFoundException("Blueprint not found");
        return bp;
    }

    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> res = new HashSet<>();
        Set<Tuple<String, String>> keys = blueprints.keySet();
        for (Tuple<String, String> tuple : keys) {
            if (tuple.getElem1().equals(author)) {
                res.add(blueprints.get(tuple));
            }
        }
        if (res.isEmpty())
            throw new BlueprintNotFoundException("Author not found");
        return res;
    }

    @Override
    public Set<Blueprint> getAllBlueprints() {
        Set<Blueprint> res = new HashSet<Blueprint>(blueprints.values());
        return res;
    }

    @Override
    public void update(Blueprint b, String author, String name) throws BlueprintNotFoundException {
        Blueprint antes = getBlueprint(author, name);
        Tuple<String, String> antesTuple = new Tuple<>(author,name);
        Tuple<String, String> nuevaTuple = new Tuple<>(b.getAuthor(),b.getName());
        blueprints.remove(antesTuple);
        blueprints.put(nuevaTuple, b);

    }


}
