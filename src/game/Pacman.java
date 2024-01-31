package game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.transform.Rotate;

/**
 * 
 * Playable character in the game. Takes key inputs to move around and keeps
 * track of powerUps, current level etc.
 * 
 * @author nikkn051, loviv462
 *
 */

public class Pacman {

	private double size = 26.0;
	private double speed = 3.5;
	private int points = 12000;
	private double posX;
	private double posY;
	private Tile position;
	private int destinationX;
	private int destinationY;
	private String direction = "";
	private boolean allowed = true;
	private String nextDirection = "";
	private PlayingField playingField;
	private boolean changedDirection = false;
	private boolean powerupActivated = false;
	private boolean powerupReset = true;
	private long powerupStart;
	private long fruitStart;
	private int level = 1;
	private int ghostsKilled;
	private boolean ghostKilled = false;
	private Image pac1;
	private Image pac2;
	private Image pac3;
	private Image pac4;
	private Image currentImage;
	private int imageCounter = 1;
	private boolean gameOver = false;
	private int lives = 3;
	private boolean lostLife = false;
	private boolean speedActivated = false;
	private long startGhostKilled;

	public Pacman(int x, int y, PlayingField playingField) {
		posX = x;
		posY = y;
		destinationX = x;
		destinationY = y;
		this.playingField = playingField;

		try {
			pac1 = new Image(new FileInputStream("pacman1.png"));
			pac2 = new Image(new FileInputStream("pacman2.png"));
			pac3 = new Image(new FileInputStream("pacman3.png"));
			pac4 = new Image(new FileInputStream("pacman4.png"));

		} catch (FileNotFoundException e) {
			System.out.println("Unable to find image-files!");
		}

		currentImage = pac1;
	}

	public void drawYourself(GraphicsContext gc) {

		if (imageCounter == 5) {
			currentImage = pac1;
		} else if (imageCounter == 10 || imageCounter == 30) {
			currentImage = pac2;
		} else if (imageCounter == 15 || imageCounter == 25) {
			currentImage = pac3;
		} else if (imageCounter == 20) {
			currentImage = pac4;
		}

		gc.save();

		if (direction.equals("right")) {
			rotate(gc, 0, posX, posY);
		} else if (direction.equals("up")) {
			rotate(gc, 270, posX, posY);
		} else if (direction.equals("down")) {
			rotate(gc, 90, posX, posY);
		}

		if (direction.equals("left")) {
			gc.translate(currentImage.getWidth() + (posX - size / 2 - 2) * 2, 0);
			gc.scale(-1, 1);
		}

		gc.drawImage(currentImage, posX - size / 2 - 2, posY - size / 2 - 2);

		gc.restore();

	}

	private void rotate(GraphicsContext gc, double angle, double px, double py) {
		Rotate r = new Rotate(angle, px, py);
		gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
	}

	public void posUpdate() {
		if (allowed && !(playingField.getNextTile(direction, position) instanceof Border)) {
			position = playingField.getNextTile(direction, position);
			destinationX = position.getPosX();
			destinationY = position.getPosY();
		}
		moveTo(destinationX, destinationY);

		if (position.getPosX() == 10 && position.getPosY() == 310) {
			position = playingField.getRightTeleportTile();
			posX = position.getPosX() + 20;
			posY = position.getPosY();
			destinationX = position.getPosX();
			destinationY = position.getPosY();

		}
		if (position.getPosX() == 590 && position.getPosY() == 310) {
			position = playingField.getLeftTeleportTile();
			posX = position.getPosX() - 20;
			posY = position.getPosY();
			destinationX = position.getPosX();
			destinationY = position.getPosY();

		}

		imageCounter++;
		if (imageCounter > 30) {
			imageCounter = 1;
		}
	}

	public void changeDirection(KeyEvent key) {
		if (allowed) {
			if (key.getCode() == KeyCode.RIGHT) {
				if (playingField.seeTile("right", position)) {
					direction = "right";
				}
			} else if (key.getCode() == KeyCode.LEFT) {
				if (playingField.seeTile("left", position)) {
					direction = "left";
				}
			} else if (key.getCode() == KeyCode.DOWN) {
				if (playingField.seeTile("down", position)) {
					direction = "down";
				}
			} else if (key.getCode() == KeyCode.UP) {
				if (playingField.seeTile("up", position)) {
					direction = "up";
				}
			}
		} else {
			if (key.getCode() == KeyCode.RIGHT) {
				nextDirection = "right";
			} else if (key.getCode() == KeyCode.LEFT) {
				nextDirection = "left";
			} else if (key.getCode() == KeyCode.DOWN) {
				nextDirection = "down";
			} else if (key.getCode() == KeyCode.UP) {
				nextDirection = "up";
			}
			changedDirection = true;
		}
	}

