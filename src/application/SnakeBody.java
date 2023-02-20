package application;

public class SnakeBody {
	// Coordinates on the grid
	private int x;
	private int y;
	
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
}
