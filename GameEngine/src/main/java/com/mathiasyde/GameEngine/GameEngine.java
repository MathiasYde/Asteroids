package com.mathiasyde.GameEngine;

import com.mathiasyde.Components.LineRender;
import com.mathiasyde.Components.Spaceship;
import com.mathiasyde.Components.Transform;
import com.mathiasyde.Datamodels.Entity;
import com.mathiasyde.Datamodels.GameModule;
import com.mathiasyde.Datamodels.Vector2f;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.canvas.Canvas;

import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import java.util.ServiceLoader;

public class GameEngine extends Application {
    protected final static Logger LOGGER = LogManager.getLogger(GameEngine.class);

    public static final Entity root = new Entity("Root");

    public static void main(String[] args) {
        Configurator.setLevel(LOGGER, Level.DEBUG);
        Arguments.parse(args);

        LOGGER.info("System information:");
        LOGGER.info("> java: {} ({})", System.getProperty("java.vendor"), System.getProperty("java.version"));
        LOGGER.info("> os: {} ({} {})", System.getProperty("os.name"), System.getProperty("os.version"), System.getProperty("os.arch"));
        LOGGER.info("> directory: {}", System.getProperty("user.dir"));

        launch(GameEngine.class);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Asteroids");

        Canvas canvas = new Canvas(800, 600);
        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);

        ServiceLoader<GameModule> loader = ServiceLoader.load(GameModule.class);
        loader.forEach(module -> LOGGER.debug("Module loaded: {}", module.getClass().getName()));

        GameEngine.root.put(new Transform(new Vector2f(200f, 200f), new Vector2f(20f, 20f), 45f));
        GameEngine.root.put(new Spaceship());
        GameEngine.root.put(new LineRender());

        GameEngine.root.traverse(entity -> entity.each(component -> component.start()));

        GraphicsContext gc = canvas.getGraphicsContext2D();
        GameEngine.root.traverse(entity -> entity.each(component -> component.render(gc)));

        stage.show();
    }
}