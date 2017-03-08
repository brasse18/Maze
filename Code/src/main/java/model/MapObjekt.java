package model;

import java.awt.Point;
import java.util.ArrayList;

public class MapObjekt extends Form
{

	private ArrayList<Direction> blocking = new ArrayList<Direction>();
	private ArrayList<Enhet> enheter = new ArrayList<Enhet>();
	private ArrayList<Item> items = new ArrayList<Item>();
	
	public int getNrOfBlocking()
	{
		return blocking.size();
	}
	
	public boolean isBlocking(Direction direction)
	{
		return blocking.contains(direction);
	}
	
	public int getNrOfEnheter()
	{
		return enheter.size();
	}
	
	public boolean isEnheter(Enhet enhet)
	{
		return enheter.contains(enhet);
	}
	
	public int getNrOfItems()
	{
		return items.size();
	}
	
	public boolean isItems(Item item)
	{
		return items.contains(item);
	}
	
	public MapObjekt(Point position){
		super();
		setPosition(position);
	}

}

