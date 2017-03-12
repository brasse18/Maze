package model;

import java.awt.Point;

public class Wall extends MapObjekt {

	public Wall(Point position) {
		super(position);
		setBlocking(true);
	}

}
