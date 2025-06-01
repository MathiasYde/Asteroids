package com.mathiasyde.Weapon;

import com.mathiasyde.Components.Collider;
import com.mathiasyde.Components.LineRender;
import com.mathiasyde.Components.Transform;
import com.mathiasyde.Datamodels.Component;
import com.mathiasyde.Datamodels.Entity;
import com.mathiasyde.Datamodels.Vector2f;
import com.mathiasyde.GameEngine.GameEngine;
import com.mathiasyde.GameEngine.Time;

import java.util.List;

public class Bullet extends Component {
    private static final List<Vector2f> BULLET_POINTS = List.of(
            new Vector2f(0, 0),
            new Vector2f(0.5f, 0.0f)
    );

    public float lifetime = 4.0f;
    public Entity owner = null;

    private LineRender line;
    private Transform transform;
    private Collider collider;

    @Override
    public void awake() {
        line = require(LineRender.class);
        transform = require(Transform.class);
        collider = require(Collider.class);
    }

    @Override
    public void start() {
        line.points = BULLET_POINTS;

        collider.onCollision = (entity -> {
            if (entity == owner) return;


            if (entity.has("saucer")) {
                entity.destroy();
            }

            if (entity.has("player")) {
                Entity player = GameEngine.cache("player");
                player.dispatch("shot");
            }

            if (entity.has("asteroid")) {
                Entity player = GameEngine.cache("player");
                player.dispatch("score", 100);
                entity.destroy();
                this.entity.destroy();
            }
        });
    }

    @Override
    public void update() {
        this.lifetime -= Time.deltaTime;
        if (this.lifetime <= 0) {
            entity.destroy();
            return;
        }

        transform.translate(transform.forward().mul(600f * Time.deltaTime));
    }
}
