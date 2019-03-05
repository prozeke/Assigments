import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import java.awt.FlowLayout;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JRadioButton;

public class ProfilePage extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void profile() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProfilePage frame = new ProfilePage();
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
	public ProfilePage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 793, 701);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		DefaultListModel friends = new DefaultListModel();
		for(User friend : Main.signedUser.getFriends()){
			friends.addElement(friend.getUserName());
		}
		
		JList listFriends = new JList(friends);
		listFriends.setBounds(25, 406, 191, 205);
		listFriends.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.add(listFriends);
		
		JButton btnRemoveSelectedFriend = new JButton("Remove Selected User");
		btnRemoveSelectedFriend.setBounds(22, 622, 194, 29);
		contentPane.add(btnRemoveSelectedFriend);
		
		JSpinner listPosts = new JSpinner();
		listPosts.setBounds(275, 197, 465, 414);
		contentPane.add(listPosts);
		
		JLabel lblNewLabel = new JLabel(Main.signedUser.getName());
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(177, 91, 203, 77);
		contentPane.add(lblNewLabel);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("normal");
		rdbtnNewRadioButton.setBounds(70, 376, 77, 23);
		contentPane.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("blocked");
		rdbtnNewRadioButton_1.setBounds(149, 376, 67, 23);
		contentPane.add(rdbtnNewRadioButton_1);
		
		JLabel lblFriends = new JLabel("Friends");
		lblFriends.setBounds(10, 380, 46, 14);
		contentPane.add(lblFriends);
	}
}
