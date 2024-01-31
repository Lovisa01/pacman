package game;

import javafx.scene.paint.Color;

/**
 * 
 * Subclass of Ghost with its own chase behavior
 * 
 * @author nikkn051, loviv462
 *
 */

public class Inky extends Ghost {

	private Ghost blinky;

	public Inky(Color color, PlayingField playingField, Tile position, int scatterPosX, int scatterPosY, Ghost blinky) {
		super(color, playingField, position, scatterPosX, scatterPosY);
		setStart(System.currentTimeMillis());
		setInit(false);
		this.blinky = blinky;
	}

	@Override
	public void chase(Pacman pacman) {
		if (intersect(pacman)) {
			pacman.setLives(pacman.getLives() - 1);
			pacman.setLostLife(true);
		}

		setCurrentColor(getColor());

		if (pacman.getLevel() >= 2) {

			int x = (pacman.getPosition().getColumnIndex() - blinky.getPosition().getColumnIndex());
			int y = (pacman.getPosition().getRowIndex() - blinky.getPosition().getRowIndex());

			if (getPosX() == 290 && getPosY() == 250) {
				setInit(false);
			}

			if (isWait()) {
				setTargetX(330);
				setTargetY(330);

				if (getPlayingField().dotsLeft() <= 240) {
					setWait(false);
					setInit(true);
				}

			} else if (isInit()) {
				setTargetY(250);
				setTargetX(290);
			} else if (!isInit() && !isWait()) {
				boolean foundTile = false;
				if (x >= 0 && y >= 0) {
					for (int i = 0; i <= y; i++) {
						for (int j = 0; j <= x; j++) {
							if (!getPlayingField().isBorder(pacman.getPosition().getRowIndex() + y - i,
									pacman.getPosition().getColumnIndex() + x - j)) {
								setTargetX((pacman.getPosition().getColumnIndex() + x - j) * 20 + 10);
								setTargetY((pacman.getPosition().getRowIndex() + y - i) * 20 + 10);
								foundTile = true;
								break;
							}
						}
						if (foundTile) {
							break;
						}
					}
				} else if (x >= 0 && y < 0) {
					for (int i = 0; i >= y; i--) {
						for (int j = 0; j <= x; j++) {
							if (!getPlayingField().isBorder(pacman.getPosition().getRowIndex() + y - i,
									pacman.getPosition().getColumnIndex() + x - j)) {
								setTargetX((pacman.getPosition().getColumnIndex() + x - j) * 20 + 10);
								setTargetY((pacman.getPosition().getRowIndex() + y - i) * 20 + 10);
								foundTile = true;
								break;
							}
						}
						if (foundTile) {
							break;
						}
					}
				} else if (x < 0 && y >= 0) {
					for (int i = 0; i <= y; i++) {
						for (int j = 0; j >= x; j--) {
							if (!getPlayingField().isBorder(pacman.getPosition().getRowIndex() + y - i,
									pacman.getPosition().getColumnIndex() + x - j)) {
								setTargetX((pacman.getPosition().getColumnIndex() + x - j) * 20 + 10);
								setTargetY((pacman.getPosition().getRowIndex() + y - i) * 20 + 10);
								foundTile = true;
								break;
							}
						}
						if (foundTile) {
							break;
						}
					}
				} else if (x < 0 && y < 0) {
					for (int i = 0; i >= y; i--) {
						for (int j = 0; j >= x; j--) {
							if (!getPlayingField().isBorder(pacman.getPosition().getRowIndex() + y - i,
									pacman.getPosition().getColumnIndex() + x - j)) {
								setTargetX((pacman.getPosition().getColumnIndex() + x - j) * 20 + 10);
								setTargetY((pacman.getPosition().getRowIndex() + y - i) * 20 + 10);
								foundTile = true;
								break;
							}
						}
						if (foundTile) {
							break;
						}
					}
				}

			}
		} else {
			setTargetX(330);
			setTargetY(330);
		}

		move();
		resetDirIfPacMove(pacman);
	}

}
