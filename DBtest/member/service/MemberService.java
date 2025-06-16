package member.service;

import java.sql.SQLException;
import java.util.Scanner;

import member.dao.MemberDAO;
import member.dto.MemberDTO;

public class MemberService {
    public static MemberDAO memberDAO = new MemberDAO();

    // 로그인 후 서브메뉴
	public static void subMenu(Scanner inputStr, MemberDTO session) throws SQLException {
		boolean subRun = true ;
		
		while(subRun) {
			System.out.println("회원 서비스 입니다.");
			System.out.println("1. 게시판 메뉴");
			System.out.println("2. 모든 회원 보기");
			System.out.println("3. 회원 자세히 보기");
			System.out.println("4. 회원 정보 수정");
			System.out.println("5. 회원 탈퇴");
			System.out.println("6. 나가기");
			System.out.print(">>>");
			String subSelect = inputStr.next();
			
			switch(subSelect) {
			case "1":
				System.out.println("게시판");
				BoardService boardService = new BoardService();
				boardService.subMenu(session, inputStr);
				break;
			case "2":
				System.out.println("모든 회원 보기");
				selectAll();
				break;
			case "3":
				System.out.println("회원 정보 자세히 보기");
				readOne(inputStr); 
				break;
			case "4":
				System.out.println("회원 정보 수정 메서드로 진입합니다.");
				modifyOne(inputStr);
				break;
			case "5":
				System.out.println("회원 탈퇴 메서드로 진입합니다.");
				deleteOne(inputStr);
				subRun = false ;
				break;
			case "6":
				System.out.println("로그아웃합니다.");
				memberDAO.connection.close(); // 종료시 connection 종료
				subRun = false ;
				break;
			} // switch문 종료(부메뉴)
		}// while 문 종료 (부메뉴 반복)
	} // subMenu 종료
	
	private static void deleteOne(Scanner inputStr) throws SQLException {
		// 게시물의 번호를 받아 삭제한다.
		System.out.println("탈퇴할 회원 id를 입력하세요");
		System.out.print(">>>");
		String selectId = inputStr.next();
		memberDAO.deleteOne(selectId);
	}

	private static void modifyOne(Scanner inputStr) throws SQLException {
		// 제목을 찾아서 내용을 수정한다.
		System.out.println("================================================== \n회원님의 아이디를 다시 입력해주세요.");
		System.out.print(">>>");
		String selectId = inputStr.next();
		memberDAO.modifyOne(selectId, inputStr);
		System.out.println("회원 정보가 성공적으로 수정되었습니다.");
	}

	private static void readOne(Scanner inputStr) throws SQLException {
		// 제목을 입력하면 내용이 보이도록 select 처리
		System.out.println("================================================== \n회원님의 아이디를 다시 입력해주세요.");
		System.out.print(">>>");
		String selectId = inputStr.next();
		
		memberDAO.readOne(selectId);
		System.out.println("=====================================================");
	}

	private static void selectAll() throws SQLException {
        System.out.println("==================================== 전체 회원 목록 입니다.====================================");
        memberDAO.selectAll();
	}

	public void insertmember(MemberDTO memberDTO, Scanner inputStr) throws SQLException {
		Scanner inputInt = new Scanner(System.in);
		MemberDAO memberDAO = new MemberDAO();
		
		System.out.println("회원가입을 진행합니다.");
		System.out.print("ID : ");
		memberDTO.setId(inputStr.next());
				
		System.out.print("PW : ");
		memberDTO.setPw(inputStr.next());
				
		System.out.print("회원 이름 : ");
		memberDTO.setMname(inputStr.next());
				
		System.out.print("생년월일(yyMMdd) : ");	
		memberDTO.setBirthday(inputInt.nextInt());
		
		System.out.print("핸드폰 번호 : ");
		memberDTO.setPhonenumber(inputStr.next());
		
		System.out.print("이메일 주소 : ");
		memberDTO.setEmail(inputStr.next());
		
		System.out.print("집주소 : ");
		memberDTO.setAddress(inputStr.next());
		
		memberDAO.insertMember(memberDTO);
		System.out.println("==========회원 가입이 완료되었습니다.==========");
	}

	

}
