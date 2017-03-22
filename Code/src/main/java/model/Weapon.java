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
	
	@Override
	public boolean useOn(Enhet enhet) {
		enhet.takeDamage(damage);
		return true;
	}

}
