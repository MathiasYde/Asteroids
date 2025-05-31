package com.mathiasyde.Components;

import com.mathiasyde.Datamodels.Component;
import com.mathiasyde.Datamodels.Entity;
import com.mathiasyde.Datamodels.RenderLayer;
import com.mathiasyde.Datamodels.Vector2f;
import com.mathiasyde.GameEngine.GameEngine;

import java.util.function.Consumer;

public class Collider extends Component {
    public float radius = 1.0f;
    public Consumer<Entity> onCollision = (Entity __) -> {};

    public void debug(RenderLayer layer) {
        Transform transform = require(Transform.class);

        layer.graphics.strokeOval(
                transform.position().x() - radius,
                transform.position().y() - radius,
                radius * 2,
                radius * 2
        );
    }

    @Override
    public void lateUpdate() {
        Entity entity = this.entity;
        Transform transform = require(Transform.class);

        for (Entity other : GameEngine.root.successors()) {
            if (other == entity) continue;
            if (!other.has(Collider.class)) continue;

            Collider otherCollider = other.get(Collider.class);
            Transform otherTransform = other.get(Transform.class);

            float distance = Vector2f.distance(
                    transform.position(),
                    otherTransform.position()
            );
            if (distance < radius + otherCollider.radius) {
                onCollision.accept(other);
                otherCollider.onCollision.accept(entity);
            }
        }
    }
}
