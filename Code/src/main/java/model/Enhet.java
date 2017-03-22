package model;

import java.awt.Point;
import java.util.ArrayList;

import model.Form;

public abstract class Enhet extends Form 
{
	private int healthPoint;
	private int movementSpeed;
	private int damage;
	private boolean dead = false;
	private Item aktiveWeapon = null;
	public ArrayList<Item> item = new ArrayList<Item>();
	
	
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
	
	public Enhet(Point position, int damage){
		super();
		setPosition(position);
		healthPoint = 100;
		movementSpeed = 1;
		this.damage = damage;
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
	
	public boolean isDead()
	{
		return dead;
	}
	
	public void takeDamage(int damage)
	{
		if (healthPoint <= 0)
		{
			dead = true;
		} else
		{
			healthPoint = healthPoint - damage;
		}
		System.out.println(damage + " damage was taken " + getHealthPoint() + " HP left");
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
		if (aktiveWeapon != null)
		{
			aktiveWeapon.useOn(enhet);
		} else {
			enhet.takeDamage(getDamage());
		}
		System.out.println("Attackt Enemy");
	}
	
	public boolean useHealthPoisonOn(Enhet enhet)
	{
		boolean out = false;
		for (int i = 0;i<item.size();i++)
		{
			if (item.get(i).getClass() == HealthPoison.class)
			{
				out = item.get(i).useOn(enhet);
				item.remove(i);
				i = item.size()+1;
			}
		}
		return out;
	}
	
	public void getHealth(int health)
	{
		healthPoint += health;
		System.out.println("Got" + health + " helth now have " + getHealthPoint() + " Helth");
	}
	
	public void addItem(Item item)
	{
		this.item.add(item);
	}
	
	public void setAktiveWeapon(Weapon weapon)
	{
		aktiveWeapon = weapon;
	}
	
	public abstract boolean isFriendly(Enhet enhet);

}

