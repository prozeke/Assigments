import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public abstract class Post implements PostInterface {
	private UUID postId;
	private String text;
	private Date date;
	private Location location;
	private ArrayList<User> tags;
	
	public Post(String text,Location location, ArrayList<User> tags){
		this.text = text;
		this.location = location;
		this.tags = tags;
		this.date = new Date();
	}
	
	
	/**
	 * sets text of the post
	 * @param text text to be setted*/
	public void setText(String text){
		this.text = text;
	}
	
	/**
	 * @return text of the post*/
	public String getText(){
		return this.text;
	}

	/**
	 * @return date of the post*/
	public Date getDate(){
		return this.date;
	} 

	public  abstract void showTags();
	
	public abstract void showLocation();
	
	public Location getLocation(){
		return this.location;
	}
	
	
	
	public ArrayList<User>  getTags(){
		return this.tags;
	} 
}
