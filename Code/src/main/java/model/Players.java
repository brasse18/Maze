package model;

import java.awt.Image;
import java.awt.Point;

import model.Enhet;

public class Players extends Enhet
{
	
	public Players(){
		super();
		this.setPosition(new Point(1, 1));
		//setImage();
	}
	
	public Players(Point position, Image image, Image imageDead){
		super();
		this.setPosition(position);
		setImage(image);
		setImageDead(imageDead);
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

