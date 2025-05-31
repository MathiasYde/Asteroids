package com.mathiasyde.GameEngine;

import com.mathiasyde.Datamodels.RenderLayer;

import java.util.List;

public class RenderLayers {
    public static final RenderLayer game = new RenderLayer("game");
    public static final RenderLayer ui = new RenderLayer("ui");
    public static final RenderLayer debug = new RenderLayer("debug");

    public static final List<RenderLayer> ALL = List.of(game, ui, debug);
}
