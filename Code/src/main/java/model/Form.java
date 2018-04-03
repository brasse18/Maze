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
	
	private Vector2d body = new Vector2d(80,80,1,1);
	private JLabel label = new JLabel("dddddd");
	
	public void setImage(Image image)
	{
		label.setIcon(new ImageIcon(image));
		label.setSize(80, 80);
		label.setLocation(body.position.x*80, body.position.y*80);
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
		label.setLocation(body.position.x*80, body.position.y*80);
		label.repaint();
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
		label.setVisible(true);
	}
	
	public Form(Image image, Image imageDead)
	{
		setImage(image);
		label.setVisible(true);
	}

}

