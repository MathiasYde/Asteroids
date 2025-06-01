package com.mathiasyde.Score;

import com.mathiasyde.Datamodels.Component;
import com.mathiasyde.Datamodels.Entity;
import com.mathiasyde.Datamodels.RenderLayer;
import com.mathiasyde.GameEngine.GameEngine;

public class ScoreDisplay extends Component {
    private Entity player;

    @Override
    public void start() {
        player = GameEngine.cache("player");
    }

    @Override
    public void gui(RenderLayer layer) {
        int lives = (int)player.dispatch("player", "getLives");
        int score = (int)player.dispatch("player", "getScore");

        layer.graphics.fillText("Lives: " + lives, 10.0, 24.0);
        layer.graphics.fillText("Score: " + score, 10.0, 32.0);
    }
}
