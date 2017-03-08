package model;

import model.Enhet;

public class Enemy extends Enhet
{
	public Enemy(){
		super();
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

