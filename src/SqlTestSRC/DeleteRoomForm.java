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

public class DeleteRoomForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtRoom;

	private String idRoom, room;
	private String idPerson;
	private boolean flag = true;
	
	
	public DeleteRoomForm() {
		setTitle("Delete Room");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 328, 270);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u0412\u0432\u0435\u0434\u0438\u0442\u0435 \u043D\u043E\u043C\u0435\u0440 \u043A\u0430\u0431\u0438\u043D\u0435\u0442\u0430");
		lblNewLabel.setBounds(79, 34, 152, 16);
		contentPane.add(lblNewLabel);
		
		txtRoom = new JTextField();
		txtRoom.setBounds(97, 63, 116, 22);
		contentPane.add(txtRoom);
		txtRoom.setColumns(10);
		
		JButton btnDeleteRoom = new JButton("Delete");
		btnDeleteRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//delete room method
				room = txtRoom.getText();
				
				if(room.equals("")){
					JOptionPane.showMessageDialog(null, "�� ������ ����� ��������", "������", JOptionPane.INFORMATION_MESSAGE);
				}
				else{
				
				try {
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}  
				
				String connectionUrl = "jdbc:sqlserver://localhost\\SQLEXPRESS;database=TestBaza;integratedSecurity=true;";
				
				
				try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement(); Statement stmt2 = con.createStatement();) {
		            String SQL = "SELECT ID, RoomNumber FROM RoomTable";
		            ResultSet rs = stmt.executeQuery(SQL);
		            
		            while (rs.next()) {
		            	if(rs.getString("RoomNumber").equals(room)){
		            		idRoom = rs.getString("ID");
		            		flag = true;
		            		break;
		            	}else{
		            		flag = false;
		            	}
		            	}
		            
		            if(flag == false){
		            	JOptionPane.showMessageDialog(null, "�������� �� ����������", "������", JOptionPane.INFORMATION_MESSAGE);
		            }
		            else{
		            	String SQLS = "SELECT ID FROM Colloborators WHERE ID_ROOM = "+idRoom+"";
		            	String SQLD = "DELETE FROM Login WHERE ID_L = "+ idPerson +"";
		            	ResultSet rs3 = stmt.executeQuery(SQLS);
		            	
		            	while (rs3.next()){
		            		idPerson = rs3.getString("ID");
		            		stmt2.executeUpdate(SQLD);
		            	}
		            	
		            	String SQL2 = "DELETE FROM Colloborators WHERE ID_ROOM = "+idRoom+"";
		            	String SQL3 = "DELETE FROM RoomTable WHERE ID = "+idRoom+"";
		            	stmt.executeUpdate(SQL2);
		            	stmt.executeUpdate(SQL3);
		            	JOptionPane.showMessageDialog(null, "�������� ������ �������", "���������", JOptionPane.INFORMATION_MESSAGE);
		            	dispose();
		            }
		            //System.out.print(id);
		        }
		        // Handle any errors that may have occurred.
		        catch (SQLException e) {
		            e.printStackTrace();
		        }
			}
			}
		});
		btnDeleteRoom.setBounds(106, 127, 97, 25);
		contentPane.add(btnDeleteRoom);
	}

}
