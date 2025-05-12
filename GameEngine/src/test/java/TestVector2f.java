import com.mathiasyde.Datamodels.Vector2f;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestVector2f {
    private static final float EPSILON = 0.001f;

    @Test
    public void testGetters() {
        Vector2f vector = new Vector2f(1f, 2f);
        assertEquals(1f, vector.x(), EPSILON);
        assertEquals(2f, vector.y(), EPSILON);
    }

    @Test
    public void testLength() {
        Vector2f vector = new Vector2f(3f, 4f);
        assertEquals(5f, vector.length(), EPSILON);
    }

    @Test
    public void testNormalized() {
        Vector2f vector = new Vector2f(3f, 4f);
        Vector2f normalized = vector.normalized();
        assertEquals(0.6f, normalized.x(), EPSILON);
        assertEquals(0.8f, normalized.y(), EPSILON);
    }
}
