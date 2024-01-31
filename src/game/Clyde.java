package game;

import javafx.scene.paint.Color;

/**
 * 
 * Subclass of Ghost with its own chase behavior
 * 
 * @author nikkn051, loviv462
 *
 */

public class Clyde extends Ghost {

	public Clyde(Color color, PlayingField playingField, Tile position, int scatterPosX, int scatterPosY) {
		super(color, playingField, position, scatterPosX, scatterPosY);
		setStart(System.currentTimeMillis());
		setInit(false);
	}

	@Override
	public void chase(Pacman pacman) {

		if (intersect(pacman)) {
			pacman.setLives(pacman.getLives() - 1);
			pacman.setLostLife(true);
		}

		setCurrentColor(getColor());

		if (pacman.getLevel() >= 3) {

			if (getPosX() == 290 && getPosY() == 250) {
				setInit(false);
			}

			double x = (pacman.getPosition().getPosX() - getPosition().getPosX());
			double y = (pacman.getPosition().getPosY() - getPosition().getPosY());

			double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));

			if (isWait()) {
				setTargetX(270);
				setTargetY(290);

				if (getPlayingField().dotsLeft() <= 240) {
					setWait(false);
					setInit(true);
				}
			} else if (isInit()) {
				setTargetY(250);
				setTargetX(290);
			} else if (distance < 160) {
				setTargetX(pacman.getPosition().getPosX());
				setTargetY(pacman.getPosition().getPosY());
			} else {
				setTargetX(getScatterPosX());
				setTargetY(getScatterPosY());
			}
		} else {
			setTargetX(270);
			setTargetY(290);
		}

		move();
		resetDirIfPacMove(pacman);

	}

}
