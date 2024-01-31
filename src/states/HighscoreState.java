package states;

import static constants.Constants.SCREEN_WIDTH;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

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
 * Subclass to GameState that handles the highscore state. reads information
 * from file and displays highscores in descending order
 * 
 * @author loviv462, nikkn051
 *
 */

public class HighscoreState extends GameState {

	private Color bgColor;
	private Image logo;
	private HashMap<Integer, String> highscores;
	private ArrayList<Integer> scores;

	public HighscoreState(GameModel model) {
		super(model);
		highscores = new HashMap<Integer, String>();
		bgColor = Color.BLACK;

		try {
			logo = new Image(new FileInputStream("pacmanLogo.png"));
		} catch (FileNotFoundException e) {
			System.out.println("Unable to find image-files!");
		}

		try {
			Scanner scanner = new Scanner(new File("text.txt"));
			while (scanner.hasNext()) {
				int points = scanner.nextInt();
				String name = scanner.next();

				highscores.put(points, name);
			}
			scanner.close();

		} catch (FileNotFoundException e) {

		}

		sortKeys();

		try {
			FileWriter writer = new FileWriter(new File("text.txt"));
			for (int i = 0; i < 10; i++) {
				if (i < scores.size()) {
					writer.write(scores.get(i) + " " + highscores.get(scores.get(i)) + "\n");
				}
			}
			writer.close();

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void sortKeys() {
		scores = new ArrayList<Integer>();
		for (Integer i : highscores.keySet()) {
			scores.add(i);
		}
		Collections.sort(scores, Collections.reverseOrder());
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(GraphicsContext g) {
		drawBg(g, bgColor);

		g.drawImage(logo, SCREEN_WIDTH / 2 - logo.getWidth() / 2 + 20, 0);

		drawHighscores(g);
		g.setFill(Color.WHITE);
		g.setTextAlign(TextAlignment.CENTER);
		g.fillText("press esc to continue to menu", 460, 610);
		g.setTextAlign(TextAlignment.LEFT);
	}

	public void drawHighscores(GraphicsContext gc) {
		gc.setFill(Color.WHITE);
		gc.setFont(new Font(20));

		gc.setFill(Color.WHITE);
		gc.fillText("#:", 342, 192);
		gc.fillText("SCORE:", 390, 192);
		gc.fillText("NAME:", 510, 192);
		
		gc.setFont(new Font(25));
		gc.setFill(Color.rgb(255, 23, 50));
		for (int i = 0; i < highscores.size(); i++) {
			if (i < 10) {
				gc.setFill(Color.rgb(255, 30 + 20 * (i + 1), 0));
				gc.setTextAlign(TextAlignment.RIGHT);
				gc.fillText((i + 1) + ":", 360, 195 + (i + 1) * 35);
				gc.setTextAlign(TextAlignment.CENTER);
				gc.fillText(scores.get(i) + " ", 435, 230 + i * 35, 135);
				gc.setTextAlign(TextAlignment.LEFT);
				gc.fillText(highscores.get(scores.get(i)), 510, 230 + i * 35);
				

			}
		}
		gc.setTextAlign(TextAlignment.LEFT);
	}

	@Override
	public void keyPressed(KeyEvent key) {
		if (key.getCode() == KeyCode.ESCAPE || key.getCode() == KeyCode.ENTER) {
			model.switchState(new MenuState(model));
		}
	}

	@Override
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

}
