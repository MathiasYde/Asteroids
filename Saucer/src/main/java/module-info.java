module Saucer {
    exports com.mathiasyde.Saucer;

    requires GameEngine;
    requires javafx.graphics;

    provides com.mathiasyde.Datamodels.GameModule
            with com.mathiasyde.Saucer.SaucerModule;
}