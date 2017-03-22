package model;

import model.Enhet;

public class Item extends Form
{

	private String name;
	private boolean isOnTheGround = false;

	public Item(boolean isOnTheGround){
		super();
		this.name = "Name less";
		this.isOnTheGround = isOnTheGround;
	}
	
	public Item(boolean isOnTheGround, String name){
		super();
		this.name = name;
		this.isOnTheGround = isOnTheGround;
	}
	
	public boolean isOnTheGround()
	{
		return isOnTheGround;
	}
	
	public void isPicktUp()
	{
		isOnTheGround = false;
	}
	
	public String getName()
	{
		return name;
	}

	public boolean useOn(Enhet enhet) {
		// TODO implement me
		return false;
	}

}

