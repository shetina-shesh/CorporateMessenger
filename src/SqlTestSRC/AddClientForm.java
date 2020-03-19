package SqlTestSRC;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddClientForm extends JFrame {

	private JPanel contentPane;
	private static final long serialVersionUID = 1L;
	private JTextField txtAddRoom;
	private JTextField txtLastName;
	private JTextField txtName;
	private JTextField txtSecondName;
	private JTextField txtPost;
	
	private String room, lastname, name, secondname, post;
	private String id;
	private boolean flag = true;

	/**
	 * Create the frame.
	 */
	public AddClientForm() {
		setTitle("Add Person");
		setBounds(100, 100, 594, 410);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblRoom = new JLabel("Room");
		lblRoom.setBounds(43, 31, 56, 16);
		contentPane.add(lblRoom);
		
		txtAddRoom = new JTextField();
		txtAddRoom.setBounds(12, 52, 116, 22);
		contentPane.add(txtAddRoom);
		txtAddRoom.setColumns(10);
		
		JLabel lblLastname = new JLabel("LastName");
		lblLastname.setBounds(43, 87, 85, 16);
		contentPane.add(lblLastname);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(43, 155, 56, 16);
		contentPane.add(lblName);
		
		JLabel lblSecondname = new JLabel("SecondName");
		lblSecondname.setBounds(22, 219, 116, 16);
		contentPane.add(lblSecondname);
		
		JLabel lblPost = new JLabel("Post");
		lblPost.setBounds(195, 87, 56, 16);
		contentPane.add(lblPost);
		
		txtLastName = new JTextField();
		txtLastName.setBounds(12, 116, 116, 22);
		contentPane.add(txtLastName);
		txtLastName.setColumns(10);
		
		txtName = new JTextField();
		txtName.setBounds(12, 184, 116, 22);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		txtSecondName = new JTextField();
		txtSecondName.setBounds(12, 248, 116, 22);
		contentPane.add(txtSecondName);
		txtSecondName.setColumns(10);
		
		txtPost = new JTextField();
		txtPost.setBounds(159, 116, 116, 22);
		contentPane.add(txtPost);
		txtPost.setColumns(10);
		
		JButton btnAddPerson = new JButton("AddPerson");
		btnAddPerson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//addPerson method
				
				room = txtAddRoom.getText();
				
				if(room.equals("")){
					JOptionPane.showMessageDialog(null, "Не введен номер кабинета", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
				}
				else{
					
				lastname = txtLastName.getText();
				name = txtName.getText();
				secondname = txtSecondName.getText();
				post = txtPost.getText();
				
				try {
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}  
				
				String connectionUrl = "jdbc:sqlserver://localhost\\SQLEXPRESS;database=TestBaza;integratedSecurity=true;";
				
				
				try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
		            String SQL = "SELECT ID, RoomNumber FROM RoomTable";
		            ResultSet rs = stmt.executeQuery(SQL);
		            
		            while (rs.next()) {
		            	if(rs.getString("RoomNumber").equals(room)){
		            		id = rs.getString("ID");
		            		flag = true;
		            		break;
		            	}else{            		
		            		flag = false;
		            	}
		            	}
		            
		            if(flag == false){
		            	JOptionPane.showMessageDialog(null, "Кабинета не существует", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
		            }
		            else{
		            	if(lastname.equals("") || name.equals("") || secondname.equals("") || post.equals("")){
		            		JOptionPane.showMessageDialog(null, "Все поля должны быть заполнены", "Предупреждение", JOptionPane.INFORMATION_MESSAGE);
		            	}
		            	else{    		 
		 			            String SQL2 = "INSERT INTO Colloborators VALUES('"+id+"','"+lastname+"','"+name+"','"+secondname+"','"+post+"')";
		 			            stmt.executeUpdate(SQL2);
			
		 			       JOptionPane.showMessageDialog(null, "Пользователь добавлен", "Успеншо", JOptionPane.INFORMATION_MESSAGE);
		 			       dispose();
		            	}
		            }
		        }
		        // Handle any errors that may have occurred.
		        catch (SQLException e) {
		            e.printStackTrace();
		        }
						
			}
		}
		});
		btnAddPerson.setBounds(235, 309, 97, 25);
		contentPane.add(btnAddPerson);
	}
}
