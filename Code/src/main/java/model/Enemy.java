package model;

import java.awt.Image;
import java.awt.Point;

import model.Enhet;

public class Enemy extends Enhet
{
	public Enemy(Point position, int damage, Image imageEnheterArr, Image imageEnheterArr2){
		super(position, damage, imageEnheterArr, imageEnheterArr2);
		
	}
	
	public Enemy(Point position)
	{
		super(position, 5);
		
	}
	
	public Enemy(Point position, int damage)
	{
		super(position, damage);
		
	}

	@Override
	public boolean isFriendly(Enhet enhet) {
		boolean friend = false;
		
		if (enhet.getClass() == this.getClass() || enhet.getClass() == Mpc.class)
		{
			friend = true;
		}
		
		return friend;
	}

}

