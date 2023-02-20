package application;

import application.SnakeDemo.Corner;
import javafx.scene.paint.Color;

public class Fruit {
	// Coordinates on the grid
	private int x;
	private int y;
	private Color color = Color.RED;
	
	public Fruit()
	{
		// Default
	}
	
	public Fruit(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public int getX() 
	{
		return x;
	}

	public void setX(int x) 
	{
		this.x = x;
	}

	public int getY() 
	{
		return y;
	}

	public void setY(int y) 
	{
		this.y = y;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
