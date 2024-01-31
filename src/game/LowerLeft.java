package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;

/**
 * 
 * Subclass to Border that draws a lower left corner on the canvas
 * 
 * @author nikkn051, loviv462
 *
 */

public class LowerLeft extends Border {

	public LowerLeft(int x, int y, int i, int j) {
		super(x, y, i, j);
	}

	public void drawYourself(GraphicsContext gc) {
		gc.setLineWidth(2);
		gc.setStroke(getColor());
		gc.strokeArc(getPosX(), getPosY() - getSize(), getSize(), getSize(), 180, 90, ArcType.OPEN);
	}

}
