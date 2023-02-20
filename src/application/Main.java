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
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
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
			nextFrame.setFont(new Font("", 75));
			nextFrame.fillText("GAME OVER", 100, 100);
			return;
		}
		
		// moving the position of the body starting from the tail
		for (int i = snake.size() - 1; i >= 1; i--) 
		{
			snake.get(i).setX(snake.get(i - 1).getX());
			snake.get(i).setY(snake.get(i - 1).getY());
		}
		
		if(currentDirection.equals("UP"))
		{
			snake.get(0).setY(snake.get(0).getY() - 1);
			if (snake.get(0).getY() < 0) 
			{
				snake.get(0).setY(height - 1);
			}
		}
		if(currentDirection.equals("DOWN"))
		{
			snake.get(0).setY(snake.get(0).getY() + 1);
			if (snake.get(0).getY() > height - 1) 
			{
				snake.get(0).setY(0);
			}
		}
		if(currentDirection.equals("LEFT"))
		{
			snake.get(0).setX(snake.get(0).getX() - 1);
			if (snake.get(0).getX() < 0) 
			{
				snake.get(0).setX(width - 1);
			}
		}
		if(currentDirection.equals("RIGHT"))
		{
			snake.get(0).setX(snake.get(0).getX() + 1);
			if (snake.get(0).getX() > width - 1) 
			{
				snake.get(0).setX(0);
			}
		}
		
		if (fruit.getX() == snake.get(0).getX() && fruit.getY() == snake.get(0).getY()) 
		{
			snake.add(new SnakeBody(-1, -1));
			produceFruit(fruit, snake);
		}
		
		for (int i = 1; i < snake.size(); i++) 
		{
			if (snake.get(0).getX() == snake.get(i).getX() && 
					snake.get(0).getY() == snake.get(i).getY()) 
			{
				gameOver = true;
			}
		}
		// Reset background color so snake color does not fill up screen
		nextFrame.setFill(Color.BLACK);
		nextFrame.fillRect(0, 0, width * hexSize, height * hexSize);
		
		// Set the color of the fruit's hex for visibility
		nextFrame.setFill(fruit.getColor());
		nextFrame.fillRect(fruit.getX() * hexSize, fruit.getY() * hexSize, hexSize, hexSize);
		
		// Update snake body position with color
		for(SnakeBody body: snake)
		{
			nextFrame.setFill(body.getColor());
			nextFrame.fillRect(body.getX() * hexSize, body.getY() * hexSize, hexSize, hexSize);
		}
		
	}
	
	public static void produceFruit(Fruit fruit, ArrayList<SnakeBody> snake) 
	{
		Boolean intersects = true;
		// place new food over hex with no snake
		while (intersects) 
		{
			// If no matching coordinates, this will break while loop
			intersects = false;
			fruit.setX(random.nextInt(width));
			fruit.setY(random.nextInt(height));
			// Crash Test lol
//			fruit.setX(12);
//			fruit.setY(12);
			
			// Check if any body part is on the same hex as new food
			for (SnakeBody body : snake) 
			{
				if (body.getX() == fruit.getX() && body.getY() == fruit.getY()) 
				{
					// If they intersect, restart loop
					intersects = true;
					break;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}