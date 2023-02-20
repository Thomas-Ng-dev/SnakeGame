package application;

import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application{
	static int width = 25;
	static int height = 25;
	static int hexSize = 25;
	static ArrayList<SnakeBody> snake = new ArrayList<SnakeBody>();
	static Random randomHex = new Random();
	
	@Override
	public void start(Stage stage) throws Exception {
		try
		{
			StackPane root = new StackPane();
			Canvas canvas = new Canvas(width * hexSize, height * hexSize);
			GraphicsContext graphicsContent = canvas.getGraphicsContext2D();
			root.getChildren().add(canvas);
			Scene scene = new Scene(root, width * hexSize, height * hexSize);
			
			int startXPos = randomHex.nextInt(width);
			int startYPos = randomHex.nextInt(height);
			snake.add(new SnakeBody(startXPos, startYPos));
			snake.add(new SnakeBody(startXPos + 1, startYPos));
			snake.add(new SnakeBody(startXPos + 2, startYPos));
			
			stage.setScene(scene);
			stage.setTitle("Snake Game");
			stage.show();
		}
		catch(Exception e)
		{
			System.out.println(e.getStackTrace());
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}