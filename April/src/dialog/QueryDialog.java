package dialog;

import java.awt.EventQueue;

import database.DatabaseConnect;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.IdentityHashMap;
import java.util.Iterator;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextPane;

import RSA.RSAUtil;

import javax.swing.JList;

public class QueryDialog {

	private JFrame queryFrame;
	private JTextField inputField;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QueryDialog window = new QueryDialog();
					window.queryFrame.setVisible(true);
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
		this.queryFrame.setVisible(true);
	}
	public void closeFrame(){
		this.queryFrame.setVisible(false);
		this.queryFrame.dispose();
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
	public QueryDialog() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		queryFrame = new JFrame();
		queryFrame.setTitle("\u67E5\u8BE2");
		queryFrame.setBounds(100, 100, 650, 300);
		queryFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		queryFrame.getContentPane().setLayout(null);
		
		JLabel inputLabel = new JLabel("\u8F93\u5165\u9700\u8981\u67E5\u8BE2\u7684\u7AD9\u70B9\uFF1A");
		inputLabel.setFont(new Font("幼圆", Font.PLAIN, 13));
		inputLabel.setHorizontalAlignment(SwingConstants.CENTER);
		inputLabel.setBounds(130, 111, 149, 38);
		queryFrame.getContentPane().add(inputLabel);
		
		inputField = new JTextField();
		inputField.setBounds(289, 116, 193, 29);
		queryFrame.getContentPane().add(inputField);
		inputField.setColumns(10);
		
		JButton selectButton = new JButton("\u67E5\u8BE2");
		selectButton.setBounds(177, 175, 93, 23);
		queryFrame.getContentPane().add(selectButton);
		
		JButton backButton = new JButton("\u8FD4\u56DE");
		backButton.setBounds(332, 175, 93, 23);
		queryFrame.getContentPane().add(backButton);
		
		JList list = new JList();
		list.setToolTipText("\u67E5\u8BE2\u7A97\u53E3");
		list.setBounds(10, 10, 614, 91);
		queryFrame.getContentPane().add(list);
		
		backButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Main main = new Main();
				main.showFrame();
				closeFrame();
			}			
		});
		
		selectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DatabaseConnect database = new DatabaseConnect();
				String inputURL = inputField.getText();
				if(inputURL.equals("")){
					JOptionPane.showMessageDialog(null, "请填写要查询的站点", "错误", JOptionPane.WARNING_MESSAGE);
					return;
				}
				IdentityHashMap result;
				try {
					result = database.findRecordByURL(encrypt(inputURL));
					if(result==null){
						JOptionPane.showMessageDialog(null, "找不到噢", "没有", JOptionPane.WARNING_MESSAGE);
						return;
					}
					else{
						Iterator iter = result.keySet().iterator();
						int key;
						String [] val;
						String queryResult = "";
						DefaultListModel model = new DefaultListModel();;
						while(iter.hasNext()){
							key = (int) iter.next();
							//System.out.println(key);
							val = (String[]) result.get(key);
							String Url=val[0];String Uname=val[1];String Pwd=val[2];
							//System.out.println(u+"\n"+uname+"\n"+pwd);
							Url = decrypt(Url);Uname = decrypt(Uname);Pwd = decrypt(Pwd);
							System.out.println(Url+"\n"+Uname+"\n"+Pwd);
							queryResult = key + "------->" 
										+ "站点 ：" + Url + "        "
										+ "Username :" + Uname + "        "
										+ "Password :" + Pwd + "\n";
							
							model.addElement(queryResult);
						}
						list.setModel(model);
						
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		
	}
}
