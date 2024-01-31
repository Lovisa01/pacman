package states;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import game.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * 
 * Subclass to GameState that that handles the play state. I.e. while your
 * playing the game. Takes key inputs, etc.
 * 
 * @author nikkn051, loviv462
 *
 */

public class PlayState extends GameState {

	private Image gameImage;
	private Color bgColor;
	private Pacman pacman;
	private PlayingField playingField;
	private Ghost blinky;
	private Ghost inky;
	private Ghost clyde;
	private Ghost pinky;
	private ArrayList<Ghost> ghosts;
	private long powerupStart;
	private long powerupEnd;
	private long powerupElapsedTime;
	private long fruitStart;
	private long fruitEnd;
	private long fruitElapsedTime;
	private boolean lost = false;
	private boolean cherry = true;
	private boolean strawberry = true;
	private boolean orange = true;

	public PlayState(GameModel model) {
		super(model);
		bgColor = Color.BLACK;
		playingField = new PlayingField();
		pacman = new Pacman(50, 50, playingField);
		pacman.setPosition(playingField.getStartPos());
		blinky = new Blinky(Color.RED, playingField, playingField.getBlinkyStartPos(), 540, 40);
		inky = new Inky(Color.AQUA, playingField, playingField.getInkyStartPos(), 550, 610, blinky);
		clyde = new Clyde(Color.ORANGE, playingField, playingField.getClydeStartPos(), 50, 610);
		pinky = new Pinky(Color.PINK, playingField, playingField.getPinkyStartPos(), 50, 50);
		ghosts = new ArrayList<Ghost>();
		ghosts.add(blinky);
		ghosts.add(inky);
		ghosts.add(pinky);
		ghosts.add(clyde);
		playingField.activateCherry();

		try {
			gameImage = new Image(new FileInputStream("Pac-Man.png"));
		} catch (FileNotFoundException e) {
			System.out.println("Unable to find image-files!");
		}
	}

	@Override
	public void update() {
		if (!lost) {
			if (pacman.isPowerupActivated()) {

				if (pacman.isPowerupReset()) {
					for (Ghost ghost : ghosts) {
						ghost.setEatable(true);
						ghost.setPrevDirection("");
					}
					pacman.setPowerupReset(false);
				}

				for (Ghost ghost : ghosts) {
					if (ghost.isEatable()) {
						ghost.scatter(pacman);
					} else {
						ghost.chase(pacman);
					}
				}

				powerupStart = pacman.getPowerupStart();
				powerupEnd = System.currentTimeMillis();
				powerupElapsedTime = powerupEnd - powerupStart;

				if (powerupElapsedTime >= 7000) {
					pacman.setPowerupActivated(false);

				}
			}
			for (Ghost ghost : ghosts) {
				ghost.setSpeed(2);
			}

			if (pacman.isSpeedActivated()) {

				pacman.setSpeed(4.5);
				fruitStart = pacman.getFruitStart();
				fruitEnd = System.currentTimeMillis();
				fruitElapsedTime = fruitEnd - fruitStart;

				if (fruitElapsedTime >= 4500) {
					pacman.setSpeedActivated(false);
					pacman.setSpeed(3.5);

					if (cherry && pacman.getLevel() % 3 == 1) {
						cherry = false;
					}

					if (strawberry && pacman.getLevel() % 3 == 2) {
						strawberry = false;
					}

					if (orange && pacman.getLevel() % 3 == 0) {
						orange = false;
					}
				}
			}

			if (!pacman.isPowerupActivated()) {
				for (Ghost ghost : ghosts) {
					ghost.chase(pacman);
				}
			}
		}

		if (pacman.getLives() <= 0) {
			if (!lost) {
				lost();
				lost = true;
			}
		} else if (pacman.lostLife()) {
			pacman.setLostLife(false);
			lostLife();
		}

		if (!lost) {
			pacman.posUpdate();
		}

		if (playingField.dotsLeft() == 0) {
			pacman.setLevel(pacman.getLevel() + 1);
			reset();

			if (cherry && pacman.getLevel() % 3 == 1) {
				playingField.activateCherry();
			} else if (strawberry && pacman.getLevel() % 3 == 2) {
				playingField.activateStrawberry();
			} else if (orange && pacman.getLevel() % 3 == 0) {
				playingField.activateOrange();

			}
		}
	}

