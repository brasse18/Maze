package model;

import model.Vector2d;
import model.Objekt;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Form
{
	
	private Vector2d body = new Vector2d(0,0,1,1);
	private JLabel label;
	
	public void setImage(Image image)
	{
		label.setIcon(new ImageIcon(image));
	}
	
	public JLabel getJLabel()
	{
		return this.label;
		
	}
	
	public Point getPosition()
	{
		return body.getPosition();
	}
	
	public void setPosition(Point position)
	{
		this.body.position = position;
		this.label.setLocation(getSize().width*getPosition().x, getSize().height*getPosition().y);
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
	
	public Form(Image image, Image imageDead)
	{
		setImage(image);
		
	}

}

