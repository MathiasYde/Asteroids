package com.mathiasyde.GameEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class Registry<T> {
    private final Map<String, Class<? extends T>> registry = new HashMap<>();

    public void register(String identifier, Class<? extends T> clazz) {
        assert identifier != null : "Identifier cannot be null";
        assert clazz != null : "Class cannot be null";
        assert !identifier.isEmpty() : "Identifier cannot be empty";

        GameEngine.LOGGER.debug("[Registry::register] Registering {} with identifier '{}'", clazz.getSimpleName(), identifier);

        registry.put(identifier, clazz);
    }

    public Class<? extends T> get(String identifier) {
        assert identifier != null : "Identifier cannot be null";
        assert !identifier.isEmpty() : "Identifier cannot be empty";

        return registry.get(identifier);
    }

    public <U extends T> U create(String identifier) {
        assert identifier != null : "Identifier cannot be null";
        assert !identifier.isEmpty() : "Identifier cannot be empty";

        Class<? extends T> clazz = registry.get(identifier);
        assert clazz != null : "No class registered with identifier: " + identifier;

        try {
            return (U) clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
    }

    public void forEach(BiConsumer<String, Class<? extends T>> consumer) {
        registry.forEach(consumer);
    }
}
