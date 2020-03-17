package SqlTestSRC;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
	private static JTextArea txtMenu;

	public static void main(String[] args) throws ClassNotFoundException, SQLException{	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminConsole frame = new AdminConsole();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}  
				String connectionUrl = "jdbc:sqlserver://localhost\\SQLEXPRESS;database=TestBaza;integratedSecurity=true;";
				
			       try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {

			    	   String SQL = 
			    	   		"SELECT distinct RoomTable.RoomNumber, STUFF((SELECT ', ' + (Colloborators.LastName + ' ' +Colloborators.Name+ ' ' +Colloborators.SecondName) FROM Colloborators WHERE Colloborators.ID_ROOM=RoomTable.ID ORDER BY Colloborators.Name FOR XML PATH('')),1,1,'') AS Name FROM RoomTable left join Colloborators ON RoomTable.ID=Colloborators.ID_ROOM";
			    	   ResultSet rs = stmt.executeQuery(SQL);

			            // Iterate through the data in the result set and display it.
			            while (rs.next()) {
			            	if(rs.getString("Name") == null){
			            		txtMenu.append(rs.getString("RoomNumber")+ "\n\n");
			            	}else{
			            		String nameFlag = rs.getString("Name");			            		
			            		txtMenu.append(rs.getString("RoomNumber")+ "\n\n");
			            			for (String flag : nameFlag.split(",")) {
			            				txtMenu.append("    " +flag+"\n\n");
			            			}
			            		System.out.println(rs.getString("RoomNumber"));
			                	for (String flag : nameFlag.split(",")) {
		            	         System.out.println(flag);
		            	      }
			            	}
			                
			            }
			        }
			        // Handle any errors that may have occurred.
			        catch (SQLException e) {
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
				txtMenu.setText("");
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
 
					
				       try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
				    	   String SQL = 
					    	   		"SELECT distinct RoomTable.RoomNumber, STUFF((SELECT ', ' + (Colloborators.LastName + ' ' +Colloborators.Name+ ' ' +Colloborators.SecondName) FROM Colloborators WHERE Colloborators.ID_ROOM=RoomTable.ID ORDER BY Colloborators.Name FOR XML PATH('')),1,1,'') AS Name FROM RoomTable left join Colloborators ON RoomTable.ID=Colloborators.ID_ROOM";
					    	   ResultSet rs = stmt.executeQuery(SQL);

					            // Iterate through the data in the result set and display it.
					            while (rs.next()) {
					            	if(rs.getString("Name") == null){
					            		txtMenu.append(rs.getString("RoomNumber")+ "\n\n");
					            	}else{
					            		String nameFlag = rs.getString("Name");			            		
					            		txtMenu.append(rs.getString("RoomNumber")+ "\n\n");
					            			for (String flag : nameFlag.split(",")) {
					            				txtMenu.append("    " +flag+"\n\n");
					            			}

				            	      }
					            	}
					                
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
		

		
		txtMenu = new JTextArea();
		txtMenu.setEditable(false);
		JScrollPane scroll = new JScrollPane(txtMenu);
		scroll.setBounds(172, 44, 350, 189);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		getContentPane().add(scroll);
		
		JButton btnAddUser = new JButton("Add user");
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//открыть новое окно
				AddClientForm addForm = new AddClientForm();
				addForm.setVisible(true);
			}
		});
		btnAddUser.setBounds(33, 208, 116, 25);
		getContentPane().add(btnAddUser);
		
	}
}