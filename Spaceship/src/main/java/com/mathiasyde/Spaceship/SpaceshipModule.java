package com.mathiasyde.Spaceship;

import com.mathiasyde.Datamodels.GameModule;
import com.mathiasyde.GameEngine.GameEngine;

public class SpaceshipModule extends GameModule {
    @Override
    public void register() {
        GameEngine.components.register("spaceship", Spaceship.class);
    }
}
