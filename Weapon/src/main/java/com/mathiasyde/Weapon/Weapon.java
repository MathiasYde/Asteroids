package com.mathiasyde.Weapon;

import com.mathiasyde.Components.Transform;
import com.mathiasyde.Datamodels.Component;
import com.mathiasyde.Datamodels.Entity;
import com.mathiasyde.Components.Spawner;
import com.mathiasyde.GameEngine.GameEngine;
import com.mathiasyde.GameEngine.Time;

public class Weapon extends Component {
    private Transform transform;
    private Spawner spawner;

    private float cooldown = 0.5f;
    private float timer = 0.0f;

    @Override
    public void awake() {
        transform = require(Transform.class);
    }

    @Override
    public void start() {
        spawner = GameEngine.cache("bullet_spawner").get(Spawner.class);
        if (entity.name().equals("player") == false) {
            this.cooldown = 4.0f;
            this.timer = 4.0f;
        }
    }

    @Override
    public void update() {
        timer -= Time.deltaTime;
    }

    public void shoot(Float angle) {
        if (timer <= 0) {
            timer = cooldown;

            Entity bullet = spawner.spawn();
            bullet.get(Bullet.class).owner = entity;
            bullet.get(Transform.class).inherit(transform);
            bullet.get(Transform.class).angle(angle);
        }
    }

    public void shoot() {
        shoot(transform.forward().angle());
    }
}
