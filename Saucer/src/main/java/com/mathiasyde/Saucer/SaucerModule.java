package com.mathiasyde.Saucer;

import com.mathiasyde.Components.Collider;
import com.mathiasyde.Components.Spawner;
import com.mathiasyde.Components.TimedSpawner;
import com.mathiasyde.Components.Transform;
import com.mathiasyde.Datamodels.Entity;
import com.mathiasyde.Datamodels.GameModule;
import com.mathiasyde.Datamodels.Vector2f;
import com.mathiasyde.GameEngine.GameEngine;

import com.mathiasyde.Saucer.Saucer;

public class SaucerModule extends GameModule {
    @Override
    public void register() {
        GameEngine.components.register("saucer", Saucer.class);
    }

    @Override
    public void start() {
        Entity spawner = GameEngine.root.spawn("SaucerSpawner");
        spawner.put(new TimedSpawner(SaucerModule::saucer, 6.0f));

        GameEngine.cache("saucer_spawner", spawner);
    }

    private static Entity saucer(Spawner spawner) {
        Entity entity = new Entity(String.format("Saucer.%03d", spawner.total()));

        entity.put("transform", (Transform transform) -> {
            transform.translate(new Vector2f(400, 300));
            float angle = (float)(Math.random() * 2 * Math.PI);
            Vector2f direction = Vector2f.fromAngle(angle);
            transform.translate(direction.mul(400f));

            transform.scale(20f);
        });
        entity.put("line");
        entity.put("saucer");
        entity.put("weapon");
        entity.put("collider", (Collider collider) -> {
            collider.radius = 24f;
        });

        return entity;
    }
}
