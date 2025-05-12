module Spaceship {
    exports com.mathiasyde.Spaceship;
    requires GameEngine;
    requires javafx.graphics;
    requires Weapon;

    provides com.mathiasyde.Datamodels.GameModule
            with com.mathiasyde.Spaceship.SpaceshipModule;
}