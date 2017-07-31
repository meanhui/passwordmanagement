package dialog;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JButton;

import RSA.UseSHA256;
import database.DatabaseConnect;
import javax.swing.JPasswordField;

public class LoginPassword {

	private JFrame frame;
	private JPasswordField oldField;
	private JPasswordField newField1;
	private JPasswordField newField2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPassword window = new LoginPassword();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginPassword() {
		initialize();
	}
	
	public void closeDialog(){
		this.frame.setVisible(false);
		this.frame.dispose();
	}
	public void openDialog(){
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("\u4FEE\u6539\u767B\u5F55\u5BC6\u7801");
		frame.getContentPane().setFont(new Font("幼圆", Font.PLAIN, 12));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel oldPwd = new JLabel("\u539F\u5BC6\u7801\uFF1A");
		oldPwd.setFont(new Font("幼圆", Font.PLAIN, 13));
		oldPwd.setHorizontalAlignment(SwingConstants.CENTER);
		oldPwd.setBounds(101, 62, 54, 15);
		frame.getContentPane().add(oldPwd);
		
		JLabel newPwd1 = new JLabel("\u65B0\u5BC6\u7801\uFF1A");
		newPwd1.setFont(new Font("幼圆", Font.PLAIN, 13));
		newPwd1.setHorizontalAlignment(SwingConstants.CENTER);
		newPwd1.setBounds(101, 105, 54, 15);
		frame.getContentPane().add(newPwd1);
		
		JLabel newPwd2 = new JLabel("\u518D\u6B21\u8F93\u5165\uFF1A");
		newPwd2.setFont(new Font("幼圆", Font.PLAIN, 13));
		newPwd2.setHorizontalAlignment(SwingConstants.CENTER);
		newPwd2.setBounds(90, 144, 65, 15);
		frame.getContentPane().add(newPwd2);
		
		JButton changeButton = new JButton("\u4FEE\u6539");
		changeButton.setBounds(109, 187, 93, 23);
		frame.getContentPane().add(changeButton);
		
		JButton backButton = new JButton("\u8FD4\u56DE");
		backButton.setBounds(224, 187, 93, 23);
		frame.getContentPane().add(backButton);
		
		oldField = new JPasswordField();
		oldField.setBounds(152, 59, 163, 21);
		frame.getContentPane().add(oldField);
		
		newField1 = new JPasswordField();
		newField1.setBounds(152, 102, 163, 21);
		frame.getContentPane().add(newField1);
		
		newField2 = new JPasswordField();
		newField2.setBounds(152, 141, 163, 21);
		frame.getContentPane().add(newField2);
		
		backButton.addActionListener(new ActionListener() {		
		public void actionPerformed(ActionEvent e) {
				Login login = new Login();
				login.openLoginDialog();
				closeDialog();
			}
		});
		
		changeButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String oldPwd = oldField.getText();
				String newPwd1 = newField1.getText();
				String newPwd2 = newField2.getText();
				if(oldPwd.equals("")||newPwd1.equals("")||newPwd2.equals("")){
					JOptionPane.showMessageDialog(null, "没有填完噢", "错误", JOptionPane.WARNING_MESSAGE);
					return ;
				}
				if(!newPwd1.equals(newPwd2)){
					JOptionPane.showMessageDialog(null, "两次输入不一致", "错误", JOptionPane.WARNING_MESSAGE);
					return ;
				}
				if(newPwd1.length()>64||newPwd2.length()>64){
					JOptionPane.showMessageDialog(null, "新密码太长了", "错误", JOptionPane.WARNING_MESSAGE);
					return ;
				}
				
				DatabaseConnect database = new DatabaseConnect();
				UseSHA256 sha = new UseSHA256();
				if(!database.loginAuthentication("admin", sha.SHA256(oldPwd))){
					JOptionPane.showMessageDialog(null, "原密码不正确", "错误", JOptionPane.WARNING_MESSAGE);
					return ;
				}
				//else System.out.println("right");
				database.changeLoginPassword("admin", sha.SHA256(newPwd1));
			}
		});
		
		
		
	}
}
