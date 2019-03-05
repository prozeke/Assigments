import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TextPost extends Post {
	
	/**
	 * Constructor of the TextPost class
	 * @param text text of the post
	 * @param location location of the post
	 * @param tags tags for this post */
	
	public TextPost(String text, Location location, ArrayList<User> tags) {
		super(text, location, tags);
	}

	@Override
	/**
	 * shows posts tags
	 */
	public void showTags() {
		ArrayList<User> tags = this.getTags();
		String show = "";
		for (User u : tags) {
			show = show + " " + u.getUserName();
		}
		System.out.printf("Friends tagged in this post:%s\n", show);
	}

	@Override
	/**
	 * shows posts location
	 */
	public void showLocation() {
		Location location = this.getLocation();
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		String slatitude = Double.toString(latitude);
		String slongitude = Double.toString(longitude);
		System.out.printf("Location: %s, %s\n", slatitude, slongitude);
	}

	/**
	 * shows posts date
	 */
	public void showDate() {
		Date date = this.getDate();
		DateFormat format = new SimpleDateFormat("mm.dd.yyyy");
		String sDate = format.format(date);
		System.out.printf("Date: %s\n", sDate);

	}

	/**
	 * shows posts text
	 */
	public void showText() {
		System.out.println(this.getText());
	}

	/**
	 * displays posts info
	 */
	public void displayPost() {
		ArrayList<User> tags = this.getTags();
		this.showText();
		this.showDate();
		this.showLocation();
		if (tags.size() > 0)
			this.showTags();

	}

}
