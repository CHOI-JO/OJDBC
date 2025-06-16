package member;

import java.sql.SQLException;
import java.util.Scanner;

import member.dao.MemberDAO;
import member.dto.MemberDTO;
import member.service.MemberService;

public class MemberExam {
	// 필드
	public static Scanner inputStr = new Scanner(System.in);
	public static MemberDTO session = null; //전역 세션 선언  //로그인 하지 않는상태
	
	// 생성자->static 블럭
			
	// 메서드
	public static void main(String[] args) throws SQLException {
		// 회원을 관리하는 Exam, 로그인 후 관리 가능
		// dto: 객체를 담당한다
		// dao: 데이터베이스에서 연동을 담당한다
		// service: 부메뉴와 서비스를 담당한다

		MemberService memberService = new MemberService();
		MemberDAO memberDAO = new MemberDAO();
		boolean run = true;		
		
		while (run) {
			System.out.println("==========테스트용 홈페이지입니다..==========");
			System.out.println("1.로그인 | 2.회원가입 | 3.종료");
			System.out.print(">>>");
			String select = inputStr.next();
			
			 switch (select) {
                case "1":
                    session = memberDAO.login(inputStr);
                    if (session != null) {
                        System.out.println("[" + session.getId() + "]님, 로그인되었습니다.");
                        MemberService.subMenu(inputStr, session);
                    }
                    break;
              
                case "2":
                	MemberDTO memberDTO = new MemberDTO();
                	memberService.insertmember(memberDTO, inputStr);
                    break;
                    
                case "3": 
                	System.out.println("프로그램을 종료합니다.");
                    run = false;
                    break;
                    
                default:
                    System.out.println("1~3까지만 입력 바랍니다.");
            } // switch문 종료
        } // while문 종료
    } // main 종료

}
