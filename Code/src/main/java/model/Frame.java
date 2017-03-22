package model;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.print.attribute.standard.JobHoldUntil;
import javax.sound.midi.MidiDevice.Info;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.SourceDataLine;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.omg.CORBA.portable.InputStream;

import com.sun.org.apache.xml.internal.security.utils.HelperNodeList;

import model.Menu;
//import model.Game;
import sun.applet.Main;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

public class Frame extends JFrame implements KeyListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//menyerna f��r spelet skapas h��r
	private Menu menuGame = new Menu(new Vector2d(0, 0, 200, 300));
	private Menu menuPause = new Menu(new Vector2d(0, 0, 200, 300));
	private Menu menuOptions = new Menu(new Vector2d(0, 0, 200, 300));
	
	//dom olika grafiska senerna skapas
	private JPanel panelMenuGame = new JPanel();
	private JPanel panelMenuOption = new JPanel();
	private JPanel panelMenuPause = new JPanel();
	private JPanel panelGame = new JPanel();
	private JPanel panelFrame = new JPanel();
	private JPanel panelWin = new JPanel();
	private JPanel panelGameOver = new JPanel();
	private JPanel panelGameHud = new JPanel();
	
	
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
	
	private Game game;
	
	private Music musik = new Music();
	
	private class JForm extends JLabel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String id;
		private Point position;
		
		public void setId(String id)
		{
			this.id = id;
		}
		
		public String getId()
		{
			return id;
		}
		
		public void setPosition(Point position)
		{
			this.position = position;
		}
		
		public Point getPosition()
		{
			return position;
		}
		
		public JForm()
		{
		}
	}
	
	private class JItem extends JForm
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		
		public JItem(Item item)
		{
			Dimension blockSize = game.gameRound.map.getBlockSize();
			setPosition(item.getPosition());
			if (item.getClass() == HealthPoison.class)
			{
			} else if (item.getClass() == Weapon.class) 
			{

			}
			setIcon(new ImageIcon(imageHealthPoison));
			setId("Item");
			setLocation(blockSize.width*item.getPosition().x, blockSize.height*item.getPosition().y);
			setSize(1*blockSize.width, 1*blockSize.height);
		}
	}
	
	private class JEnhet extends JForm
	{
		
		public JEnhet(Enhet enhet)
		{
			Dimension blockSize = game.gameRound.map.getBlockSize();
			setHorizontalTextPosition(JLabel.CENTER);
			setVerticalTextPosition(JLabel.BOTTOM);
			setFont(new Font(getFont().getName(), Font.BOLD, 15));
			setForeground(Color.BLUE);
			setPosition(enhet.getPosition());
			if (enhet.getClass() == Enemy.class)
			{
				if (enhet.getDamage() == 5) {
					setIcon(new ImageIcon(imageEnemyArmerLite));
				} else if (enhet.getDamage() == 10) {
					setIcon(new ImageIcon(imageEnemyArmerMedium));
					
				} else if (enhet.getDamage() == 25) {
					setIcon(new ImageIcon(imageEnemyArmerHevy));
				} else if (enhet.getDamage() > 25) {
					setIcon(new ImageIcon(imageEnemyArmerBrutal));
				} else {
					setIcon(new ImageIcon(imageEnemy));
				}

				setText("HP: " + enhet.getHealthPoint());
				setId("Enemy");
			} else if (enhet.getClass() == Players.class) 
			{
				setIcon(new ImageIcon(imagePlayer));
				setText("HP: " + enhet.getHealthPoint());
				setId("Player");
			} else if (enhet.getClass() == Mpc.class) {
				setIcon(new ImageIcon(imageMpc));
				setId("MpcX" + enhet.getPosition().getX() + "Y" + enhet.getPosition().getY());
			}
			setLocation(blockSize.width*enhet.getPosition().x, blockSize.height*enhet.getPosition().y);
			setSize(enhet.getSize().width*blockSize.width, enhet.getSize().height*blockSize.height);
		}
	}
	
	
	
	private class JMapObjekt extends JForm
	{
		
		public JMapObjekt(MapObjekt objekt) {
			Dimension blockSize = game.gameRound.map.getBlockSize();
			setLocation(blockSize.width*objekt.getPosition().x, blockSize.height*objekt.getPosition().y);
			setSize(objekt.getSize().width*blockSize.width, objekt.getSize().height*blockSize.height);
			
			if (objekt.getClass() == Wall.class)
			{
				setIcon(new ImageIcon(imageWall));
			} else if (objekt.getClass() == Ground.class) 
			{
				setIcon(new ImageIcon(imageGround));
			} else if (objekt.getClass() == Goal.class) 
			{
				setIcon(new ImageIcon(imageGoal));
			}
			setId("MapX" + objekt.getPosition().getX() + "Y" + objekt.getPosition().getY());
		}
	}
	
	private class Music implements Runnable
	{

		  @SuppressWarnings("resource")
		@Override
		  public void run() {

				AudioPlayer MGP = AudioPlayer.player;
				AudioStream BGM;
					try {
						BGM = new AudioStream(new FileInputStream("/home/brasse/Projekt/Maze/resurs/sound/backrounds.wav"));
						MGP.start(BGM);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
		  }
	}
	
	private class ButtonListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent event)
		{
			String buttonText = event.getActionCommand();
			if(buttonText.equals("Start"))
			{
				panelMenuGame.setVisible(false);
				panelMenuOption.setVisible(false);
				musik.run();
				startGame();
			}
			else if(buttonText.equals("Option"))
			{
				panelMenuGame.setVisible(false);
				panelMenuOption.setVisible(true);
				
			}
			else if(buttonText.equals("Loade"))
			{
				try {
					load();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(buttonText.equals("Save"))
			{
				try {
					save();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(buttonText.equals("Back"))
			{
				panelMenuGame.setVisible(true);
				panelMenuOption.setVisible(false);
			}
			else if(buttonText.equals("Exit"))
			{
				System.exit(0);
			}
		}
	}
	
	public void save() throws FileNotFoundException
	{
		Map map = game.gameRound.map;
		String outString = "";
		
		outString = map.getPlayer().getPosition().x + ":" + map.getPlayer().getPosition().y + ":";
		
		try(  PrintWriter out = new PrintWriter( "map.txt" )  ){
		    out.println(outString);
		}
	}
	
	public void load() throws IOException
	{
		Map map = game.gameRound.map;
		BufferedReader br = new BufferedReader(new FileReader("map.txt"));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        String[] position = sb.toString().split(":");
	        map.getPlayer().setPosition(new Point(Integer.parseInt(position[0]), Integer.parseInt(position[1])));
	        updatePlayer(panelGame);
	    } finally {
	        br.close();
	    }
	}

	public Frame(String name){
		super();
		this.game = new Game();
		loadImageBuffer();
		menuGame.addButton(new Button("Start", new Vector2d(0, 0, 300, 50)));
		menuGame.addButton(new Button("Option", new Vector2d(0, 60, 100, 50)));
		menuGame.addButton(new Button("Exit", new Vector2d(0, 120, 100, 50)));
		
		menuOptions.addButton(new Button("Save", new Vector2d(0, 0, 300, 50)));
		menuOptions.addButton(new Button("Loade", new Vector2d(0, 60, 100, 50)));
		menuOptions.addButton(new Button("Back", new Vector2d(0, 120, 100, 50)));
		
		configFrame(name);
	}
	
	private void loadImageBuffer()
	{
		Dimension blockSize = game.gameRound.map.getBlockSize();
		try {
			bufferWall = ImageIO.read(new File("resurs/image/wall.png"));
			bufferGround = ImageIO.read(new File("resurs/image/ground.jpg"));
			bufferGoal = ImageIO.read(new File("resurs/image/stairs.png"));
			bufferEnhet = ImageIO.read(new File("resurs/image/characters.png"));
			bufferHealthPoison = ImageIO.read(new File("resurs/image/healthPoison.png"));
			
			imageGoal = bufferGoal.getScaledInstance(blockSize.width, blockSize.height, Image.SCALE_SMOOTH);
			imageGround = bufferGround.getScaledInstance(blockSize.width, blockSize.height, Image.SCALE_SMOOTH);
			imageWall = bufferWall.getScaledInstance(blockSize.width, blockSize.height, Image.SCALE_SMOOTH);
			imagePlayer = bufferEnhet.getSubimage(160*1, 136*1, 160, 136).getScaledInstance(blockSize.width-10, blockSize.height-10, Image.SCALE_SMOOTH);
			imageEnemy = bufferEnhet.getSubimage(160*3, 136*2, 160, 136).getScaledInstance(blockSize.width-10, blockSize.height-10, Image.SCALE_SMOOTH);
			imageMpc = bufferEnhet.getSubimage(160*3, 136*1, 160, 136).getScaledInstance(blockSize.width-10, blockSize.height-10, Image.SCALE_SMOOTH);
			imageDead = bufferEnhet.getSubimage(160*4, 136*1, 160, 136).getScaledInstance(blockSize.width, blockSize.height-10, Image.SCALE_SMOOTH);
			imageHealthPoison = bufferHealthPoison.getScaledInstance(blockSize.width, blockSize.height, Image.SCALE_SMOOTH);
			
			imageEnemyArmerLite = bufferEnhet.getSubimage(160*3, 136*2, 160, 136).getScaledInstance(blockSize.width-10, blockSize.height-10, Image.SCALE_SMOOTH);
			imageEnemyArmerMedium = bufferEnhet.getSubimage(160*2, 136*2, 160, 136).getScaledInstance(blockSize.width-10, blockSize.height-10, Image.SCALE_SMOOTH);
			imageEnemyArmerHevy = bufferEnhet.getSubimage(160*1, 136*2, 160, 136).getScaledInstance(blockSize.width-10, blockSize.height-10, Image.SCALE_SMOOTH);
			imageEnemyArmerBrutal = bufferEnhet.getSubimage(160*1, 136*0, 160, 136).getScaledInstance(blockSize.width-10, blockSize.height-10, Image.SCALE_SMOOTH);
			
		} catch (IOException e) {
			System.out.println("Cant loade images");
			e.printStackTrace();
		}
	}
	
	public void configFrame(String name)
	{
		this.setSize(800, 800);
		this.setTitle(name);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.addKeyListener(this);
		this.setFocusable(true);
		
		Map map = game.gameRound.map;
		
		panelGame.setVisible(false);
		panelMenuOption.setVisible(false);
		
		panelFrame.setBackground(Color.BLUE);
		
		panelWin.setBackground(Color.BLACK);
		JLabel labelWin = new JLabel("You Win");
		panelWin.add(labelWin);
		panelWin.setSize(800,100);
		panelWin.setVisible(false);
		
		panelGameOver.setBackground(Color.BLACK);
		JLabel labelGameOver = new JLabel("Game Over");
		panelGameOver.add(labelGameOver);
		panelGameOver.setSize(800,100);
		panelGameOver.setVisible(false);
		
		panelGameHud.setBackground(Color.GREEN);
		panelGameHud.setLocation(0, 700);
		panelGameHud.setSize(300, 100);
		panelGameHud.setVisible(false);
		
		panelGame.setBackground(Color.RED);
		panelGame.setSize(map.getBlockSize().width*map.getBody().size.width, map.getBlockSize().height*map.getBody().size.height);
		panelGame.setLocation((-map.getBlockSize().width*map.getPlayer().getPosition().x)+(this.getSize().width/2)-(map.getBlockSize().height/2), (-map.getBlockSize().height*map.getPlayer().getPosition().y)+(this.getSize().height/2)-(map.getBlockSize().height/2));
		panelGame.addKeyListener(this);
		panelGame.setLayout(null);
		
		panelMenuOption.setBackground(Color.GREEN);
		panelMenuOption.setLayout(new BoxLayout(panelMenuOption, BoxLayout.Y_AXIS));
		panelMenuOption.setBounds(menuOptions.body.position.x, menuOptions.body.position.y, menuOptions.body.size.width, this.getSize().height);
		
		panelMenuPause.setBackground(Color.ORANGE);
		
		panelMenuGame.setBackground(Color.orange);
		panelMenuGame.setLayout(new BoxLayout(panelMenuGame, BoxLayout.Y_AXIS));
		panelMenuGame.setBounds(menuGame.body.position.x, menuGame.body.position.y, menuGame.body.size.width, this.getSize().height);
		
		panelFrame.add(panelMenuGame);
		panelFrame.add(panelMenuOption);
		panelFrame.add(panelGameOver);
		panelFrame.add(panelWin);
		panelFrame.add(panelGameHud);
		panelFrame.add(panelGame);
		panelFrame.setLayout(null);
		
		
		loadMenu(menuGame, panelMenuGame);
		loadMenu(menuOptions, panelMenuOption);
		loadMenu(menuPause, panelMenuPause);
		//loadGame(game, panelGame);
		
		this.add(panelFrame);
		
	}
	
	public void startGame()
	{
		game.gameRound.map.makeNewMap();
		loadGame();
		loadeGameHud();
		panelGame.setVisible(true);
		panelGameHud.setVisible(true);
	}
	
	public void loadMenu(Menu menu, JPanel panel)
	{
		JButton button;
		ButtonListener listner = new ButtonListener();
		for (int i = 0;i<menu.getButtons().size();i++)
		{
			button = new JButton(menu.getButtons().get(i).getLabel());
			button.setSize(menu.getButtons().get(i).getBody().getSize());
			button.setLocation(menu.getButtons().get(i).getBody().getPosition());
			button.addActionListener(listner);
			button.setAlignmentX(Component.CENTER_ALIGNMENT);
			panel.add(button);
		}
	}
	
	public void loadeGameHud()
	{
		Enhet player = game.gameRound.map.getPlayer();
		int healthPoisonNr = 0;
		
		for (int i = 0;i<player.item.size();i++)
		{
			if (player.item.get(i).getClass() == HealthPoison.class)
			{
				healthPoisonNr++;
			}
		}
		
		JForm JHealthPoison = new JForm();
		JHealthPoison.setId("HudHealthPoisons");
		JHealthPoison.setIcon(new ImageIcon(imageHealthPoison));
		JHealthPoison.setText(healthPoisonNr + "X");

		JHealthPoison.setSize(100, 50);
		panelGameHud.add(JHealthPoison);
	}
	
	public void loadGame()
	{
		Enhet player = game.gameRound.map.getPlayer();
		Map map = game.gameRound.map;
		
		JEnhet GUIPlayer = new JEnhet(player);
		JEnhet GUIEnhet;
		JItem GUIItem;
		panelGame.add(GUIPlayer);
		JMapObjekt mapBlock;
		mapBlock = new JMapObjekt(game.gameRound.map.getGoal());
		panelGame.add(mapBlock);
		for (int y = 0;y<game.gameRound.map.getBody().size.height;y++)
		{
			for (int x = 0;x<game.gameRound.map.getBody().size.width;x++)
			{
				mapBlock = new JMapObjekt(game.gameRound.map.getMap()[x][y]);
				if (game.gameRound.map.getMap()[x][y].getNrOfEnheter() != 0)
				{
					for (int i = 0;i<game.gameRound.map.getMap()[x][y].getNrOfEnheter();i++)
					{
						GUIEnhet = new JEnhet(game.gameRound.map.getMap()[x][y].getEnhetAt(i));
						panelGame.add(GUIEnhet);
						
					}
				}
				if (game.gameRound.map.getMap()[x][y].getNrOfItems() != 0)
				{
					for (int i = 0;i<game.gameRound.map.getMap()[x][y].getNrOfItems();i++)
					{
						GUIItem = new JItem(game.gameRound.map.getMap()[x][y].getItemAt(i));
						panelGame.add(GUIItem);
					}
				}
				panelGame.add(mapBlock);
			}
		}
	}
	
	public void updateGui(JPanel panel, String name, Point position)
	{
		Map map = game.gameRound.map;
		for (int i = 0;i<panel.getComponents().length;i++)
		{
			if (panel.getComponent(i).getClass() == JForm.class || panel.getComponent(i).getClass() == JEnhet.class || panel.getComponent(i).getClass() == JItem.class)
			{
				JForm temp = (JForm) panel.getComponent(i);
				
				if (temp.getId() == name)
				{
					if (position.x == temp.getPosition().x && position.y == temp.getPosition().y)
					{
						if (panel.getComponent(i).getClass() == JItem.class)
						{
							System.out.println("Item");
							if (map.getMap()[temp.getPosition().x][temp.getPosition().y].getNrOfItems() == 0)
							{
								panel.remove(i);
							}
						} else if (panel.getComponent(i).getClass() == JEnhet.class) {
							JEnhet tempEnhet = (JEnhet) panel.getComponent(i);
							Enhet enhet = map.getMap()[position.x][position.y].getEnhetAt(0);
							Dimension blockSize = game.gameRound.map.getBlockSize();
							tempEnhet.setLocation(blockSize.width*enhet.getPosition().x, blockSize.height*enhet.getPosition().y);
							if (enhet.isDead())
							{
								tempEnhet.setIcon(new ImageIcon(imageDead));
								tempEnhet.setText("DEAD");
							} else
							{
								tempEnhet.setText("HP: " + enhet.getHealthPoint());
							}
						}
					}
				}
			}
		}
	}
	
	public void updatePlayer(JPanel panel)
	{
		Enhet player = game.gameRound.map.getPlayer();
		Dimension blockSize = game.gameRound.map.getBlockSize();
		int healthPoisonNr = 0;
		for (int i = 0;i<panel.getComponents().length;i++)
		{
			if (panel.getComponent(i).getClass() == JEnhet.class)
			{
				JEnhet temp = (JEnhet) panel.getComponent(i);
				if (temp.getId() == "Player")
				{
					temp.setLocation(blockSize.width*player.getPosition().x, blockSize.height*player.getPosition().y);
					if (player.isDead())
					{
						temp.setIcon(new ImageIcon(imageDead));
						temp.setText("DEAD");
					} else
					{
						temp.setText("HP: " + player.getHealthPoint());
					}
				}
			}
		}
		
		for (int i = 0;i<player.item.size();i++)
		{
			if (player.item.get(i).getClass() == HealthPoison.class)
			{
				healthPoisonNr++;
			}
		}
		
		for (int i = 0;i<panelGameHud.getComponents().length;i++)
		{
			if (panelGameHud.getComponent(i).getClass() == JForm.class)
			{
				JForm temp = (JForm) panelGameHud.getComponent(i);
				if (temp.getId() == "HudHealthPoisons")
				{
					temp.setText(healthPoisonNr + "X");
				}
			}
		}
	}
	
	public void gameOver()
	{
		panelGameOver.setVisible(true);
	}
	
	public void pause()
	{
		
	}
	
	public static void main(String[] args) {
		Frame gui = new Frame("Maze");
		gui.setVisible(true);
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		Enhet player = game.gameRound.map.getPlayer();
		Map map = game.gameRound.map;
		
	    if (key == KeyEvent.VK_ESCAPE) {
	        System.out.println("Pause Game");
	        panelMenuGame.setVisible(true);
	    }
	    
	    if (key == KeyEvent.VK_H) {
	        System.out.println("Try to Using Health Poison");
	        if (player.useHealthPoisonOn(player))
	        {
	        	System.out.println("Uset 1 Health Poison");
	        	updatePlayer(panelGame);
	        	
	        } else {
	        	System.out.println("No Health Poison left");
			}
	    }
	    
	    if (!player.isDead())
	    {
	    if (key == KeyEvent.VK_LEFT) {
	        System.out.println("Try to move Left");
	        Direction direction = Direction.left;
	        switch (map.moveEnhet(player, direction)) {
			case Move:
			{
				System.out.println("Moved Left");
				updatePlayer(panelGame);
				panelGame.setLocation(panelGame.getLocation().x+map.getBlockSize().width, panelGame.getLocation().y);
			}
				break;
			case Block:
			{
				System.out.println("Cant move Left");
			}
				break;
			case Fight:
			{
				System.out.println("Cant move Left you attackt a enemy");
				updateGui(panelGame, "Enemy", map.getNextStep(player, direction));
				updatePlayer(panelGame);
			}
				break;
			case goal:
			{
				System.out.println("You have reast the goal!!!");
				updatePlayer(panelGame);
				panelGame.setLocation(panelGame.getLocation().x+map.getBlockSize().width, panelGame.getLocation().y);
				panelWin.setVisible(true);
			}
				break;
			case dead:
			{
				System.out.println("You are Dead");
				updatePlayer(panelGame);
				updateGui(panelGame, "Enemy", map.getNextStep(player, direction));
				gameOver();
			}
				break;
			case pickUp:
			{
				panelGame.setLocation(panelGame.getLocation().x+map.getBlockSize().width, panelGame.getLocation().y);
				updateGui(panelGame, "Item", map.getNextStep(player, direction));
				updatePlayer(panelGame);
				
			}
				break;

			default:
				break;
			}
	    }

	    if (key == KeyEvent.VK_RIGHT) {
	    	System.out.println("Try to move Right");
	    	Direction direction = Direction.right;
	        switch (map.moveEnhet(player, direction)) {
			case Move:
			{
				System.out.println("Moved Right");
				updatePlayer(panelGame);
				panelGame.setLocation(panelGame.getLocation().x-map.getBlockSize().width, panelGame.getLocation().y);
			}
				break;
			case Block:
			{
				System.out.println("Cant move Right");
			}
				break;
			case Fight:
			{
				System.out.println("Cant move Right you attackt a enemy");
				updateGui(panelGame, "Enemy", map.getNextStep(player, direction));
				updatePlayer(panelGame);
			}
				break;
			case goal:
			{
				System.out.println("You have reast the goal!!!");
				updatePlayer(panelGame);
				panelGame.setLocation(panelGame.getLocation().x-map.getBlockSize().width, panelGame.getLocation().y);
				panelWin.setVisible(true);
			}
			break;
			case dead:
			{
				System.out.println("You are Dead");
				updatePlayer(panelGame);
				updateGui(panelGame, "Enemy", map.getNextStep(player, direction));
				gameOver();
			}
				break;
			case pickUp:
			{
				panelGame.setLocation(panelGame.getLocation().x-map.getBlockSize().width, panelGame.getLocation().y);
				updateGui(panelGame, "Item", map.getNextStep(player, direction));
				updatePlayer(panelGame);
				
			}
				break;

			default:
				break;
			}
	    }

	    if (key == KeyEvent.VK_UP) {
	    	System.out.println("Try to move Up");
	    	Direction direction = Direction.up;
	        switch (map.moveEnhet(player, direction)) {
			case Move:
			{
				System.out.println("Moved Up");
				updatePlayer(panelGame);
				panelGame.setLocation(panelGame.getLocation().x, panelGame.getLocation().y+map.getBlockSize().height);
			}
				break;
			case Block:
			{
				System.out.println("Cant move Up");
			}
				break;
			case Fight:
			{
				System.out.println("Cant move Up you attackt a enemy");
				updateGui(panelGame, "Enemy", map.getNextStep(player, direction));
				updatePlayer(panelGame);
			}
				break;
			case goal:
			{
				System.out.println("You have reast the goal!!!");
				updatePlayer(panelGame);
				panelGame.setLocation(panelGame.getLocation().x, panelGame.getLocation().y+map.getBlockSize().height);
				panelWin.setVisible(true);
			}
				break;
			case dead:
			{
				System.out.println("You are Dead");
				updateGui(panelGame, "Enemy", map.getNextStep(player, direction));
				updatePlayer(panelGame);
				gameOver();
			}
				break;
			case pickUp:
			{
				panelGame.setLocation(panelGame.getLocation().x, panelGame.getLocation().y+map.getBlockSize().height);
				updateGui(panelGame, "Item", map.getNextStep(player, direction));
				updatePlayer(panelGame);
				
			}
				break;

			default:
				break;
			}
	    }

	    if (key == KeyEvent.VK_DOWN) {
	    	System.out.println("Try to move Down");
	    	Direction direction = Direction.down;
	        switch (map.moveEnhet(player, direction)) {
			case Move:
			{
				System.out.println("Moved Down");
				updatePlayer(panelGame);
				panelGame.setLocation(panelGame.getLocation().x, panelGame.getLocation().y-map.getBlockSize().height);
			}
				break;
			case Block:
			{
				System.out.println("Cant move Down");
			}
				break;
			case Fight:
			{
				System.out.println("Cant move Down you attackt a enemy");
				updateGui(panelGame, "Enemy", map.getNextStep(player, direction));
				updatePlayer(panelGame);
			}
				break;
			case goal:
			{
				System.out.println("You have reast the goal!!!");
				updatePlayer(panelGame);
				panelGame.setLocation(panelGame.getLocation().x, panelGame.getLocation().y-map.getBlockSize().height);
				panelWin.setVisible(true);
			}
				break;
			case dead:
			{
				System.out.println("You are Dead");
				updatePlayer(panelGame);
				updateGui(panelGame, "Enemy", map.getNextStep(player, direction));
				gameOver();
			}
				break;
			case pickUp:
			{
				panelGame.setLocation(panelGame.getLocation().x, panelGame.getLocation().y-map.getBlockSize().height);
				updateGui(panelGame, "Item", map.getNextStep(player, direction));
				updatePlayer(panelGame);
				
			}
				break;
				

			default:
				break;
			}   
	    }
	    }
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	


}

