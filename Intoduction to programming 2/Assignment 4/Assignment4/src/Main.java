import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.ScrollPane;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.JPasswordField;
import java.awt.Color;
import java.awt.Window.Type;
import javax.swing.border.TitledBorder;
import javax.swing.ImageIcon;

public class Main extends JFrame {

	public static boolean anySign = false;
	public static User signedUser = null;

	/**
	 * returns the list back but makes x number of member from start dissapear.
	 * lists length will be x times less
	 * 
	 * @param l
	 *            firstlist
	 * @param x
	 *            how much less
	 * @return lesser list
	 */
	public static String[] makeless(String[] l, int x) {
		String[] rlist = new String[l.length - x];
		for (int i = 1; i < l.length; i++)
			rlist[i - x] = l[i];
		return rlist;
	}

	/**
	 * sign a users to the system. only one user can be signed at a time.
	 * displays a message which tell whether user signed in or not. if not shows
	 * why.
	 * 
	 * @param userName
	 *            users userName
	 * @param password
	 *            users passwords
	 */
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
	 * 
	 * @param signedUser
	 *            user that signed in
	 * @param userName
	 *            username of the added user
	 * @return true if succeed.else false
	 */
	public static boolean addFriend(User signedUser, User user) {
		for (User friend : signedUser.getFriends()) {
			if (user.equals(friend)) {
				return false;
			}
		}
		signedUser.addFriend(user);
		return true;

		/**
		 * System.out.println("No such user!"); return false;
		 **/
	}

	/**
	 * removes friend from friends. displays a message which tell whether
	 * removed or not. if not shows why.
	 * 
	 * @param signedUser
	 *            user that signed in
	 * @param userName
	 *            username of the user that wanted to be removed
	 * @return true if succeed.else false
	 */
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
	 * blocks user displays a message which tell whether blocked or not. if not
	 * shows why.
	 * 
	 * @param signedUser
	 *            user that signed in
	 * @param userName
	 *            username of the user that wanted to be blocked
	 * @return true if succeed.else false
	 */
	public static boolean blockFriend(User signedUser, String userName) {
		for (User user : UserCollection.users) {
			if (userName.equals(user.getUserName())) {
				signedUser.block(user);
				return true;
			}
		}
		System.out.println("No such user!");
		return false;
	}


	/**
	 * manipulates given input list and does commands that will be given as
	 * zeroth element of the input list
	 * 
	 * @param inputList
	 *            input list for userCollection methods
	 * @return true if any command case is handled. else returns false
	 */
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
			String relationship = input[5];
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			String date = input[3];
			Date dateOfBirth;
			try {
				dateOfBirth = format.parse(date);

				if (UserCollection.addUser(name, userName, password, dateOfBirth, school, relationship)) {
					System.out.printf("%s has been successfully added.\n", name);
				}
			} catch (ParseException ex) {
				System.out.println("Wrong Date format.");
			}

