package SqlTestSRC;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.Button;
import javax.swing.JTextPane;
import java.awt.Label;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTextField;
import javax.swing.JTextArea;

public class AdminConsole extends JFrame {
	public String room;
	public String person;

	private static final long serialVersionUID = 1L;
	private JTextField txtAddRoom;
	private JTextArea txtMenu;

	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		 
			
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminConsole frame = new AdminConsole();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public AdminConsole() {
		setResizable(false);
		setTitle("Admin");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 595, 403);
		getContentPane().setLayout(null);
		
		JButton btnAddRoom = new JButton("Add room");
		btnAddRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//method
				room = txtAddRoom.getText();
				
				
				try {
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}  
				String connectionUrl = "jdbc:sqlserver://localhost\\SQLEXPRESS;database=TestBaza;integratedSecurity=true;";
				
			       try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			            String SQL = "INSERT INTO RoomTable VALUES('"+room+"')";
			            stmt.executeUpdate(SQL);
			        }
			        // Handle any errors that may have occurred.
			        catch (SQLException e) {
			            e.printStackTrace();
			        }
				
				
			}
		});
		btnAddRoom.setBounds(33, 38, 116, 25);
		getContentPane().add(btnAddRoom);
		
		txtAddRoom = new JTextField();
		txtAddRoom.setBounds(33, 76, 116, 22);
		getContentPane().add(txtAddRoom);
		txtAddRoom.setColumns(10);
		
		JButton btnShowRoom = new JButton("Show room");
		btnShowRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//method
				txtMenu.setText("");
				try {
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}  
				String connectionUrl = "jdbc:sqlserver://localhost\\SQLEXPRESS;database=TestBaza;integratedSecurity=true;";
				
			       try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			            String SQL = "SELECT RoomNumber FROM RoomTable";
			            ResultSet rs = stmt.executeQuery(SQL);

			            // Iterate through the data in the result set and display it.
			            while (rs.next()) {
			            	txtMenu.append(rs.getString("RoomNumber")+"\n");
			                System.out.println(rs.getString("RoomNumber"));
			            }
			        }
			        // Handle any errors that may have occurred.
			        catch (SQLException e) {
			            e.printStackTrace();
			        }
			}
		});
		btnShowRoom.setBounds(33, 111, 116, 25);
		getContentPane().add(btnShowRoom);
		

		
		txtMenu = new JTextArea();
		txtMenu.setBounds(172, 44, 350, 189);
		getContentPane().add(txtMenu);
		
	}
	

}
