package com.mathiasyde.Saucer;

import com.mathiasyde.Datamodels.GameModule;
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
        spawner.put(new Spawner(SaucerModule::saucer))
    }

    private Entity saucer(Spawner spawner) {
        Entity entity = new Entity(String.format("Saucer.%03d", spawner.count()));

        entity.put("transform");
        entity.put("line");
        entity.put("spaceship");
        entity.put("weapon");

        return entity;
    }
}
