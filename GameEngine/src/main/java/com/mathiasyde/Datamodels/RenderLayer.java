package com.mathiasyde.Datamodels;

import javafx.scene.canvas.GraphicsContext;


import javafx.scene.canvas.Canvas;

public class RenderLayer {
    private String name;
    private boolean enabled = true;

    public RenderLayer(String name) {
        this.name = name;
    }

    public GraphicsContext graphics;
    public Canvas canvas;
}
