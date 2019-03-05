import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Collection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.text.ParseException;

public class User {
	private static int numberOfUser = 0;
	private int userId;
	private String name;
	private String userName;
	private String password;
	private Date dateOfBirth;
	private String school;
	private Date lastLogin = null;
	private ArrayList<User> friends = new ArrayList<User>();
	private HashSet<User> blocked = new HashSet<User>();
	private ArrayList<TextPost> posts = new ArrayList<TextPost>();
	private boolean isSignin = false;

	/**
	 * Constructor of User class.
	 * 
	 * @param name
	 *            users name
	 * @param userName
	 *            user uses this name for signing in and most of other commands
	 *            which will be given to Main class
	 * @param password
	 *            users password
	 * @param dateOfBirth
	 *            users date o birth is converted to Date type
	 * @param school
	 *            users school
	 */
	public User(String name, String userName, String password, Date dateOfBirth, String school) {
		this.numberOfUser++;
		this.userId = (this.numberOfUser);
		this.name = name;
		this.userName = userName;
		this.password = password;
		this.school = school;
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return list of users friends
	 */
	public ArrayList<User> getFriends() {
		return this.friends;
	}

	/**
	 * @return set of blocked users
	 */
	public HashSet<User> getBlocked() {
		return this.blocked;
	}

	/**
	 * @return list of posts that posted by calling user
	 */
	public ArrayList<TextPost> getPosts() {
		return this.posts;
	}

	/**
	 * @return users user name
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * @return users user id
	 */
	public int getUserId() {
		return this.userId;

	}

	/**
	 * @return users password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * @return true if signed in.else false
	 */
	public boolean isSignin() {
		return this.isSignin;

	}

	/**
	 * make users is sign in attribute true
	 */
	public void signIn() {
		this.isSignin = true;
	}

	/**
	 * make users is sign in attribute false
	 */
	public void signOut() {
		this.isSignin = false;
	}

	/**
	 * rewrites users name.note that it is not users user name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * rewrites users school
	 */
	public void setSchool(String school) {
		this.school = school;
	}

	/**
	 * rewrites users passsword
	 */
	public void setPassword(String password) {
		this.password = password;

	}

	/**
	 * revalues users birthdate
	 */
	public void setDateOfBirth(String date) {
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date dateOfBirth = format.parse(date);
			this.dateOfBirth = dateOfBirth;
		} catch (ParseException ex) {
			System.out.println("Wrong format for date");

		}
	}

	/**
	 * precondition:all userNames are unique looks whether calling user is same
	 * with the input user. looks equality only by checking their userName
	 * considering userName is unique for every user.
	 * 
	 * @param user
	 *            the user that will be matched
	 * @return true if two user are same. else false
	 */
	public boolean equals(User user) {
		String userName1 = this.userName;
		String userName2 = user.userName;
		if (userName1.equals(userName2)) {
			return true;
		} else
			return false;

	}

	/**
	 * changes users info with the given parameters
	 */
	public void update(String name, String date, String school) {
		this.setName(name);
		this.setDateOfBirth(date);
		this.setSchool(school);
	}

	/**
	 * adds friend to users friends collection
	 * 
	 * @param friend
	 *            the friend that will be added
	 */
	public void addFriend(User friend) {
		this.friends.add(friend);
	}

	/**
	 * removes friend from friends collection
	 * 
	 * @param friend
	 *            the friend that will be removed
	 */
	public void removeFriend(User friend) {
		this.friends.remove(friend);
	}

	/**
	 * in order to find which friends can be tagged
	 * 
	 * @param stags
	 *            string list of usernames that wanted to be tagged
	 * @return tagable users
	 */
	public ArrayList<User> tagList(String[] stags) {
		ArrayList<User> tags = new ArrayList<User>();
		for (String stag : stags) {
			boolean check = false;
			for (User friend : this.friends) {
				String userName = friend.userName;
				if (userName.equals(stag)) {
					tags.add(friend);
					check = true;
				}
			}
			if (!check) {
				System.out.printf("Username %s is not your friend, and will not be tagged!\n", stag);
			}

		}
		return tags;
	}

	/**
	 * adds posts collection a TextPost
	 * 
	 * @param text
	 *            text of the post
	 * @param longitude
	 *            longitude of the place where post has posted
	 * @param latitude
	 *            latitude of the place where post has posted
	 * @param tags
	 *            tagged Users to the post
	 */
	public void postText(String text, String slongitude, String slatitude, ArrayList<User> tags) {
		double longitude = Double.parseDouble(slongitude);
		double latitude = Double.parseDouble(slatitude);
		Location loc = new Location(latitude, longitude);
		TextPost post = new TextPost(text, loc, tags);
		this.posts.add(post);
	}

	/**
	 * adds posts collection a ImagePost
	 * 
	 * @param text
	 *            text of the post
	 * @param longitude
	 *            longitude of the place where post has posted
	 * @param latitude
	 *            latitude of the place where post has posted
	 * @param tags
	 *            tagged Users to the post
	 * @param filePath
	 *            file path to the image
	 * @param resolution
	 *            resolution of the image
	 */
	public void postImage(String text, String slongitude, String slatitude, ArrayList<User> tags, String filePath,
			String resolution) {
		double longitude = Double.parseDouble(slongitude);
		double latitude = Double.parseDouble(slatitude);
		Location loc = new Location(latitude, longitude);
		ImagePost post = new ImagePost(text, loc, tags, filePath, resolution);
		this.posts.add(post);
	}

	/**
	 * adds posts collection a VideoPost
	 * 
	 * @param text
	 *            text of the post
	 * @param longitude
	 *            longitude of the place where post has posted
	 * @param latitude
	 *            latitude of the place where post has posted
	 * @param tags
	 *            tagged Users to the post
	 * @param filePath
	 *            file path to the image
	 * @param videoDuration
	 *            videos length
	 */
	public void postVideo(String text, String slongitude, String slatitude, ArrayList<User> tags, String filePath,
			String videoDuration) {
		double videoDur = Double.parseDouble(videoDuration);
		if (videoDur > VideoPost.maxlength) {
			System.out.println("Error: Your video exceeds maximum allowed duration of 10 minutes.");
		} else {
			double longitude = Double.parseDouble(slongitude);
			double latitude = Double.parseDouble(slatitude);
			Location loc = new Location(latitude, longitude);

			VideoPost post = new VideoPost(text, loc, tags, filePath, videoDur);
			this.posts.add(post);
		}

	}

	/**
	 * removes the last post that user has posted
	 */
	public void removeLastPost() {
		if (this.posts.size() > 0) {
			this.posts.remove(this.posts.size() - 1);
			System.out.println("Your last post has been successfully removed.");
		} else
			System.out.println("Error: You don't have any posts.");
	}

	/**
	 * 
	 * blocks a user. adds blocked user to blocked collection
	 * 
	 * @param user
	 *            blocked user
	 */
	public void block(User user) {
		this.blocked.add(user);
	}

	/**
	 * 
	 * unblocks a user. removes user from blocked collection
	 * 
	 * @param user
	 *            unblocked
	 */

	public void unblock(User user) {
		this.blocked.remove(user);
	}

	/**
	 * displays users info
	 */
	public void displayUser() {
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String date = format.format(this.dateOfBirth);
		System.out.printf("Name: %s\nUsername: %s\nDate of Birth: %s\nSchool: %s\n", this.name, this.userName, date,
				this.school);

	}

}