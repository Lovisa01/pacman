package game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class FruitTile extends Tile {

	private boolean hasFruit = true;
	private Image cherry;
	private Image strawberry;
	private Image orange;
	private Image currentImage;

	public FruitTile(int x, int y, int i, int j, boolean hasFruit) {
		super(x, y, i, j);
		this.hasFruit = hasFruit;
		setHasDot(false);
		setColor(Color.RED);

		try {
			cherry = new Image(new FileInputStream("cherry.png"));
			strawberry = new Image(new FileInputStream("strawberry.png"));
			orange = new Image(new FileInputStream("orange.png"));
		} catch (FileNotFoundException e) {
			System.out.println("Unable to find image-files!");
		}
		currentImage = cherry;
	}

	public void drawYourself(GraphicsContext gc) {
		if(hasFruit) {
			//gc.setFill(getColor());
			//gc.fillRect(getPosX()-10, getPosY()-10, 20, 20);
			
			gc.drawImage(currentImage, getPosX()-15, getPosY()-15);
		}
	}

	@Override
	public int eat() {
		int ate = 0;
		if (hasFruit && currentImage == cherry) {
			ate = 3;
			hasFruit = false;
		} else if (hasFruit && currentImage == strawberry) {
			ate = 4;
			hasFruit = false;
		} else if (hasFruit && currentImage == orange) {
			ate = 5;
			hasFruit = false;
		}
		return ate;
	}
	
	public void setCurrentImage(String name) {
		if (name.equals("cherry")) {
			currentImage = cherry;
		} else if(name.equals("strawberry")) {
			currentImage = strawberry;
		} else if (name.equals("orange")) {
			currentImage = orange;
		}
	}
	
	public void setHasFruit(boolean hasFruit) {
		this.hasFruit = hasFruit;
	}

}
