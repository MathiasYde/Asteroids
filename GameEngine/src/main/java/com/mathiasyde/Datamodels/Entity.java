package com.mathiasyde.Datamodels;

import com.mathiasyde.GameEngine.GameEngine;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Entity {
    private boolean enabled = true;
    public final List<Entity> children = new ArrayList<>();
    private final Map<Class<? extends Component>, Component> components = new HashMap<>();

    private final String name;
    private Entity parent = null;

    public Entity(String name) {
        this.name = name;
    }

    /// accumulate a value from component instances of component type found in parent traversal
    /// @param type the component type to use in reduction
    /// @param initial the initial value to use in reduction (usually a zero value)
    /// @param reducer the reducer function to apply to each component instance
    /// @return <U> the reduced value
    public <T extends Component, U> U reduce(Class<T> type, Supplier<U> initial, BiFunction<T, U, U> reducer) {
        U sum = initial.get();

        Entity current = this;
        while (current != null) {
            Component component = current.get(type);
            sum = reducer.apply(type.cast(component), sum);
            current = current.parent;
        }

        return sum;
    }

    public Object dispatch(String component, String event, Object... args) {
        GameEngine.LOGGER.debug("[Entity::dispatch] dispatching event: {} to component: {} of {}", event, component, this.name);
        Class<?>[] types = Arrays.stream(args)
                .map(Object::getClass)
                .toArray(Class<?>[]::new);

        Component receiver = components.get(GameEngine.components.get(component));
        if (receiver != null) {
            try {
                Method method = receiver.getClass().getMethod(event, types);
                return method.invoke(receiver, args);
            } catch (NoSuchMethodException error) {
            } catch (Exception error) {
                GameEngine.LOGGER.error("[Entity::dispatch] Error invoking method '{}' on component '{}' on entity '{}'", event, component, this.name, error);
            }
        }

        return null;
    }

    public void dispatch(String event, Object... args) {
        GameEngine.LOGGER.debug("[Entity::dispatch] dispatching event: {} to {}", event, this.name);
        Class<?>[] types = Arrays.stream(args)
                .map(Object::getClass)
                .toArray(Class<?>[]::new);

        each(component -> {
            try {
                Method method = component.getClass().getMethod(event, types);
                Object result = method.invoke(component, args);
            } catch (NoSuchMethodException error) {
                // getMethod is going to throw so many NoSuchMethodExceptions
                // so we can just ignore them since we only want to invoke methods that exist
            } catch (Exception error) {
                GameEngine.LOGGER.error("[Entity::dispatch] Error invoking method: {} on component: {} of entity: {}", event, component.getClass().getSimpleName(), this.name, error);
            }
        });
    }

    public String name() {
        return name;
    }

    public Entity spawn(Entity child) {
        GameEngine.LOGGER.debug("[Entity::spawn(Entity)] spawning entity: {}", child.name);

        this.children.add(child);
        child.parent = this;
        child.each(Component::awake);
        child.each(Component::start);
        return child;
    }

    public Entity spawn(String name) {
        return this.spawn(new Entity(name));
    }

    public void traverse(Consumer<Entity> consumer) {
        if (enabled == false) { return; }
        consumer.accept(this);
        try {
            children.forEach(child -> child.traverse(consumer));
        } catch (Exception error) {} // ignore ConcurrentModificationException
    }

    public void enabled(boolean enabled) {
        this.enabled = enabled;
        all(component -> component.enabled(enabled));
    }

    public boolean enabled() {
        return enabled;
    }

    /// apply a consumer to each enabled component of this entity
    /// @param consumer the consumer to apply to each enabled component of this entity
    public void each(Consumer<Component> consumer) {
        components.values().stream().filter(Component::enabled).forEach(consumer);
    }

    /// apply a consumer to all components of this entity, regardless of enabled state
    /// @param consumer the consumer to apply to all components of this entity
    public void all(Consumer<Component> consumer) {
        components.values().forEach(consumer);
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
                return type.cast(component);
            }
        }

        return null;
    }

    /// get the component instance from this entity of component type
    /// @param type the component type to get from this entity
    /// @return <T> the component instance, if present, otherwise null
    public <T extends Component> T get(Class<T> type) {
        return type.cast(components.get(type));
    }

    /// test if this entity has a component instance of the component type
    /// @param type the component type to test if this entity has a component instance of
    /// @return boolean true if this entity has a component instance of the component type
    public <T extends Component> boolean has(Class<T> type) {
        return components.containsKey(type) && components.get(type) != null;
    }

    public boolean has(String identifier) {
        return has(GameEngine.components.get(identifier));
    }

    /// ensure that this entity has a component instance of the component type, if not, generate a new component instance to put into this component type
    /// @param type the component type to ensure this entity has
    /// @param supplier the supplier lambda to generate a new component instance if this entity does not have the component type
    public <T extends Component> T ensure(Class<T> type, Supplier<T> supplier) {
        if (has(type) == false) { put(supplier.get()); }
        return type.cast(components.get(type));
    }

    public Entity parent() {
        return parent;
    }

    public Collection<Entity> children() {
        return children;
    }

    /// destroy this entity and all its children from existence
    public void destroy() {
        enabled(false);
        children.forEach(Entity::destroy);
        parent.abort(this);
    }

    /// abort the child from this parent entity
    /// @param child the child entity to abort from this parent entity
    /// @return the aborted child entity
    private Entity abort(Entity child) {
        GameEngine.LOGGER.debug("[Entity::abort] aborting child: {} from parent: {}", child.name, this.name);
        children.remove(child);
        child.parent = null;
        return child;
    }

    public Iterable<Entity> successors() {
        return new Iterable<Entity>() {
            @Override
            public Iterator<Entity> iterator() {
                return new Iterator<Entity>() {
                    private final Stack<Entity> stack = new Stack<>();

                    {
                        stack.push(Entity.this);
                    }

                    @Override
                    public boolean hasNext() {
                        return !stack.isEmpty();
                    }

                    @Override
                    public Entity next() {
                        Entity entity = stack.pop();
                        entity.children.forEach(stack::push);
                        return entity;
                    }
                };
            }
        };
    }
}
