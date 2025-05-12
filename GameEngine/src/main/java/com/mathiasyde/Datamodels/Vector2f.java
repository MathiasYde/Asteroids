package com.mathiasyde.Datamodels;

public class Vector2f {
    public static final Vector2f ZERO = new Vector2f(0, 0);
    public static final Vector2f UP = new Vector2f(0f, -1f);
    public static final Vector2f DOWN = new Vector2f(0f, 1f);
    public static final Vector2f LEFT = new Vector2f(-1f, 0f);
    public static final Vector2f RIGHT = new Vector2f(1f, 0f);

    private float x;
    private float y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2f(double x, double y) {
        this.x = (float) x;
        this.y = (float) y;
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public Vector2f normalized() {
        float length = length();
        if (length == 0) { return ZERO; }
        return mul(1f / length);
    }

    public Vector2f add(Vector2f vector) {
        return new Vector2f(x + vector.x, y + vector.y);
    }

    public Vector2f sub(Vector2f vector) {
        return new Vector2f(x - vector.x, y - vector.y);
    }

    public Vector2f mul(float scalar) {
        return new Vector2f(x * scalar, y * scalar);
    }

    public Vector2f div(float scalar) {
        return new Vector2f(x / scalar, y / scalar);
    }

    public Vector2f negate() {
        return new Vector2f(-x, -y);
    }

    public Vector2f horizontal() {
        return new Vector2f(x, 0);
    }

    public Vector2f vertical() {
        return new Vector2f(0, y);
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("Vector2f(%f, %f)", x, y);
    }
}
