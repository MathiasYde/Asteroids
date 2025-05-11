package com.mathiasyde.Components;

import com.mathiasyde.Datamodels.Component;
import com.mathiasyde.Datamodels.Vector2f;

import java.util.ArrayList;

public class Spaceship extends Component {

    private LineRender line;

    @Override
    public void start() {
        line = require(LineRender.class);
        line.points = new ArrayList<>() {
            {
                add(new Vector2f(1.0f, 0.0f));
                add(new Vector2f(-1.0f, -1.0f));
                add(new Vector2f(-0.5f, 0.0f));
                add(new Vector2f(-1.0f, 1.0f));
            }
        };
    }
}
