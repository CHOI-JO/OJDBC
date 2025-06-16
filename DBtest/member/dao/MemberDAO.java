package member.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import member.dto.MemberDTO;

public class MemberDAO {

	// 필드
	public MemberDTO memberDTO = new MemberDTO();	
	public Connection connection = null; 
	public Statement statement = null; 
	public PreparedStatement preparestatement = null; 
	public ResultSet resultset = null; 
	public int result = 0; 
		
	//이클립스DB연결 
	public MemberDAO() {
		try { // 예외가 발생할 수 있는 실행문 프로그램 강제종료 처리용
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 1단계 ojdbc6.jar 호출
			connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.179:1521:xe", "membertest","membertest"); 
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 이름이나, ojdbc6.jar 파일이 잘못 되었습니다.");
			e.printStackTrace();
			System.exit(0); // 강제 종료
		} catch (SQLException e) {
			System.out.println("url, id, pw가 잘못 되었습니다. MemberDAO에 기본생성자를 확인하세요");
			e.printStackTrace();
			System.exit(0); // 강제 종료
		}

	} // 기본 메서드 종료
		
	public MemberDTO login(Scanner inputStr) throws SQLException {
	    System.out.print("아이디를 입력하세요: ");
	    String id = inputStr.next();
	    System.out.print("비밀번호를 입력하세요: ");
	    String pw = inputStr.next();
	    
	    MemberDTO session = null;

	    try {
	        String sql = "SELECT * FROM member WHERE id = ? AND pw = ?";
	        preparestatement = connection.prepareStatement(sql);
	        preparestatement.setString(1, id);
	        preparestatement.setString(2, pw);
	        resultset = preparestatement.executeQuery();

	        if (resultset.next()) {
	            session = new MemberDTO(); // 로그인된 회원 객체 생성
	            session.setId(resultset.getString("id"));
	            session.setPw(resultset.getString("pw"));

	            System.out.println("[" + session.getId() + "]님 환영합니다");
	        } else {
	            System.out.println("로그인이 실패 하였습니다. 아이디 또는 비밀번호를 다시 확인해주세요.");
	        }

	    } catch (SQLException e) {
	        System.out.println("예외발생 : login() 메서드 확인");
	        e.printStackTrace();
	    } finally {
	        preparestatement.close();
	    }
	    return session;
	}// login() 메서드 종료
	
	public void insertMember(MemberDTO memberDTO) throws SQLException {
		try {
			String sql = "INSERT INTO member(mno, id, pw, mname, birthday, phonenumber, email, address, regidate) "
			           + "VALUES (board_seq.nextval, ?, ?, ?, ?, ?, ?, ?, sysdate)";
			preparestatement = connection.prepareStatement(sql);

			preparestatement.setString(1, memberDTO.getId());
			preparestatement.setString(2, memberDTO.getPw());
			preparestatement.setString(3, memberDTO.getMname());
			preparestatement.setInt(4, memberDTO.getBirthday());  // 생년월일: 숫자
			preparestatement.setString(5, memberDTO.getPhonenumber());
			preparestatement.setString(6, memberDTO.getEmail());
			preparestatement.setString(7, memberDTO.getAddress());

			result = preparestatement.executeUpdate(); // 쿼리문 실행해서 결과를 정수로 받음

			if (result > 0) {
				connection.commit(); // 영구저장
			} else {
				System.out.println("쿼리 실행 결과 : " + result);
				System.out.println("입력실패!!!");
				connection.rollback(); // 롤백(저장취소)
			}
		} catch (SQLException e) {
			System.out.println("예외발생 : insertMember()메서드에 쿼리문을 확인하세요 ");
			e.printStackTrace();

		} finally {
			if (preparestatement != null) {
			    preparestatement.close();
			}
		}
		
	}

	public void selectAll() throws SQLException { 
		try {
			String sql = "select * from member order by mno desc";

			statement = connection.createStatement(); 
			resultset = statement.executeQuery(sql); 

			System.out.printf("%-5s %-10s %-10s %-8s %-15s %-20s %-15s %-12s\n", "번호", "id", "이름", "생년월일", "핸드폰 번호", "이메일 주소", "집 주소", "가입일");
			while (resultset.next()) {
				 System.out.printf("%-5d %-10s %-10s %-8d %-15s %-20s %-15s %-12s\n", resultset.getInt("mno"), resultset.getString("id"), resultset.getString("mname"), resultset.getInt("birthday"), resultset.getString("phonenumber"), resultset.getString("email"), resultset.getString("address"), resultset.getDate("regidate").toString());
			}
			System.out.println("==================================");
		} catch (SQLException e) {
			// 오류발생시 예외처리문
			System.out.println("selectAll()메서드에 쿼리문이 잘못 되었습니다.");
			e.printStackTrace();
		} finally {
			// 항상실행문
			resultset.close();
			statement.close();
			// 열린 객체를 닫아야 다른 메서드도 정상 작동함.
		}
		
	}

