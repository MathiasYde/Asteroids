package com.mathiasyde.Score;

import com.mathiasyde.Datamodels.Entity;
import com.mathiasyde.Datamodels.GameModule;
import com.mathiasyde.GameEngine.GameEngine;

public class ScoreModule extends GameModule {
    @Override
    public void start() {
        Entity score = new Entity("ScoreDisplay");
        score.put(new ScoreDisplay());
        GameEngine.root.spawn(score);
        GameEngine.cache("score", score);
    }
}
