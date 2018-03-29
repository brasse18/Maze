package model;
import java.awt.Dimension;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import model.Enhet;
import model.Players;
import model.MapObjekt;
import model.Vector2d;

public class Map
{
	private Vector2d body;// = new Vector2d(0,0,10,20);
	private MapObjekt[][] map;// = new MapObjekt[body.size.width][body.size.height];
	private MapObjekt goal = new Goal(new Point(18, 8));
	private ArrayList<Enhet> enheter = new ArrayList<Enhet>();
	private Enhet player = new Players();
	private Dimension blockSize = new Dimension(80, 80);
	public Enhet enhet;
	
	public Map()
	{
		makeNewMap();
	}
	
	public Map(int inMap[][],Vector2d inBody,Point pleyerPos)
	{

		setMap(inMap, inBody, pleyerPos);
		
	}
	
	public void loadeFromFile(String costomMapFile, int mapNr)  throws IOException
	{
		int newMap[][];
        Point newPlayerPoint;
        Vector2d newBody;
		
		InputStreamReader mapFile = new InputStreamReader(this.getClass().getResourceAsStream("/map/map1.map"));
		FileInputStream fstream;
		DataInputStream in;
		if (mapNr == 0)
		{
			fstream = new FileInputStream(costomMapFile);
			in = new DataInputStream(fstream);
			mapFile = new InputStreamReader(in);
		}
		else if (mapNr == 1)
		{
			mapFile = new InputStreamReader(this.getClass().getResourceAsStream("/map/map1.map"));
		}
		else if (mapNr == 2)
		{
			mapFile = new InputStreamReader(this.getClass().getResourceAsStream("/map/map2.map"));
		}
		else if (mapNr == 3)
		{
			mapFile = new InputStreamReader(this.getClass().getResourceAsStream("/map/map3.map"));
		}
		else if (mapNr == 4)
		{
			mapFile = new InputStreamReader(this.getClass().getResourceAsStream("/map/map4.map"));
		}
		
		BufferedReader br = new BufferedReader(mapFile);
		
		
	    try {
	        String line = br.readLine();
	        String[] size = line.toString().split(":");
	        newBody = new Vector2d(0, 0, Integer.parseInt(size[0]), Integer.parseInt(size[1]));
	        //System.out.println(newBody.size.getWidth() + " " + newBody.size.getHeight());
	        newMap = new int[Integer.parseInt(size[0])][Integer.parseInt(size[1])];
	        
	        // loade map from file
	        for (int y = 0;y<newBody.size.getHeight();y++)
	        {
	        	line = br.readLine();
	        	String[] arrayX = line.toString().split(":");
		        for (int x = 0;x<newBody.size.getWidth();x++)
		        {
		        	newMap[x][y] = Integer.parseInt(arrayX[x]);
		        	//System.out.print(newMap[x][y]);
		        }
		        //System.out.println("");
	        }
	        
	        
	        
	        // loade position for player from file
	        line = br.readLine();
	        String[] player = line.toString().split(":");
	        //System.out.println("Player pos: X:" + player[0] + " Y:" + player[1]);
	        
	        newPlayerPoint = new Point(Integer.parseInt(player[0]),Integer.parseInt(player[1]));
	        
	        // loading in new map
	        setMap(newMap, newBody, newPlayerPoint);
	        
	        
	        // loade enheter from file
	        line = br.readLine();
	        int nrOfEnheter = Integer.parseInt(line.toString());
	        for (int i = 0; i < nrOfEnheter; i++) {
	        	line = br.readLine();
		        String[] enhet = line.toString().split(":");
		        
		        switch (Integer.parseInt(enhet[2])) {
				case 1:
					//System.out.println(enhet[0] + " " + enhet[1] + " " + enhet[3]);
					addEnhet(new Enemy(new Point(Integer.parseInt(enhet[0]), Integer.parseInt(enhet[1])), Integer.parseInt(enhet[3])));
					break;
					
				case 0:
					//System.out.println(enhet[0] + " " + enhet[1] + " " + enhet[3]);
					addEnhet(new Mpc(new Point(Integer.parseInt(enhet[0]), Integer.parseInt(enhet[1])), Integer.parseInt(enhet[3])));
					break;

				default:
					break;
				}
			}
	        
	        
	        // loade item
	        line = br.readLine();
	        int nrOfItems = Integer.parseInt(line.toString());
	        
	        for (int i = 0; i < nrOfItems; i++) {
	        	line = br.readLine();
		        String[] item = line.toString().split(":");
		        
		        switch (Integer.parseInt(item[0])) {
				case 1:
					addItem(new HealthPoison(new Point(Integer.parseInt(item[1]), Integer.parseInt(item[2])), Integer.parseInt(item[3])));
					break;
					
				case 2:
					addItem(new Weapon(Integer.parseInt(item[3]), new Point(Integer.parseInt(item[1]), Integer.parseInt(item[2]))));
					break;

				default:
					break;
				}
			}
	        
	        
	        
	        
	    } finally {
	        br.close();
	    }
	}
	
