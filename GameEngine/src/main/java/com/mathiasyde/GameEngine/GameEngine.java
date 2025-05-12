package com.mathiasyde.GameEngine;

import com.mathiasyde.Components.LineRender;
import com.mathiasyde.Components.Transform;
import com.mathiasyde.Datamodels.Component;
import com.mathiasyde.Datamodels.Entity;
import com.mathiasyde.Datamodels.GameModule;
import javafx.animation.AnimationTimer;
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

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

public class GameEngine extends Application {
    public final static Logger LOGGER = LogManager.getLogger(GameEngine.class);

    public static final Entity root = new Entity("Root");

    public static final Map<Class<? extends GameModule>, GameModule> modules = new HashMap<>();
    public static Registry<Component> components = new Registry<>();

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
        loader.forEach(module -> modules.put(module.getClass(), module));

        components.register("transform", Transform.class);
        components.register("line", LineRender.class);

        System.out.println("modules: " + modules.values());
        modules.forEach((__, module) -> module.register());
        modules.forEach((__, module) -> module.start());

        GameEngine.root.traverse(entity -> entity.each(component -> component.awake()));
        GameEngine.root.traverse(entity -> entity.each(component -> component.start()));

        scene.setOnKeyPressed(event -> {
            LOGGER.debug("Key pressed: {}", event.getCode());
        });

        scene.setOnKeyReleased(event -> {
            LOGGER.debug("Key released: {}", event.getCode());
        });

        GraphicsContext gc = canvas.getGraphicsContext2D();

        AnimationTimer eventLoop = new AnimationTimer() {
            private static final long FRAME_INTERVAL = 1_000_000_000 / 60; // 60 FPS
            private long lastTime = System.nanoTime();

            @Override
            public void handle(long now) {
                // TODO(mayde): the framerate sometimes drops to 30 fps
                if (now - lastTime < FRAME_INTERVAL) { return; }

                Time.deltaTime = (now - lastTime) / 1_000_000_000.0f;
                lastTime = now;

                stage.setTitle(String.format("Asteroids - FPS: %.2f", 1.0 / Time.deltaTime));
                GameEngine.root.traverse(entity -> entity.each(Component::update));
                GameEngine.root.traverse(entity -> entity.each(Component::lateUpdate));

                gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
                GameEngine.root.traverse(entity -> entity.each(component -> component.render(gc)));
            }
        };

        eventLoop.start();
        stage.show();
    }
}