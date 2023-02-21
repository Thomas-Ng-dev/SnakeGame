package application;

import java.util.ArrayList;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Controller {
	
	
	
	public Controller()
	{
		// default
	}
	
	/**
	 * Moving the body by updating the position of the body parts to the
	 * coordinates of the previous body parts starting from the tail.
	 * @param snake
	 */
	public static void updateBodyPosition(ArrayList<SnakeBody> snake)
	{
		for (int i = snake.size() - 1; i >= 1; i--) 
		{
			snake.get(i).setX(snake.get(i - 1).getX());
			snake.get(i).setY(snake.get(i - 1).getY());
		}
	}
	
	/**
	 * Allows the snake to travel to the other side of the window when it crosses
	 * the window border. Also updates the head's position.
	 * @param currentDirection
	 * @param snake
	 * @param width
	 * @param height
	 */
	public static void updateHeadPositionAndAllowBorderTraversal
		(String currentDirection, ArrayList<SnakeBody> snake, int width, int height)
	{
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
	}
	
	/**
	 * Relocate the fruit and checking that it does not move to a hex
	 * where a snake body part exists already.
	 * @param fruit
	 * @param snake
	 * @param width
	 * @param height
	 */
	private static void produceFruit(Fruit fruit, ArrayList<SnakeBody> snake, 
			int width, int height) 
	{
		Random random = new Random();
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
	
	/**
	 * Add a new SnakeBody when the snake head and fruit are in the 
	 * same coordinates
	 * @param fruit
	 * @param snake
	 * @param width
	 * @param height
	 */
	public static void newFruitAfterConsumptionAndSnakeGrowth(Fruit fruit, ArrayList<SnakeBody> snake,
			int width, int height)
	{
		// Grow when fruit and snake touch
		if (fruit.getX() == snake.get(0).getX() && fruit.getY() == snake.get(0).getY()) 
		{
			snake.add(new SnakeBody(-1, -1));
			produceFruit(fruit, snake, width, height);
		}
	}
	
	/**
	 * Check if the head of the snake is in contact with any snake body part.
	 * @param gameOver
	 * @param snake
	 */
	public static Boolean isSnakeTouchingItself(ArrayList<SnakeBody> snake)
	{
		for (int i = 1; i < snake.size(); i++) 
		{
			if (snake.get(0).getX() == snake.get(i).getX() && 
					snake.get(0).getY() == snake.get(i).getY()) 
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * After each frame, update the colors of every hex to show new
	 * location of snake, fruit and background.
	 * @param nextFrame
	 * @param fruit
	 * @param snake
	 * @param width
	 * @param height
	 * @param hexSize
	 */
	public static void updateHexColors(GraphicsContext nextFrame, Fruit fruit, 
			ArrayList<SnakeBody> snake, int width, int height, int hexSize)
	{
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
}