			return true;

		}

		case ("REMOVEUSER"): {
			String userName = input[0];
			if (UserCollection.removeUser(userName)) {
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
	 * manipulates given input list and does commands that will be given as
	 * zeroth element of the input list
	 * 
	 * @param inputList
	 *            input list for all operations other than UserCollection
	 *            methods
	 */
	public static void performCommands(String[] inputList) {
		String sSignedUser = inputList[1];
		signedUser = User.sUserNametoUser(sSignedUser);
		for (User user : UserCollection.users) {
			user.setSignIn(true);
		}

		String[] input = makeless(inputList, 1);
		switch (inputList[0]) {

		case ("UPDATEPROFILE"): {
			String name = input[0];
			String date = input[1];
			String school = input[2];
			signedUser.update(name, date, school,"Single");
			System.out.println("Your user profile has been successfully updated.");

			break;
		}
		case ("CHPASS"): {
			String oldPassword = input[0];
			String newPassword = input[1];
			String userPassword = signedUser.getPassword();
			if (userPassword.equals(oldPassword)) {
				signedUser.setPassword(newPassword);
			} else
				System.out.println("Password mismatch! Please, try again.");
			break;
		}
		case ("ADDFRIEND"): {
			String userName = input[1];
			User friend = User.sUserNametoUser(userName);
			addFriend(signedUser, friend);
			break;
		}
		case ("REMOVEFRIEND"): {
			String userName = input[0];
			removeFriend(signedUser, userName);
			break;
		}
		case ("ADDPOST-TEXT"): {
			input = makeless(input, 1);
			ArrayList<User> tags = new ArrayList<User>();
			String text = input[0];
			String slongitude = input[1];
			String slatitude = input[2];
			if (input.length == 4) {
				tags = signedUser.tagList(input[3].split(":"));
			}

			signedUser.postText(text, slongitude, slatitude, tags);
			
			break;
		}
		case ("ADDPOST-IMAGE"): {
			input = makeless(input, 1);
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
			
			break;
		}
		case ("ADDPOST-VIDEO"): {
			input = makeless(input, 1);
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
			
			break;
		}
		case ("REMOVELASTPOST"): {
			signedUser.removeLastPost();
			break;
		}
		case ("BLOCKFRIEND"): {
			boolean check = true;
			String userName = input[0];
			for (User user : UserCollection.users) {
				if (userName.equals(user.getUserName())) {
					signedUser.block(user);
					
					check = false;
				}
			}
			if (check)
				System.out.println("No such user!");
			break;
		}
		case ("UNBLOCK"): {
			String userName = input[0];
			User user = User.sUserNametoUser(userName);
			boolean check = true;
			for (User blocked : signedUser.getBlocked()) {
				if (user.equals(blocked)) {
					signedUser.unblock(blocked);
					System.out.printf("%s has been successfully unblocked.\n", userName);
					check = false;
					break;
				}
			}
			if (check)
				System.out.println("No such user in your blocked users list!");

			break;
		}
		case ("LISTFRIENDS"): {
			for (User friend : signedUser.getFriends()) {
				friend.displayUser();
				System.out.println("---------------------------");
			}
			break;
		}
		case ("LISTUSERS"): {
			for (User user : UserCollection.users) {
				user.displayUser();
				System.out.println("---------------------------");
			}
			break;
		}
		case ("SHOWBLOCKEDFRIENDS"): {
			boolean check = true;
			if (signedUser.getBlocked().size() == 0) {
				System.out.println("You haven’t blocked any users yet!");
				break;
			}
			for (User blocked : signedUser.getBlocked()) {
				for (User friend : signedUser.getFriends()) {
					if (blocked.equals(friend)) {
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
			if (signedUser.getBlocked().size() == 0)
				System.out.println("You haven’t blocked any users yet!");
			for (User blocked : signedUser.getBlocked()) {
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

	public boolean isAUser(String userName){
		for (User user:UserCollection.users){
			if(userName.equals(user.getUserName()))
				return true;
		}
		return false;
	}
	
	private JPanel contentPane;
	private JTextField userNameText;
	private JScrollPane scrollPane;
	public static JList list;
	private JPasswordField passwordField;

	
	
	/**
	 * Launch the application.
	 */
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
				String relationship = inputList[5];
				DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
				String date = inputList[3];
				Date dateOfBirth;
				try {
					dateOfBirth = format.parse(date);
					UserCollection.addUser(name, userName, password, dateOfBirth, school, relationship);
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
			anySign = true;
			for (User user : UserCollection.users) {
				user.setSignIn(true);
			}
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] inputList = line.split("\t");
				boolean check = userCollectionCommands(inputList);
				if (anySign)
					performCommands(inputList);
			}
			anySign = false;
			for (User user : UserCollection.users) {
				user.setSignIn(false);
			}
			
			scanner.close();
		} catch (FileNotFoundException ex) {
			System.out.println("No File Found!");
			return;
		}
		

		

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setTitle("Facebook Login Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 625, 343);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String userName = userNameText.getText();
				String password = passwordField.getText();
				User user = User.sUserNametoUser(userName);
				if (!isAUser(userName)){
					JOptionPane.showMessageDialog(contentPane, "There is no user with this user name");
					return;
				}
				if(!password.equals(user.getPassword())){
					JOptionPane.showMessageDialog(contentPane, "Password is not correct for this user name");
					return;
				}
				user.signIn();
				ProfilePage profile = new ProfilePage();
				profile.profile();
				contentPane.setVisible(false);
			}
		});
		btnLogin.setBounds(407, 162, 179, 32);
		contentPane.add(btnLogin);

		userNameText = new JTextField();
		userNameText.setHorizontalAlignment(SwingConstants.LEFT);
		userNameText.setBounds(407, 100, 179, 20);
		contentPane.add(userNameText);
		userNameText.setColumns(10);

		DefaultListModel dlc = new DefaultListModel();
		for (User user : UserCollection.users) {
			dlc.addElement(user.getUserName());
		}

		scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 205, 452, 32);
		contentPane.add(scrollPane);
		list = new JList();
		scrollPane.setViewportView(list);
		list.setValueIsAdjusting(true);
		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		list.setModel(dlc);

		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addMouseListener(new MouseAdapter() {

			@Override

			public void mouseClicked(MouseEvent arg0) {
				String name = (String) list.getSelectedValue();
				User user = User.sUserNametoUser(name);
				userNameText.setText(name);
				passwordField.setText(user.getPassword());

			}
		});

		JLabel lblNewLabel = new JLabel("Registired Users");
		lblNewLabel.setBounds(27, 180, 95, 14);
		contentPane.add(lblNewLabel);

		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUserName.setBounds(332, 103, 65, 14);
		contentPane.add(lblUserName);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(332, 134, 65, 14);
		contentPane.add(lblPassword);

		JButton btnRemoveUser = new JButton("Remove User");
		btnRemoveUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (list.getSelectedIndex() > -1) {
					int reply = JOptionPane.showConfirmDialog(null,"Are you Sure?","",JOptionPane.YES_NO_OPTION);
					if(reply == JOptionPane.YES_OPTION){
					String name = (String) list.getSelectedValue();
					User user = User.sUserNametoUser(name);
					UserCollection.removeUser(name);
					dlc.removeElement(name);
					list.setModel(dlc);}
				}

			}
		});
		btnRemoveUser.setBounds(27, 248, 114, 23);
		contentPane.add(btnRemoveUser);

		JButton btnNewButton_1 = new JButton("Create User");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			CreateUser createOne = new CreateUser();
			createOne.create();
			
			
			
			}
			
		});
		btnNewButton_1.setBounds(350, 248, 129, 23);
		contentPane.add(btnNewButton_1);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(407, 131, 179, 20);
		contentPane.add(passwordField);
		
		JButton btnNewButton = new JButton("System");
		btnNewButton.setEnabled(false);
		btnNewButton.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton.setBounds(0, 0, 609, 23);
		contentPane.add(btnNewButton);
	
	}
}
