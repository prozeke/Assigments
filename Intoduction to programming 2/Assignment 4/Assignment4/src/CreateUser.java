import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;

public class CreateUser extends JFrame {

	private JPanel contentPane;
	private JTextField iUserName;
	private JTextField iPassword;
	private JTextField iPasswordRe;
	private JTextField iName;
	private JTextField iSchoolGraduated;
	private JTextField iBirthDate;
	private JTextField txtUserName;
	private JTextField txtPassword;
	private JTextField txtPasswordRetype;
	private JTextField txtSchoolGraduated;
	private JTextField txtBirthDate;
	private JTextField txtBirthDate_1;
	private JTextField txtRealationshipStatus;
	private JTextField txtNameSurname;
	/**
	 * Launch the application.
	 */
	public static void create() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateUser frame = new CreateUser();
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
	public CreateUser() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 367, 491);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Create User");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(91, 95, 171, 35);
		contentPane.add(lblNewLabel);
		
		iUserName = new JTextField();
		iUserName.setBounds(138, 165, 86, 20);
		contentPane.add(iUserName);
		iUserName.setColumns(10);
		
		iPassword = new JPasswordField();
		iPassword.setBounds(138, 196, 86, 20);
		contentPane.add(iPassword);
		iPassword.setColumns(10);
		
		iPasswordRe = new JPasswordField();
		iPasswordRe.setBounds(138, 227, 86, 20);
		contentPane.add(iPasswordRe);
		iPasswordRe.setColumns(10);
		
		iName = new JTextField();
		iName.setBounds(138, 258, 171, 20);
		contentPane.add(iName);
		iName.setColumns(10);
		
		iSchoolGraduated = new JTextField();
		iSchoolGraduated.setBounds(138, 289, 171, 20);
		contentPane.add(iSchoolGraduated);
		iSchoolGraduated.setColumns(10);
		
		iBirthDate = new JTextField();
		iBirthDate.setBounds(138, 320, 86, 20);
		contentPane.add(iBirthDate);
		iBirthDate.setColumns(10);
		
		txtUserName = new JTextField();
		txtUserName.setBorder(null);
		txtUserName.setEditable(false);
		txtUserName.setText("User name");
		txtUserName.setBounds(10, 165, 86, 20);
		contentPane.add(txtUserName);
		txtUserName.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setText("Password");
		txtPassword.setEditable(false);
		txtPassword.setColumns(10);
		txtPassword.setBorder(null);
		txtPassword.setBounds(10, 196, 86, 20);
		contentPane.add(txtPassword);
		
		txtPasswordRetype = new JTextField();
		txtPasswordRetype.setText("Password re-type");
		txtPasswordRetype.setEditable(false);
		txtPasswordRetype.setColumns(10);
		txtPasswordRetype.setBorder(null);
		txtPasswordRetype.setBounds(10, 227, 106, 20);
		contentPane.add(txtPasswordRetype);
		
		txtNameSurname = new JTextField();
		txtNameSurname.setText("Name Surname");
		txtNameSurname.setEditable(false);
		txtNameSurname.setColumns(10);
		txtNameSurname.setBorder(null);
		txtNameSurname.setBounds(10, 258, 106, 20);
		contentPane.add(txtNameSurname);
		
		txtSchoolGraduated = new JTextField();
		txtSchoolGraduated.setText("School Graduated");
		txtSchoolGraduated.setEditable(false);
		txtSchoolGraduated.setColumns(10);
		txtSchoolGraduated.setBorder(null);
		txtSchoolGraduated.setBounds(10, 289, 106, 20);
		contentPane.add(txtSchoolGraduated);
		
		txtBirthDate = new JTextField();
		txtBirthDate.setText("Birth Date");
		txtBirthDate.setEditable(false);
		txtBirthDate.setColumns(10);
		txtBirthDate.setBorder(null);
		txtBirthDate.setBounds(10, 320, 86, 20);
		contentPane.add(txtBirthDate);
		
		txtRealationshipStatus = new JTextField();
		txtRealationshipStatus.setText("Realationship Status");
		txtRealationshipStatus.setEditable(false);
		txtRealationshipStatus.setColumns(10);
		txtRealationshipStatus.setBorder(null);
		txtRealationshipStatus.setBounds(10, 351, 122, 20);
		contentPane.add(txtRealationshipStatus);
		
		JComboBox status = new JComboBox();
		status.addItem("Single");
		status.addItem("In Relationship");
		status.addItem("Divorced");
		status.addItem("Complicated");
		status.setBounds(138, 351, 124, 20);
		contentPane.add(status);
		
		JButton btnNewButton = new JButton("Create");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			String userName = iUserName.getText();
			String name = iName.getText();
			String school = iSchoolGraduated.getText();
			String password = iPassword.getText();
			String passwordRe = iPasswordRe.getText();
			String relationship = (String)status.getSelectedItem();
			String sdate = iBirthDate.getText();
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date dateOfBirth;
			if(userName.equals("")){
				JOptionPane.showMessageDialog(contentPane, "Please enter a valid user name");
				return;}
			else{
				for(User user : UserCollection.users){
					if(userName.equals(user.getUserName())){
						JOptionPane.showMessageDialog(contentPane, "This user name is already taken\nPlease enter an unique user name");
						return;
					}
				}
			}
			if(password.equals("")){
				JOptionPane.showMessageDialog(contentPane, "Please enter a password");
				return;}
			else if(!password.equals(passwordRe)){
				JOptionPane.showMessageDialog(contentPane, "You couldn't sucessfully re-enter your password\nPlease try again");
				return;
			}
			
			if(name.equals("")){
				JOptionPane.showMessageDialog(contentPane, "Please enter your name and surname");
				return;}
			
			if(school.equals("")){
				JOptionPane.showMessageDialog(contentPane, "Please enter your school");
				return;}
			
			try {
				dateOfBirth = format.parse(sdate);

				if ( UserCollection.addUser(name, userName, password, dateOfBirth, school, relationship)) {
					JOptionPane.showMessageDialog(contentPane,userName+" has been successfully added");
					DefaultListModel model = (DefaultListModel) Main.list.getModel();
					User user = UserCollection.users.get(UserCollection.users.size()-1);
					model.addElement(user.getUserName());
				}
			} catch (ParseException ex) {
				JOptionPane.showMessageDialog(contentPane, "Wrong Date Format\nFormat: dd/MM/yyyy");
			}

			
			
			}
		});
		btnNewButton.setBounds(135, 404, 122, 23);
		contentPane.add(btnNewButton);
	}
}
