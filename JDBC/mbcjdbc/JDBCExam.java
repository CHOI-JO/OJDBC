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
		// 오라클과 자바를 연동하는 jdbc 테스트
		// 프로벡트 우클릭, 빌드패스 -> 컨피그 빌드 패스에 -> 외부 라이브러리 추가 필수 ojdbc6.jar
		// referenced Libraries가 추가된다. -> ojdbc6.jar를 import 할 수 있가,
		// 예외 처리: 서비스 제공중에 런타입이 중단되는 것을 막기위한 기법
		// throws SQLException: sql 런타입 중에 오류나 버그로 중단되는 것을 처리한다.
		// 맨위에 import java.sql.SQLException; 확인
		
		// 패키지는 3개로 나누어 구현한다.
		// DTO -> 객체용, 게터/세터
		// DAO -> 데이터베이스에 접속하여 CRUD 퀴리 처리용
		// SERVICE -> 자바에서 SRUD 부메뉴용
		
		Scanner input = new Scanner(System.in);
		
		System.out.println("사원 이름: ");
		String name = input.next();
		
		System.out.println("부서명: ");
		String dept = input.next();

		System.out.println("입사 점수: ");
		double score = input.nextDouble();
		// 키보드로 입력 완료 -> num은 db에서 시쿼스 객체로 자동 주입
		
		// 객체를 생성하여 dao에게 전달해야함.
		EmpDAO empDAO = new EmpDAO(); //DAO 객체 생성 -> 메서드 호출하여 값 전달
		empDAO.insertEMP(name, dept, score);
		
		System.out.println("모든 데이터 확인해보기");
		empDAO.selectEMPALL();
		
		System.out.println("검색하고 싶은 이름을 입력하세요");
		System.out.println(">>> ");
		String searchName = input.next();
		empDAO.searchEMP(searchName);


	}

}
