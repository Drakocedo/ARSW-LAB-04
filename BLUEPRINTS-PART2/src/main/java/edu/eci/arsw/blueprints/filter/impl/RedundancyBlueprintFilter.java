package edu.eci.arsw.blueprints.filter.impl;

import edu.eci.arsw.blueprints.filter.BlueprintFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Component("redundancy")
public class RedundancyBlueprintFilter implements BlueprintFilter {

    @Override
    public Blueprint filter(Blueprint blueprint) {
        Set<Point> pointSet = new HashSet<>(blueprint.getPoints());
        Point points[] = new Point[pointSet.size()];
        int index = 0;
        for (Point p : pointSet){
            points[index++] = p;
        }
        return new Blueprint(blueprint.getAuthor(),blueprint.getName(),points);
    }

}
