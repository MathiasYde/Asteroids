module Score {
    requires GameEngine;
    requires javafx.graphics;

    exports com.mathiasyde.Score;

    provides com.mathiasyde.Datamodels.GameModule
            with com.mathiasyde.Score.ScoreModule;
}