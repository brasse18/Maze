package model;
import java.awt.Dimension;
import java.awt.Point;
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
		
		map[18][6].addItem(new HealthPoison(new Point(18, 6)));
		
		player.addItem(new HealthPoison(false));
		player.addItem(new HealthPoison(false));
		player.addItem(new HealthPoison(false));
	}
	
	private void addMpcer()
	{
		addEnhet(new Mpc(new Point(12, 8)));
	}
	
	
	public String toString(int x)
	{
		String outMap = "";
		
		for (int y = 0;y<getBody().size.getWidth();y++)
		{
			if (outMap != "")
			{
				outMap += ":";
			}
			if (getMap()[x][y].isBlocking() == true)
			{
				outMap += "1";
			}
			else if (getMap()[x][y].isBlocking() == false)
			{
				outMap += "2";
			}
		}
		
		return outMap;
	}
	
	private void addEnemysToMap()
	{
		addEnhet(new Enemy(new Point(10, 5), 5));
		addEnhet(new Enemy(new Point(3, 3), 10));
		addEnhet(new Enemy(new Point(8, 3), 25));
		addEnhet(new Enemy(new Point(18, 2), 50));
		
	}
	
	public void addEnhet(Enhet enhet)
	{
		if (enhet.getClass() != Players.class)
		{
			enheter.add(enhet);
			map[enhet.getPosition().x][enhet.getPosition().y].addEnhet(enhet);
		}
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
