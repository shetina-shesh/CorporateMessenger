package SqlTestSRC;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteUserForm extends JFrame {

	private JPanel contentPane;
	private JTextField txtRoom;
	private JTextField txtLastName;
	private JTextField txtPost;
	private JTextField txtName;
	private JTextField txtSecondName;
	
	private String id, room;
	private boolean flag = true;

	public DeleteUserForm() {
		setTitle("Delete User");
		setBounds(100, 100, 630, 409);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtRoom = new JTextField();
		txtRoom.setColumns(10);
		txtRoom.setBounds(12, 45, 116, 22);
		contentPane.add(txtRoom);
		
		JLabel label = new JLabel("Room");
		label.setBounds(43, 24, 56, 16);
		contentPane.add(label);
		
		txtLastName = new JTextField();
		txtLastName.setColumns(10);
		txtLastName.setBounds(12, 109, 116, 22);
		contentPane.add(txtLastName);
		
		JLabel label_1 = new JLabel("LastName");
		label_1.setBounds(43, 80, 85, 16);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("Post");
		label_2.setBounds(195, 80, 56, 16);
		contentPane.add(label_2);
		
		txtPost = new JTextField();
		txtPost.setColumns(10);
		txtPost.setBounds(159, 109, 116, 22);
		contentPane.add(txtPost);
		
		JLabel label_3 = new JLabel("Name");
		label_3.setBounds(43, 148, 56, 16);
		contentPane.add(label_3);
		
		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(12, 177, 116, 22);
		contentPane.add(txtName);
		
		JLabel label_4 = new JLabel("SecondName");
		label_4.setBounds(22, 212, 116, 16);
		contentPane.add(label_4);
		
		txtSecondName = new JTextField();
		txtSecondName.setColumns(10);
		txtSecondName.setBounds(12, 241, 116, 22);
		contentPane.add(txtSecondName);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//method delete user
				room = txtRoom.getText();
				
				if(room.equals("")){
					JOptionPane.showMessageDialog(null, "Не введен номер кабинета", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
				}
				else{
				
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
						
				            String SQL2 = "DELETE FROM Colloborators WHERE ID_ROOM = "+id+" AND LastName = '"+txtLastName.getText()+"' AND Name = '"+txtName.getText()+"' AND SecondName = '"+txtSecondName.getText()+"' AND Post = '"+txtPost.getText()+"'";
				            String SQL3 = "SELECT * FROM Colloborators WHERE ID_ROOM = "+id+"";
				            ResultSet rs3 = stmt.executeQuery(SQL3);
				            
				            while (rs3.next()) {
				            	if(rs3.getString("LastName").equals(txtLastName.getText()) && rs3.getString("Name").equals(txtName.getText()) && rs3.getString("SecondName").equals(txtSecondName.getText()) && rs3.getString("Post").equals(txtPost.getText())){
				            		stmt.executeUpdate(SQL2);
				            		JOptionPane.showMessageDialog(null, "Пользователь удален", "Выполнено", JOptionPane.INFORMATION_MESSAGE);
				            		//flag = true;
				            		dispose();
				            	}
				            	else{
				            		flag = false;
				            	}
				            	}
				            if(flag == false){
				            	
				            	JOptionPane.showMessageDialog(null, "Возможно допущена ошибка в вводимых данных. Исправьте и повторите попытку", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
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
		btnDelete.setBounds(195, 304, 97, 25);
		contentPane.add(btnDelete);
	}
}
