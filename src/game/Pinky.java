package game;

import javafx.scene.paint.Color;

/**
 * 
 * Subclass of Ghost with its own chase behavior
 * 
 * @author nikkn051, loviv462
 *
 */

public class Pinky extends Ghost {

	public Pinky(Color color, PlayingField playingField, Tile position, int scatterPosX, int scatterPosY) {
		super(color, playingField, position, scatterPosX, scatterPosY);
		setInit(false);
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

		if (isWait()) {
			setTargetY(getPlayingField().getPinkyStartPos().getPosY());
			setTargetX(getPlayingField().getPinkyStartPos().getPosX());
			setPrevDirection("left");

			if (getPlayingField().dotsLeft() <= 230) {
				setWait(false);
				setInit(true);
			}

		} else if (isInit()) {
			setTargetY(250);
			setTargetX(290);
		} else if (!isInit() && !isWait()) {

			if (pacman.getDirection().equals("right")) {
				for (int i = 0; i <= 4; i++) {
					if (!getPlayingField().isBorder(pacman.getPosition().getRowIndex(),
							pacman.getPosition().getColumnIndex() + 4 - i)) {
						setTargetX((pacman.getPosition().getColumnIndex() + 4 - i) * 20 + 10);
						setTargetY((pacman.getPosition().getRowIndex()) * 20 + 10);
						break;
					}
				}
			} else if (pacman.getDirection().equals("left")) {
				for (int i = 0; i <= 4; i++) {
					if (!getPlayingField().isBorder(pacman.getPosition().getRowIndex(),
							pacman.getPosition().getColumnIndex() - 4 + i)) {
						setTargetX((pacman.getPosition().getColumnIndex() - 4 + i) * 20 + 10);
						setTargetY((pacman.getPosition().getRowIndex()) * 20 + 10);
						break;
					}
				}
			} else if (pacman.getDirection().equals("up")) {
				for (int i = 0; i <= 4; i++) {
					if (!getPlayingField().isBorder(pacman.getPosition().getRowIndex() - 4 + i,
							pacman.getPosition().getColumnIndex())) {
						setTargetX((pacman.getPosition().getColumnIndex()) * 20 + 10);
						setTargetY((pacman.getPosition().getRowIndex() - 4 + i) * 20 + 10);
						break;
					}
				}
			} else if (pacman.getDirection().equals("down")) {
				for (int i = 0; i <= 4; i++) {
					if (!getPlayingField().isBorder(pacman.getPosition().getRowIndex() + 4 - i,
							pacman.getPosition().getColumnIndex())) {
						setTargetX((pacman.getPosition().getColumnIndex()) * 20 + 10);
						setTargetY((pacman.getPosition().getRowIndex() + 4 - i) * 20 + 10);
						break;
					}
				}
			}
		}

		move();

		resetDirIfPacMove(pacman);
	}

}
