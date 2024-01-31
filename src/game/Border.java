package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * 
 * Subclass to tile, but also abstract superclass to different kinds of borders.
 * 
 * @author nikkn051, loviv462
 *
 */

public abstract class Border extends Tile {

	public Border(int x, int y, int i, int j) {
		super(x, y, i, j);
		setHasDot(false);
		setColor(Color.BLUE);
	}

	public abstract void drawYourself(GraphicsContext gc);

}
