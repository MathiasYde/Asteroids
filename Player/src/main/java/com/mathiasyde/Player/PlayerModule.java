package com.mathiasyde.Player;

import com.mathiasyde.Components.Transform;
import com.mathiasyde.Datamodels.Entity;
import com.mathiasyde.Datamodels.GameModule;
import com.mathiasyde.Datamodels.Vector2f;
import com.mathiasyde.GameEngine.GameEngine;

public class PlayerModule extends GameModule {
    @Override
    public void start() {
        Entity player = new Entity("Player");

        player.put("transform", (Transform transform) -> {
            transform.translate(new Vector2f(400f, 300f));
            transform.scale(20f);
            transform.rotate(0f);
        });
        player.put("line");
        player.put("spaceship");
        player.put("weapon");

        GameEngine.root.spawn(player);
    }
}
