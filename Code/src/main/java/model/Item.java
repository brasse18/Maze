package model;

import model.Enhet;

public class Item extends Form
{

	private String name;

	public Item(){
		super();
		this.name = "Name less";
	}
	
	public Item(String name){
		super();
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}

	public boolean useOn(Enhet enhet) {
		// TODO implement me
		return false;
	}

}

