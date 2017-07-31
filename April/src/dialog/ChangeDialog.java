package dialog;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.IdentityHashMap;
import java.util.Iterator;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JComboBox;

import database.DatabaseConnect;

import javax.swing.JFormattedTextField;

import RSA.RSAUtil;

public class ChangeDialog {

	private JFrame changeFrame;
	private JTextField nameField;
	private JTextField newField;

	public String getMd5(String str) throws NoSuchAlgorithmException{
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(str.getBytes());
		return new BigInteger(1, md.digest()).toString(16);

}
	
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChangeDialog window = new ChangeDialog();
					window.changeFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/
	
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
		this.changeFrame.setVisible(true);
	}
	public void closeFrame(){
		this.changeFrame.setVisible(false);
		this.changeFrame.dispose();
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
	public ChangeDialog() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize() {
		changeFrame = new JFrame();
		changeFrame.setTitle("\u4FEE\u6539");
		changeFrame.setBounds(100, 100, 450, 300);
		changeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		changeFrame.getContentPane().setLayout(null);
		
		JButton changeButton = new JButton("\u4FEE\u6539");
		changeButton.setBounds(118, 182, 93, 23);
		changeFrame.getContentPane().add(changeButton);
		
		JButton backButton = new JButton("\u8FD4\u56DE");
		backButton.setBounds(331, 228, 93, 23);
		changeFrame.getContentPane().add(backButton);
		
		JLabel idLabel = new JLabel("\u7F16\u53F7\uFF1A");
		idLabel.setFont(new Font("幼圆", Font.PLAIN, 12));
		idLabel.setHorizontalAlignment(SwingConstants.CENTER);
		idLabel.setBounds(95, 47, 54, 15);
		changeFrame.getContentPane().add(idLabel);
		
		JLabel oldLabel = new JLabel("\u7528\u6237\u540D\uFF1A");
		oldLabel.setHorizontalAlignment(SwingConstants.CENTER);
		oldLabel.setFont(new Font("幼圆", Font.PLAIN, 12));
		oldLabel.setBounds(95, 88, 54, 15);
		changeFrame.getContentPane().add(oldLabel);
		
		nameField = new JTextField();
		nameField.setBounds(151, 84, 163, 23);
		changeFrame.getContentPane().add(nameField);
		nameField.setColumns(10);
		
		JLabel newLabel = new JLabel("\u65B0\u5BC6\u7801\uFF1A");
		newLabel.setFont(new Font("幼圆", Font.PLAIN, 12));
		newLabel.setHorizontalAlignment(SwingConstants.CENTER);
		newLabel.setBounds(95, 132, 54, 15);
		changeFrame.getContentPane().add(newLabel);
		
		newField = new JTextField();
		newField.setBounds(151, 124, 163, 23);
		changeFrame.getContentPane().add(newField);
		newField.setColumns(10);
		
		JButton delButton = new JButton("\u5220\u9664");
		delButton.setBounds(221, 182, 93, 23);
		changeFrame.getContentPane().add(delButton);
		
		MyJComboBox comboBox = new MyJComboBox();
		comboBox.setBounds(151, 43, 60, 23);
		changeFrame.getContentPane().add(comboBox);
		
		JLabel extraLabel = new JLabel("");
		extraLabel.setBounds(221, 46, 172, 16);
		changeFrame.getContentPane().add(extraLabel);
		
		comboBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
				int num = (int) e.getItem();
				//System.out.println(a);
				DatabaseConnect database = new DatabaseConnect();
				IdentityHashMap result = database.findRecordByid(num);
				
				Iterator iter = result.keySet().iterator();
				String [] val;
				int key;
				while(iter.hasNext()){
					key = (int) iter.next();
					val = (String[]) result.get(key);
					String Url=val[0];
					try {
						Url = decrypt(Url);
						extraLabel.setText("该编号对应的记录:"+Url);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				}
			}
		});
		
		backButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Main main = new Main();
				main.showFrame();
				closeFrame();
			}			
		});
		
		changeButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int num = (int) comboBox.getSelectedItem();
				String newName = nameField.getText();
				String newPwd = newField.getText();
				if(newName.equals("")||newPwd.equals("")){
					JOptionPane.showMessageDialog(null, "没有填完噢", "错误", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if(newName.length()>64||newPwd.length()>64){
					JOptionPane.showMessageDialog(null, "用户名和密码太长了", "错误", JOptionPane.WARNING_MESSAGE);
					return;
				}
				try {
					newName = encrypt(newName);
					newPwd = encrypt(newPwd);
					System.out.println(decrypt(newName)+" "+decrypt(newPwd));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//System.out.println(num+oldData+newData);
				DatabaseConnect database = new DatabaseConnect();
				database.updateRecord(num, newName,newPwd);
			}
			
		});
		
		delButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int num = (int) comboBox.getSelectedItem();
				
				DatabaseConnect database = new DatabaseConnect();
				if(database.findRecordByid(num)==null){
					JOptionPane.showMessageDialog(null, "这条记录已经被删除了", "错误", JOptionPane.WARNING_MESSAGE);
					return;
				}
				else
				database.dropRecord(num);
			}
		});
		
		
	}
}
