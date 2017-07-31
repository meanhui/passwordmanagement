package dialog;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

	private JFrame mainFrame;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
*/
	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainFrame = new JFrame("Main");
		mainFrame.setBounds(100, 100, 450, 300);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(null);
		
		JButton entryButton = new JButton("\u5F55\u5165");
		entryButton.setFont(new Font("свт╡", Font.PLAIN, 13));
		entryButton.setBounds(153, 49, 126, 32);
		mainFrame.getContentPane().add(entryButton);
		
		JButton queryButton = new JButton("\u67E5\u8BE2");
		queryButton.setFont(new Font("свт╡", Font.PLAIN, 13));
		queryButton.setBounds(153, 107, 126, 32);
		mainFrame.getContentPane().add(queryButton);
		
		JButton changeButton = new JButton("\u4FEE\u6539");
		changeButton.setFont(new Font("свт╡", Font.PLAIN, 13));
		changeButton.setBounds(153, 162, 126, 32);
		mainFrame.getContentPane().add(changeButton);
		
		entryButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				EntryDialog entryDialog = new EntryDialog();
				entryDialog.showFrame();
				closeFrame();
			}			
		});
		
		queryButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				QueryDialog queryDialog = new QueryDialog();
				queryDialog.showFrame();
				closeFrame();
			}			
		});
		
		changeButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				ChangeDialog changeDialog = new ChangeDialog();
				changeDialog.showFrame();
				closeFrame();
			}			
		});
		
		
	}
	public void showFrame(){
		this.mainFrame.setVisible(true);
	}
	public void closeFrame(){
		this.mainFrame.setVisible(false);
		this.mainFrame.dispose();
		try {
			finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
}
