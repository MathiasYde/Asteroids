package com.mathiasyde.Weapon;

import com.mathiasyde.Components.Collider;
import com.mathiasyde.Components.Spawner;
import com.mathiasyde.Components.TimedSpawner;
import com.mathiasyde.Components.Transform;
import com.mathiasyde.Datamodels.Entity;
import com.mathiasyde.Datamodels.GameModule;
import com.mathiasyde.GameEngine.GameEngine;

public class WeaponModule extends GameModule {
    @Override
    public void register() {
        GameEngine.components.register("weapon", Weapon.class);
        GameEngine.components.register("bullet", Bullet.class);
    }

    @Override
    public void awake() {
        Entity spawner = GameEngine.root.spawn("BulletSpawner");
        spawner.put(new Spawner(WeaponModule::bullet));
        GameEngine.cache("bullet_spawner", spawner);
    }

    private static Entity bullet(Spawner spawner) {
        Entity entity = new Entity(String.format("Bullet.%03d", spawner.total() ));

        entity.put(new Transform());
        entity.put("bullet");
        entity.put("line");
        entity.put("collider", (Collider collider) -> {
            collider.radius = 4f;
        });

        return entity;
    }
}
