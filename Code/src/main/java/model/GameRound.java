package model;

import java.awt.Point;

import model.Map;

public class GameRound
{

	public Map map = new Map(new Vector2d(0, 0, 20, 10));
	
	public GameRound(){
		super();
		
	}
	
	public void loadMap(int newMap[][], Vector2d vector2d, Point playerPoint)
	{
		map = new Map(newMap, vector2d, playerPoint);
	}

	public boolean gameOver() {
		// TODO implement me
		return false;
	}

	public boolean gameWin() {
		// TODO implement me
		return false;
	}

	public void gameBattle(Enhet enhetOne, Enhet enhetTwo) {
		// TODO implement me
	}

}

