package model;

import java.awt.Point;

import model.Enhet;

public class Player extends Enhet
{
	
	public Player(){
		super();
		this.setPosition(new Point(1, 1));
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

