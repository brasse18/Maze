package model;
import model.Button;

import java.util.ArrayList;


public class Menu
{
	private ArrayList<Button> buttons = new ArrayList<Button>();
	public Vector2d body = new Vector2d();
	
	
	public ArrayList<Button> getButtons()
	{
		return buttons;
	}
	
	public void addButton(Button button)
	{
		buttons.add(button);
	}
	
	public Menu(){
		super();
	}
	
	public Menu(Vector2d body){
		super();
		this.body = body;
	}

}

