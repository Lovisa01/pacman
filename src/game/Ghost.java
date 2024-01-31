package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * 
 * Superclass that has information regarding the behavior and characteristics of
 * all the ghosts. Ghost is an NPC in the game that either chases pacman or
 * scatters to its "home corner"
 * 
 * @author nikkn051, loviv462
 *
 */

public abstract class Ghost {

	private int posX;
	private int posY;
	private int size = 25;
	private int speed = 2;
	private Color color;
	private Color currentColor;
	private int targetX;
	private int targetY;
	private int scatterPosX;
	private int scatterPosY;
	private String prevDirection = "";
	private PlayingField playingField;
	private Tile position;
	private boolean init = true;
	private boolean wait = true;
	private long start;
	private long end;
	private long elapsedTime;
	private boolean eatable = true;

	public Ghost(Color color, PlayingField playingField, Tile position, int scatterPosX, int scatterPosY) {
		this.color = color;
		this.currentColor = color;
		this.playingField = playingField;
		this.position = position;
		posX = position.getPosX();
		posY = position.getPosY();
		this.scatterPosX = scatterPosX;
		this.scatterPosY = scatterPosY;
	}

	public void drawYourself(GraphicsContext gc) {
		gc.setFill(currentColor);
		gc.fillOval(posX - size / 2, posY - size / 2, size, size);
		gc.fillRect(posX - size / 2, posY, size, 15);
		gc.setFill(Color.WHITE);
		gc.fillOval(posX - 8, posY - 3, 7, 8);
		gc.fillOval(posX + 1, posY - 3, 7, 8);
		gc.setFill(Color.BLACK);
		gc.fillOval(posX - 5, posY - 1, 4, 5);
		gc.fillOval(posX + 3, posY - 1, 4, 5);

	}

	public abstract void chase(Pacman pacman);

	public boolean intersect(Pacman pacman) {
		if (pacman.getPosition().getPosX() - 10 <= posX && pacman.getPosition().getPosX() + 10 >= posX
				&& pacman.getPosition().getPosY() - 10 <= posY && pacman.getPosition().getPosY() + 10 >= posY) {
			return true;
		} else {
			return false;
		}
	}

	public void scatter(Pacman pacman) {
		if (!init && !wait) {
			this.targetX = scatterPosX;
			this.targetY = scatterPosY;
		}

		setCurrentColor(Color.BLUE);

		if (posX == position.getPosX() && posY == position.getPosY()) {
			position = getNextTile(position, prevDirection);
		}
		moveTo(position.getPosX(), position.getPosY());

		if (posX == 290 && posY == 250) {
			init = false;
		}

		if (intersect(pacman)) {
			pacman.killGhost();
			setPosition(playingField.getGhostStartPos(this));
			init = true;
			eatable = false;
		}
	}

	public void move() {
		if (posX == position.getPosX() && posY == position.getPosY()) {
			position = getNextTile(position, prevDirection);
		}
		moveTo(position.getPosX(), position.getPosY());
	}

	public void resetDirIfPacMove(Pacman pacman) {
		if (pacman.getDirection().equals("right") && prevDirection.equals("left")) {
			prevDirection = "";
		}
		if (pacman.getDirection().equals("left") && prevDirection.equals("right")) {
			prevDirection = "";
		}
		if (pacman.getDirection().equals("up") && prevDirection.equals("down")) {
			prevDirection = "";
		}
		if (pacman.getDirection().equals("down") && prevDirection.equals("up")) {
			prevDirection = "";
		}
	}

	private Tile getNextTile(Tile tmpPos, String prevDir) {
		Tile right = null;
		Tile left = null;
		Tile up = null;
		Tile down = null;
		double rDist = 10000;
		double lDist = 10000;
		double dDist = 10000;
		double uDist = 10000;

		if (playingField.seeTile("right", tmpPos)) {
			right = playingField.getNextTile("right", tmpPos);
			if (right.getRowIndex() == 15 && right.getColumnIndex() == 29) {
				right = null;
			}
		}
		if (playingField.seeTile("left", tmpPos)) {
			left = playingField.getNextTile("left", tmpPos);
			if (left.getRowIndex() == 15 && left.getColumnIndex() == 0) {
				left = null;
			}
		}
		if (playingField.seeTile("down", tmpPos)) {
			down = playingField.getNextTile("down", tmpPos);
		}
		if (playingField.seeTile("up", tmpPos)) {
			up = playingField.getNextTile("up", tmpPos);
		}

		if (right != null && !prevDirection.equals("left")) {
			rDist = Math.sqrt(Math.pow(targetX - right.getPosX(), 2) + Math.pow(targetY - right.getPosY(), 2));
		}
		if (left != null && !prevDirection.equals("right")) {
			lDist = Math.sqrt(Math.pow(targetX - left.getPosX(), 2) + Math.pow(targetY - left.getPosY(), 2));
		}
		if (down != null && !prevDirection.equals("up")) {
			dDist = Math.sqrt(Math.pow(targetX - down.getPosX(), 2) + Math.pow(targetY - down.getPosY(), 2));
		}
		if (up != null && !prevDirection.equals("down")) {
			uDist = Math.sqrt(Math.pow(targetX - up.getPosX(), 2) + Math.pow(targetY - up.getPosY(), 2));
		}

		if (rDist <= dDist && rDist <= uDist && rDist <= lDist && right != null && !prevDirection.equals("left")) {
			prevDirection = "right";
			return right;
		}
		if (lDist <= uDist && lDist <= dDist && lDist <= rDist && left != null && !prevDirection.equals("right")) {
			prevDirection = "left";
			return left;
		}
		if (dDist <= lDist && dDist <= rDist && dDist <= uDist && down != null && !prevDirection.equals("up")) {
			prevDirection = "down";
			return down;
		}
		if (uDist <= rDist && uDist <= lDist && uDist <= dDist && up != null && !prevDirection.equals("down")) {
			prevDirection = "up";
			return up;
		}

		prevDirection = "";
		return tmpPos;
	}

	private void moveTo(int x, int y) {
		if (posX < x) {
			posX += speed;
		}
		if (posX > x) {
			posX -= speed;
		}
		if (posY > y) {
			posY -= speed;
		}
		if (posY < y) {
			posY += speed;
		}
	}

	public void setPosition(Tile position) {
		this.position = position;
		posX = position.getPosX();
		posY = position.getPosY();
	}

	public int getTargetX() {
		return targetX;
	}

	public void setTargetX(int targetX) {
		this.targetX = targetX;
	}

	public int getTargetY() {
		return targetY;
	}

	public void setTargetY(int targetY) {
		this.targetY = targetY;
	}

	public void setPrevDirection(String prevDirection) {
		this.prevDirection = prevDirection;
	}

	public String getPrevDirection() {
		return prevDirection;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getPosY() {
		return posY;
	}

	public Tile getPosition() {
		return position;
	}

	public void setTilePos(Tile pos) {
		position = pos;
	}

	public boolean isInit() {
		return init;
	}

	public void setInit(boolean init) {
		this.init = init;
	}

	public PlayingField getPlayingField() {
		return playingField;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isWait() {
		return wait;
	}

	public void setWait(boolean wait) {
		this.wait = wait;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	public long getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public boolean isEatable() {
		return eatable;
	}

	public void setEatable(boolean eatable) {
		this.eatable = eatable;
	}

	public void setCurrentColor(Color currentColor) {
		this.currentColor = currentColor;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getSpeed() {
		return speed;
	}

	public int getScatterPosX() {
		return scatterPosX;
	}

	public int getScatterPosY() {
		return scatterPosY;
	}
}
