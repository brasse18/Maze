package model;

import java.awt.Point;

import model.Enhet;

public class Mpc extends Enhet
{

	public Mpc(){
		super();
		setImage(Objekt.Enemy);
	}
	
	public Mpc(Point position)
	{
		super(position, 5);
	}
	
	public Mpc(Point position, int damage)
	{
		super(position, damage);
	}

	@Override
	public boolean isFriendly(Enhet enhet) {
		return true;
	}

}

