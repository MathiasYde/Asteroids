module GameEngine {
    exports com.mathiasyde.GameEngine;
    exports com.mathiasyde.Datamodels;
    exports com.mathiasyde.Components;

    requires javafx.graphics;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;

    uses com.mathiasyde.Datamodels.GameModule;
}