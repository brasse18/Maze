package model;

import java.awt.Point;

import model.Form;

public abstract class Enhet extends Form 
{
	private int healthPoint;
	private int movementSpeed;
	private int damage;
	public Item[] item;
	
	
	public Enhet(){
		super();
		healthPoint = 100;
		movementSpeed = 1;
		damage = 10;
	}
	
	public Enhet(Point position){
		super();
		setPosition(position);
		healthPoint = 100;
		movementSpeed = 1;
		damage = 10;
	}
	
	public void move(Direction direction)
	{
		switch (direction) {
		case up:
		{
			setPosition(new Point(this.getPosition().x, this.getPosition().y-movementSpeed));
		}
			break;
		case down:
		{
			setPosition(new Point(this.getPosition().x, this.getPosition().y+movementSpeed));
		}
			break;
		case left:
		{
			setPosition(new Point(this.getPosition().x-movementSpeed, this.getPosition().y));
		}
			break;
		case right:
		{
			setPosition(new Point(this.getPosition().x+movementSpeed, this.getPosition().y));
		}
			break;

		default:
			break;
		}
	}
	
	public void takeDamage(int damage)
	{
		healthPoint = healthPoint - damage;
		System.out.println(damage + " damage was taken" + getHealthPoint() + " HP left");
	}

	public int getHealthPoint() {
		return healthPoint;
	}
	
	public String getInfoToString()
	{
		return "HP: " + getHealthPoint() + " DM: " + getDamage();
	}
	
	public int getMovementSpeed() {
		return movementSpeed;
	}

	
	public int getDamage() {
		return damage;
	}
	
	public void attack(Enhet enhet)
	{
		enhet.takeDamage(getDamage());
		System.out.println("Attackt Enemy");
	}

	
	public abstract boolean isFriendly(Enhet enhet);

}

