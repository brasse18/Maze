package model;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import model.Enhet;
import model.Player;
import model.MapObjekt;
import model.Vector2d;

public class Map
{
	private Vector2d body = new Vector2d(0,0,10,10);
	private ArrayList<ArrayList<MapObjekt>> map = new ArrayList<ArrayList<MapObjekt>>();
	//private MapObjekt[][] map = new MapObjekt[body.size.width][body.size.height];
	private ArrayList<Enhet> enheter = new ArrayList<Enhet>();
	private Enhet player = new Player();
	private Dimension blockSize = new Dimension(50, 50);
	public Enhet enhet;
	
	public Map()
	{
		makeNewMap();
	}
	
	public void makeNewMap()
	{
		for (int x = 0;x<body.size.width;x++)
		{
			map.add(new ArrayList<MapObjekt>());
			for (int y = 0;y<body.size.height;y++)
			{
				map.get(x).add(new MapObjekt(new Point(x, y)));
			}
		}
	}
	
	public void print()
	{
		for (int x = 0;x<body.size.width;x++)
		{
			for (int y = 0;y<body.size.height;y++)
			{
				System.out.println(map.get(x).get(y));
			}
		}
	}
	
	public void addEnhet(Enhet enhet)
	{
		enheter.add(enhet);
	}
	
	public Enhet getPlayer()
	{
		return player;
	}
	
	public Vector2d getBody()
	{
		return body;
	}
	
	public Dimension getBlockSize()
	{
		
		return blockSize;
	}
	
	public Map(Vector2d body){
		super();
		this.body = body;
	}

	public ArrayList<ArrayList<MapObjekt>> getMap() {
		
		return map;
	}

	
	public Enhet getEnheter(Point position) {
		int id = 0;
		for (int i = 0;i<enheter.size();i++)
		{
			if (enheter.get(i).getPosition() == position)
			{
				id = i;
			}
		}
		return enheter.get(id);
	}
	
	public boolean moveEnhet(Enhet enhet, String direction) {
		enhet.setPosition(new Point(enhet.getPosition().x+1,enhet.getPosition().y+1));
		return true;
	}

}
