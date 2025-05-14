package com.mathiasyde.Components;

import com.mathiasyde.Datamodels.Entity;
import com.mathiasyde.Datamodels.Component;

public class Spawner extends Component {
    private Class<Entity> prototype;

    public Spawner(Class<Entity> prototype) {
        this.prototype = prototype;
    }

    public Entity spawn() {
        try {
            return entity.spawn(prototype.getDeclaredConstructor().newInstance());
        } catch (Exception error) {}
        return null;
    }
}