	public void setMap(int inMap[][],Vector2d inBody,Point pleyerPos)
	{
		enheter.clear();
		System.out.println(enheter.size());
		enheter.add(new Enemy(new Point(6, 6), 10));
		System.out.println(enheter.size());
		
		
		
		body = inBody;
		map = new MapObjekt[body.size.width][body.size.height];
		player.setPosition(pleyerPos);
		
		for (int y = 0;y<body.size.height;y++)
		{
			for (int x = 0;x<body.size.width;x++)
			{
				switch (inMap[x][y])
				{
				case 0:
				{
					map[x][y] = new Ground(new Point(x, y));
				}
				break;
				case 1:
				{
					map[x][y] = new Wall(new Point(x, y));
				}
				break;
				case 2:
				{
					
				}
				break;
				}
				}
		  }
	}
	
	public Enhet[] getEnhet()
	{
		System.out.println(enheter.size());
		Enhet[] arr = new Enhet[enheter.size()];
		
		for (int i = 0; i < enheter.size(); i++) {
			arr[i] = enheter.get(i);
		}
		
		
		return arr;
		
	}
	
	
	public void makeNewMap()
	{
		for (int x = 0;x<body.size.width;x++)
		{
			for (int y = 0;y<body.size.height;y++)
			{
				if (x == 0 || x == body.size.width-1 || y == 0 || y == body.size.height-1)
				{
					map[x][y] = new Wall(new Point(x, y));
				} else {
					map[x][y] = new Ground(new Point(x, y));
				}
			}
		}
		
		for (int i = 0;i<10;i++)
		{
			if (i != 5)
			{
				if (i == 4)
				{
					map[10][i] = new Wall(new Point(10, i));
					map[16][i] = new Wall(new Point(16, i));
					map[17][i] = new Wall(new Point(17, i));
					map[18][i] = new Wall(new Point(18, i));
					
				} else {
					map[10][i] = new Wall(new Point(10, i));
				}
				
			}
		}
		
		
		addEnemysToMap();
		addMpcer();

	}
	
	private void addMpcer()
	{
		addEnhet(new Mpc(new Point(12, 8)));
	}
	
	public String toString()
	{
		String outMap = "";
		
		for (int y = 0;y<body.size.height;y++)
		{
			for (int x = 0;x<body.size.width;x++)
			{
				if (getMap()[x][y].getClass() == Wall.class)
				{
					outMap += "1";
				}
				else if (getMap()[x][y].getClass() == Ground.class)
				{
					outMap += "0";
				}
				if (x < body.size.width-1)
				{
					outMap += ":";
				}
			}
			outMap += "\n";
		}
		//System.out.println(outMap);
		
		return outMap;
	}
	
	public void addEnemysToMap()
	{
		addEnhet(new Enemy(new Point(10, 5), 5));
		//addEnhet(new Enemy(new Point(3,  3), 10));
		addEnhet(new Enemy(new Point(8,  3), 25));
		addEnhet(new Enemy(new Point(18, 2), 50));
		
	}
	
