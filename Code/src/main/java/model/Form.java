package model;

import model.Vector2d;
import model.Objekt;

import java.awt.Dimension;
import java.awt.Point;

public class Form
{
	
	private Vector2d body = new Vector2d(0,0,1,1);
	private String image;
	
	public void setImage(Objekt objekt)
	{
		
	}
	
	public Point getPosition()
	{
		return body.getPosition();
	}
	
	public void setPosition(Point position)
	{
		this.body.position = position;
	}
	
	public Dimension getSize()
	{
		return body.size;
	}
	
	public void setSize(Dimension size)
	{
		this.body.size = size;
	}
	
	public Form(){
		super();
	}

}

