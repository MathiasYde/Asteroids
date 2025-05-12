package com.mathiasyde.Components;

import com.mathiasyde.Datamodels.Component;
import com.mathiasyde.Datamodels.Vector2f;

public class Transform extends Component {
    private Vector2f position;
    private Vector2f scale;
    private float rotation;

    public Transform() {
        position = new Vector2f(0f, 0f);
        scale = new Vector2f(1f, 1f);
        rotation = 0f;
    }

    public Transform(Vector2f position) {
        this.position = position;
        this.scale = new Vector2f(1f, 1f);
        this.rotation = 0f;
    }

    public Transform(Vector2f position, Vector2f scale, float rotation) {
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
    }

    public void translate(Vector2f translation) {
        position = position.add(translation);
    }

    public Vector2f position() {
        return position;
    }

    public Vector2f scale() {
        return scale;
    }

    public void scale(Vector2f scale) {
        this.scale = scale;
    }

    public void scale(float scale) {
        this.scale = this.scale.mul(scale);
    }

    public float rotation() {
        return rotation;
    }

    public void rotate(float radians) {
        rotation += radians;
    }

    public Vector2f forward() {
        return new Vector2f(Math.cos(rotation), Math.sin(rotation));
    }
}
