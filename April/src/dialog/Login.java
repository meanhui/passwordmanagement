package dialog;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import RSA.UseSHA256;
import database.DatabaseConnect;

public class Login {
	private JFrame frame;
	private JTextField textField;
	private JLabel tip;
	private static Login loginWindow;
	private JButton resetButton;
	
	public Login() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame("Password Management");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("\u53E3\u4EE4\uFF1A");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("幼圆", Font.PLAIN, 15));
		label.setBounds(94, 78, 67, 27);
		frame.getContentPane().add(label);
		
		textField = new JPasswordField();
		textField.setBounds(155, 78, 172, 27);		
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnSubmit = new JButton("\u767B\u5F55");
		btnSubmit.setFont(new Font("幼圆", Font.PLAIN, 12));
		btnSubmit.setBounds(107, 137, 105, 23);
		frame.getContentPane().add(btnSubmit);
		
		tip = new JLabel("\u57FA\u4E8ERSA\u52A0\u5BC6\u7684\u5BC6\u7801\u7BA1\u7406\u5668 Ver1.0");
		tip.setFont(new Font("幼圆", Font.PLAIN, 12));
		tip.setBounds(245, 238, 189, 23);
		frame.getContentPane().add(tip);
		
		resetButton = new JButton("\u4FEE\u6539\u53E3\u4EE4");
		resetButton.setFont(new Font("幼圆", Font.PLAIN, 12));
		resetButton.setBounds(237, 137, 111, 23);
		frame.getContentPane().add(resetButton);
		
		resetButton.addActionListener(new ActionListener() {		
		public void actionPerformed(ActionEvent e) {
				LoginPassword lp = new LoginPassword();
				lp.openDialog();
				closeLoginDialog();
			}
		});
		
		btnSubmit.addActionListener(new ActionListener() {
			//登录按钮
			@Override
			public void actionPerformed(ActionEvent e) {
				String kouling = textField.getText();
				
				if(kouling.equals("")){
					Toolkit.getDefaultToolkit().beep();
	                JOptionPane.showMessageDialog(null, "口令没有填噢", "不对", JOptionPane.WARNING_MESSAGE);
	                return;
				} else
					try {
						DatabaseConnect database = new DatabaseConnect();
						UseSHA256 sha = new UseSHA256();
						if(database.loginAuthentication("admin",sha.SHA256(kouling))){
							Main mainDialog = new Main();
							mainDialog.showFrame();
							closeLoginDialog();
						}
						else{
							Toolkit.getDefaultToolkit().beep();
						    JOptionPane.showMessageDialog(null, "不对", "不对", JOptionPane.WARNING_MESSAGE);
						    return;
						}
					} catch (HeadlessException e1) {
						e1.printStackTrace();
					}
			}
		});
		
	}
	
	public void closeLoginDialog(){
		this.frame.setVisible(false);
		this.frame.dispose();
	}
	public void openLoginDialog(){
		this.frame.setVisible(true);
	}
	
//	public static String getMd5(String str) throws NoSuchAlgorithmException{
//		
//			MessageDigest md = MessageDigest.getInstance("MD5");
//			md.update(str.getBytes());
//			return new BigInteger(1, md.digest()).toString(16);
//
//	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					loginWindow = new Login();
					loginWindow.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
