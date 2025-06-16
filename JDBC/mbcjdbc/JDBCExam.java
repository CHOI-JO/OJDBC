package mbcjdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import mbcjdbc.DAO.EmpDAO;

public class JDBCExam {

	public static void main(String[] args) throws SQLException {
		// ����Ŭ�� �ڹٸ� �����ϴ� jdbc �׽�Ʈ
		// ���κ�Ʈ ��Ŭ��, �����н� -> ���Ǳ� ���� �н��� -> �ܺ� ���̺귯�� �߰� �ʼ� ojdbc6.jar
		// referenced Libraries�� �߰��ȴ�. -> ojdbc6.jar�� import �� �� �ְ�,
		// ���� ó��: ���� �����߿� ��Ÿ���� �ߴܵǴ� ���� �������� ���
		// throws SQLException: sql ��Ÿ�� �߿� ������ ���׷� �ߴܵǴ� ���� ó���Ѵ�.
		// ������ import java.sql.SQLException; Ȯ��
		
		// ��Ű���� 3���� ������ �����Ѵ�.
		// DTO -> ��ü��, ����/����
		// DAO -> �����ͺ��̽��� �����Ͽ� CRUD ���� ó����
		// SERVICE -> �ڹٿ��� SRUD �θ޴���
		
		Scanner input = new Scanner(System.in);
		
		System.out.println("��� �̸�: ");
		String name = input.next();
		
		System.out.println("�μ���: ");
		String dept = input.next();

		System.out.println("�Ի� ����: ");
		double score = input.nextDouble();
		// Ű����� �Է� �Ϸ� -> num�� db���� ������ ��ü�� �ڵ� ����
		
		// ��ü�� �����Ͽ� dao���� �����ؾ���.
		EmpDAO empDAO = new EmpDAO(); //DAO ��ü ���� -> �޼��� ȣ���Ͽ� �� ����
		empDAO.insertEMP(name, dept, score);
		
		System.out.println("��� ������ Ȯ���غ���");
		empDAO.selectEMPALL();
		
		System.out.println("�˻��ϰ� ���� �̸��� �Է��ϼ���");
		System.out.println(">>> ");
		String searchName = input.next();
		empDAO.searchEMP(searchName);


	}

}
