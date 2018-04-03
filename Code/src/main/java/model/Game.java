package model;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

import model.GameRound;
import model.GameStatus;

public class Game
{

	public GameRound gameRound;
	private GameStatus gameStatus = GameStatus.Stop;
	
	private BufferedImage bufferWall;
	private BufferedImage bufferGround;
	private BufferedImage bufferGoal;
	private BufferedImage bufferEnhet;
	private BufferedImage bufferHealthPoison;
	private Image imageGoal;
	private Image imageGround;
	private Image imageWall;
	private Image imagePlayer;
	private Image imageEnemy;
	private Image imageMpc;
	private Image imageDead;
	private Image imageEnemyArmerLite;
	private Image imageEnemyArmerMedium;
	private Image imageEnemyArmerHevy;
	private Image imageEnemyArmerBrutal;
	private Image imageHealthPoison;
	
	private Image[] imageItemArr;
	private Image[] imageEnheterArr;
	private Image[] imageMappArr;

	public Game() throws IOException{
		super();
		
		loadeAllImages();
		gameRound = new GameRound(imageItemArr, imageEnheterArr, imageMappArr);
		
	}
	
	public void loadeAllImages()
	{
		Dimension blockSize = new Dimension(80, 80);
		try {
			bufferWall = ImageIO.read(Frame.class.getResourceAsStream("/image/wall.png"));
			bufferGround = ImageIO.read(Frame.class.getResourceAsStream("/image/ground.jpg"));
			bufferGoal = ImageIO.read(Frame.class.getResourceAsStream("/image/stairs.png"));
			bufferEnhet = ImageIO.read(Frame.class.getResourceAsStream("/image/characters.png"));
			bufferHealthPoison = ImageIO.read(Frame.class.getResourceAsStream("/image/healthPoison.png"));
			
			imageGoal = bufferGoal.getScaledInstance(blockSize.width, blockSize.height, Image.SCALE_SMOOTH);
			imageGround = bufferGround.getScaledInstance(blockSize.width, blockSize.height, Image.SCALE_SMOOTH);
			imageWall = bufferWall.getScaledInstance(blockSize.width, blockSize.height, Image.SCALE_SMOOTH);
			
			imageMappArr = new Image[3];
			imageMappArr[0] = imageGoal;
			imageMappArr[1] = imageGround;
			imageMappArr[2] = imageWall;
			
			
			imageHealthPoison = bufferHealthPoison.getScaledInstance(blockSize.width, blockSize.height, Image.SCALE_SMOOTH);
			
			imageItemArr = new Image[1];
			imageItemArr[0] = imageHealthPoison;
			
			
			imagePlayer = bufferEnhet.getSubimage(160*1, 136*1, 160, 136).getScaledInstance(blockSize.width-10, blockSize.height-10, Image.SCALE_SMOOTH);
			imageEnemy = bufferEnhet.getSubimage(160*3, 136*2, 160, 136).getScaledInstance(blockSize.width-10, blockSize.height-10, Image.SCALE_SMOOTH);
			imageMpc = bufferEnhet.getSubimage(160*3, 136*1, 160, 136).getScaledInstance(blockSize.width-10, blockSize.height-10, Image.SCALE_SMOOTH);
			imageDead = bufferEnhet.getSubimage(160*4, 136*1, 160, 136).getScaledInstance(blockSize.width, blockSize.height-10, Image.SCALE_SMOOTH);
			
			
			
			imageEnemyArmerLite = bufferEnhet.getSubimage(160*3, 136*2, 160, 136).getScaledInstance(blockSize.width-10, blockSize.height-10, Image.SCALE_SMOOTH);
			imageEnemyArmerMedium = bufferEnhet.getSubimage(160*2, 136*2, 160, 136).getScaledInstance(blockSize.width-10, blockSize.height-10, Image.SCALE_SMOOTH);
			imageEnemyArmerHevy = bufferEnhet.getSubimage(160*1, 136*2, 160, 136).getScaledInstance(blockSize.width-10, blockSize.height-10, Image.SCALE_SMOOTH);
			imageEnemyArmerBrutal = bufferEnhet.getSubimage(160*1, 136*0, 160, 136).getScaledInstance(blockSize.width-10, blockSize.height-10, Image.SCALE_SMOOTH);
			
			imageEnheterArr = new Image[8];
			imageEnheterArr[0] = imagePlayer;
			imageEnheterArr[1] = imageEnemy;
			imageEnheterArr[2] = imageMpc;
			imageEnheterArr[3] = imageDead;
			imageEnheterArr[4] = imageEnemyArmerLite;
			imageEnheterArr[5] = imageEnemyArmerMedium;
			imageEnheterArr[6] = imageEnemyArmerHevy;
			imageEnheterArr[7] = imageEnemyArmerBrutal;
			
			
		} catch (IOException e) {
			System.out.println("Cant loade images");
			e.printStackTrace();
		}
	}

	public void save(String fileName) throws FileNotFoundException {
		
		String outMap = "";
		MapObjekt mapObjekt;
		
		if (gameRound != null)
		{
			
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
			
	}

	public void load() {
		
	}

	public void startGame() throws IOException {
		if (gameRound != null)
		{
			gameRound = new GameRound(imageItemArr, imageEnheterArr, imageMappArr);
		}
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

