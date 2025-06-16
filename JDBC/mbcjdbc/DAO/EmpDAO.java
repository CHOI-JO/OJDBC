package mbcjdbc.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EmpDAO {
	//jdbc�� ����ϴ� Ŭ����
	// ����Ŭ�� ��;�� �ۼ��Ͽ� srud�� ����
	// jdbc�� �̹� ������� �ܺ� ���̺귯���� 5�ܰ� ������ �ʼ�
	// 1�ܰ�: �����ϴ� ��ü Connection
	// 2�ܰ�: �������� �����ϴ� ��ü statement(����, ��������), PreparedStatement(����, ��������)
	// 3�ܰ�: �������� �����ϰ� 
	// 4�ܰ�: ������ �������� ���� select(Resultset) / �������� 
	// 5�ܰ�: ������ ����
	
	// �ʵ�
	
	
	// �⺻ ������ (������ �⺻���� ����)
	
	
	// �޼��� 
	public void insertEMP(String name, String dept, double score) throws SQLException{
		// 3���� ���� �Ű������� ���޹޾� emp ���̺�� �����ؾ���
		
		Connection conn = null; //db�� �����ϴ� ��ü
		Statement stmt = null; // ������ ���尴ü
		PreparedStatement pstmt = null; //������ ���� ��ü(����)
		ResultSet resultSet = null; //���� ���� ����� ǥ�� ���
		int result = 0; // ���� ���� ����� ������ ��� s, u, d�� ������� 1(����), 0(����)
		// ������ -> 1���� ���� ���� / ����/ ���� �Ǿ����ϴ�.
		// ���н� -> 0���� ���� ���� / ����/ ���� �Ǿ����ϴ�.
		
		try { // ���ܰ� �߻����ɼ��� �ִ� ���๮���� Ȱ��
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 1�ܰ�: ojdbc6.jar ȣ��
			conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.179:1521:xe", "jdbctest", "jdbctest");
			
			stmt = conn.createStatement(); //���� ó���� ��ü ����
			// insert intp emp(num,name,dept,score) values(emp_squ.nextval, 'name', 'dept', 'score');
			
			String sql = "insert into emp(num,name,dept,score)" + "values(emp_seq.nextval, '" + name + "', '" + dept + "', '" + score + "')";
			result = stmt.executeUpdate(sql); // int Ÿ������ ����� ���� ��ɾ�
			
			if(result>0) {
				System.out.println(result + "���� �����͸� �߰��߽��ϴ�. \n");
				conn.commit();
			} else {
				System.out.println("���: " + result + "�Դϴ�.");
				System.out.println("�Է� ���� �ѹ� �˴ϴ�.");
				conn.rollback();
			}
		}catch(ClassNotFoundException e) { // ojdbc6.jar.�� ã�� �� ���ų� forname�� �߸���
			System.out.println("ojdbc6.jar�� ���ų� forName�� ���ڿ��� �߸��Ǿ����ϴ�.");
			e.printStackTrace(); // �ڹٿ��� �����ϴ� ���� �޽��� ��� (���� ���ڵ�)
		}catch(SQLException e) {
			System.out.println("URL�̳� ID, PW�� Ȯ�����ּ���.");
			e.printStackTrace();
		}finally { 
			// �����̵� ���е� ������ �������� ����Ǵ� ��
			// ������ �ݴ�� �ݾ���
			stmt.close();
			System.out.println("������Ʈ�� ����");
			conn.close();
			System.out.println("conn�� ����");
		}
	}
	
	public void selectEMPALL() throws SQLException { // throws SQLException ȣ���� ������ ����ó�� ��.(close()��)
		// emp ���̺� ��� ������ ��ȣ�������� �������� ���
		Connection conn = null; //db�� �����ϴ� ��ü
		Statement stmt = null; // ������ ���尴ü
		PreparedStatement pstmt = null; //������ ���� ��ü(����)
		ResultSet resultSet = null; //���� ���� ����� ǥ�� ���
		int result = 0; // ���� ���� ����� ������ ��� s, u, d�� ������� 1(����), 0(����)

		try {
			// ���� �߻� ���ɼ� �ִ� ���๮
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 1�ܰ�: ojdbc6.jar ȣ��
			conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.179:1521:xe", "jdbctest", "jdbctest");
			
			stmt = conn.createStatement(); //���� ó���� ��ü ����
			
			String sql = "select * from emp order by num asc";
			resultSet = stmt.executeQuery(sql); // ����� ǥ�� �޾ƾ� ��
			
			System.out.println("---------------------------------------");
			System.out.println("��� ��ȣ\t �̸�\t �μ�\t �Ի�����");
			System.out.println("---------------------------------------");
			
			while (resultSet.next()) { // resultSet�� ǥ�������� ����� ���� .next() ���� ���� ������ true, ������ false
				System.out.print(resultSet.getInt(1) + "\t"); // .getInt(1) �ε��� ǥ����
				System.out.print(resultSet.getString(2) + "\t"); // .getString(2) �ε��� ǥ����
				System.out.print(resultSet.getString(3) + "\t");
				System.out.println(resultSet.getDouble(4) + "\t");
//				System.out.print(resultSet.getInt("num") + "\t"); // .getInt(1) �ε��� ǥ����
//				System.out.print(resultSet.getString("name") + "\t"); // .getString(2) �ε��� ǥ����
//				System.out.print(resultSet.getString("dept") + "\t");
//				System.out.println(resultSet.getDouble("score") + "\t");
			} // ǥ���� ��� ��!

		} catch(ClassNotFoundException e) { // ojdbc6.jar.�� ã�� �� ���ų� forname�� �߸���
			System.out.println("ojdbc6.jar�� ���ų� forName�� ���ڿ��� �߸��Ǿ����ϴ�.");
			e.printStackTrace(); // �ڹٿ��� �����ϴ� ���� �޽��� ��� (���� ���ڵ�)
		}catch(SQLException e) {
			System.out.println("URL�̳� ID, PW�� Ȯ�����ּ���.");
			e.printStackTrace();
		}finally { 
			// �����̵� ���е� ������ �������� ����Ǵ� ��
			// ������ �ݴ�� �ݾ���
			
			resultSet.close();
			System.out.println("������Ʈ�� ����");
			conn.close();
			System.out.println("conn�� ����");
		}

	} // selectEmpaLL �޼��� ����
	
	public void insert2EMP(String name, String dept, double score) throws SQLException {
		// 3���� ���� �Ű������� �޾� emp ���̺� ����(PreparedStatement:����)
		Connection conn = null; //db�� �����ϴ� ��ü
		Statement stmt = null; // ������ ���尴ü
		PreparedStatement pstmt = null; //������ ���� ��ü(����)
		ResultSet resultSet = null; //���� ���� ����� ǥ�� ���
		int result = 0; // ���� ���� ����� ������ ��� s, u, d�� ������� 1(����), 0(����)

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 1�ܰ�
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "mbcuser", "1234");

			String sql = "insert into emp(num, name, dept, score) values(emp_seq.nextval, ?, ?, ?)";
			// ?���� �Ķ���ͷ� ���޿�(��������) ?-> ���Ķ����
			pstmt = conn.prepareStatement(sql); // ���������� �ϼ�
			pstmt.setString(1, name);
			pstmt.setString(2, dept);
			pstmt.setDouble(3, score); // ���Ķ���� �ϼ�

			result = pstmt.executeUpdate(); // �������� �� ��� ������ ����

			if (result > 0) {
				System.out.println(result + "���� �����͸� �߰� �߽��ϴ�. \n");
				conn.commit();
			} else {
				System.out.println("��� : " + result + " �Դϴ�.");
				System.out.println("�Է� ���� �ѹ� �˴ϴ�.");
				conn.rollback();
			}

		} catch (ClassNotFoundException e) {
			// Ŭ������ ã�� �� ���� ���ܹ߻�
			System.out.println("Class.forName ����̹��� ã�� �� �����ϴ�."); // 1�ܰ� ����
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("connection ������ �ٸ��ϴ�. : url, id, pw�� Ȯ���ϼ���"); // 2�ܰ� ����
			e.printStackTrace();
		} finally { // ������ ���๮ -> db���� �����ϵ� �����ϵ� close �ʼ�
			pstmt.close();
			conn.close();
		}

	} // ���������� insert ����

	public void updateEMP(String dept, double score, String name, int num) throws SQLException {

		Connection conn = null; //db�� �����ϴ� ��ü
		Statement stmt = null; // ������ ���尴ü
		PreparedStatement pstmt = null; //������ ���� ��ü(����)
		ResultSet resultSet = null; //���� ���� ����� ǥ�� ���
		int result = 0; // ���� ���� ����� ������ ��� s, u, d�� ������� 1(����), 0(����)

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 1�ܰ�
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "mbcuser", "1234");

			String sql = "update emp set dept=?, score=?, name=? where num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dept);
			pstmt.setDouble(2, score);
			pstmt.setString(3, name);
			pstmt.setInt(4, num); // ���Ķ���� �ϼ�

			result = pstmt.executeUpdate(); // �������� �� ��� ������ ����

			if (result > 0) {
				System.out.println(result + "���� �����͸� ���� �Ͽ����ϴ�. \n");
				conn.commit();
			} else {
				System.out.println("��� : " + result + " �Դϴ�.");
				System.out.println("�Է� ���� ã�� ���� �����ϴ�.");
				conn.rollback();
			}

		} catch (ClassNotFoundException e) {
			// Ŭ������ ã�� �� ���� ���ܹ߻�
			System.out.println("Class.forName ����̹��� ã�� �� �����ϴ�."); // 1�ܰ� ����
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("connection ������ �ٸ��ϴ�. : url, id, pw�� Ȯ���ϼ���"); // 2�ܰ� ����
			e.printStackTrace();
		} finally { // ������ ���๮ -> db���� �����ϵ� �����ϵ� close �ʼ�
			pstmt.close();
			conn.close();
		}

	} // updateEMP �޼��� ����

	public void deleteEMP(String name) throws SQLException {
		// �̸����� ����
		Connection conn = null; //db�� �����ϴ� ��ü
		Statement stmt = null; // ������ ���尴ü
		PreparedStatement pstmt = null; //������ ���� ��ü(����)
		ResultSet resultSet = null; //���� ���� ����� ǥ�� ���
		int result = 0; // ���� ���� ����� ������ ��� s, u, d�� ������� 1(����), 0(����)

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 1�ܰ�
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "mbcuser", "1234");

			String sql = "delete from emp where name = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name); // ���Ķ���� �ϼ�

			result = pstmt.executeUpdate(); // �������� �� ��� ������ ����

			if (result > 0) {
				System.out.println(result + "���� �����͸� ���� �Ͽ����ϴ�. \n");
				conn.commit();
			} else {
				System.out.println("��� : " + result + " �Դϴ�.");
				System.out.println("���� ���� ã�� ���� �����ϴ�.");
				conn.rollback();
			}

		} catch (ClassNotFoundException e) {
			// Ŭ������ ã�� �� ���� ���ܹ߻�
			System.out.println("Class.forName ����̹��� ã�� �� �����ϴ�."); // 1�ܰ� ����
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("connection ������ �ٸ��ϴ�. : url, id, pw�� Ȯ���ϼ���"); // 2�ܰ� ����
			e.printStackTrace();
		} finally { // ������ ���๮ -> db���� �����ϵ� �����ϵ� close �ʼ�
			pstmt.close();
			conn.close();
		}

	} // deleteEMP �޼��� ����

	public void searchEMP(String searchName) throws SQLException { // �̸��˻���
		// Ű����� �Է¹��� ��ü�� DB���� ã�� ����Ѵ�.

		Connection conn = null; //db�� �����ϴ� ��ü
		Statement stmt = null; // ������ ���尴ü
		PreparedStatement pstmt = null; //������ ���� ��ü(����)
		ResultSet resultSet = null; //���� ���� ����� ǥ�� ���
		int result = 0; // ���� ���� ����� ������ ��� s, u, d�� ������� 1(����), 0(����)

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 1�ܰ�: ojdbc6.jar ȣ��
			conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.179:1521:xe", "jdbctest", "jdbctest");
			
			stmt = conn.createStatement(); //���� ó���� ��ü ����
			
			String sql = "select * from emp where name= " + searchName + "'";
			System.out.println("�Էµ� ������ : " + sql);
			pstmt = conn.prepareStatement(sql);
			//pstmt.setString(1, searchName);
			
			resultSet =pstmt.executeQuery(); // ������ ���� -> ���ǥ
			
			if(resultSet.next()) {
				System.out.println("-------------------");
				System.out.println("��� : " + resultSet.getInt("num"));
				System.out.println("�̸� : " + resultSet.getString("name"));
				System.out.println("�μ� : " + resultSet.getString("dept"));
				System.out.println("�Ի����� : " + resultSet.getDouble("score"));
				System.out.println("-------------------");
			}
			

		} catch (ClassNotFoundException e) {
			// Ŭ������ ã�� �� ���� ���ܹ߻�
			System.out.println("Class.forName ����̹��� ã�� �� �����ϴ�."); // 1�ܰ� ����
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("connection ������ �ٸ��ϴ�. : url, id, pw�� Ȯ���ϼ���"); // 2�ܰ� ����
			e.printStackTrace();
		} finally { // ������ ���๮ -> db���� �����ϵ� �����ϵ� close �ʼ�
			pstmt.close();
			conn.close();
		}

	}
}
