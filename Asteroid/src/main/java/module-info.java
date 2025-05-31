module Asteroid {
    requires GameEngine;

    provides com.mathiasyde.Datamodels.GameModule
            with com.mathiasyde.Asteroid.AsteroidModule;
}