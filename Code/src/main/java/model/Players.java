package model;

import java.awt.Point;

import model.Enhet;

public class Players extends Enhet
{
	
	public Players(){
		super(new Point(1, 1), 15);
		setImage(Objekt.Player);
		
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

