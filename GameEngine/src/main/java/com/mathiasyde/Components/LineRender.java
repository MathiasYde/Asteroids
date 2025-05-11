package com.mathiasyde.Components;

import com.mathiasyde.Datamodels.Component;
import com.mathiasyde.Datamodels.Vector2f;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class LineRender extends Component {
    public List<Vector2f> points = new ArrayList<>();

    private Transform transform;

    @Override
    public void start() {
        transform = require(Transform.class);
    }

    @Override
    public void render(GraphicsContext gc) {
        if (points.isEmpty()) {
            return;
        }

        gc.save();
        gc.translate(transform.position().x(), transform.position().y());
        gc.scale(transform.scale().x(), transform.scale().y());
        gc.rotate(transform.rotation());

        gc.setLineWidth(0.2f);

        int n = points.size() + 1;
        double[] x = new double[n];
        double[] y = new double[n];

        for (int i = 0; i < n; i++) {
            Vector2f point = points.get(i % (n - 1));

            x[i] = point.x();
            y[i] = point.y();
        }

        gc.strokePolyline(x, y, n);
        gc.restore();
    }
}
