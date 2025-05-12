import com.mathiasyde.Components.Transform;
import com.mathiasyde.Datamodels.Component;
import com.mathiasyde.Datamodels.Entity;
import com.mathiasyde.Datamodels.Vector2f;
import com.mathiasyde.GameEngine.GameEngine;
import org.junit.jupiter.api.Test;

import com.mathiasyde.GameEngine.Registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestComponentRegistry {
    private static final float EPSILON = 0.001f;

    @Test
    public void registryComponentRegistration() {
        Registry<Component> registry = new Registry<>();

        registry.register("transform", Transform.class);
        assertEquals(Transform.class, registry.get("transform"));

        Transform instance = registry.create("transform");
        assertNotNull(instance);
        assertEquals(Transform.class, instance.getClass());
    }

    @Test
    public void putComponentOnEntityFromRegistry() {
        GameEngine.components.register("transform", Transform.class);

        Entity entity = new Entity("test");
        entity.put("transform");

        Transform transform = entity.get(Transform.class);
        assertNotNull(transform);
    }

    @Test
    public void putComponentOnEntityFromRegistryWithParameters() {
        GameEngine.components.register("transform", Transform.class);

        Entity entity = new Entity("test");
        entity.put("transform", (Transform transform) -> {
            transform.translate(new Vector2f(1, 2));
            transform.scale(new Vector2f(4f, -5f));
            transform.rotate(45f);
        });

        Transform transform = entity.get(Transform.class);
        assertNotNull(transform);

        assertEquals(entity, transform.entity);

        assertEquals(1, transform.position().x(), EPSILON);
        assertEquals(2, transform.position().y(), EPSILON);
        assertEquals(4f, transform.scale().x(), EPSILON);
        assertEquals(-5f, transform.scale().y(), EPSILON);
        assertEquals(45f, transform.rotation(), EPSILON);
    }
}
