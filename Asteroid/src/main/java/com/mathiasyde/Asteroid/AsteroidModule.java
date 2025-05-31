package com.mathiasyde.Asteroid;

import com.mathiasyde.Components.Collider;
import com.mathiasyde.Components.Spawner;
import com.mathiasyde.Components.TimedSpawner;
import com.mathiasyde.Components.Transform;
import com.mathiasyde.Datamodels.Entity;
import com.mathiasyde.Datamodels.GameModule;
import com.mathiasyde.Datamodels.Vector2f;
import com.mathiasyde.GameEngine.GameEngine;

public class AsteroidModule extends GameModule {
    @Override
    public void register() {
        GameEngine.components.register("asteroid", Asteroid.class);
    }

    @Override
    public void awake() {
        Entity spawner = GameEngine.root.spawn("AsteroidSpawner");
        spawner.put(new TimedSpawner(AsteroidModule::asteroid));
        GameEngine.cache("asteroid_spawner", spawner);
    }

    @Override
    public void start() {
//        Entity spawner = GameEngine.cache("asteroid_spawner");
//        spawner.get(TimedSpawner.class)
    }

    private static Entity asteroid(Spawner spawner) {
        Entity entity = new Entity(String.format("Asteroid.%03d", spawner.total() ));

        entity.put(new Asteroid());
        entity.put("transform", (Transform transform) -> {
            float angle = (float)(Math.random() * Math.TAU);
            Vector2f screen = Vector2f.fromAngle(angle);

            Vector2f world = screen.mul(600f).add(new Vector2f(400f, 300f));
            float wiggle = (float)((Math.random() - 0.5f) * Math.PI * 0.25f);

            transform.translate(world);
            transform.rotate(angle + (float)(Math.PI) + wiggle);
        });
        entity.put("collider", (Collider collider) -> {
            collider.radius = 24f;
        });
        entity.put("line");

        return entity;
    }
}
