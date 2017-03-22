package model;

import java.awt.Point;

public class HealthPoison extends Item {
	
	int health;
	
	public HealthPoison(boolean isOnTheGround)
	{
		super(isOnTheGround);
		health = 30;
	}
	
	public HealthPoison(boolean isOnTheGround,int helth) {
		super(isOnTheGround);
		this.health = helth;
	}


	public HealthPoison(Point position) {
		super(true);
		setPosition(position);
	}


	@Override
	public boolean useOn(Enhet enhet) {
		enhet.getHealth(health);
		return true;
	}

}
