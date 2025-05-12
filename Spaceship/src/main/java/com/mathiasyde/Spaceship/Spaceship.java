package com.mathiasyde.Spaceship;

import com.mathiasyde.Components.LineRender;
import com.mathiasyde.Components.Transform;
import com.mathiasyde.Datamodels.Component;
import com.mathiasyde.Datamodels.Vector2f;
import com.mathiasyde.GameEngine.Time;
import com.mathiasyde.Weapon.Weapon;
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
    private Weapon weapon;

    @Override
    public void awake() {
        line = require(LineRender.class);
        transform = require(Transform.class);
        weapon = require(Weapon.class);
    }

    @Override
    public void start() {
        line.points = SPACESHIP_POINTS;
        line.color = entity.name().equals("Player") ? Color.GREEN : Color.RED;
    }

    @Override
    public void update() {
        transform.rotate(1.0f * Time.deltaTime);

        float thrust = 1.0f;

        float vdt = thrust * acceleration * Time.deltaTime;
        velocity = Math.clamp(velocity + vdt, 0f, maxVelocity);

        Vector2f fdt = transform.forward().mul(velocity * Time.deltaTime);
        transform.translate(fdt);

        System.out.println(velocity);

////        Vector2f move = new Vector2f(0.0f, 0.0f);
//        float rotate = (float) (Math.random() - 0.5f) * 0.1f;
//        float thrust = Math.random() < 0.2f ? 1.0f : 0.0f;
//
//        if (Math.random() < 0.4f) {
//            weapon.shoot();
//        }
//
//        System.out.println("---------");
//        System.out.println("rotate = " + rotate);
//        System.out.println("thrust = " + thrust);
//        transform.rotate(rotate * Time.deltaTime * 40f);
//
//        System.out.println("transform.forward() = " + transform.forward());
//        Vector2f tdt = transform.forward().mul(thrust * 100f * Time.deltaTime);
//
//        System.out.println("tdt = " + tdt);
//        transform.translate(tdt);
    }
}
