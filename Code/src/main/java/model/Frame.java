package model;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
	private Game game = new Game();
	
	private class JEnhet extends JLabel
	{
		private String id;
		
		public void setId(String id)
		{
			this.id = id;
		}
		
		public String getId()
		{
			return id;
		}
		
		public JEnhet(Enhet enhet)
		{
			BufferedImage myPicture;
			String image = "/home/brasse/Projekt/Maze/image/player.jpg";
			Dimension blockSize = game.gameRound.map.getBlockSize();
			if (enhet.getClass() == Player.class)
			{
				image = "/home/brasse/Projekt/Maze/image/player.jpg";
				setText("Player");
				setId("Player");
			} else if (enhet.getClass() == Enemy.class) {
				image = "/home/brasse/Projekt/Maze/image/enemy.jpg";
				setText("Enemy");
				setId("Enemy");
			}
			setLocation(blockSize.width*enhet.getPosition().x, blockSize.height*enhet.getPosition().y);
			setSize(enhet.getSize().width*blockSize.width, enhet.getSize().height*blockSize.height);
			try {
				myPicture = ImageIO.read(new File(image));
				setIcon(new ImageIcon(myPicture));
			} catch (IOException e) {
				System.out.println("Cant loade image " + image);
				e.printStackTrace();
			}
		}
	}
	
	private class JMapObjekt extends JLabel
	{
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
			BufferedImage myPicture;
			String image = "/home/brasse/Projekt/Maze/image/ground.jpg";
			Dimension blockSize = game.gameRound.map.getBlockSize();
			setLocation(blockSize.width*objekt.getPosition().x, blockSize.height*objekt.getPosition().y);
			setSize(objekt.getSize().width*blockSize.width, objekt.getSize().height*blockSize.height);
			setId("MapX" + objekt.getPosition().getX() + "Y" + objekt.getPosition().getY());
			try {
				myPicture = ImageIO.read(new File(image));
				setIcon(new ImageIcon(myPicture));
			} catch (IOException e) {
				System.out.println("Cant loade image " + image);
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
		menuGame.addButton(new Button("Start", new Vector2d(0, 0, 300, 50)));
		menuGame.addButton(new Button("Option", new Vector2d(0, 60, 100, 50)));
		menuGame.addButton(new Button("Exit", new Vector2d(0, 120, 100, 50)));
		
		menuOptions.addButton(new Button("Save", new Vector2d(0, 0, 300, 50)));
		menuOptions.addButton(new Button("Loade", new Vector2d(0, 60, 100, 50)));
		menuOptions.addButton(new Button("Back", new Vector2d(0, 120, 100, 50)));
		
		
		configFrame(name);
	}
	
	public void configFrame(String name)
	{
		this.setSize(600, 600);
		this.setTitle(name);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.addKeyListener(this);
		this.setFocusable(true);
		
		
		panelGame.setVisible(false);
		panelMenuOption.setVisible(false);
		
		panelFrame.setBackground(Color.BLUE);
		
		panelGame.setBackground(Color.RED);
		panelGame.setSize(600, 400);
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
		
		JEnhet JPlayer = new JEnhet(player);
		panelGame.add(JPlayer);
		
		JMapObjekt mapBlock;
		for (int y = 0;y<game.gameRound.map.getBody().size.height;y++)
		{
			for (int x = 0;x<game.gameRound.map.getBody().size.width;x++)
			{
				mapBlock = new JMapObjekt(map.getMap().get(x).get(y));
				panelGame.add(mapBlock);
			}
		}
	}
	
	public void updateGui(JPanel panel, String name)
	{
		for (int i = 0;i<panel.getComponents().length;i++)
		{
			//System.out.println(panel.getComponent(i).getName());
			if (panel.getComponent(i).getClass() == JEnhet.class)
			{
				JEnhet temp = (JEnhet) panel.getComponent(i);
				System.out.println(temp.getId());
				if (temp.getId() == name)
				{
					Enhet player = game.gameRound.map.getPlayer();
					Dimension blockSize = game.gameRound.map.getBlockSize();
					temp.setLocation(blockSize.width*player.getPosition().x, blockSize.height*player.getPosition().y);
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

	    if (key == KeyEvent.VK_LEFT) {
	        System.out.println("Move Left");
	        game.gameRound.map.getPlayer().move(Direction.left);
	        updateGui(panelGame, "Player");
	    }

	    if (key == KeyEvent.VK_RIGHT) {
	    	System.out.println("Move Right");
	        game.gameRound.map.getPlayer().move(Direction.right);
	        updateGui(panelGame, "Player");
	    }

	    if (key == KeyEvent.VK_UP) {
	    	System.out.println("Move Up");
	        game.gameRound.map.getPlayer().move(Direction.up);
	        updateGui(panelGame, "Player");
	    }

	    if (key == KeyEvent.VK_DOWN) {
	    	System.out.println("Move Down");
	        game.gameRound.map.getPlayer().move(Direction.down);
	        updateGui(panelGame, "Player");
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

