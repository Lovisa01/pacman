package game;

import javafx.scene.canvas.GraphicsContext;

/**
 * 
 * Creates the playing field with tiles and draws itself to the canvas
 * 
 * @author nikkn051, loviv462
 *
 */

public class PlayingField {

	private int rows = 33;
	private int columns = 30;
	private Tile[][] map = new Tile[rows][columns];

	public PlayingField() {
		createBorders();
	}

	public Tile getNextTile(String direction, Tile currentPos) {
		if (currentPos.getRowIndex() < rows - 1 && currentPos.getColumnIndex() < columns - 1
				&& currentPos.getRowIndex() > 0 && currentPos.getColumnIndex() > 0) {
			if (direction.equals("up")) {
				return map[currentPos.getRowIndex() - 1][currentPos.getColumnIndex()];
			} else if (direction.equals("down")) {
				return map[currentPos.getRowIndex() + 1][currentPos.getColumnIndex()];
			} else if (direction.equals("right")) {
				return map[currentPos.getRowIndex()][currentPos.getColumnIndex() + 1];
			} else if (direction.equals("left")) {
				return map[currentPos.getRowIndex()][currentPos.getColumnIndex() - 1];
			}
		}
		return currentPos;
	}

	public int dotsLeft() {
		int amount = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (map[i][j].hasDot() || map[i][j].hasPowerPellet()) {
					amount++;
				}
			}
		}
		return amount;
	}

	public boolean isBorder(int i, int j) {

		if (i < 33 && i >= 0 && j < 30 && j >= 0) {
			return map[i][j] instanceof Border;
		} else {
			return false;
		}
	}

	public boolean seeTile(String direction, Tile currentPos) {
		Tile tile = currentPos;
		if (currentPos.getRowIndex() < rows - 1 && currentPos.getColumnIndex() < columns - 1
				&& currentPos.getRowIndex() > 0 && currentPos.getColumnIndex() > 0) {
			if (direction.equals("up")) {
				tile = map[currentPos.getRowIndex() - 1][currentPos.getColumnIndex()];
			} else if (direction.equals("down")) {
				tile = map[currentPos.getRowIndex() + 1][currentPos.getColumnIndex()];
			} else if (direction.equals("right")) {
				tile = map[currentPos.getRowIndex()][currentPos.getColumnIndex() + 1];
			} else if (direction.equals("left")) {
				tile = map[currentPos.getRowIndex()][currentPos.getColumnIndex() - 1];
			}
		}
		if (tile instanceof Border
				|| (tile.getRowIndex() == 13 && tile.getColumnIndex() == 14 && direction.equals("down"))
				|| (tile.getRowIndex() == 13 && tile.getColumnIndex() == 15 && direction.equals("down"))) {
			return false;
		} else {
			return true;
		}
	}

	public void drawYourself(GraphicsContext gc) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				map[i][j].drawYourself(gc);
			}
		}
	}

	public void activateCherry() {
		FruitTile fruit = (FruitTile) map[18][16];

		fruit.setCurrentImage("cherry");
		fruit.setHasFruit(true);
	}

	public void activateStrawberry() {
		FruitTile fruit = (FruitTile) map[12][13];

		fruit.setCurrentImage("strawberry");
		fruit.setHasFruit(true);
	}

	public void activateOrange() {
		FruitTile fruit = (FruitTile) map[18][12];

		fruit.setCurrentImage("orange");
		fruit.setHasFruit(true);
	}

	public Tile getStartPos() {
		return map[24][13];
	}

	public Tile getLeftTeleportTile() {
		return map[15][1];
	}

	public Tile getRightTeleportTile() {
		return map[15][28];
	}

	public Tile getBlinkyStartPos() {
		return map[16][15];
	}

	public Tile getInkyStartPos() {
		return map[16][16];
	}

	public Tile getClydeStartPos() {
		return map[14][13];
	}

	public Tile getPinkyStartPos() {
		return map[15][12];
	}

	public Tile getGhostStartPos(Ghost ghost) {
		if (ghost instanceof Blinky) {
			return map[16][15];
		} else if (ghost instanceof Inky) {
			return map[16][16];
		} else if (ghost instanceof Pinky) {
			return map[15][12];
		} else {
			return map[14][13];
		}
	}

	public void createBorders() {
		for (int j = 0; j < columns; j++) {
			for (int i = 0; i < rows; i++) {
				map[i][j] = new Tile(j * 20 + 10, i * 20 + 10, i, j);
			}
		}

		for (int i = 10; i < 21; i++) {
			for (int j = 8; j < 22; j++) {
				map[i][j] = new Tile(j * 20 + 10, i * 20 + 10, i, j, false);
			}
		}

		for (int j = 0; j < 7; j++) {
			map[15][j] = new Tile(j * 20 + 10, 15 * 20 + 10, 15, j, false);
		}
		for (int j = 23; j < 30; j++) {
			map[15][j] = new Tile(j * 20 + 10, 15 * 20 + 10, 15, j, false);
		}

		for (int j = 0; j < 5; j++) {
			map[12][j] = new Tile(j * 20 + 10, 12 * 20 + 10, 12, j, false);
		}
		for (int j = 0; j < 5; j++) {
			map[18][j] = new Tile(j * 20 + 10, 18 * 20 + 10, 12, j, false);
		}
		for (int j = 0; j < 5; j++) {
			map[12][j] = new Tile(j * 20 + 10, 12 * 20 + 10, 12, j, false);
		}
		for (int j = 25; j < 30; j++) {
			map[18][j] = new Tile(j * 20 + 10, 18 * 20 + 10, 12, j, false);
		}
		for (int j = 25; j < 30; j++) {
			map[12][j] = new Tile(j * 20 + 10, 12 * 20 + 10, 12, j, false);
		}
		map[4][4] = new Tile(4 * 20 + 10, 4 * 20 + 10, 4, 4, false);
		map[4][5] = new Tile(5 * 20 + 10, 4 * 20 + 10, 4, 5, false);
		map[4][24] = new Tile(24 * 20 + 10, 4 * 20 + 10, 4, 24, false);
		map[4][25] = new Tile(25 * 20 + 10, 4 * 20 + 10, 4, 25, false);
		for (int j = 9; j < 12; j++) {
			map[4][j] = new Tile(j * 20 + 10, 4 * 20 + 10, 4, j, false);
		}
		for (int j = 18; j < 21; j++) {
			map[4][j] = new Tile(j * 20 + 10, 4 * 20 + 10, 4, j, false);
		}

		map[4][2] = new Tile(2 * 20 + 10, 4 * 20 + 10, 4, 2, false, true);
		map[4][27] = new Tile(27 * 20 + 10, 4 * 20 + 10, 4, 27, false, true);
		map[24][2] = new Tile(2 * 20 + 10, 24 * 20 + 10, 24, 2, false, true);
		map[24][27] = new Tile(27 * 20 + 10, 24 * 20 + 10, 24, 27, false, true);

		map[18][16] = new FruitTile(16 * 20 + 10, 18 * 20 + 10, 18, 16, false);
		map[12][13] = new FruitTile(13 * 20 + 10, 12 * 20 + 10, 12, 13, false);
		map[18][12] = new FruitTile(12 * 20 + 10, 18 * 20 + 10, 18, 12, false);

		for (int j = 1; j < 29; j++) {
			map[0][j] = new HorizontalBorder(j * 20 + 10, 0 * 20 + 10, 0, j);
		}
		for (int j = 2; j < 14; j++) {
			map[1][j] = new HorizontalBorder(j * 20 + 10, 1 * 20 + 10, 1, j);
		}
		for (int j = 16; j < 28; j++) {
			map[1][j] = new HorizontalBorder(j * 20 + 10, 1 * 20 + 10, 1, j);
		}
		for (int i = 1; i < 11; i++) {
			map[i][0] = new VerticalBorder(0 * 20 + 10, i * 20 + 10, i, 0);
		}
		for (int i = 20; i < 32; i++) {
			map[i][0] = new VerticalBorder(0 * 20 + 10, i * 20 + 10, i, 0);
		}
		for (int j = 1; j < 29; j++) {
			map[32][j] = new HorizontalBorder(j * 20 + 10, 32 * 20 + 10, 32, j);
		}
		for (int i = 1; i < 11; i++) {
			map[i][29] = new VerticalBorder(29 * 20 + 10, i * 20 + 10, i, 29);
		}
		for (int i = 20; i < 32; i++) {
			map[i][29] = new VerticalBorder(29 * 20 + 10, i * 20 + 10, i, 29);
		}
		for (int j = 2; j < 28; j++) {
			map[31][j] = new HorizontalBorder(j * 20 + 10, 31 * 20 + 10, 31, j);
		}
		for (int i = 2; i < 10; i++) {
			map[i][1] = new VerticalBorder(1 * 20 + 10, i * 20 + 10, i, 1);
		}
		for (int i = 21; i < 25; i++) {
			map[i][1] = new VerticalBorder(1 * 20 + 10, i * 20 + 10, i, 1);
		}
		for (int i = 27; i < 31; i++) {
			map[i][1] = new VerticalBorder(1 * 20 + 10, i * 20 + 10, i, 1);
		}
		for (int i = 2; i < 10; i++) {
			map[i][28] = new VerticalBorder(28 * 20 + 10, i * 20 + 10, i, 28);
		}
		for (int i = 21; i < 25; i++) {
			map[i][28] = new VerticalBorder(28 * 20 + 10, i * 20 + 10, i, 28);
		}
		for (int i = 27; i < 31; i++) {
			map[i][28] = new VerticalBorder(28 * 20 + 10, i * 20 + 10, i, 28);
		}
		for (int j = 4; j < 6; j++) {
			map[3][j] = new HorizontalBorder(j * 20 + 10, 3 * 20 + 10, 3, j);
		}
		for (int j = 9; j < 12; j++) {
			map[3][j] = new HorizontalBorder(j * 20 + 10, 3 * 20 + 10, 3, j);
		}
		for (int j = 18; j < 21; j++) {
			map[3][j] = new HorizontalBorder(j * 20 + 10, 3 * 20 + 10, 3, j);
		}
		for (int j = 24; j < 26; j++) {
			map[3][j] = new HorizontalBorder(j * 20 + 10, 3 * 20 + 10, 3, j);
		}
		for (int j = 4; j < 6; j++) {
			map[5][j] = new HorizontalBorder(j * 20 + 10, 5 * 20 + 10, 5, j);
		}
		for (int j = 9; j < 12; j++) {
			map[5][j] = new HorizontalBorder(j * 20 + 10, 5 * 20 + 10, 5, j);
		}
		for (int j = 18; j < 21; j++) {
			map[5][j] = new HorizontalBorder(j * 20 + 10, 5 * 20 + 10, 5, j);
		}
		for (int j = 24; j < 26; j++) {
			map[5][j] = new HorizontalBorder(j * 20 + 10, 5 * 20 + 10, 5, j);
		}
		for (int j = 4; j < 6; j++) {
			map[7][j] = new HorizontalBorder(j * 20 + 10, 7 * 20 + 10, 7, j);
		}
		for (int j = 12; j < 18; j++) {
			map[7][j] = new HorizontalBorder(j * 20 + 10, 7 * 20 + 10, 7, j);
		}
		for (int j = 24; j < 26; j++) {
			map[7][j] = new HorizontalBorder(j * 20 + 10, 7 * 20 + 10, 7, j);
		}
		for (int j = 4; j < 6; j++) {
			map[7][j] = new HorizontalBorder(j * 20 + 10, 7 * 20 + 10, 7, j);
		}
		for (int j = 12; j < 14; j++) {
			map[8][j] = new HorizontalBorder(j * 20 + 10, 8 * 20 + 10, 8, j);
		}
		for (int j = 16; j < 18; j++) {
			map[8][j] = new HorizontalBorder(j * 20 + 10, 8 * 20 + 10, 8, j);
		}
		for (int j = 24; j < 26; j++) {
			map[8][j] = new HorizontalBorder(j * 20 + 10, 8 * 20 + 10, 8, j);
		}
		for (int j = 2; j < 6; j++) {
			map[10][j] = new HorizontalBorder(j * 20 + 10, 10 * 20 + 10, 10, j);
		}
		for (int j = 10; j < 12; j++) {
			map[10][j] = new HorizontalBorder(j * 20 + 10, 10 * 20 + 10, 10, j);
		}
		for (int j = 18; j < 20; j++) {
			map[10][j] = new HorizontalBorder(j * 20 + 10, 10 * 20 + 10, 10, j);
		}
		for (int j = 24; j < 28; j++) {
			map[10][j] = new HorizontalBorder(j * 20 + 10, 10 * 20 + 10, 10, j);
		}
		for (int j = 1; j < 5; j++) {
			map[11][j] = new HorizontalBorder(j * 20 + 10, 11 * 20 + 10, 11, j);
		}
		for (int j = 10; j < 12; j++) {
			map[11][j] = new HorizontalBorder(j * 20 + 10, 11 * 20 + 10, 11, j);
		}
		for (int j = 18; j < 20; j++) {
			map[11][j] = new HorizontalBorder(j * 20 + 10, 11 * 20 + 10, 11, j);
		}
		for (int j = 25; j < 29; j++) {
			map[11][j] = new HorizontalBorder(j * 20 + 10, 11 * 20 + 10, 11, j);
		}
		for (int j = 0; j < 5; j++) {
			map[13][j] = new HorizontalBorder(j * 20 + 10, 13 * 20 + 10, 13, j);
		}
		for (int j = 12; j < 14; j++) {
			map[13][j] = new HorizontalBorder(j * 20 + 10, 13 * 20 + 10, 13, j);
		}
		for (int j = 16; j < 18; j++) {
			map[13][j] = new HorizontalBorder(j * 20 + 10, 13 * 20 + 10, 13, j);
		}
		for (int j = 25; j < 30; j++) {
			map[13][j] = new HorizontalBorder(j * 20 + 10, 13 * 20 + 10, 13, j);
		}
		for (int j = 0; j < 6; j++) {
			map[14][j] = new HorizontalBorder(j * 20 + 10, 14 * 20 + 10, 14, j);
		}
		for (int j = 24; j < 30; j++) {
			map[14][j] = new HorizontalBorder(j * 20 + 10, 14 * 20 + 10, 14, j);
		}
		for (int j = 0; j < 6; j++) {
			map[16][j] = new HorizontalBorder(j * 20 + 10, 16 * 20 + 10, 16, j);
		}
		for (int j = 24; j < 30; j++) {
			map[16][j] = new HorizontalBorder(j * 20 + 10, 16 * 20 + 10, 16, j);
		}
		for (int j = 0; j < 5; j++) {
			map[17][j] = new HorizontalBorder(j * 20 + 10, 17 * 20 + 10, 17, j);
		}
		for (int j = 12; j < 18; j++) {
			map[17][j] = new HorizontalBorder(j * 20 + 10, 17 * 20 + 10, 17, j);
		}
		for (int j = 25; j < 30; j++) {
			map[17][j] = new HorizontalBorder(j * 20 + 10, 17 * 20 + 10, 17, j);
		}
		for (int j = 1; j < 5; j++) {
			map[19][j] = new HorizontalBorder(j * 20 + 10, 19 * 20 + 10, 19, j);
		}
		for (int j = 12; j < 18; j++) {
			map[19][j] = new HorizontalBorder(j * 20 + 10, 19 * 20 + 10, 19, j);
		}
		for (int j = 25; j < 29; j++) {
			map[19][j] = new HorizontalBorder(j * 20 + 10, 19 * 20 + 10, 19, j);
		}
		for (int j = 2; j < 6; j++) {
			map[20][j] = new HorizontalBorder(j * 20 + 10, 20 * 20 + 10, 20, j);
		}
		for (int j = 12; j < 14; j++) {
			map[20][j] = new HorizontalBorder(j * 20 + 10, 20 * 20 + 10, 20, j);
		}
		for (int j = 16; j < 18; j++) {
			map[20][j] = new HorizontalBorder(j * 20 + 10, 20 * 20 + 10, 20, j);
		}
		for (int j = 24; j < 28; j++) {
			map[20][j] = new HorizontalBorder(j * 20 + 10, 20 * 20 + 10, 20, j);
		}
		for (int j = 4; j < 6; j++) {
			map[22][j] = new HorizontalBorder(j * 20 + 10, 22 * 20 + 10, 22, j);
		}
		for (int j = 9; j < 12; j++) {
			map[22][j] = new HorizontalBorder(j * 20 + 10, 22 * 20 + 10, 22, j);
		}
		for (int j = 18; j < 21; j++) {
			map[22][j] = new HorizontalBorder(j * 20 + 10, 22 * 20 + 10, 22, j);
		}
		for (int j = 24; j < 26; j++) {
			map[22][j] = new HorizontalBorder(j * 20 + 10, 22 * 20 + 10, 22, j);
		}
		for (int j = 4; j < 5; j++) {
			map[23][j] = new HorizontalBorder(j * 20 + 10, 23 * 20 + 10, 23, j);
		}
		for (int j = 9; j < 12; j++) {
			map[23][j] = new HorizontalBorder(j * 20 + 10, 23 * 20 + 10, 23, j);
		}
		for (int j = 18; j < 21; j++) {
			map[23][j] = new HorizontalBorder(j * 20 + 10, 23 * 20 + 10, 23, j);
		}
		for (int j = 25; j < 26; j++) {
			map[23][j] = new HorizontalBorder(j * 20 + 10, 23 * 20 + 10, 23, j);
		}
		for (int j = 2; j < 3; j++) {
			map[25][j] = new HorizontalBorder(j * 20 + 10, 25 * 20 + 10, 25, j);
		}
		for (int j = 12; j < 18; j++) {
			map[25][j] = new HorizontalBorder(j * 20 + 10, 25 * 20 + 10, 25, j);
		}
		for (int j = 27; j < 28; j++) {
			map[25][j] = new HorizontalBorder(j * 20 + 10, 25 * 20 + 10, 25, j);
		}
		for (int j = 2; j < 3; j++) {
			map[26][j] = new HorizontalBorder(j * 20 + 10, 26 * 20 + 10, 26, j);
		}
		for (int j = 12; j < 14; j++) {
			map[26][j] = new HorizontalBorder(j * 20 + 10, 26 * 20 + 10, 26, j);
		}
		for (int j = 16; j < 18; j++) {
			map[26][j] = new HorizontalBorder(j * 20 + 10, 26 * 20 + 10, 26, j);
		}
		for (int j = 27; j < 28; j++) {
			map[26][j] = new HorizontalBorder(j * 20 + 10, 26 * 20 + 10, 26, j);
		}
		for (int j = 4; j < 8; j++) {
			map[28][j] = new HorizontalBorder(j * 20 + 10, 28 * 20 + 10, 28, j);
		}
		for (int j = 10; j < 12; j++) {
			map[28][j] = new HorizontalBorder(j * 20 + 10, 28 * 20 + 10, 28, j);
		}
		for (int j = 18; j < 20; j++) {
			map[28][j] = new HorizontalBorder(j * 20 + 10, 28 * 20 + 10, 28, j);
		}
		for (int j = 22; j < 26; j++) {
			map[28][j] = new HorizontalBorder(j * 20 + 10, 28 * 20 + 10, 28, j);
		}
		for (int j = 4; j < 12; j++) {
			map[29][j] = new HorizontalBorder(j * 20 + 10, 29 * 20 + 10, 29, j);
		}
		for (int j = 18; j < 26; j++) {
			map[29][j] = new HorizontalBorder(j * 20 + 10, 29 * 20 + 10, 29, j);
		}
		for (int i = 4; i < 5; i++) {
			map[i][3] = new VerticalBorder(3 * 20 + 10, i * 20 + 10, i, 3);
		}
		for (int i = 12; i < 13; i++) {
			map[i][5] = new VerticalBorder(5 * 20 + 10, i * 20 + 10, i, 5);
		}
		for (int i = 18; i < 19; i++) {
			map[i][5] = new VerticalBorder(5 * 20 + 10, i * 20 + 10, i, 5);
		}
		for (int i = 24; i < 26; i++) {
			map[i][5] = new VerticalBorder(5 * 20 + 10, i * 20 + 10, i, 5);
		}
		for (int i = 4; i < 5; i++) {
			map[i][6] = new VerticalBorder(6 * 20 + 10, i * 20 + 10, i, 6);
		}
		for (int i = 11; i < 14; i++) {
			map[i][6] = new VerticalBorder(6 * 20 + 10, i * 20 + 10, i, 6);
		}
		for (int i = 17; i < 20; i++) {
			map[i][6] = new VerticalBorder(6 * 20 + 10, i * 20 + 10, i, 6);
		}
		for (int i = 23; i < 26; i++) {
			map[i][6] = new VerticalBorder(6 * 20 + 10, i * 20 + 10, i, 6);
		}
		for (int i = 4; i < 5; i++) {
			map[i][8] = new VerticalBorder(8 * 20 + 10, i * 20 + 10, i, 8);
		}
		for (int i = 8; i < 14; i++) {
			map[i][8] = new VerticalBorder(8 * 20 + 10, i * 20 + 10, i, 8);
		}
		for (int i = 17; i < 20; i++) {
			map[i][8] = new VerticalBorder(8 * 20 + 10, i * 20 + 10, i, 8);
		}
		for (int i = 26; i < 28; i++) {
			map[i][8] = new VerticalBorder(8 * 20 + 10, i * 20 + 10, i, 8);
		}
		for (int i = 8; i < 10; i++) {
			map[i][9] = new VerticalBorder(9 * 20 + 10, i * 20 + 10, i, 9);
		}
		for (int i = 12; i < 14; i++) {
			map[i][9] = new VerticalBorder(9 * 20 + 10, i * 20 + 10, i, 9);
		}
		for (int i = 17; i < 20; i++) {
			map[i][9] = new VerticalBorder(9 * 20 + 10, i * 20 + 10, i, 9);
		}
		for (int i = 26; i < 28; i++) {
			map[i][9] = new VerticalBorder(9 * 20 + 10, i * 20 + 10, i, 9);
		}
		for (int i = 14; i < 17; i++) {
			map[i][11] = new VerticalBorder(11 * 20 + 10, i * 20 + 10, i, 11);
		}
		for (int i = 4; i < 5; i++) {
			map[i][12] = new VerticalBorder(12 * 20 + 10, i * 20 + 10, i, 12);
		}
		for (int i = 2; i < 5; i++) {
			map[i][14] = new VerticalBorder(14 * 20 + 10, i * 20 + 10, i, 14);
		}
		for (int i = 9; i < 10; i++) {
			map[i][14] = new VerticalBorder(14 * 20 + 10, i * 20 + 10, i, 14);
		}
		for (int i = 10; i < 11; i++) {
			map[i][14] = new VerticalBorder(14 * 20 + 10, i * 20 + 10, i, 14);
		}
		for (int i = 21; i < 23; i++) {
			map[i][14] = new VerticalBorder(14 * 20 + 10, i * 20 + 10, i, 14);
		}
		for (int i = 27; i < 29; i++) {
			map[i][14] = new VerticalBorder(14 * 20 + 10, i * 20 + 10, i, 14);
		}
		for (int i = 2; i < 5; i++) {
			map[i][14] = new VerticalBorder(14 * 20 + 10, i * 20 + 10, i, 14);
		}
		for (int i = 9; i < 10; i++) {
			map[i][15] = new VerticalBorder(15 * 20 + 10, i * 20 + 10, i, 15);
		}
		for (int i = 10; i < 11; i++) {
			map[i][15] = new VerticalBorder(15 * 20 + 10, i * 20 + 10, i, 15);
		}
		for (int i = 21; i < 23; i++) {
			map[i][15] = new VerticalBorder(15 * 20 + 10, i * 20 + 10, i, 15);
		}
		for (int i = 27; i < 29; i++) {
			map[i][15] = new VerticalBorder(15 * 20 + 10, i * 20 + 10, i, 15);
		}
		for (int i = 4; i < 5; i++) {
			map[i][17] = new VerticalBorder(17 * 20 + 10, i * 20 + 10, i, 17);
		}

		for (int i = 4; i < 5; i++) {
			map[i][26] = new VerticalBorder(26 * 20 + 10, i * 20 + 10, i, 26);
		}
		for (int i = 12; i < 13; i++) {
			map[i][24] = new VerticalBorder(24 * 20 + 10, i * 20 + 10, i, 24);
		}
		for (int i = 18; i < 19; i++) {
			map[i][24] = new VerticalBorder(24 * 20 + 10, i * 20 + 10, i, 24);
		}
		for (int i = 24; i < 26; i++) {
			map[i][24] = new VerticalBorder(24 * 20 + 10, i * 20 + 10, i, 24);
		}
		for (int i = 4; i < 5; i++) {
			map[i][23] = new VerticalBorder(23 * 20 + 10, i * 20 + 10, i, 23);
		}
		for (int i = 11; i < 14; i++) {
			map[i][23] = new VerticalBorder(23 * 20 + 10, i * 20 + 10, i, 23);
		}
		for (int i = 17; i < 20; i++) {
			map[i][23] = new VerticalBorder(23 * 20 + 10, i * 20 + 10, i, 23);
		}
		for (int i = 23; i < 26; i++) {
			map[i][23] = new VerticalBorder(23 * 20 + 10, i * 20 + 10, i, 23);
		}
		for (int i = 4; i < 5; i++) {
			map[i][21] = new VerticalBorder(21 * 20 + 10, i * 20 + 10, i, 21);
		}
		for (int i = 8; i < 14; i++) {
			map[i][21] = new VerticalBorder(21 * 20 + 10, i * 20 + 10, i, 21);
		}
		for (int i = 17; i < 20; i++) {
			map[i][21] = new VerticalBorder(21 * 20 + 10, i * 20 + 10, i, 21);
		}
		for (int i = 26; i < 28; i++) {
			map[i][21] = new VerticalBorder(21 * 20 + 10, i * 20 + 10, i, 21);
		}
		for (int i = 8; i < 10; i++) {
			map[i][20] = new VerticalBorder(20 * 20 + 10, i * 20 + 10, i, 20);
		}
		for (int i = 12; i < 14; i++) {
			map[i][20] = new VerticalBorder(20 * 20 + 10, i * 20 + 10, i, 20);
		}
		for (int i = 17; i < 20; i++) {
			map[i][20] = new VerticalBorder(20 * 20 + 10, i * 20 + 10, i, 20);
		}
		for (int i = 26; i < 28; i++) {
			map[i][20] = new VerticalBorder(20 * 20 + 10, i * 20 + 10, i, 20);
		}
		for (int i = 14; i < 17; i++) {
			map[i][18] = new VerticalBorder(18 * 20 + 10, i * 20 + 10, i, 18);
		}
		for (int i = 2; i < 5; i++) {
			map[i][15] = new VerticalBorder(15 * 20 + 10, i * 20 + 10, i, 15);
		}
		for (int j = 4; j < 6; j++) {
			map[8][j] = new HorizontalBorder(j * 20 + 10, 8 * 20 + 10, 8, j);
		}

		map[0][0] = new UpperLeft(10, 10, 0, 0);
		map[0][29] = new UpperRight(29 * 20 + 10, 0 * 20 + 10, 0, 29);
		map[1][28] = new UpperRight(28 * 20 + 10, 1 * 20 + 10, 1, 28);
		map[32][0] = new LowerLeft(0 * 20 + 10, 32 * 20 + 10, 0, 32);
		map[32][29] = new LowerRight(29 * 20 + 10, 32 * 20 + 10, 29, 32);
		map[31][28] = new LowerRight(28 * 20 + 10, 31 * 20 + 10, 28, 31);
		map[1][1] = new UpperLeft(1 * 20 + 10, 1 * 20 + 10, 1, 1);
		map[10][1] = new LowerLeft(1 * 20 + 10, 10 * 20 + 10, 10, 1);
		map[11][0] = new LowerLeft(0 * 20 + 10, 11 * 20 + 10, 11, 0);
		map[10][6] = new UpperRight(6 * 20 + 10, 10 * 20 + 10, 10, 6);
		map[11][5] = new UpperRight(5 * 20 + 10, 11 * 20 + 10, 11, 5);
		map[13][5] = new LowerRight(5 * 20 + 10, 13 * 20 + 10, 13, 5);
		map[14][6] = new LowerRight(6 * 20 + 10, 14 * 20 + 10, 14, 6);
		map[16][6] = new UpperRight(6 * 20 + 10, 16 * 20 + 10, 16, 6);
		map[3][3] = new UpperLeft(3 * 20 + 10, 3 * 20 + 10, 3, 3);
		map[5][3] = new LowerLeft(3 * 20 + 10, 5 * 20 + 10, 5, 3);
		map[5][6] = new LowerRight(6 * 20 + 10, 5 * 20 + 10, 5, 6);
		map[3][6] = new UpperRight(6 * 20 + 10, 3 * 20 + 10, 3, 6);
		map[1][14] = new UpperRight(14 * 20 + 10, 1 * 20 + 10, 1, 14);
		map[1][15] = new UpperLeft(15 * 20 + 10, 1 * 20 + 10, 1, 15);
		map[5][14] = new LowerLeft(14 * 20 + 10, 5 * 20 + 10, 5, 14);
		map[5][15] = new LowerRight(15 * 20 + 10, 5 * 20 + 10, 5, 15);
		map[3][8] = new UpperLeft(8 * 20 + 10, 3 * 20 + 10, 3, 8);
		map[3][12] = new UpperRight(12 * 20 + 10, 3 * 20 + 10, 3, 12);
		map[5][8] = new LowerLeft(8 * 20 + 10, 5 * 20 + 10, 5, 8);
		map[5][12] = new LowerRight(12 * 20 + 10, 5 * 20 + 10, 5, 12);
		map[3][17] = new UpperLeft(17 * 20 + 10, 3 * 20 + 10, 3, 17);
		map[3][21] = new UpperRight(21 * 20 + 10, 3 * 20 + 10, 3, 21);
		map[5][17] = new LowerLeft(17 * 20 + 10, 5 * 20 + 10, 5, 17);
		map[5][21] = new LowerRight(21 * 20 + 10, 5 * 20 + 10, 5, 21);
		map[3][23] = new UpperLeft(23 * 20 + 10, 3 * 20 + 10, 3, 23);
		map[3][26] = new UpperRight(26 * 20 + 10, 3 * 20 + 10, 3, 26);
		map[5][23] = new LowerLeft(23 * 20 + 10, 5 * 20 + 10, 5, 23);
		map[5][26] = new LowerRight(26 * 20 + 10, 5 * 20 + 10, 5, 26);
		map[7][3] = new UpperLeft(3 * 20 + 10, 7 * 20 + 10, 7, 3);
		map[7][6] = new UpperRight(6 * 20 + 10, 7 * 20 + 10, 7, 6);
		map[8][3] = new LowerLeft(3 * 20 + 10, 8 * 20 + 10, 8, 3);
		map[8][6] = new LowerRight(6 * 20 + 10, 8 * 20 + 10, 8, 6);
		map[7][23] = new UpperLeft(23 * 20 + 10, 7 * 20 + 10, 7, 23);
		map[7][26] = new UpperRight(26 * 20 + 10, 7 * 20 + 10, 7, 26);
		map[8][23] = new LowerLeft(23 * 20 + 10, 8 * 20 + 10, 8, 23);
		map[8][26] = new LowerRight(26 * 20 + 10, 8 * 20 + 10, 8, 26);
		map[7][11] = new UpperLeft(11 * 20 + 10, 7 * 20 + 10, 11, 23);
		map[7][18] = new UpperRight(18 * 20 + 10, 7 * 20 + 10, 7, 18);
		map[8][11] = new LowerLeft(11 * 20 + 10, 8 * 20 + 10, 8, 11);
		map[8][18] = new LowerRight(18 * 20 + 10, 8 * 20 + 10, 8, 18);
		map[11][14] = new LowerLeft(14 * 20 + 10, 11 * 20 + 10, 11, 14);
		map[11][15] = new LowerRight(15 * 20 + 10, 11 * 20 + 10, 11, 15);
		map[8][15] = new UpperLeft(15 * 20 + 10, 8 * 20 + 10, 8, 15);
		map[8][14] = new UpperRight(14 * 20 + 10, 8 * 20 + 10, 8, 14);
		map[7][8] = new UpperLeft(8 * 20 + 10, 7 * 20 + 10, 7, 8);
		map[7][9] = new UpperRight(9 * 20 + 10, 7 * 20 + 10, 7, 9);
		map[7][20] = new UpperLeft(20 * 20 + 10, 7 * 20 + 10, 7, 20);
		map[7][21] = new UpperRight(21 * 20 + 10, 7 * 20 + 10, 7, 21);
		map[11][9] = new UpperLeft(9 * 20 + 10, 11 * 20 + 10, 11, 9);
		map[10][9] = new LowerLeft(9 * 20 + 10, 10 * 20 + 10, 10, 9);
		map[11][20] = new UpperRight(20 * 20 + 10, 11 * 20 + 10, 11, 20);
		map[10][20] = new LowerRight(20 * 20 + 10, 10 * 20 + 10, 10, 20);
		map[10][12] = new UpperRight(12 * 20 + 10, 10 * 20 + 10, 10, 12);
		map[11][12] = new LowerRight(12 * 20 + 10, 11 * 20 + 10, 11, 12);
		map[10][17] = new UpperLeft(17 * 20 + 10, 10 * 20 + 10, 10, 17);
		map[11][17] = new LowerLeft(17 * 20 + 10, 11 * 20 + 10, 11, 17);
		map[14][8] = new LowerLeft(8 * 20 + 10, 14 * 20 + 10, 14, 8);
		map[14][9] = new LowerRight(9 * 20 + 10, 14 * 20 + 10, 14, 9);
		map[14][20] = new LowerLeft(20 * 20 + 10, 14 * 20 + 10, 14, 20);
		map[14][21] = new LowerRight(21 * 20 + 10, 14 * 20 + 10, 14, 21);
		map[10][28] = new LowerRight(28 * 20 + 10, 10 * 20 + 10, 10, 28);
		map[11][29] = new LowerRight(29 * 20 + 10, 11 * 20 + 10, 11, 29);
		map[10][23] = new UpperLeft(23 * 20 + 10, 10 * 20 + 10, 10, 23);
		map[11][24] = new UpperLeft(24 * 20 + 10, 11 * 20 + 10, 11, 24);
		map[14][23] = new LowerLeft(23 * 20 + 10, 14 * 20 + 10, 14, 23);
		map[13][24] = new LowerLeft(24 * 20 + 10, 13 * 20 + 10, 13, 24);
		map[16][23] = new UpperLeft(23 * 20 + 10, 16 * 20 + 10, 16, 23);
		map[17][24] = new UpperLeft(24 * 20 + 10, 17 * 20 + 10, 17, 24);
		map[19][24] = new LowerLeft(24 * 20 + 10, 19 * 20 + 10, 19, 24);
		map[20][23] = new LowerLeft(23 * 20 + 10, 20 * 20 + 10, 20, 23);
		map[13][11] = new UpperLeft(11 * 20 + 10, 13 * 20 + 10, 13, 11);
		map[17][11] = new LowerLeft(11 * 20 + 10, 17 * 20 + 10, 17, 11);
		map[13][18] = new UpperRight(18 * 20 + 10, 13 * 20 + 10, 13, 18);
		map[17][18] = new LowerRight(18 * 20 + 10, 17 * 20 + 10, 17, 18);
		map[17][5] = new UpperRight(5 * 20 + 10, 17 * 20 + 10, 17, 5);
		map[19][5] = new LowerRight(5 * 20 + 10, 19 * 20 + 10, 19, 5);
		map[20][6] = new LowerRight(6 * 20 + 10, 20 * 20 + 10, 20, 6);
		map[19][0] = new UpperLeft(0 * 20 + 10, 19 * 20 + 10, 19, 0);
		map[20][1] = new UpperLeft(1 * 20 + 10, 20 * 20 + 10, 20, 1);
		map[19][29] = new UpperRight(29 * 20 + 10, 19 * 20 + 10, 19, 29);
		map[20][28] = new UpperRight(28 * 20 + 10, 20 * 20 + 10, 20, 28);
		map[16][8] = new UpperLeft(8 * 20 + 10, 16 * 20 + 10, 16, 8);
		map[16][9] = new UpperRight(9 * 20 + 10, 16 * 20 + 10, 16, 9);
		map[20][8] = new LowerLeft(8 * 20 + 10, 20 * 20 + 10, 20, 8);
		map[20][9] = new LowerRight(9 * 20 + 10, 20 * 20 + 10, 20, 9);
		map[16][20] = new UpperLeft(20 * 20 + 10, 16 * 20 + 10, 16, 20);
		map[16][21] = new UpperRight(21 * 20 + 10, 16 * 20 + 10, 16, 21);
		map[20][20] = new LowerLeft(20 * 20 + 10, 20 * 20 + 10, 20, 20);
		map[20][21] = new LowerRight(21 * 20 + 10, 20 * 20 + 10, 20, 21);
		map[19][11] = new UpperLeft(11 * 20 + 10, 19 * 20 + 10, 19, 11);
		map[20][11] = new LowerLeft(11 * 20 + 10, 20 * 20 + 10, 20, 11);
		map[19][18] = new UpperRight(18 * 20 + 10, 19 * 20 + 10, 19, 18);
		map[20][18] = new LowerRight(18 * 20 + 10, 20 * 20 + 10, 20, 18);
		map[20][14] = new UpperRight(14 * 20 + 10, 20 * 20 + 10, 20, 14);
		map[20][15] = new UpperLeft(15 * 20 + 10, 20 * 20 + 10, 20, 15);
		map[23][14] = new LowerLeft(14 * 20 + 10, 23 * 20 + 10, 23, 14);
		map[23][15] = new LowerRight(15 * 20 + 10, 23 * 20 + 10, 23, 15);
		map[22][3] = new UpperLeft(3 * 20 + 10, 22 * 20 + 10, 22, 3);
		map[23][3] = new LowerLeft(3 * 20 + 10, 23 * 20 + 10, 23, 3);
		map[22][6] = new UpperRight(6 * 20 + 10, 22 * 20 + 10, 22, 6);
		map[23][5] = new UpperRight(5 * 20 + 10, 23 * 20 + 10, 23, 5);
		map[26][5] = new LowerLeft(5 * 20 + 10, 26 * 20 + 10, 26, 5);
		map[26][6] = new LowerRight(6 * 20 + 10, 26 * 20 + 10, 26, 6);
		map[28][3] = new UpperLeft(3 * 20 + 10, 28 * 20 + 10, 28, 3);
		map[29][3] = new LowerLeft(3 * 20 + 10, 29 * 20 + 10, 29, 3);
		map[28][8] = new LowerRight(8 * 20 + 10, 28 * 20 + 10, 28, 8);
		map[28][9] = new LowerLeft(9 * 20 + 10, 28 * 20 + 10, 28, 9);
		map[25][8] = new UpperLeft(8 * 20 + 10, 25 * 20 + 10, 25, 8);
		map[25][9] = new UpperRight(9 * 20 + 10, 25 * 20 + 10, 25, 9);
		map[22][8] = new UpperLeft(8 * 20 + 10, 22 * 20 + 10, 22, 8);
		map[23][8] = new LowerLeft(8 * 20 + 10, 23 * 20 + 10, 23, 8);
		map[22][12] = new UpperRight(12 * 20 + 10, 22 * 20 + 10, 22, 12);
		map[23][12] = new LowerRight(12 * 20 + 10, 23 * 20 + 10, 23, 12);
		map[28][12] = new UpperRight(12 * 20 + 10, 28 * 20 + 10, 28, 12);
		map[29][12] = new LowerRight(12 * 20 + 10, 29 * 20 + 10, 29, 12);
		map[25][11] = new UpperLeft(11 * 20 + 10, 25 * 20 + 10, 25, 11);
		map[26][11] = new LowerLeft(11 * 20 + 10, 26 * 20 + 10, 26, 11);
		map[25][18] = new UpperRight(18 * 20 + 10, 25 * 20 + 10, 25, 18);
		map[26][18] = new LowerRight(18 * 20 + 10, 26 * 20 + 10, 26, 18);
		map[26][14] = new UpperRight(14 * 20 + 10, 26 * 20 + 10, 26, 14);
		map[26][15] = new UpperLeft(15 * 20 + 10, 26 * 20 + 10, 26, 15);
		map[29][14] = new LowerLeft(14 * 20 + 10, 29 * 20 + 10, 29, 14);
		map[29][15] = new LowerRight(15 * 20 + 10, 29 * 20 + 10, 29, 15);
		map[28][17] = new UpperLeft(17 * 20 + 10, 28 * 20 + 10, 28, 17);
		map[29][17] = new LowerLeft(17 * 20 + 10, 29 * 20 + 10, 29, 17);
		map[28][20] = new LowerRight(20 * 20 + 10, 28 * 20 + 10, 28, 20);
		map[28][21] = new LowerLeft(21 * 20 + 10, 28 * 20 + 10, 28, 21);
		map[25][20] = new UpperLeft(20 * 20 + 10, 25 * 20 + 10, 25, 20);
		map[25][21] = new UpperRight(21 * 20 + 10, 25 * 20 + 10, 25, 21);
		map[29][26] = new LowerRight(26 * 20 + 10, 29 * 20 + 10, 29, 26);
		map[28][26] = new UpperRight(26 * 20 + 10, 28 * 20 + 10, 28, 26);
		map[26][26] = new LowerLeft(26 * 20 + 10, 26 * 20 + 10, 26, 26);
		map[25][26] = new UpperLeft(26 * 20 + 10, 25 * 20 + 10, 25, 26);
		map[26][28] = new UpperRight(28 * 20 + 10, 26 * 20 + 10, 26, 28);
		map[25][28] = new LowerRight(28 * 20 + 10, 25 * 20 + 10, 25, 28);
		map[23][26] = new LowerRight(26 * 20 + 10, 23 * 20 + 10, 23, 26);
		map[22][26] = new UpperRight(26 * 20 + 10, 22 * 20 + 10, 22, 26);
		map[22][23] = new UpperLeft(23 * 20 + 10, 22 * 20 + 10, 22, 23);
		map[23][24] = new UpperLeft(24 * 20 + 10, 23 * 20 + 10, 23, 24);
		map[26][24] = new LowerRight(24 * 20 + 10, 26 * 20 + 10, 26, 24);
		map[26][23] = new LowerLeft(23 * 20 + 10, 26 * 20 + 10, 26, 23);
		map[22][17] = new UpperLeft(17 * 20 + 10, 22 * 20 + 10, 22, 17);
		map[23][17] = new LowerLeft(17 * 20 + 10, 23 * 20 + 10, 23, 17);
		map[22][21] = new UpperRight(21 * 20 + 10, 22 * 20 + 10, 22, 21);
		map[23][21] = new LowerRight(21 * 20 + 10, 23 * 20 + 10, 23, 21);
		map[25][1] = new LowerLeft(1 * 20 + 10, 25 * 20 + 10, 25, 1);
		map[26][1] = new UpperLeft(1 * 20 + 10, 26 * 20 + 10, 26, 1);
		map[25][3] = new UpperRight(3 * 20 + 10, 25 * 20 + 10, 25, 3);
		map[26][3] = new LowerRight(3 * 20 + 10, 26 * 20 + 10, 26, 3);
		map[31][1] = new LowerLeft(1 * 20 + 10, 31 * 20 + 10, 31, 1);

	}

}
