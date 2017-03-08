package model;

import model.Enhet;

public class Mpc extends Enhet
{

	public Mpc(){
		super();
	}

	@Override
	public boolean isFriendly(Enhet enhet) {
		return true;
	}

}

