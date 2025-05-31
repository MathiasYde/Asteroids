package com.mathiasyde.Datamodels;

import javafx.scene.canvas.GraphicsContext;

public abstract class Component {
    public Entity entity;
    private boolean enabled = true;

    public boolean enabled() { return enabled; }
    public void enabled(boolean enabled) {
        this.enabled = enabled;

        if (enabled) {
            enable();
        } else {
            disable();
        }
    }

    protected <T extends Component> T require(Class<T> type) {
        assert entity.has(type);
        return entity.get(type);
    }

    private void enable() {}
    private void disable() {}
    public void awake() {}
    public void start() {}
    public void update() {}
    public void lateUpdate() {}
    public void render(RenderLayer layer) {}
    public void debug(RenderLayer layer) {}
    public void gui(RenderLayer layer) {}
}
