package model;
import model.Vector2d;


public class Button
{

	private String label;
	private Vector2d body;

	public Button(String label, Vector2d body){
		super();
		this.label = label;
		this.body = body;
	}
	
	public String getLabel()
	{
		return label;
	}
	
	public Vector2d getBody()
	{
		return body;
	}

}

