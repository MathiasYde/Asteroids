module Player {
    requires GameEngine;
    requires Weapon;
    requires Spaceship;

    provides com.mathiasyde.Datamodels.GameModule
            with com.mathiasyde.Player.PlayerModule;
}