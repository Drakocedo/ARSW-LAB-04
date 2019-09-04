package edu.eci.arsw.blueprints.filter.impl;

import edu.eci.arsw.blueprints.filter.BlueprintFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.List;

@Component("subsampling")
public class SubsamplingBlueprintFilter implements BlueprintFilter {

    @Override
    public Blueprint filter(Blueprint blueprint) {
        List<Point> points = blueprint.getPoints();
        Point newPoints[] = new Point[points.size()/2 + points.size() % 2];
        int index = 0;
        for (int i = 0; i < points.size() ; i += 2){
            newPoints[index++] = points.get(i);
        }
        return new Blueprint(blueprint.getAuthor(),blueprint.getName(),newPoints);
    }

}
