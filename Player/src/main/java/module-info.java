module Player {
    requires GameEngine;
    requires javafx.graphics;

    exports com.mathiasyde.Player;

    provides com.mathiasyde.Datamodels.GameModule
            with com.mathiasyde.Player.PlayerModule;
}