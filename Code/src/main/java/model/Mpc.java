package model;

import java.awt.Image;
import java.awt.Point;

import model.Enhet;

public class Mpc extends Enhet
{

	public Mpc(Point point, int damage, Image imageEnheterArr, Image imageEnheterArr2){
		super(point, damage, imageEnheterArr, imageEnheterArr2);
		//setImage(Objekt.Enemy);
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

