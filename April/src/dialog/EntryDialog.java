package dialog;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import database.DatabaseConnect;
import RSA.RSAUtil;

public class EntryDialog {

	private JFrame entryFrame;
	private JTextField urlField;
	private JTextField usernameField;
	private JTextField passwordField;

	/**
	 * Launch the application.
	 * @throws Exception 
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EntryDialog window = new EntryDialog();
					window.entryFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
*/
	public String encrypt(String data) throws Exception{
		RSAUtil rsaTool = new RSAUtil();
		return rsaTool.encodeBase64(rsaTool.encrypt(rsaTool.getKeyPair().getPublic(), data.getBytes()));
	}
	public String decrypt(String data) throws Exception{
		RSAUtil rsaTool = new RSAUtil();
		byte[] decdata = rsaTool.decrypt(rsaTool.getKeyPair().getPrivate(),  
                rsaTool.decodeBase64(data));
		String dec = new String(decdata);
		return dec;
	}
	
	public void showFrame(){
		this.entryFrame.setVisible(true);
	}
	public void closeFrame(){
		this.entryFrame.setVisible(false);
		this.entryFrame.dispose();
		try {
			finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Create the application.
	 */
	public EntryDialog() {
		initialize();
		/*
		看见 喜欢的女孩子有对象了
		 她好像越来越美了，旁边那位长很高。。
		 欸
		 这罪恶的世界啊
		 挣扎在作业中的代码狗 
		 只能在这里留下一条sad的注释
		 					2017.4.25
		 */
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		entryFrame = new JFrame("录入");
		entryFrame.setBounds(100, 100, 450, 300);
		entryFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		entryFrame.getContentPane().setLayout(null);
		
		JLabel urlLabel = new JLabel("\u7AD9\u70B9 \uFF1A");
		urlLabel.setFont(new Font("幼圆", Font.PLAIN, 14));
		urlLabel.setHorizontalAlignment(SwingConstants.CENTER);
		urlLabel.setBounds(79, 62, 71, 28);
		entryFrame.getContentPane().add(urlLabel);
		
		urlField = new JTextField();
		urlField.setBounds(151, 65, 158, 24);
		entryFrame.getContentPane().add(urlField);
		urlField.setColumns(10);
		
		JLabel usernameLabel = new JLabel("\u7528\u6237\u540D\uFF1A");
		usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		usernameLabel.setFont(new Font("幼圆", Font.PLAIN, 13));
		usernameLabel.setBounds(69, 100, 84, 28);
		entryFrame.getContentPane().add(usernameLabel);
		
		usernameField = new JTextField();
		usernameField.setBounds(151, 104, 158, 24);
		entryFrame.getContentPane().add(usernameField);
		usernameField.setColumns(10);
		
		JLabel passwordLabel = new JLabel("\u5BC6\u7801  \uFF1A");
		passwordLabel.setFont(new Font("幼圆", Font.PLAIN, 13));
		passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		passwordLabel.setBounds(69, 138, 84, 28);
		entryFrame.getContentPane().add(passwordLabel);
		
		passwordField = new JTextField();
		passwordField.setBounds(151, 142, 158, 24);
		entryFrame.getContentPane().add(passwordField);
		passwordField.setColumns(10);
		
		JButton entryButton = new JButton("\u5F55\u5165");
		entryButton.setFont(new Font("幼圆", Font.PLAIN, 13));
		entryButton.setBounds(102, 193, 93, 23);
		entryFrame.getContentPane().add(entryButton);
		
		JButton resetButton = new JButton("\u91CD\u7F6E");
		resetButton.setFont(new Font("幼圆", Font.PLAIN, 13));
		resetButton.setBounds(228, 193, 93, 23);
		entryFrame.getContentPane().add(resetButton);
		
		JButton backButton = new JButton("\u8FD4\u56DE");
		backButton.setBounds(331, 228, 93, 23);
		entryFrame.getContentPane().add(backButton);
		
		backButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Main main = new Main();
				main.showFrame();
				closeFrame();
			}			
		});
		
		entryButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String url = urlField.getText();
				String username = usernameField.getText();
				String password = passwordField.getText();
				
				if(url.equals("")||username.equals("")||password.equals("")){
					JOptionPane.showMessageDialog(null, "还没填完噢", "错误", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if(url.length()>64||username.length()>64||password.length()>64){
					JOptionPane.showMessageDialog(null, "信息太长了", "错误", JOptionPane.WARNING_MESSAGE);
					return;
				}
				try {
					//加密
					String encUrl = encrypt(url);
					String encUsername = encrypt(username);
					String encPassword = encrypt(password);
					System.out.println(encUrl+"\n"+encUsername+"\n"+encPassword+"\n");
					/*解密
					String decUrl = decrypt(encUrl);
					String decUsername = decrypt(encUsername);
					String decpwd = decrypt(encPassword);

					System.out.println("EntryDialog:"+decUrl+"\n"+decUsername+"\n"+decpwd);
					*/
					
					DatabaseConnect database = new DatabaseConnect();
					database.addRecord(encUrl, encUsername, encPassword);
					//JOptionPane.showMessageDialog(null, "记录添加成功", "完成", JOptionPane.INFORMATION_MESSAGE);
					
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				urlField.setText("");
				usernameField.setText("");
				passwordField.setText("");
			}
		});
		
		
	}
}


