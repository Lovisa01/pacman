package states;

import static constants.Constants.SCREEN_WIDTH;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * Subclass to GameState that handles game over state when the user has lost the game. 

 * takes the name of user ad input and writes the name and score to a file
 * 
 * @author nikkn051, loviv462
 */

public class GameOverState extends GameState {

	private Color bgColor;
	private Image logo;
	private String username = "";
	private String totalPoints;

	public GameOverState(GameModel model, String totalPoints) {
		super(model);
		this.totalPoints = totalPoints;
		bgColor = Color.BLACK;

		try {
			logo = new Image(new FileInputStream("pacmanLogo.png"));
		} catch (FileNotFoundException e) {
			System.out.println("Unable to find image-files!");
		}
	}

	public void draw(GraphicsContext g) {
		drawBg(g, bgColor);
		g.setFill(Color.WHITE);
		g.fillText("YOUR SCORE: ", 230, 240);
		g.fillText(totalPoints, 400, 240);
		g.fillText("ENTER NAME: ", 230, 300);
		g.fillText(username, 400, 300);

		g.drawImage(logo, SCREEN_WIDTH / 2 - logo.getWidth() / 2 + 20, 0);
	}

	public void keyPressed(KeyEvent key) {
		if (key.getCode() == KeyCode.ENTER) {

			try {
				FileWriter writer = new FileWriter(new File("text.txt"), true);
				writer.write(totalPoints + " " + username + "\n");
				writer.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}

			model.switchState(new HighscoreState(model));

		} else if (key.getCode() == KeyCode.BACK_SPACE) {
			if (username.length() > 0) {
				username = username.substring(0, username.length() - 1);
			}
		} else {
			if (key.getCode().isLetterKey()) {
				username += key.getCode();
			}
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent me) {
		// TODO Auto-generated method stub

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