	public void lost() {
		String totalPoints = Integer.toString(pacman.getPoints());
		model.switchState(new GameOverState(model, totalPoints));
	}

	public void reset() {
		playingField.createBorders();
		pacman.setDirection("");
		pacman.setPosition(playingField.getStartPos());
		pacman.setAllowed(true);
		for (Ghost ghost : ghosts) {
			if (ghost == blinky) {
				ghost.setInit(true);
			} else {
				ghost.setWait(true);
			}
			ghost.setPosition(playingField.getGhostStartPos(ghost));
		}

		pacman.setGhostsKilled(0);
	}

	public void lostLife() {
		pacman.setDirection("");
		pacman.setPosition(playingField.getStartPos());
		pacman.setAllowed(true);
		for (Ghost ghost : ghosts) {
			if (ghost == blinky) {
				ghost.setInit(true);
			} else {
				ghost.setWait(true);
			}
			ghost.setPosition(playingField.getGhostStartPos(ghost));
		}

		pacman.setGhostsKilled(0);
	}

	@Override
	public void draw(GraphicsContext g) {
		long timer;
		drawBg(g, bgColor);
		playingField.drawYourself(g);
		pacman.drawYourself(g);
		for (Ghost ghost : ghosts) {
			ghost.drawYourself(g);
		}
		drawPoints(g);
		drawLevel(g);
		drawLives(g);

		if (lost) {
			drawGameOver(g);
		}

		if (pacman.isGhostKilled()) {
			timer = System.currentTimeMillis();
			g.setFill(Color.WHITE);
			g.setFont(new Font(10));
			if (Math.pow(2, pacman.getGhostsKilled()) * 100 > 100) {
				g.fillText("+" + (int) Math.pow(2, pacman.getGhostsKilled()) * 100 + "", pacman.getPosX() + 9,
						pacman.getPosY() - 9);
			}

			if (timer - pacman.getStartGhostKilled() >= 7000) {
				pacman.setGhostKilled(false);
			}
		}
		g.drawImage(gameImage, 650, 250);
		g.setFont(new Font(17));
		g.fillText("press esc to go \nback to menu", 760, 620);
		g.setFont(new Font(25));
	}

	@Override
	public void keyPressed(KeyEvent key) {
		if (!lost) {
			pacman.changeDirection(key);
		}
		if (key.getCode() == KeyCode.ESCAPE) {
			model.switchState(new MenuState(model));
		} else if(key.getCode() == KeyCode.ENTER) {
			pacman.setLevel(pacman.getLevel() + 1);
			reset();

			if (cherry && pacman.getLevel() % 3 == 1) {
				playingField.activateCherry();
			} else if (strawberry && pacman.getLevel() % 3 == 2) {
				playingField.activateStrawberry();
			} else if (orange && pacman.getLevel() % 3 == 0) {
				playingField.activateOrange();

			}
		}
			
	}

	public void mouseClicked(MouseEvent me) {

	}

	@Override
	public void activate() {
		// TODO Auto-generated method stub
	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub

	}

	public void drawPoints(GraphicsContext gc) {
		String points = Integer.toString(pacman.getPoints());

		gc.setFill(Color.WHITE);

		gc.setFont(new Font(25));
		gc.fillText("POINTS: ", 650, 50);
		gc.fillText(points, 750, 50);
	}

	public void drawLevel(GraphicsContext gc) {
		String levels = Integer.toString(pacman.getLevel());

		gc.setFill(Color.WHITE);

		gc.setFont(new Font(25));
		gc.fillText("LEVEL: ", 650, 100);
		gc.fillText(levels, 750, 100);
	}

	public void drawGameOver(GraphicsContext gc) {

		gc.setFill(Color.RED);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setFont(new Font(40));
		gc.fillText("GAME OVER", 300, 320);
		gc.setTextAlign(TextAlignment.LEFT);

	}

	public void drawLives(GraphicsContext gc) {
		gc.setFill(Color.WHITE);

		gc.setFont(new Font(25));
		gc.fillText("LIVES: ", 630, 590);

		for (int i = 0; i < pacman.getLives(); i++) {
			gc.drawImage(pacman.getPac1(), 630 + (35 * i), 610);
		}
	}

}
