package model;

import java.awt.Image;
import java.awt.Point;

import model.Map;

public class GameRound
{

	public Map map = new Map(new Vector2d(0, 0, 20, 10));
	private Image[] imageItemArr;
	private Image[] imageEnheterArr;
	private Image[] imageMappArr;
	
	public GameRound(Image[] imageItemArr, Image[] imageEnheterArr, Image[] imageMappArr){
		super();
		map.setImageArr(imageItemArr, imageEnheterArr, imageMappArr);
		
	}
	
	public void loadMap(int newMap[][], Vector2d vector2d, Point playerPoint)
	{
		map.setMap(newMap, vector2d, playerPoint);
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

