package model;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import model.GameRound;
import model.GameStatus;

public class Game
{

	public GameRound gameRound = new GameRound();
	private GameStatus gameStatus = GameStatus.Stop;

	public Game(){
		super();
	}

	public void save(String fileName) throws FileNotFoundException {
		
		String outMap = "";
		MapObjekt mapObjekt;
		Map map = gameRound.map;
		
		
		if (map.getPlayer().getPosition() == new Point(1, 1))
		{
			System.out.print("gg");
		}
		
		    outMap = map.toString();
			try(  PrintWriter out = new PrintWriter( "map.save" )  ){
			    out.println(outMap);
			}
			
	}

	public void load() {
		
	}

	public void startGame() {
		gameStatus = GameStatus.Runing;
	}

	public void stopGame() {
		gameStatus = GameStatus.Stop;
	}

	public void pauseGame() {
		gameStatus = GameStatus.Pause;
	}
	
	public GameStatus getStatus()
	{
		return gameStatus;
	}
}

