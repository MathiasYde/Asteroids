package com.mathiasyde.Asteroid;

import com.mathiasyde.Components.Collider;
import com.mathiasyde.Components.LineRender;
import com.mathiasyde.Components.Transform;
import com.mathiasyde.Datamodels.Component;
import com.mathiasyde.Datamodels.Vector2f;
import com.mathiasyde.GameEngine.Time;

import java.util.List;

record AsteroidShape(float size, List<Vector2f> points) {}

public class Asteroid extends Component {
    private static final List<AsteroidShape> ASTEROID_SHAPES = List.of(
        new AsteroidShape(42f, List.of(
                new Vector2f(0, 40),
                new Vector2f(25, 35),
                new Vector2f(40, 15),
                new Vector2f(30, -10),
                new Vector2f(40, -30),
                new Vector2f(10, -40),
                new Vector2f(-10, -35),
                new Vector2f(-25, -40),
                new Vector2f(-40, -15),
                new Vector2f(-30, 5),
                new Vector2f(-40, 20),
                new Vector2f(-15, 35)
        )),
        new AsteroidShape(28f, List.of(
                new Vector2f(0, 25),
                new Vector2f(20, 15),
                new Vector2f(25, -5),
                new Vector2f(15, -20),
                new Vector2f(0, -25),
                new Vector2f(-20, -15),
                new Vector2f(-25, 0),
                new Vector2f(-15, 20)
        )),
            new AsteroidShape(24, List.of(
                    new Vector2f(0, 15),
                    new Vector2f(12, 5),
                    new Vector2f(10, -10),
                    new Vector2f(0, -15),
                    new Vector2f(-12, -5),
                    new Vector2f(-10, 10)
            ))
    );

    public float speed = 100f;

    private LineRender line;
    private Collider collider;
    private Transform transform;

    @Override
    public void awake() {
        transform = require(Transform.class);
        line = require(LineRender.class);
        collider = require(Collider.class);
    }

    @Override
    public void start() {
        AsteroidShape shape = ASTEROID_SHAPES.get((int) (Math.random() * ASTEROID_SHAPES.size()));
        line.points = shape.points();
        collider.radius = shape.size();
        speed = (float) (Math.random() * 100 + 50);
    }

    @Override
    public void update() {
        transform.translate(transform.forward().mul(speed * Time.deltaTime));
    }
}
