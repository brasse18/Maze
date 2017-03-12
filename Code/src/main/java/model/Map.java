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
	private MapObjekt[][] map = new MapObjekt[body.size.width][body.size.height];
	private ArrayList<Enhet> enheter = new ArrayList<Enhet>();
	private Enhet player = new Player();
	private Dimension blockSize = new Dimension(70, 70);
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
		addEnemysToMap();
	}
	
	
	
	private void addEnemysToMap()
	{
		addEnhet(new Enemy(new Point(4, 3)));
	}
	
	public void addEnhet(Enhet enhet)
	{
		if (enhet.getClass() != Player.class)
		{
			enheter.add(enhet);
			map[enhet.getPosition().x][enhet.getPosition().y].addEnhet(enhet);
		}
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
	
	public Status moveEnhet(Enhet enhet, Direction direction) {
		Status status = Status.Move;
		Point nextStep = getNextStep(enhet, direction);
		
		if (enhet.getMovementSpeed() > 1)
		{
			
		} else {
			if (!isPositionOutOfMap(nextStep))
			{
				if (!map[nextStep.x][nextStep.y].isBlocking())
				{
					if (map[nextStep.x][nextStep.y].getNrOfEnheter() != 0)
					{
						if (!map[nextStep.x][nextStep.y].getEnhetAt(0).isFriendly(enhet))
						{
							enhet.attack(map[nextStep.x][nextStep.y].getEnhetAt(0));
							status = Status.Fight;
						}
					} else {
						enhet.move(direction);
					}
					
				} else {
					status = Status.Block;
				}
			} else {
				status = Status.Block;
			}
		}
		return status;
	}

}