	public void readOne(String selectId) throws SQLException {
		// 제목 문자열이 넘어온 것을 select 처리하여 출력 함

		try {
			String sql = "select mno, id, pw, mname, birthday, phonenumber, email, address, regidate from member where id= ?";
			preparestatement = connection.prepareStatement(sql);
			preparestatement.setString(1, selectId); // service에서 넘어온 찾고 싶은 제목이 ?로 넘어간다.
			resultset = preparestatement.executeQuery(); // 쿼리문 실행 후 결과를 표로 받는다.

			if (resultset.next()) {
				MemberDTO memberDTO = new MemberDTO();

				memberDTO.setMno(resultset.getInt("mno"));
				memberDTO.setId(resultset.getString("id"));
				memberDTO.setPw(resultset.getString("pw"));
				memberDTO.setMname(resultset.getString("mname"));
				memberDTO.setBirthday(resultset.getInt("birthday"));
				memberDTO.setPhonenumber(resultset.getString("phonenumber"));
				memberDTO.setEmail(resultset.getString("email"));
				memberDTO.setAddress(resultset.getString("address"));
				memberDTO.setRegidate(resultset.getDate("regidate"));

				System.out.println("=========================");
				System.out.println("회원 번호 : " + memberDTO.getMno());
				System.out.println("회원 아이디 : " + memberDTO.getId());
				System.out.println("회원 비밀번호 : " + memberDTO.getPw());
				System.out.println("회원 이름 : " + memberDTO.getMname());
				System.out.println("회원 생년월일 : " + memberDTO.getBirthday());
				System.out.println("회원 핸드폰 번호 : " + memberDTO.getPhonenumber());
				System.out.println("회원 이메일 주소 : " + memberDTO.getEmail());
				System.out.println("회원 집 주소 : " + memberDTO.getAddress());
				System.out.println("가입일 : " + memberDTO.getRegidate());

			} else {
				// 검색 결과가 없으면!!!
				System.out.println("해당하는 게시물이 존재하지 않습니다.");

			} // if문 종료

		} catch (SQLException e) {
			System.out.println("예외발생 : readOne() 메서드를 확인하세요");
			e.printStackTrace();
		} finally {
			// 항상 실행문
			resultset.close();
			preparestatement.close();
		}
	}//readOne() 메서드 종료

	public void modifyOne(String selectId, Scanner inputStr) throws SQLException {
		MemberDTO memberDTO = new MemberDTO();
		Scanner inputInt = new Scanner(System.in);
		System.out.println("수정할 " + selectId + "정보를 순서대로 입력하세요");
		
		System.out.print("이름 : ");
		memberDTO.setMname(inputStr.next());
		System.out.print("비밀번호 : ");
		memberDTO.setPw(inputStr.next());
		System.out.print("생년월일 : ");
		memberDTO.setBirthday(inputInt.nextInt());
		System.out.print("핸드폰 번호 : ");
		memberDTO.setPhonenumber(inputStr.next());
		System.out.print("이메일 주소 : ");
		memberDTO.setEmail(inputStr.next());
		System.out.print("집 주소 : ");
		memberDTO.setAddress(inputStr.next());

		try {
			String sql = "update member set mname=? , pw =?, birthday =?, phonenumber=?, email=?, address=? where id=?";
			preparestatement = connection.prepareStatement(sql);
			preparestatement.setString(1, memberDTO.getMname()); 
			preparestatement.setString(2, memberDTO.getPw()); 
			preparestatement.setInt(3, memberDTO.getBirthday()); 
			preparestatement.setString(4, memberDTO.getPhonenumber()); 
			preparestatement.setString(5, memberDTO.getEmail()); 
			preparestatement.setString(6, memberDTO.getAddress()); 
			preparestatement.setString(7, selectId); 

			result = preparestatement.executeUpdate(); // 쿼리문 실행후 결과를 정수로 보냄

			if (result > 0) {
				System.out.println(memberDTO.getMname() + "님의 정보가 수정 되었습니다. ");
				connection.commit(); // 영구 저장
			} else {
				System.out.println("수정이 되지 않았습니다.");
				connection.rollback();
			}

		} catch (SQLException e) {
			System.out.println("예외발생 : modify() 메서드와 sql문을 확인하세요!!!");
			e.printStackTrace();
		} finally {
			preparestatement.close();
		}
	}

	public void deleteOne(String selectId) throws SQLException {
		// 서비스에서 받은 기존 아이디의 데이터를 삭제한다.
		try {
	        String sql = "DELETE FROM member WHERE id = ?";
	        preparestatement = connection.prepareStatement(sql);
	        preparestatement.setString(1, selectId);

	        result = preparestatement.executeUpdate();

	        if (result > 0) {
	            System.out.println(selectId + "님이 탈퇴되었습니다.");
	            connection.commit(); 
	        } else {
	            System.out.println("탈퇴되지 않았습니다.");
	            connection.rollback();
	        }

	        selectAll(); // 삭제 후 전체 회원 보기

	    } catch (SQLException e) {
	        System.out.println("예외발생 : deleteOne() 메서드에서 오류");
	        e.printStackTrace();
	    } finally {
	    	preparestatement.close();	       
	    }
	}


	
	
}
