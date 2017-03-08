package model;

import java.awt.Dimension;
import java.awt.Point;

public class Vector2d {
	
	Point position = new Point();
	Dimension size = new Dimension();
	
	Vector2d(int x, int y, int width, int height)
	{
		position.setLocation(x, y);
		size.setSize(width, height);
	}
	
	Vector2d()
	{

	}
	
	public Dimension getSize()
	{
		return size;
	}
	
	public Point getPosition()
	{
		return position;
	}
	
}
