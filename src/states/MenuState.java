package states;

import static constants.Constants.SCREEN_WIDTH;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * 
 * Subclass to GameState that handles the menu state. Displays buttons which when pressed will
 * switch to another state
 * 
 * @author loviv462, nikkn051
 *
 */
public class MenuState extends GameState {

	private Color bgColor;
	private PlayState playState;
	private HighscoreState highscoreState;
	private Image logo;
	private Image play;
	private Image highscore;
	private Image exit;

	public MenuState(GameModel model) {
		super(model);
		playState = new PlayState(model);
		highscoreState = new HighscoreState(model);
		bgColor = Color.BLACK;

		try {
			logo = new Image(new FileInputStream("pacmanLogo.png"));
			highscore = new Image(new FileInputStream("highscore.png"));
			play = new Image(new FileInputStream("play.png"));
			exit = new Image(new FileInputStream("exit.png"));
		} catch (FileNotFoundException e) {
			System.out.println("Unable to find image-files!");
		}

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(GraphicsContext g) {
		drawBg(g, bgColor);

		g.drawImage(logo, SCREEN_WIDTH / 2 - logo.getWidth() / 2 + 20, 0);
		g.drawImage(play, SCREEN_WIDTH / 2 - play.getWidth() / 2, 150);
		g.drawImage(highscore, SCREEN_WIDTH / 2 - highscore.getWidth() / 2, 300);
		g.drawImage(exit, SCREEN_WIDTH / 2 - exit.getWidth() / 2, 450);

	}

	@Override
	public void keyPressed(KeyEvent key) {
		/*
		 * if (key.getCode() == KeyCode.ENTER) { model.switchState(playState); } else if
		 * (key.getCode() == KeyCode.ESCAPE) { System.exit(0); }
		 */

	}

	public void mouseClicked(MouseEvent me) {
		if (me.getX() >= 270 && me.getX() <= 650 && me.getY() >= 175 && me.getY() <= 280) {
			model.switchState(playState);
		} else if (me.getX() >= 270 && me.getX() <= 650 && me.getY() >= 340 && me.getY() <= 430) {
			model.switchState(highscoreState);
		} else if (me.getX() >= 270 && me.getX() <= 650 && me.getY() >= 490 && me.getY() <= 570) {
			System.exit(0);
		}
	}

	@Override
	public void activate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub

	}

}
