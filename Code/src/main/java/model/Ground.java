package model;

import java.awt.Point;

public class Ground extends MapObjekt {

	public Ground(Point position) {
		super(position);
		setBlocking(false);
	}

}
