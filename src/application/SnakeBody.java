package application;

import javafx.scene.paint.Color;

public class SnakeBody {
	// Coordinates on the grid
	private int x;
	private int y;
	private Color color = Color.WHITE;
	
	public SnakeBody()
	{
		// Default
	}
	
	public SnakeBody(int x, int y)
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
