package com.mathiasyde.GameEngine;

import com.mathiasyde.Components.Collider;
import com.mathiasyde.Components.LineRender;
import com.mathiasyde.Components.Transform;
import com.mathiasyde.Datamodels.Component;
import com.mathiasyde.Datamodels.Entity;
import com.mathiasyde.Datamodels.GameModule;
import com.mathiasyde.Datamodels.Vector2f;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;

import javafx.scene.input.KeyCode;
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
    private static final Map<String, Entity> cache = new HashMap<>();

    public static final Map<Class<? extends GameModule>, GameModule> modules = new HashMap<>();
    public static Registry<Component> components = new Registry<>();

    public static final Map<KeyCode, Boolean> keys = new HashMap<>();

    public static void main(String[] args) {
        Configurator.setLevel(LOGGER, Level.INFO);
        Arguments.parse(args);

        LOGGER.info("System information:");
        LOGGER.info("> java: {} ({})", System.getProperty("java.vendor"), System.getProperty("java.version"));
        LOGGER.info("> os: {} ({} {})", System.getProperty("os.name"), System.getProperty("os.version"), System.getProperty("os.arch"));
        LOGGER.info("> directory: {}", System.getProperty("user.dir"));

        launch(GameEngine.class);
    }

    public static void cache(String identifier, Entity entity) {
        assert identifier != null : "Identifier cannot be null";
        assert entity != null : "Entity cannot be null";
        assert !identifier.isEmpty() : "Identifier cannot be empty";

        LOGGER.debug("[GameEngine::cache(String, Entity)] Caching entity: {} with identifier: {}", entity.name(), identifier);
        cache.put(identifier, entity);
    }

    public static Entity cache(String identifier) {
        assert identifier != null : "Identifier cannot be null";
        assert !identifier.isEmpty() : "Identifier cannot be empty";

        LOGGER.debug("[GameEngine::cache(String)] Getting entity with identifier: {}", identifier);
        return cache.get(identifier);
    }

    public static boolean keyDown(KeyCode keyCode) {
        return GameEngine.keys.computeIfAbsent(keyCode, __ -> false);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Asteroids");
        Scene scene = Render.setup(stage);

        ServiceLoader<GameModule> loader = ServiceLoader.load(GameModule.class);
        loader.forEach(module -> modules.put(module.getClass(), module));

        components.register("transform", Transform.class);
        components.register("line", LineRender.class);
        components.register("collider", Collider.class);

        System.out.println("modules: " + modules.values());
        modules.forEach((__, module) -> module.register());
        modules.forEach((__, module) -> module.awake());
        modules.forEach((__, module) -> module.start());

        GameEngine.root.traverse(entity -> entity.each(component -> component.awake()));
        GameEngine.root.traverse(entity -> entity.each(component -> component.start()));

        scene.setOnKeyPressed(event -> {
            keys.put(event.getCode(), true);
        });

        scene.setOnKeyReleased(event -> {
            keys.put(event.getCode(), false);
        });

        AnimationTimer eventLoop = new AnimationTimer() {
            private static final long FRAME_INTERVAL = 1_000_000_000 / 60; // 60 FPS
            private long lastTime = System.nanoTime();

            @Override
            public void handle(long now) {
                // TODO(mayde): the framerate sometimes drops to 30 fps
//                if (now - lastTime < FRAME_INTERVAL) { return; }

                Time.deltaTime = (now - lastTime) / 1_000_000_000.0f;
                lastTime = now;

                Time.update();

                stage.setTitle(String.format("Asteroids - FPS: %.2f", 1.0 / Time.deltaTime));
                GameEngine.root.traverse(entity -> entity.each(Component::update));
                GameEngine.root.traverse(entity -> entity.each(Component::lateUpdate));

                Render.clear();
                GameEngine.root.traverse(entity -> entity.each(component -> component.render(RenderLayers.game)));
                GameEngine.root.traverse(entity -> entity.each(component ->  component.gui(RenderLayers.ui)));
                GameEngine.root.traverse(entity -> entity.each(component -> component.debug(RenderLayers.debug)));
            }
        };

        eventLoop.start();
        stage.show();
    }
}