package model;

import java.awt.Point;

public class HealthPoison extends Item {
	
	int health;
	
	public HealthPoison(Point position, boolean isOnTheGround,int helth) {
		super(isOnTheGround);
		this.health = helth;
		setPosition(position);
	}


	public HealthPoison(Point position,int helth) {
		super(true);
		this.health = helth;
		setPosition(position);
	}
	
	public int getHealth()
	{
		return health;
	}


	@Override
	public boolean useOn(Enhet enhet) {
		enhet.getHealth(health);
		return true;
	}

	@Override
	public String toString()
	{
		return "1:" + getPosition().x + ":" +  getPosition().y + ":" + getHealth();	
	}
	
}
