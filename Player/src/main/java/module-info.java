module Player {
    requires GameEngine;

    provides com.mathiasyde.Datamodels.GameModule
            with com.mathiasyde.Player.PlayerModule;
}