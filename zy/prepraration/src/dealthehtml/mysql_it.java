package dealthehtml;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class mysql_it {
	public Connection conn;
	public mysql_it(){
		try {
			Class.forName("org.gjt.mm.mysql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();//�ĳɵ����
		}
		try {
			this.conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","abc");
		}catch(SQLException e)
		{
			e.printStackTrace();//�ĳɵ���
		}
	}
	//save_it()
}
