package com.mathiasyde.Components;

import com.mathiasyde.Datamodels.Component;
import com.mathiasyde.Datamodels.RenderLayer;
import com.mathiasyde.Datamodels.Vector2f;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class LineRender extends Component {
    public List<Vector2f> points = new ArrayList<>();
    public Color color = Color.BLACK;

    private Transform transform;

    @Override
    public void start() {
        transform = require(Transform.class);
    }

    @Override
    public void render(RenderLayer layer) {
        if (points.isEmpty()) {
            return;
        }

        layer.graphics.save();
        layer.graphics.translate(transform.position().x(), transform.position().y());
        layer.graphics.scale(transform.scale().x(), transform.scale().y());
        layer.graphics.rotate(transform.rotation() * 180 / Math.PI);

        layer.graphics.setLineWidth(0.2f);
        layer.graphics.setStroke(color);

        int n = points.size() + 1;
        double[] x = new double[n];
        double[] y = new double[n];

        for (int i = 0; i < n; i++) {
            Vector2f point = points.get(i % (n - 1));

            x[i] = point.x();
            y[i] = point.y();
        }

        layer.graphics.strokePolyline(x, y, n);
        layer.graphics.restore();
    }
}
