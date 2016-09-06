package sample;

import javafx.scene.canvas.GraphicsContext;

public class RunnableGC implements Runnable {

    private GraphicsContext gc = null;
    private Stroke stroke = null;

    public RunnableGC(GraphicsContext gc, Stroke stroke) {
        this.gc = gc;
        this.stroke = stroke;
    }

    public void run() {
        gc.fillOval(stroke.x, stroke.y, stroke.strokeSize, stroke.strokeSize); // <---- this is the actual work we need to do
    }
}