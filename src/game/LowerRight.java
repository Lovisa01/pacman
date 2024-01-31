package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;

/**
 * 
 * Subclass to Border that draws lower right corner on the canvas
 * 
 * @author nikkn051, loviv462
 *
 */

public class LowerRight extends Border {

	public LowerRight(int x, int y, int i, int j) {
		super(x, y, i, j);
	}

	public void drawYourself(GraphicsContext gc) {
		gc.setLineWidth(2);
		gc.setStroke(getColor());
		gc.strokeArc(getPosX() - getSize(), getPosY() - getSize(), getSize(), getSize(), 270, 90, ArcType.OPEN);
	}

}
