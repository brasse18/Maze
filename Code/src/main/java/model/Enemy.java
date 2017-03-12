package model;

import java.awt.Point;

import model.Enhet;

public class Enemy extends Enhet
{
	public Enemy(){
		super();
		setImage(Objekt.Enemy);
	}
	
	public Enemy(Point position)
	{
		super(position);
		
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