	public void moveTo(int x, int y) {
		int eaten = 0;
		if (direction.equals("right")) {
			if (posX < x) {
				posX += speed;
				allowed = false;
			} else if (posX >= x) {
				posX = x;
				allowed = true;
				eaten = position.eat();
				if (changedDirection) {
					if (playingField.seeTile(nextDirection, position)) {
						direction = nextDirection;
						changedDirection = false;
					}
				}
			}
		} else if (direction.equals("left")) {
			if (posX > x) {
				posX -= speed;
				allowed = false;
			} else if (posX <= x) {
				posX = x;
				allowed = true;
				eaten = position.eat();
				if (changedDirection) {
					if (playingField.seeTile(nextDirection, position)) {
						direction = nextDirection;
						changedDirection = false;
					}
				}
			}
		} else if (direction.equals("up")) {
			if (posY > y) {
				posY -= speed;
				allowed = false;
			} else if (posY <= y) {
				posY = y;
				allowed = true;
				eaten = position.eat();
				if (changedDirection) {
					if (playingField.seeTile(nextDirection, position)) {
						direction = nextDirection;
						changedDirection = false;
					}
				}
			}
		} else if (direction.equals("down")) {
			if (posY < y) {
				posY += speed;
				allowed = false;
			} else if (posY >= y) {
				posY = y;
				allowed = true;
				eaten = position.eat();
				if (changedDirection) {
					if (playingField.seeTile(nextDirection, position)) {
						direction = nextDirection;
						changedDirection = false;
					}
				}
			}
		}

		if (eaten == 1) {
			points += 10;
		} else if (eaten == 2) {
			ghostsKilled = 0;
			points += 50;
			powerupStart = System.currentTimeMillis();
			powerupActivated = true;
			powerupReset = true;
		} else if (eaten == 3) {
			points += 100;
			speedActivated = true;
			fruitStart = System.currentTimeMillis();
		} else if (eaten == 4) {
			points += 300;
			speedActivated = true;
			fruitStart = System.currentTimeMillis();
		} else if (eaten == 5) {
			points += 500;
			speedActivated = true;
			fruitStart = System.currentTimeMillis();
		}
	}

	public void gameOver() {
		gameOver = true;
	}

	public void killGhost() {
		ghostsKilled++;
		ghostKilled = true;
		points += Math.pow(2, ghostsKilled) * 100;

		if (ghostsKilled == 1) {
			startGhostKilled = System.currentTimeMillis();
		}
	}

	public double getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public Tile getPosition() {
		return position;
	}

	public void setPosition(Tile position) {
		this.position = position;
		posX = position.getPosX();
		posY = position.getPosY();
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String dir) {
		direction = dir;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public boolean isPowerupActivated() {
		return powerupActivated;
	}

	public void setPowerupActivated(boolean powerupActivated) {
		this.powerupActivated = powerupActivated;
	}

	public long getPowerupStart() {
		return powerupStart;
	}

	public long getFruitStart() {
		return fruitStart;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getGhostsKilled() {
		return ghostsKilled;
	}

	public void setGhostsKilled(int ghostsKilled) {
		this.ghostsKilled = ghostsKilled;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public boolean lostLife() {
		return lostLife;
	}

	public void setLostLife(boolean lostLife) {
		this.lostLife = lostLife;
	}

	public Image getPac1() {
		return pac1;
	}

	public boolean isAllowed() {
		return allowed;
	}

	public void setAllowed(boolean allowed) {
		this.allowed = allowed;
	}

	public boolean isPowerupReset() {
		return powerupReset;
	}

	public void setPowerupReset(boolean powerupReset) {
		this.powerupReset = powerupReset;
	}

	public boolean isSpeedActivated() {
		return speedActivated;
	}

	public void setSpeedActivated(boolean speedActivated) {
		this.speedActivated = speedActivated;
	}

	public boolean isGhostKilled() {
		return ghostKilled;
	}

	public void setGhostKilled(boolean ghostKilled) {
		this.ghostKilled = ghostKilled;
	}

	public long getStartGhostKilled() {
		return startGhostKilled;
	}
}
