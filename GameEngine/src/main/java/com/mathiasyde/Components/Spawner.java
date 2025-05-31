package com.mathiasyde.Components;

import com.mathiasyde.Datamodels.Entity;
import com.mathiasyde.Datamodels.Component;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class Spawner extends Component {
    private Function<Spawner, Entity> prototype;
    private long total = 0;

    public Spawner(Function<Spawner, Entity> prototype) {
        this.prototype = prototype;
    }

    public Entity spawn() {
        total += 1;
        return entity.spawn(prototype.apply(this));
    }

    @Override
    public void lateUpdate() {
        // for now, destroy any disabled children
        for (Entity child : entity.children()) {
            if (child.enabled()) {
                continue;
            }

            child.destroy();
        }
    }

    public long total() {
        return total;
    }
}
