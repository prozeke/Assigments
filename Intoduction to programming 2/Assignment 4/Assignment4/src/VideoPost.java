import java.util.ArrayList;


public class VideoPost extends TextPost{
	public static final int  maxlength = 10;
	private String fileName;
	private double videoDur;
	
	/**
	 * Constructor of the ImagePost class
	 * @param text text of the post
	 * @param location location of the post
	 * @param tags tags for this post 
	 * @param fileName file name of the image
	 * @param VideoDur duration of the video*/
	public VideoPost(String text,Location location, ArrayList<User> tags,String fileName,double videoDur){
		super(text,location,tags);
		this.fileName = fileName;
		this.videoDur = videoDur;
	}

	public void displayPost(){
		ArrayList<User> tags = this.getTags();
		this.showText();
		this.showDate();
		this.showLocation();
		String sDuration;
		sDuration = Double.toString(this.videoDur);
		System.out.printf("Video: %s\nVideo duration: %s minutes\n", this.fileName,sDuration);
		if(tags.size()>0)
			this.showTags();
	}

}
