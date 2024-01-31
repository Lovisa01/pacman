package game;

import javafx.scene.canvas.GraphicsContext;

/**
 * 
 * Subclass to Border that draws a horizontal border on the canvas
 * 
 * @author nikkn051, loviv462
 *
 */

public class HorizontalBorder extends Border {

	public HorizontalBorder(int x, int y, int i, int j) {
		super(x, y, i, j);
	}

	public void drawYourself(GraphicsContext gc) {
		gc.setLineWidth(2);
		gc.setStroke(getColor());
		gc.strokeLine(getPosX() - getSize() / 2, getPosY(), getPosX() + getSize() / 2, getPosY());
	}

}
