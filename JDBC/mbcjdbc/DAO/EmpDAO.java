package mbcjdbc.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EmpDAO {
	//jdbc를 담당하는 클래스
	// 오라클과 퀄;를 작성하여 srud를 제공
	// jdbc는 이미 만들어진 외부 라이브러리로 5단계 설정이 필수
	// 1단계: 연결하는 객체 Connection
	// 2단계: 쿼리문을 생성하는 객체 statement(구형, 정적쿼리), PreparedStatement(신형, 동적쿼리)
	// 3단계: 쿼리문을 실행하고 
	// 4단계: 쿼리문 실행결과를 받음 select(Resultset) / 나머지는 
	// 5단계: 연결을 종료
	
	// 필드
	
	
	// 기본 생성자 (생략시 기본으로 만듬)
	
	
	// 메서드 
	public void insertEMP(String name, String dept, double score) throws SQLException{
		// 3개의 값을 매개값으로 전달받아 emp 테이블로 전달해야함
		
		Connection conn = null; //db에 연결하는 객체
		Statement stmt = null; // 쿼리문 저장객체
		PreparedStatement pstmt = null; //쿼리문 저장 객체(신형)
		ResultSet resultSet = null; //쿼리 실행 결과를 표로 출력
		int result = 0; // 쿼리 실행 결과를 정수로 출력 s, u, d는 결과값이 1(성공), 0(실패)
		// 성공시 -> 1개의 행이 삽입 / 수정/ 삭제 되었습니다.
		// 실패시 -> 0개의 행이 삽입 / 수정/ 삭제 되었습니다.
		
		try { // 예외가 발생가능성이 있는 실행문에서 활용
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 1단계: ojdbc6.jar 호출
			conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.179:1521:xe", "jdbctest", "jdbctest");
			
			stmt = conn.createStatement(); //쿼리 처리용 객체 생성
			// insert intp emp(num,name,dept,score) values(emp_squ.nextval, 'name', 'dept', 'score');
			
			String sql = "insert into emp(num,name,dept,score)" + "values(emp_seq.nextval, '" + name + "', '" + dept + "', '" + score + "')";
			result = stmt.executeUpdate(sql); // int 타입으로 결과를 받을 명령어
			
			if(result>0) {
				System.out.println(result + "행의 데이터를 추가했습니다. \n");
				conn.commit();
			} else {
				System.out.println("결과: " + result + "입니다.");
				System.out.println("입력 실패 롤백 됩니다.");
				conn.rollback();
			}
		}catch(ClassNotFoundException e) { // ojdbc6.jar.를 찾을 수 없거나 forname이 잘못됨
			System.out.println("ojdbc6.jar이 없거나 forName에 문자열이 잘못되었습니다.");
			e.printStackTrace(); // 자바에서 제공하는 오류 메시지 출력 (빨간 글자들)
		}catch(SQLException e) {
			System.out.println("URL이나 ID, PW를 확인해주세요.");
			e.printStackTrace();
		}finally { 
			// 성공이든 실패든 무조건 마지막에 실행되는 문
			// 열림과 반대로 닫아중
			stmt.close();
			System.out.println("스테이트문 종료");
			conn.close();
			System.out.println("conn문 종료");
		}
	}
	
	public void selectEMPALL() throws SQLException { // throws SQLException 호출한 곳에서 예외처리 함.(close()용)
		// emp 테이블에 모든 정보를 번호기준으로 내림차순 출력
		Connection conn = null; //db에 연결하는 객체
		Statement stmt = null; // 쿼리문 저장객체
		PreparedStatement pstmt = null; //쿼리문 저장 객체(신형)
		ResultSet resultSet = null; //쿼리 실행 결과를 표로 출력
		int result = 0; // 쿼리 실행 결과를 정수로 출력 s, u, d는 결과값이 1(성공), 0(실패)

		try {
			// 오류 발생 가능성 있는 실행문
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 1단계: ojdbc6.jar 호출
			conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.179:1521:xe", "jdbctest", "jdbctest");
			
			stmt = conn.createStatement(); //쿼리 처리용 객체 생성
			
			String sql = "select * from emp order by num asc";
			resultSet = stmt.executeQuery(sql); // 결과를 표로 받아야 함
			
			System.out.println("---------------------------------------");
			System.out.println("사원 번호\t 이름\t 부서\t 입사점수");
			System.out.println("---------------------------------------");
			
			while (resultSet.next()) { // resultSet은 표형식으로 결과가 나옴 .next() 다음 값이 있으면 true, 없으면 false
				System.out.print(resultSet.getInt(1) + "\t"); // .getInt(1) 인덱스 표현법
				System.out.print(resultSet.getString(2) + "\t"); // .getString(2) 인덱스 표현법
				System.out.print(resultSet.getString(3) + "\t");
				System.out.println(resultSet.getDouble(4) + "\t");
//				System.out.print(resultSet.getInt("num") + "\t"); // .getInt(1) 인덱스 표현법
//				System.out.print(resultSet.getString("name") + "\t"); // .getString(2) 인덱스 표현법
//				System.out.print(resultSet.getString("dept") + "\t");
//				System.out.println(resultSet.getDouble("score") + "\t");
			} // 표형식 출력 끝!

		} catch(ClassNotFoundException e) { // ojdbc6.jar.를 찾을 수 없거나 forname이 잘못됨
			System.out.println("ojdbc6.jar이 없거나 forName에 문자열이 잘못되었습니다.");
			e.printStackTrace(); // 자바에서 제공하는 오류 메시지 출력 (빨간 글자들)
		}catch(SQLException e) {
			System.out.println("URL이나 ID, PW를 확인해주세요.");
			e.printStackTrace();
		}finally { 
			// 성공이든 실패든 무조건 마지막에 실행되는 문
			// 열림과 반대로 닫아중
			
			resultSet.close();
			System.out.println("스테이트문 종료");
			conn.close();
			System.out.println("conn문 종료");
		}

	} // selectEmpaLL 메서드 종료
	
	public void insert2EMP(String name, String dept, double score) throws SQLException {
		// 3개의 값을 매개값으로 받아 emp 테이블에 저장(PreparedStatement:신형)
		Connection conn = null; //db에 연결하는 객체
		Statement stmt = null; // 쿼리문 저장객체
		PreparedStatement pstmt = null; //쿼리문 저장 객체(신형)
		ResultSet resultSet = null; //쿼리 실행 결과를 표로 출력
		int result = 0; // 쿼리 실행 결과를 정수로 출력 s, u, d는 결과값이 1(성공), 0(실패)

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 1단계
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "mbcuser", "1234");

			String sql = "insert into emp(num, name, dept, score) values(emp_seq.nextval, ?, ?, ?)";
			// ?에는 파라미터로 전달용(동적쿼리) ?-> 인파라미터
			pstmt = conn.prepareStatement(sql); // 동적쿼리문 완성
			pstmt.setString(1, name);
			pstmt.setString(2, dept);
			pstmt.setDouble(3, score); // 인파라미터 완성

			result = pstmt.executeUpdate(); // 쿼리실행 후 결과 정수로 만듬

			if (result > 0) {
				System.out.println(result + "행의 데이터를 추가 했습니다. \n");
				conn.commit();
			} else {
				System.out.println("결과 : " + result + " 입니다.");
				System.out.println("입력 실패 롤백 됩니다.");
				conn.rollback();
			}

		} catch (ClassNotFoundException e) {
			// 클래스를 찾을 수 없는 예외발생
			System.out.println("Class.forName 드라이버를 찾을 수 없습니다."); // 1단계 예외
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("connection 정보가 다름니다. : url, id, pw를 확인하세요"); // 2단계 예외
			e.printStackTrace();
		} finally { // 무조건 실행문 -> db값을 성공하든 실패하든 close 필수
			pstmt.close();
			conn.close();
		}

	} // 동적쿼리문 insert 종료

	public void updateEMP(String dept, double score, String name, int num) throws SQLException {

		Connection conn = null; //db에 연결하는 객체
		Statement stmt = null; // 쿼리문 저장객체
		PreparedStatement pstmt = null; //쿼리문 저장 객체(신형)
		ResultSet resultSet = null; //쿼리 실행 결과를 표로 출력
		int result = 0; // 쿼리 실행 결과를 정수로 출력 s, u, d는 결과값이 1(성공), 0(실패)

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 1단계
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "mbcuser", "1234");

			String sql = "update emp set dept=?, score=?, name=? where num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dept);
			pstmt.setDouble(2, score);
			pstmt.setString(3, name);
			pstmt.setInt(4, num); // 인파라미터 완성

			result = pstmt.executeUpdate(); // 쿼리실행 후 결과 정수로 만듬

			if (result > 0) {
				System.out.println(result + "행의 데이터를 수정 하였습니다. \n");
				conn.commit();
			} else {
				System.out.println("결과 : " + result + " 입니다.");
				System.out.println("입력 실패 찾는 값이 없습니다.");
				conn.rollback();
			}

		} catch (ClassNotFoundException e) {
			// 클래스를 찾을 수 없는 예외발생
			System.out.println("Class.forName 드라이버를 찾을 수 없습니다."); // 1단계 예외
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("connection 정보가 다름니다. : url, id, pw를 확인하세요"); // 2단계 예외
			e.printStackTrace();
		} finally { // 무조건 실행문 -> db값을 성공하든 실패하든 close 필수
			pstmt.close();
			conn.close();
		}

	} // updateEMP 메서드 종료

	public void deleteEMP(String name) throws SQLException {
		// 이름으로 삭제
		Connection conn = null; //db에 연결하는 객체
		Statement stmt = null; // 쿼리문 저장객체
		PreparedStatement pstmt = null; //쿼리문 저장 객체(신형)
		ResultSet resultSet = null; //쿼리 실행 결과를 표로 출력
		int result = 0; // 쿼리 실행 결과를 정수로 출력 s, u, d는 결과값이 1(성공), 0(실패)

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 1단계
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "mbcuser", "1234");

			String sql = "delete from emp where name = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name); // 인파라미터 완성

			result = pstmt.executeUpdate(); // 쿼리실행 후 결과 정수로 만듬

			if (result > 0) {
				System.out.println(result + "행의 데이터를 삭제 하였습니다. \n");
				conn.commit();
			} else {
				System.out.println("결과 : " + result + " 입니다.");
				System.out.println("삭제 실패 찾는 값이 없습니다.");
				conn.rollback();
			}

		} catch (ClassNotFoundException e) {
			// 클래스를 찾을 수 없는 예외발생
			System.out.println("Class.forName 드라이버를 찾을 수 없습니다."); // 1단계 예외
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("connection 정보가 다름니다. : url, id, pw를 확인하세요"); // 2단계 예외
			e.printStackTrace();
		} finally { // 무조건 실행문 -> db값을 성공하든 실패하든 close 필수
			pstmt.close();
			conn.close();
		}

	} // deleteEMP 메서드 종료

	public void searchEMP(String searchName) throws SQLException { // 이름검색용
		// 키보드로 입력받은 객체를 DB에서 찾아 출력한다.

		Connection conn = null; //db에 연결하는 객체
		Statement stmt = null; // 쿼리문 저장객체
		PreparedStatement pstmt = null; //쿼리문 저장 객체(신형)
		ResultSet resultSet = null; //쿼리 실행 결과를 표로 출력
		int result = 0; // 쿼리 실행 결과를 정수로 출력 s, u, d는 결과값이 1(성공), 0(실패)

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 1단계: ojdbc6.jar 호출
			conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.179:1521:xe", "jdbctest", "jdbctest");
			
			stmt = conn.createStatement(); //쿼리 처리용 객체 생성
			
			String sql = "select * from emp where name= " + searchName + "'";
			System.out.println("입력된 쿼리문 : " + sql);
			pstmt = conn.prepareStatement(sql);
			//pstmt.setString(1, searchName);
			
			resultSet =pstmt.executeQuery(); // 쿼리문 실행 -> 결과표
			
			if(resultSet.next()) {
				System.out.println("-------------------");
				System.out.println("사번 : " + resultSet.getInt("num"));
				System.out.println("이름 : " + resultSet.getString("name"));
				System.out.println("부서 : " + resultSet.getString("dept"));
				System.out.println("입사점수 : " + resultSet.getDouble("score"));
				System.out.println("-------------------");
			}
			

		} catch (ClassNotFoundException e) {
			// 클래스를 찾을 수 없는 예외발생
			System.out.println("Class.forName 드라이버를 찾을 수 없습니다."); // 1단계 예외
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("connection 정보가 다름니다. : url, id, pw를 확인하세요"); // 2단계 예외
			e.printStackTrace();
		} finally { // 무조건 실행문 -> db값을 성공하든 실패하든 close 필수
			pstmt.close();
			conn.close();
		}

	}
}
