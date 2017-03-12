package model;

import java.awt.Point;

public class Goal extends MapObjekt {

	public Goal(Point position) {
		super(position);
		setBlocking(false);
	}

}
