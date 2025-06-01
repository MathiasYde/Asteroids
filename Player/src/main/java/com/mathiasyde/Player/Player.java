package com.mathiasyde.Player;

import com.mathiasyde.Components.Collider;
import com.mathiasyde.Datamodels.Component;
import com.mathiasyde.Datamodels.Entity;
import com.mathiasyde.GameEngine.GameEngine;
import com.mathiasyde.GameEngine.Time;

public class Player extends Component {
    public int lives = 3;
    public int score = 0;

    public Integer getLives() { return lives; }
    public Integer getScore() { return score; }

    private Collider collider;

    @Override
    public void awake() {
        collider = require(Collider.class);
    }

    @Override
    public void start() {
    }

    public void score(Integer points) {
        score += points;
    }

    public void shot() {
        lives -= 1;
        entity.enabled(false);

        Entity saucers = GameEngine.cache("saucer_spawner");
        saucers.children().clear();
        saucers.enabled(false);

        if (lives <= 0) {
            System.exit(0);
        }

        Time.after(4.0f, () -> {
            saucers.enabled(true);
            entity.enabled(true);
        });
    }
}
