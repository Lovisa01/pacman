package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * 
 * Superclass that builds the playingfield in the game. Keeps track of position
 * and indexes, if it has a dot or not etc.
 * 
 * @author nikkn051, loviv462
 *
 */

public class Tile {

	private int posX;
	private int posY;
	private int size = 20;
	private Color color;
	private int pelletSize = 15;
	private int rowIndex;
	private int columnIndex;
	private boolean hasDot = true;
	private boolean hasPowerPellet = false;

	public Tile(int x, int y, int i, int j) {
		posX = x;
		posY = y;
		rowIndex = i;
		columnIndex = j;
		color = Color.BLANCHEDALMOND;
	}

	public Tile(int x, int y, int i, int j, boolean dot) {
		posX = x;
		posY = y;
		rowIndex = i;
		columnIndex = j;
		hasDot = dot;
		color = Color.BLANCHEDALMOND;
	}

	public Tile(int x, int y, int i, int j, boolean dot, boolean powerPellet) {
		posX = x;
		posY = y;
		rowIndex = i;
		columnIndex = j;
		hasDot = dot;
		hasPowerPellet = powerPellet;
		color = Color.BLANCHEDALMOND;
	}

	public void drawYourself(GraphicsContext gc) {
		if (hasDot) {
			gc.setFill(color);
			gc.fillOval(posX - size / 8, posY - size / 8, size / 4, size / 4);
		}
		if (hasPowerPellet) {
			gc.setFill(color);
			gc.fillOval(posX - pelletSize / 2, posY - pelletSize / 2, pelletSize, pelletSize);
		}
	}

	public int eat() {
		int ate = 0;
		if (hasDot) {
			hasDot = false;
			ate = 1;
		}
		if (hasPowerPellet) {
			hasPowerPellet = false;
			ate = 2;
		}

		return ate;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getSize() {
		return size;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public int getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	public boolean hasDot() {
		return hasDot;
	}

	public boolean hasPowerPellet() {
		return hasPowerPellet;
	}

	public void setHasDot(boolean hasDot) {
		this.hasDot = hasDot;
	}

	public void setHasPowerPellet(boolean hasPowerPellet) {
		this.hasPowerPellet = hasPowerPellet;
	}

}
