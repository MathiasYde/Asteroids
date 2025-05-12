import com.mathiasyde.Components.Transform;
import com.mathiasyde.Datamodels.Component;
import com.mathiasyde.Datamodels.Entity;

import com.mathiasyde.Datamodels.Vector2f;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestEntity {
    @Test
    public void entityParentChildRelation() {
        Entity root = new Entity("TestRoot");
        Entity child = new Entity("TestChild");
        Entity grandchild = new Entity("TestGrandchild");

        root.spawn(child);
        child.spawn(grandchild);

        assertEquals(grandchild.parent().parent(), root);
    }

    @Test
    public void entityHasComponent() {
        Entity root = new Entity("TestRoot");
        Component transform = new Transform();
        root.put(transform);

        assertTrue(root.has(Transform.class));
        assertEquals(root.get(Transform.class), transform);
        assertEquals(root, transform.entity);
    }

    @Test
    public void entityRemoveComponent() {
        Entity root = new Entity("TestRoot");
        Component transform = new Transform();
        root.put(transform);

        assertTrue(root.has(Transform.class));
        assertEquals(root.get(Transform.class), transform);
        assertEquals(root, transform.entity);

        root.remove(Transform.class);
        assertFalse(root.has(Transform.class));
        assertNotEquals(root, transform.entity);
        assertNull(transform.entity);
    }

    @Test
    public void entityEnsureComponent() {
        Entity root = new Entity("TestRoot");
        Transform transform = root.ensure(Transform.class, Transform::new);

        assertNotEquals(null, transform);
        assertNotEquals(null, transform.entity);
        assertEquals(root.get(Transform.class), transform);
        assertEquals(root, transform.entity);
    }

    @Test
    public void entityReduce() {
        Entity parent = new Entity("TestRoot");
        Entity child = new Entity("TestChild");
        Entity grandchild = new Entity("TestGrandchild");

        parent.put(new Transform(new Vector2f(2, 0)));
        child.put(new Transform(new Vector2f(1, -1)));
        grandchild.put(new Transform(new Vector2f(-4, 6)));

        parent.spawn(child);
        child.spawn(grandchild);

        Vector2f position = grandchild.reduce(Transform.class, Vector2f::new, (Transform transform, Vector2f value) -> value.add(transform.position()));
        assertEquals(position.x(), 2 + 1 - 4);
        assertEquals(position.y(), 0 - 1 + 6);
    }
}
