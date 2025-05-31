package com.mathiasyde.Saucer;

import com.mathiasyde.Components.LineRender;
import com.mathiasyde.Components.Transform;
import com.mathiasyde.Datamodels.Component;
import com.mathiasyde.Datamodels.Entity;
import com.mathiasyde.Datamodels.Vector2f;
import com.mathiasyde.GameEngine.GameEngine;
import com.mathiasyde.GameEngine.Time;

import java.util.List;

public class Saucer extends Component {
    private static final List<Vector2f> SAUCER_POINTS = List.of(
            new Vector2f(-1.0,  0.0),  // Left front
            new Vector2f(-0.7,  0.3),  // Left top
            new Vector2f( 0.7,  0.3),  // Right top
            new Vector2f( 1.0,  0.0),  // Right front
            new Vector2f( 0.7, -0.3),  // Right bottom
            new Vector2f(-0.7, -0.3),  // Left bottom
            new Vector2f(-1.0,  0.0)  // Back to Left front
    );

    private float speed = 100f;
    private float direction = 0f;
    private float timer = 0.5f;

    private LineRender line;
    private Transform transform;

    @Override
    public void awake() {
        transform = require(Transform.class);
        line = require(LineRender.class);
    }

    @Override
    public void start() {
        line.points = SAUCER_POINTS;
    }

    @Override
    public void update() {
        timer -= Time.deltaTime;
        if (timer <= 0) {
            timer = 4.0f;
            direction = (float)(Math.random() * Math.PI * 2);
        }

        Entity player = GameEngine.cache("player");

        if (player.enabled() == true) {
            Vector2f position = player.get(Transform.class).position();
            float angle = position.sub(transform.position()).angle();
            entity.dispatch("shoot", angle);
        }

        float delta = (float) (Time.deltaTime * speed);
        transform.translate(Vector2f.fromAngle(direction).mul(delta));
    }
}
