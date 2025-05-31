package com.mathiasyde.GameEngine;

import com.mathiasyde.Datamodels.RenderLayer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class Render {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public static Scene setup(Stage stage) {
        StackPane root = new StackPane();

        for (RenderLayer layer : RenderLayers.ALL) {
            layer.canvas = new Canvas(WIDTH, HEIGHT);
            layer.graphics = layer.canvas.getGraphicsContext2D();

            root.getChildren().add(layer.canvas);
        }

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setScene(scene);
        return scene;
    }

    /// clear each render layer
    public static void clear() {
        each(layer -> {
            layer.graphics.clearRect(0, 0, layer.canvas.getWidth(), layer.canvas.getHeight());
        });
    }

    public static void each(Consumer<RenderLayer> consumer) {
        for (RenderLayer layer : RenderLayers.ALL) {
            consumer.accept(layer);
        }
    }
}
