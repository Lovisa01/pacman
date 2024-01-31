package game;

import javafx.scene.canvas.GraphicsContext;

/**
 * 
 * Subclass to Border that draws a vertical border on the canvas
 * 
 * @author nikkn051, loviv462
 *
 */

public class VerticalBorder extends Border {

	public VerticalBorder(int x, int y, int i, int j) {
		super(x, y, i, j);
	}

	public void drawYourself(GraphicsContext gc) {
		gc.setLineWidth(2);
		gc.setStroke(getColor());
		gc.strokeLine(getPosX(), getPosY() - getSize() / 2, getPosX(), getPosY() + getSize() / 2);
	}

}
