import java.util.ArrayList;

public class ImagePost extends TextPost {
	private String fileName;
	private String res;

	/**
	 * Constructor of the ImagePost class
	 * @param text text of the post
	 * @param location location of the post
	 * @param tags tags for this post 
	 * @param fileName file name of the image
	 * @param res resolution of the image with string representation*/
	public ImagePost(String text, Location location, ArrayList<User> tags, String fileName, String res) {
		super(text, location, tags);
		this.fileName = fileName;
		this.res = res;
	}

	
	public void displayPost(){
		ArrayList<User> tags = this.getTags();
		this.showText();
		this.showDate();
		this.showLocation();
		System.out.printf("Image: %s\nImage resolution: %s\n", this.fileName,this.res);
		if(tags.size()>0)
			this.showTags();
	}


}
