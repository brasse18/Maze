package model;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import model.GameRound;

public class Game
{

	public GameRound gameRound = new GameRound();

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
		
		
		//outMap = map.toString();
			//for (int x = 0;x<gameRound.map.getBody().size.getHeight();x++)
			//{
			//	outMap = gameRound.map.toString(x);
			//}
			//try(  PrintWriter out = new PrintWriter( "map.txt" )  ){
			//    out.println(outMap);
			//}
			
	}

	public void load() {
		String test = "10:20:30:43";
		String[] gg = test.split(":");
		System.out.println(gg[1]);
	}

	public void startGame() {
		// TODO implement me
	}

	public void stopGame() {
		// TODO implement me
	}

	public void pauseGame() {
		// TODO implement me
	}

}

