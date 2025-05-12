import com.mathiasyde.Components.Transform;
import com.mathiasyde.Datamodels.Vector2f;

import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.RepeatedTest;

public class TestTransform {
    private static final float EPSILON = 0.001f;

    @Test
    public void transformDefaultValues() {
        Transform transform = new Transform();

        // Test default values
        assertEquals(0f, transform.position().x(), 0.001f);
        assertEquals(0f, transform.position().y(), 0.001f);
        assertEquals(1f, transform.scale().x(), 0.001f);
        assertEquals(1f, transform.scale().y(), 0.001f);
        assertEquals(0f, transform.rotation(), 0.001f);
    }

    @Test
    public void transformTranslation() {
        Transform transform = new Transform();
        Vector2f translation = new Vector2f(5f, 3f);
        transform.translate(translation);

        // Test position after translation
        assertEquals(5f, transform.position().x(), EPSILON);
        assertEquals(3f, transform.position().y(), EPSILON);
    }

    @RepeatedTest(360)
    public void transformForwardVector(RepetitionInfo info) {
        Transform transform = new Transform();

        float rotation = (float) (info.getCurrentRepetition() * Math.PI / 180);
        transform.rotate(rotation);

        // Test rotation
        assertEquals(rotation, transform.rotation(), EPSILON);

        // Test forward vector after rotation
        Vector2f forward = transform.forward();
        assertEquals(Math.cos(rotation), forward.x(), EPSILON);
        assertEquals(Math.sin(rotation), forward.y(), EPSILON);
    }
}
