import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;

public class UserCollection {
	public static Collection<User> users = new ArrayList<User>();

	/**adds user to the system.
	 * does not add if given userName is already exists
	 * @return true if user added. else false*/
	public static boolean addUser(String name, String userName, String password, Date dateOfBirth, String school) {
		for (Iterator<User> iter = users.iterator(); iter.hasNext();) {
			User u = iter.next();
			if (u.getUserName().equals(userName)) {
				return false;
			}

		}
		User user = new User(name, userName, password, dateOfBirth, school);
		users.add(user);
		return true;

	}

	/**removes user to the system.
	 * @return true if user removed. else false*/
	public static boolean removeUser(int userId) {
		for (User u : users) {
			if (u.getUserId() == userId) {
				users.remove(u);
				return true;
			}

		}
		return false;

	}

	/**
	 * precondition: userName must be unique for all users.If not, output could
	 * be not as expected. signs in the user if password and userName matches.
	 * otherwise displays an error message.
	 * 
	 * @param userName
	 *            username of the user that wants to sign in
	 * @param password
	 *            entered password by the user
	 * @return true if the user signed in.else false
	 */
	public static boolean userSignIn(String userName, String password) {
		for (User u : users) {
			if (userName.equals(u.getUserName())) {
				if (password.equals(u.getPassword())) {
					u.signIn();
					System.out.println("You have successfully signed in.");
					return true;
				} else {
					System.out.println("Invalid username or password! Please try again.");
					return false;
				}
			}
		}
		System.out.println("No such user!");
		return false;
	}

	/**
	 * shows posts of a specific user
	 * @param user user whoose post will be shown*/
	public static void showPosts(String userName) {
		for (User u : users) {
			if (userName.equals(u.getUserName())) {
				ArrayList<TextPost> posts = u.getPosts();
				for (TextPost post : posts) {
					post.displayPost();
					System.out.println("----------------------");
				}
			}
		}

	}

}
