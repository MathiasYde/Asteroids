package com.mathiasyde.Spaceship;

import com.mathiasyde.Components.LineRender;
import com.mathiasyde.Components.Transform;
import com.mathiasyde.Datamodels.Component;
import com.mathiasyde.Datamodels.Vector2f;
import com.mathiasyde.GameEngine.GameEngine;
import com.mathiasyde.GameEngine.Time;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Spaceship extends Component {
    private static final List<Vector2f> SPACESHIP_POINTS = new ArrayList<>() {
        {
            add(new Vector2f(1.0f, 0.0f));
            add(new Vector2f(-1.0f, -1.0f));
            add(new Vector2f(-0.5f, 0.0f));
            add(new Vector2f(-1.0f, 1.0f));
        }
    };

    public float velocity = 0.0f;
    public float maxVelocity = 200.0f;
    public float acceleration = 80.0f; // how fast the spaceship speeds up
    public float deceleration = 0.2f; // how fast the spaceship slows down without braking

    private LineRender line;
    private Transform transform;

    @Override
    public void awake() {
        line = require(LineRender.class);
        transform = require(Transform.class);
    }

    @Override
    public void start() {
        line.points = SPACESHIP_POINTS;
        line.color = entity.name().equals("Player") ? Color.GREEN : Color.RED;
    }

    @Override
    public void update() {
        Vector2f directionals = Vector2f.ZERO;
        if (GameEngine.keyDown(KeyCode.W)) { directionals = directionals.add(Vector2f.UP); }
        if (GameEngine.keyDown(KeyCode.S)) { directionals = directionals.add(Vector2f.DOWN); }
        if (GameEngine.keyDown(KeyCode.A)) { directionals = directionals.add(Vector2f.LEFT); }
        if (GameEngine.keyDown(KeyCode.D)) { directionals = directionals.add(Vector2f.RIGHT); }

        float rotate = directionals.x();
        float thrust = -directionals.y();

        transform.rotate(rotate * Time.deltaTime * 4f);

        float vdt = thrust * acceleration * Time.deltaTime;
        velocity = Math.clamp(velocity + vdt, 0f, maxVelocity);

        Vector2f fdt = transform.forward().mul(velocity * Time.deltaTime);
        transform.translate(fdt);

        if (GameEngine.keyDown(KeyCode.SPACE)) {
            entity.dispatch("shoot");
        }
    }
}
