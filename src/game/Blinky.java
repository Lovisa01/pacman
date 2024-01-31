package game;

import javafx.scene.paint.Color;

/**
 * 
 * Subclass of Ghost with its own chase behavior
 * 
 * @author nikkn051, loviv462
 *
 */

public class Blinky extends Ghost {

	public Blinky(Color color, PlayingField playingField, Tile position, int scatterPosX, int scatterPosY) {
		super(color, playingField, position, scatterPosX, scatterPosY);
		setWait(false);
	}

	@Override
	public void chase(Pacman pacman) {

		if (intersect(pacman)) {
			pacman.setLives(pacman.getLives() - 1);
			pacman.setLostLife(true);
		}

		setCurrentColor(getColor());

		if (getPosX() == 290 && getPosY() == 250) {
			setInit(false);
		}

		if (isInit()) {
			setTargetY(250);
			setTargetX(290);
		} else {
			setTargetX(pacman.getPosition().getPosX());
			setTargetY(pacman.getPosition().getPosY());

		}

		move();
		resetDirIfPacMove(pacman);
	}

}
