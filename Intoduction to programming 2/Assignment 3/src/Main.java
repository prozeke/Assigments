import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Main {
	public static boolean anySign = false;
	public static User signedUser = null;

	/**
	 * returns the list back but makes x number of member from start dissapear.
	 * lists length will be x times less
	 * @param l firstlist
	 * @param x how much less
	 * @return lesser list*/
	public static String[] makeless(String[] l, int x) {
		String[] rlist = new String[l.length - x];
		for (int i = 1; i < l.length; i++)
			rlist[i - x] = l[i];
		return rlist;
	}

	/**
	 * sign a users to the system.
	 * only one user can be signed at a time.
	 * displays a message which tell whether user signed in or not.
	 * if not shows why.
	 * @param userName users userName
	 * @param password users passwords */
	public static User signIn(String userName, String password) {
		if (UserCollection.userSignIn(userName, password)) {
			for (User user : UserCollection.users) {
				if (user.isSignin()) {
					anySign = true;
					return user;
				}
			}
		}
		User noneUser = null;
		return noneUser;

	}
	
	/**
	 * adds friend to signed user
	 * @param signedUser user that signed in
	 * @param userName username of the added user
	 * @return true if succeed.else false*/
	public static boolean addFriend(User signedUser, String userName) {
		for (User user : UserCollection.users) {
			if (userName.equals(user.getUserName())) {
				for (User friend : signedUser.getFriends()) {
					if (userName.equals(friend.getUserName())) {
						System.out.println("This user is already in your friend list!");
						return false;
					}

				}
				signedUser.addFriend(user);
				System.out.printf("%s has been successfully added to your friend list.\n", userName);
				return true;
			}
		}
		System.out.println("No such user!");
		return false;
	}

	/**
	 * removes friend from friends.
	 * displays a message which tell whether removed  or not.
	 * if not shows why.
	 * @param signedUser user that signed in
	 * @param userName username of the user that wanted to be removed
	 * @return true if succeed.else false*/
	public static boolean removeFriend(User signedUser, String userName) {
		for (User friend : signedUser.getFriends()) {
			if (userName.equals(friend.getUserName())) {
				signedUser.removeFriend(friend);
				System.out.printf("%s has been successfully removed from your friend list.\n", userName);
				return true;
			}
		}
		System.out.println("No such friend!");
		return false;
	}

	/**
	 * blocks user
	 * displays a message which tell whether blocked  or not.
	 * if not shows why.
	 * @param signedUser user that signed in
	 * @param userName username of the user that wanted to be blocked
	 * @return true if succeed.else false*/
	public static boolean blockFriend(User signedUser,String userName){
		for(User user : UserCollection.users){
			if(userName.equals(user.getUserName())){
				signedUser.block(user);
				System.out.printf("%s has been successfully blocked.\n",userName);
				return true;						
			}
		}
		System.out.println("No such user!");
		return false;
	}
	
	/**
	 * manipulates given input list and does commands that will be given as zeroth element of the input list
	 * @param inputList input list for userCollection methods
	 * @return true if any command case is handled. else returns false*/
	public static boolean userCollectionCommands(String[] inputList) {
		String[] input = makeless(inputList, 1);
		switch (inputList[0]) {
		case ("SIGNIN"): {
			String userName = input[0];
			String password = input[1];
			signedUser = signIn(userName, password);
			return true;

		}

		case ("ADDUSER"): {
			String name = input[0];
			String userName = input[1];
			String password = input[2];
			String school = input[4];
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			String date = input[3];
			Date dateOfBirth ;
			try {
				dateOfBirth = format.parse(date);
				
				if (UserCollection.addUser(name, userName, password, dateOfBirth, school)) {
					System.out.printf("%s has been successfully added.\n", name);
				}
			} catch (ParseException ex) {
				System.out.println("Wrong Date format.");
			}

			return true;

		}

		case ("REMOVEUSER"): {
			int userId = Integer.parseInt(input[0]);
			if (UserCollection.removeUser(userId)) {
				System.out.println("User has been successfully removed.");
			} else {
				System.out.println("No such user!");
			}
			return true;

		}
		case ("SHOWPOSTS"): {
			String userName = input[0];
			System.out.println("**************");
			System.out.printf("%s's Posts\n", userName);
			System.out.println("**************");
			UserCollection.showPosts(userName);
			return true;

		}
		}
		return false;
	}

	/**
	 * manipulates given input list and does commands that will be given as zeroth element of the input list
	 * @param inputList input list for all operations other than UserCollection methods*/
	public static void performCommands(String[] inputList, User signedUser) {
		String[] input = makeless(inputList, 1);
		switch (inputList[0]) {

		case ("UPDATEPROFILE"): {
			String name = input[0];
			String date = input[1];
			String school = input[2];
			signedUser.update(name, date, school);
			System.out.println("Your user profile has been successfully updated.");
			
			break;
		}
		case ("CHPASS"): {
			String oldPassword = input[0];
			String newPassword = input[1];
			String userPassword = signedUser.getPassword();
			if(userPassword.equals(oldPassword)){
				signedUser.setPassword(newPassword);
			}
			else
				System.out.println("Password mismatch! Please, try again.");
			break;
		}
		case ("ADDFRIEND"): {
			String userName = input[0];
			addFriend(signedUser, userName);
			break;
		}
		case ("REMOVEFRIEND"): {
			String userName = input[0];
			removeFriend(signedUser,userName);
			break;
		}
		case ("ADDPOST-TEXT"): {
			ArrayList<User> tags = new ArrayList<User>();
			String text = input[0];
			String slongitude = input[1];
			String slatitude = input[2];
			if (input.length == 4) {
				tags = signedUser.tagList(input[3].split(":"));
			}

			signedUser.postText(text, slongitude, slatitude, tags);
			System.out.println("The post has been successfully added.");
			break;
		}
		case ("ADDPOST-IMAGE"): {
			ArrayList<User> tags = new ArrayList<User>();
			String text = input[0];
			String slongitude = input[1];
			String slatitude = input[2];
			int noTags = 1;
			if (input.length == 6) {
				tags = signedUser.tagList(input[3].split(":"));
				noTags = 0;
			}
			String path = input[4 - noTags];
			String res = input[5 - noTags];

			signedUser.postImage(text, slongitude, slatitude, tags, path, res);
			System.out.println("The post has been successfully added.");
			break;
		}
		case ("ADDPOST-VIDEO"): {
			ArrayList<User> tags = new ArrayList<User>();
			String text = input[0];
			String slongitude = input[1];
			String slatitude = input[2];
			int noTags = 1;
			if (input.length == 6) {
				tags = signedUser.tagList(input[3].split(":"));
				noTags = 0;
			}
			String path = input[4 - noTags];
			String videoDuration = input[5 - noTags];
			signedUser.postVideo(text, slongitude, slatitude, tags, path, videoDuration);
			System.out.println("The post has been successfully added.");
			break;
		}
		case ("REMOVELASTPOST"): {
			signedUser.removeLastPost();
			break;
		}
		case ("BLOCK"): {
			boolean check = true;
			String userName = input[0];
			for(User user:UserCollection.users){
				if(userName.equals(user.getUserName())){
					signedUser.block(user);
					System.out.printf("%s has been successfully blocked.\n", userName);
					check = false;
				}
			}
			if(check)
				System.out.println("No such user!");
			break;
		}
		case ("UNBLOCK"): {
			String userName = input[0];
			boolean check = true;
			for (User blocked:signedUser.getBlocked()){
				if(userName.equals(blocked.getUserName())){
					signedUser.unblock(blocked);
					System.out.printf("%s has been successfully unblocked.\n", userName);
					check = false;
					break;
				}
			}
			if(check)
				System.out.println("No such user in your blocked users list!");
			
			break;
		}
		case ("LISTFRIENDS"): {
			for(User friend:signedUser.getFriends()){
				friend.displayUser();
				System.out.println("---------------------------");
			}
			break;
		}
		case ("LISTUSERS"): {
			for(User user:UserCollection.users){
				user.displayUser();
				System.out.println("---------------------------");
			}
			break;
		}
		case ("SHOWBLOCKEDFRIENDS"): {
			boolean check = true;
			if(signedUser.getBlocked().size() == 0){
				System.out.println("You haven’t blocked any users yet!");
				break;
			}
			for(User blocked:signedUser.getBlocked()){
				for(User friend:signedUser.getFriends()){
					if(blocked.equals(friend)){
						friend.displayUser();
						System.out.println("---------------------------");
						check = false;
					}
				}
			}
			if (check)
				System.out.println("You haven’t blocked any friends yet!");
			break;
		}
		case ("SHOWBLOCKEDUSERS"): {
			if(signedUser.getBlocked().size() == 0)
				System.out.println("You haven’t blocked any users yet!");
			for(User blocked:signedUser.getBlocked()){
				blocked.displayUser();
				System.out.println("---------------------------");
			}
			break;
		}
		case ("SIGNOUT"): {
			signedUser.signOut();
			anySign = false;
			System.out.println("You have succesfully signed out.");
			break;
		}

		}

	}

	public static void main(String[] args) {

		try {
			Scanner scanner = new Scanner(new File("users.txt"));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] inputList = line.split("\t");
				String name = inputList[0];
				String userName = inputList[1];
				String password = inputList[2];
				String school = inputList[4];
				DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
				String date = inputList[3];
				Date dateOfBirth;
				try {
					dateOfBirth = format.parse(date);
					UserCollection.addUser(name, userName, password, dateOfBirth, school);
				} catch (ParseException ex) {
					System.out.println("Wrong Date format.");
				}

			}
			scanner.close();
		} catch (FileNotFoundException ex) {
			System.out.println("No File Found!");
			return;
		}

		try {
			Scanner scanner = new Scanner(new File("commands.txt"));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] inputList = line.split("\t");
				System.out.println("-----------------------");
				System.out.println("Command: " + line);
				boolean check = userCollectionCommands(inputList);
				if (anySign)
					performCommands(inputList, signedUser);
				else if (!check)
					System.out.println("Error: Please sign in and try again.");
			}
			scanner.close();
		} catch (FileNotFoundException ex) {
			System.out.println("No File Found!");
			return;
		}

		
	}

}