	public void addEnhet(Enhet enhet)
	{
		//System.out.println(enhet.getInfoToString());
		
		if (enhet.getClass() != Players.class)
		{
			enheter.add(enhet);
			map[enhet.getPosition().x][enhet.getPosition().y].addEnhet(enhet);
		}
	}
	
	public void addItem(Item item)
	{
		System.out.println(item.toString());
		map[item.getPosition().x][item.getPosition().y].addItem(item);
	}
	
	public int getNrOfEnheter()
	{
		System.out.println(enheter.isEmpty());
		return enheter.size();
	}
	
	public MapObjekt getGoal()
	{
		return goal;
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
		map = new MapObjekt[body.size.width][body.size.height];
	}

	public MapObjekt[][] getMap() {
		
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
	
	public Point getNextStep(Enhet enhet, Direction direction)
	{
		Point position = new Point();
		
		switch (direction) {
		case up:
		{
			position = new Point(enhet.getPosition().x, enhet.getPosition().y-enhet.getMovementSpeed());
		}
			break;
		case down:
		{
			position = new Point(enhet.getPosition().x, enhet.getPosition().y+enhet.getMovementSpeed());
		}
			break;
		case left:
		{
			position = new Point(enhet.getPosition().x-enhet.getMovementSpeed(), enhet.getPosition().y);
		}
			break;
		case right:
		{
			position = new Point(enhet.getPosition().x+enhet.getMovementSpeed(), enhet.getPosition().y);
		}
			break;

		default:
			break;
		}
		
		return position;
	}
	
	private boolean isPositionOutOfMap(Point position)
	{
		boolean outOfMap = false;
		
		if (position.x < 0 ||  position.x >= body.size.width || position.y < 0 ||  position.y >= body.size.height)
		{
			outOfMap = true;
		}
		
		return outOfMap;
	}
	
	public boolean isOnGoal(Point position)
	{
		boolean isOnGoal = false;
		if (goal.getPosition().x == position.x && goal.getPosition().y == position.y)
		{
			isOnGoal = true;
		}
		
		return isOnGoal;
	}
	
	public Status moveEnhet(Enhet enhet, Direction direction) {
		Status status = Status.Move;
		Point nextStep = getNextStep(enhet, direction);
		
		if (enhet.isDead())
		{
			
		}
		else {
		
		if (enhet.getMovementSpeed() > 1)
		{
			
		} else {
			if (isOnGoal(nextStep))
			{
				status = Status.goal;
				enhet.move(direction);
			} else if (!isPositionOutOfMap(nextStep))
			{
				if (!map[nextStep.x][nextStep.y].isBlocking())
				{
					if (map[nextStep.x][nextStep.y].getNrOfEnheter() != 0)
					{
						if (!map[nextStep.x][nextStep.y].getEnhetAt(0).isFriendly(enhet))
						{
							if (map[nextStep.x][nextStep.y].getEnhetAt(0).isDead())
							{
								enhet.move(direction);
							} else {
								enhet.attack(map[nextStep.x][nextStep.y].getEnhetAt(0));
								map[nextStep.x][nextStep.y].getEnhetAt(0).attack(enhet);
								if (enhet.isDead())
								{
									status = Status.dead;
								} else {
									status = Status.Fight;
								}
								
							}
						} else 
						{
							enhet.move(direction);
						}
					} else 
					{
						if (map[nextStep.x][nextStep.y].getNrOfItems() != 0)
						{
							System.out.println("Pickt up Item");
							map[nextStep.x][nextStep.y].enhetPicksUpItemAt(0, enhet);
							
							status = status.pickUp;
							
						}
						enhet.move(direction);
					}
					
				} else 
				{
					status = Status.Block;
				}
			} else 
			{
				status = Status.Block;
			}
		}
		}
		return status;
	}
	


}
