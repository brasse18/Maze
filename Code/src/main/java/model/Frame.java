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
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import model.Menu;
//import model.Game;

public class Frame extends JFrame implements KeyListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//menyerna f��r spelet skapas h��r
	private Menu menuGame = new Menu(new Vector2d(0, 0, 200, 300));
	//private Menu menuPause = new Menu(new Vector2d(0, 0, 200, 300));
	private Menu menuOptions = new Menu(new Vector2d(0, 0, 200, 300));
	
	//dom olika grafiska senerna skapas
	private JPanel panelMenuGame = new JPanel();
	private JPanel panelMenuOption = new JPanel();
	//private JPanel panelMenuPause = new JPanel();
	private JPanel panelGame = new JPanel();
	private JPanel panelFrame = new JPanel();
	
	BufferedImage bufferWall;
	BufferedImage bufferGround;
	BufferedImage bufferGoal;
	BufferedImage bufferPlayer;
	BufferedImage bufferEnemy;
	BufferedImage bufferMpc;
	
	private Game game;
	
	
	private class JEnhet extends JLabel
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
				setIcon(new ImageIcon(bufferEnemy.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
				setText("HP: " + enhet.getHealthPoint());
				setId("Enemy");
			} else if (enhet.getClass() == Player.class) 
			{
				setIcon(new ImageIcon(bufferPlayer.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
				setText("HP: " + enhet.getHealthPoint());
				setId("Player");
			} else if (enhet.getClass() == Mpc.class) {
				setIcon(new ImageIcon(bufferMpc.getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
				setId("MpcX" + enhet.getPosition().getX() + "Y" + enhet.getPosition().getY());
			}
			setLocation(blockSize.width*enhet.getPosition().x, blockSize.height*enhet.getPosition().y);
			setSize(enhet.getSize().width*blockSize.width, enhet.getSize().height*blockSize.height);
		}
	}
	
	
	
	private class JMapObjekt extends JLabel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String id;
		
		public void setId(String id)
		{
			this.id = id;
		}
		
		public String getId()
		{
			return id;
		}
		
		public JMapObjekt(MapObjekt objekt) {
			Dimension blockSize = game.gameRound.map.getBlockSize();
			setLocation(blockSize.width*objekt.getPosition().x, blockSize.height*objekt.getPosition().y);
			setSize(objekt.getSize().width*blockSize.width, objekt.getSize().height*blockSize.height);
			
			if (objekt.getClass() == Wall.class)
			{
				setIcon(new ImageIcon(bufferWall.getScaledInstance(70, 70, Image.SCALE_SMOOTH)));
			} else if (objekt.getClass() == Ground.class) 
			{
				setIcon(new ImageIcon(bufferGround.getScaledInstance(70, 70, Image.SCALE_SMOOTH)));
			} else if (objekt.getClass() == Goal.class) 
			{
				System.out.println("Goal");
				setIcon(new ImageIcon(bufferGoal.getScaledInstance(70, 70, Image.SCALE_SMOOTH)));
			}
			setId("MapX" + objekt.getPosition().getX() + "Y" + objekt.getPosition().getY());
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
				startGame();
			}
			else if(buttonText.equals("Option"))
			{
				panelMenuGame.setVisible(false);
				panelMenuOption.setVisible(true);
				
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
		
		try {
			bufferWall = ImageIO.read(new File("/home/brasse/Projekt/Maze/image/wall.png"));
			bufferGround = ImageIO.read(new File("/home/brasse/Projekt/Maze/image/ground.jpg"));
			bufferGoal = ImageIO.read(new File("/home/brasse/Projekt/Maze/image/stairs.png"));
			bufferPlayer = ImageIO.read(new File("/home/brasse/Projekt/Maze/image/player.jpg"));
			bufferEnemy = ImageIO.read(new File("/home/brasse/Projekt/Maze/image/enemy.png"));
			bufferMpc = ImageIO.read(new File("/home/brasse/Projekt/Maze/image/mpc.png"));
			
			
			
		} catch (IOException e) {
			System.out.println("Cant loade images");
			e.printStackTrace();
		}
	
	}
	
	public void configFrame(String name)
	{
		this.setSize(700, 700);
		this.setTitle(name);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.addKeyListener(this);
		this.setFocusable(true);
		
		panelGame.setVisible(false);
		panelMenuOption.setVisible(false);
		
		panelFrame.setBackground(Color.BLUE);
		
		panelGame.setBackground(Color.RED);
		panelGame.setSize(700, 700);
		panelGame.addKeyListener(this);
		panelGame.setLayout(null);
		
		panelMenuOption.setBackground(Color.GREEN);
		panelMenuOption.setLayout(new BoxLayout(panelMenuOption, BoxLayout.Y_AXIS));
		panelMenuOption.setBounds(menuOptions.body.position.x, menuOptions.body.position.y, menuOptions.body.size.width, this.getSize().height);
		
		panelMenuGame.setBackground(Color.orange);
		panelMenuGame.setLayout(new BoxLayout(panelMenuGame, BoxLayout.Y_AXIS));
		panelMenuGame.setBounds(menuGame.body.position.x, menuGame.body.position.y, menuGame.body.size.width, this.getSize().height);
		
		panelFrame.add(panelMenuGame);
		panelFrame.add(panelMenuOption);
		panelFrame.add(panelGame);
		panelFrame.setLayout(null);
		
		loadMenu(menuGame, panelMenuGame);
		loadMenu(menuOptions, panelMenuOption);
		//loadGame(game, panelGame);
		
		this.add(panelFrame);
		
	}
	
	public void startGame()
	{
		game.gameRound.map.makeNewMap();
		loadGame();
		panelGame.setVisible(true);
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
	
	public void loadGame()
	{
		Enhet player = game.gameRound.map.getPlayer();
		Map map = game.gameRound.map;
		
		JEnhet GUIPlayer = new JEnhet(player);
		JEnhet GUIEnhet;
		panelGame.add(GUIPlayer);
		
		JMapObjekt mapBlock;
		mapBlock = new JMapObjekt(map.getGoal());
		panelGame.add(mapBlock);
		for (int y = 0;y<game.gameRound.map.getBody().size.height;y++)
		{
			for (int x = 0;x<game.gameRound.map.getBody().size.width;x++)
			{
				mapBlock = new JMapObjekt(map.getMap()[x][y]);
				//System.out.println(map.getMap()[x][y].getNrOfEnheter());
				if (map.getMap()[x][y].getNrOfEnheter() != 0)
				{
					for (int i = 0;i<map.getMap()[x][y].getNrOfEnheter();i++)
					{
						GUIEnhet = new JEnhet(map.getMap()[x][y].getEnhetAt(i));
						panelGame.add(GUIEnhet);
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
			if (panel.getComponent(i).getClass() == JEnhet.class)
			{
				JEnhet temp = (JEnhet) panel.getComponent(i);
				if (temp.getId() == name)
				{
					if (position.x == temp.getPosition().x && position.y == temp.getPosition().y)
					{
						Enhet enhet = map.getEnheter(position);
						Dimension blockSize = game.gameRound.map.getBlockSize();
						temp.setLocation(blockSize.width*enhet.getPosition().x, blockSize.height*enhet.getPosition().y);
						temp.setText("HP: " + enhet.getHealthPoint());
					}

				}
			}
		}
	}
	
	public void updatePlayer(JPanel panel)
	{
		for (int i = 0;i<panel.getComponents().length;i++)
		{
			//System.out.println(panel.getComponent(i).getName());
			if (panel.getComponent(i).getClass() == JEnhet.class)
			{
				JEnhet temp = (JEnhet) panel.getComponent(i);
				if (temp.getId() == "Player")
				{
					Enhet player = game.gameRound.map.getPlayer();
					Dimension blockSize = game.gameRound.map.getBlockSize();
					temp.setLocation(blockSize.width*player.getPosition().x, blockSize.height*player.getPosition().y);
					temp.setText("HP: " + player.getHealthPoint());
				}
			}
		}
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
		
	    if (key == KeyEvent.VK_LEFT) {
	        System.out.println("Try to move Left");
	        Direction direction = Direction.left;
	        switch (map.moveEnhet(player, direction)) {
			case Move:
			{
				System.out.println("Moved Left");
				updatePlayer(panelGame);
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
			}
				break;
			case goal:
			{
				System.out.println("You have reast the goal!!!");
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
			}
				break;
			case goal:
			{
				System.out.println("You have reast the goal!!!");
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
			}
				break;
			case goal:
			{
				System.out.println("You have reast the goal!!!");
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
			}
				break;
			case goal:
			{
				System.out.println("You have reast the goal!!!");
				updatePlayer(panelGame);
			}
				break;

			default:
				break;
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

