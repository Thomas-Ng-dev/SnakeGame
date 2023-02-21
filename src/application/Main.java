package application;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application{
	static int width = 25;
	static int height = 25;
	static int hexSize = 25;
	static ArrayList<SnakeBody> snake = new ArrayList<SnakeBody>();
	static Random random = new Random();
	static boolean gameOver = false;
	static String[] direction = {"UP", "DOWN", "LEFT", "RIGHT"};
	static String currentDirection = direction[random.nextInt(direction.length - 1)];
	
	@Override
	public void start(Stage stage) throws Exception {
		
		try
		{
			// Random fruit starting position
			Fruit fruit = new Fruit(random.nextInt(width), random.nextInt(height));
//			Fruit fruit = new Fruit(0, 0);
			StackPane root = new StackPane();
			
			// Set size of window and hex grid
			Canvas canvas = new Canvas(width * hexSize, height * hexSize);
			GraphicsContext graphicsContent = canvas.getGraphicsContext2D();
			graphicsContent.setFill(Color.BLACK);
			graphicsContent.fillRect(0, 0, width * hexSize, height * hexSize);
			
			root.getChildren().add(canvas);
			
			Scene scene = new Scene(root, width * hexSize, height * hexSize);
			
			new AnimationTimer() 
			{
				long lastFrame = 0;
				
				public void handle(long now) 
				{
					if (now - lastFrame > 100000000)
					{
						lastFrame = now;
						nextFrame(graphicsContent, fruit);
					}
				}
			}.start();
			
			// Controls
			scene.setOnKeyPressed(new EventHandler<KeyEvent>()
			{
				@Override
				public void handle(KeyEvent event)
				{
					if(event.getCode() == KeyCode.UP)
					{
						currentDirection = direction[0];
					}
					if(event.getCode() == KeyCode.DOWN)
					{
						currentDirection = direction[1];
					}
					if(event.getCode() == KeyCode.LEFT)
					{
						currentDirection = direction[2];
					}
					if(event.getCode() == KeyCode.RIGHT)
					{
						currentDirection = direction[3];
					}
				}
			});
			
			// Set a random starting position for the snake
			int startXPos = random.nextInt(width);
			int startYPos = random.nextInt(height);
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
	
	public static void nextFrame(GraphicsContext nextFrame, Fruit fruit)
	{
		if (gameOver == true) 
		{
			nextFrame.setFill(Color.RED);
			nextFrame.setFont(new Font("comic sans ms", 50));
			nextFrame.fillText("GAME OVER \nSnake Length: " + snake.size() +
					"\nFruits Eaten: " + (snake.size() - 3), 100, 100);
			return;
		}
		
		Controller.updateBodyPosition(snake);
		Controller.updateHeadPositionAndAllowBorderTraversal(currentDirection, snake, width, height);
		Controller.newFruitAfterConsumptionAndSnakeGrowth(fruit, snake, width, height);
		gameOver = Controller.isSnakeTouchingItself(snake);
		Controller.updateHexColors(nextFrame, fruit, snake, width, height, hexSize);
	}

	
	public static void main(String[] args) {
		launch(args);
	}
}