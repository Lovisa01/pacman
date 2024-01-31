package states;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * This class represents the current state of the game. This implementation of a
 * state machine keeps a reference to the current state
 */

public class GameModel {

	private GameState currentState;

	public GameModel() {
		// We start out in the MenuState.
		this.currentState = new MenuState(this);
	}

	/**
	 * Switch to a new state, stored in the 'state' reference.
	 *
	 * This will call 'deactivate' on the current state, then store the new state as
	 * the current state, and finally call 'activate' on the new current state.
	 */
	public void switchState(GameState nextState) {
		currentState.deactivate();
		currentState = nextState;
		currentState.activate();
	}

	/**
	 * Delegates the keyPress from GamePanel to the current state
	 * 
	 * @param key
	 */
	public void keyPressed(KeyEvent key) {
		currentState.keyPressed(key);
	}

	public void mouseClicked(MouseEvent me) {
		currentState.mouseClicked(me);
	}

	/**
	 * The update function is called every iteration of the game loop. it's usually
	 * used to update the games logic e.g. objects position, velocity, etc...
	 */
	public void update() {
		currentState.update();
	}

	/**
	 * @param g Graphics object passed from GamePanel This function delegates
	 *          drawing from the GamePanel to the current state
	 */
	public void draw(GraphicsContext g) {
		currentState.draw(g);
	}
}
