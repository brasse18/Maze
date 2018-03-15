package model;

import java.awt.Point;

public class Weapon extends Item {
	
	int damage;
	
	public Weapon() {
		super(false);
		damage = 10;
	}
	
	public Weapon(int damage) {
		super(false);
		this.damage = damage;
	}
	
	public Weapon(int damage, Point position) {
		super(true);
		this.damage = damage;
		this.setPosition(position);
	}
	
	public int getDamage()
	{
		return damage;
	}
	
	@Override
	public boolean useOn(Enhet enhet) {
		enhet.takeDamage(damage);
		return true;
	}

	@Override
	public String toString()
	{
		return "2:" + getPosition().x + ":" +  getPosition().y + ":" + getDamage();	
	}
	
	
}
