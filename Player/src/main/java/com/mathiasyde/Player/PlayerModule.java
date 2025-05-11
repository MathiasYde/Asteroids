package com.mathiasyde.Player;

import com.mathiasyde.Components.Transform;
import com.mathiasyde.Datamodels.Entity;
import com.mathiasyde.Datamodels.GameModule;
import com.mathiasyde.GameEngine.GameEngine;

public class PlayerModule extends GameModule {
    @Override
    public void start() {
        Entity player = new Entity("Player");
        player.put(new Transform());

        GameEngine.root.spawn(player);
    }
}
