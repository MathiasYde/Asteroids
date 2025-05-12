package com.mathiasyde.Datamodels;

import com.mathiasyde.GameEngine.GameEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Entity {
    private final List<Entity> children = new ArrayList<>();
    private final Map<Class<? extends Component>, Component> components = new HashMap<>();

    private String name = "Entity";
    private Entity parent = null;

    public Entity(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    public Entity spawn(Entity child) {
        GameEngine.LOGGER.debug("[Entity::spawn] spawning entity: {}", child.name);

        this.children.add(child);
        child.parent = this;
        return child;
    }

    public void traverse(Consumer<Entity> consumer) {
        consumer.accept(this);
        children.forEach(child -> child.traverse(consumer));
    }

    public void each(Consumer<Component> consumer) {
        components.values().stream().filter(Component::enabled).forEach(consumer);
    }

    /// put component on this entity
    /// @param component the component instance to put onto this entity
    /// @return <T> the component instance that has been put onto this entity
    public <T extends Component> T put(Component component) {
        GameEngine.LOGGER.debug("[Entity::put(Component)] put {} on {}", component.getClass().getSimpleName(), this.name);

        component.entity = this;
        this.components.put(component.getClass(), component);
        return (T) component;
    }

    /// put component with identifier from registry on this entity
    /// @param identifier the component identifier to put on this entity
    public Component put(String identifier) {
        GameEngine.LOGGER.debug("[Entity::put(String)] put '{}' on {}", identifier, this.name);

        Class<? extends Component> clazz = GameEngine.components.get(identifier);
        if (clazz != null) {
            Component component = GameEngine.components.create(identifier);
            if (component != null) {
                component.entity = this;
                components.put(clazz, component);
                return component;
            }
        }

        GameEngine.LOGGER.warn("[Entity::put(String)] No component registered with identifier: {}", identifier);
        return null;
    }

    /// put component with identifier from registry on this entity,
    /// if the component is present, apply the consumer to the component
    /// @param identifier the component identifier to put on this entity
    /// @param consumer the consumer to apply to the component instance
    public <T extends Component> void put(String identifier, Consumer<T> consumer) {
        Component component = this.put(identifier);
        if (component != null) {
            consumer.accept((T)component);
        }
    }

    /// remove the component of type on this entity
    /// @param type the component type to remove from this entity
    /// @return <T> the component instance, if present, that has been removed from this entity
    public <T extends Component> T remove(Class<T> type) {
        GameEngine.LOGGER.debug("[Entity::remove(Class)] remove {} from {}", type.getSimpleName(), this.name);

        if (has(type)) {
            Component component = get(type);
            if (component != null) {
                component.entity = null;
                components.remove(type);
                return (T) component;
            }
        }

        return null;
    }

    /// get the component instance from this entity of component type
    /// @param type the component type to get from this entity
    /// @return <T> the component instance, if present, otherwise null
    public <T extends Component> T get(Class<T> type) {
        return (T) components.get(type);
    }

    /// test if this entity has a component instance of the component type
    /// @param type the component type to test if this entity has a component instance of
    /// @return boolean true if this entity has a component instance of the component type
    public <T extends Component> boolean has(Class<T> type) {
        return components.containsKey(type) && components.get(type) != null;
    }

    /// ensure that this entity has a component instance of the component type, if not, generate a new component instance to put into this component type
    /// @param type the component type to ensure this entity has
    /// @param supplier the supplier lambda to generate a new component instance if this entity does not have the component type
    public <T extends Component> T ensure(Class<T> type, Supplier<T> supplier) {
        if (has(type) == false) { put(supplier.get()); }
        return (T) components.get(type);
    }

    public Entity parent() {
        return parent;
    }
}
