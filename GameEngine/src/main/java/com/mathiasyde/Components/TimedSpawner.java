package com.mathiasyde.Components;

import com.mathiasyde.Datamodels.Entity;
import com.mathiasyde.GameEngine.Time;

import java.util.function.Function;

public class TimedSpawner extends Spawner {
    public float time = 2.0f;
    public float cooldown = 2.0f;

    public TimedSpawner(Function<Spawner, Entity> prototype) {
        super(prototype);
    }

    @Override
    public void update() {
        time -= Time.deltaTime;
        if (time <= 0) {
            time = cooldown;
            spawn();
        }
    }
}
