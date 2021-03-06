package model;

import java.awt.Point;
import java.util.ArrayList;

public class MapObjekt extends Form
{

	//private ArrayList<Direction> blocking = new ArrayList<Direction>();
	private boolean blocking = false;
	private ArrayList<Enhet> enheter = new ArrayList<Enhet>();
	private ArrayList<Item> items = new ArrayList<Item>();
	
	public boolean isBlocking()
	{
		return blocking;
	}
	
	public void setBlocking(boolean blocking)
	{
		this.blocking = blocking;
	}
	
	public int getNrOfEnheter()
	{
		return enheter.size();
	}
	
	public Enhet getEnhetAt(int index)
	{
		
		return enheter.get(index);
	}
	
	public void addEnhet(Enhet enhet)
	{
		enheter.add(enhet);
	}
	
	public boolean isEnheter(Enhet enhet)
	{
		return enheter.contains(enhet);
	}
	
	public int getNrOfItems()
	{
		return items.size();
	}
	
	public void addItem(Item item)
	{
		this.items.add(item);
	}
	
	public Item getItemAt(int index)
	{
		return items.get(index);
	}
	
	public void enhetPicksUpItemAt(int index, Enhet enhet)
	{
		items.get(index).isPicktUp();
		enhet.addItem(items.get(index));
		items.remove(index);
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

