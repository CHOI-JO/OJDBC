package member.service;

import java.sql.SQLException;
import java.util.Scanner;

import member.dao.BoardDAO;
import member.dto.BoardDTO;
import member.dto.MemberDTO;

public class BoardService {
	public BoardDAO boardDAO = new BoardDAO();
	
	// 생성자
	
	// 메서드 (부메뉴, 생성, 모두보기, 1개보기, 수정하기, 삭제하기) 
	public void subMenu(MemberDTO session, Scanner inputStr) throws SQLException {
		boolean subRun = true ;
		while(subRun) {
			System.out.println("\n==========================================");
			System.out.println("회원용 게시판입니다.");
			System.out.println("1. 게시판 모두보기");
			System.out.println("2. 게시글 작성");
			System.out.println("3. 게시글 자세히 보기");
			System.out.println("4. 게시글 수정");
			System.out.println("5. 게시글 삭제");
			System.out.println("6. 나가기");
			System.out.print(">>>");
			String subSelect = inputStr.next();
			
			switch(subSelect) {
			case "1":
				selectAll();
				break;
			case "2":
				insertBoard(session, inputStr);
				break;
			case "3":
				readOne(session, inputStr); 
				break;
			case "4":
				modify(session, inputStr);
				break;
			case "5":
				deleteOne();
				break;
			case "6":
				System.out.println("게시판 종료");
				boardDAO.connection.close(); // 게시판 종료시 connection 종료
				subRun = false ;
				break;		
			} // switch문 종료(부메뉴)
		}// while 문 종료 (부메뉴 반복)
	} // 부메뉴 메서드 종료

	private void deleteOne() throws SQLException {
		// 게시물의 번호를 받아 삭제한다.
		System.out.println("삭제하려는 게시글의 번호를 입력하세요");
		Scanner inputInt = new Scanner(System.in);
		System.out.print(">>>");
		int selectBno = inputInt.nextInt();
		boardDAO.deleteOne(selectBno);
		
		
	}

	private void modify(MemberDTO session, Scanner inputStr) throws SQLException {
		// 제목을 찾아서 내용을 수정한다.
		System.out.println("수정하려는 게시글 제목을 입력하세요");
		System.out.print(">>>");
		String title = inputStr.next();
		boardDAO.modify(title, inputStr);
		System.out.println("=========================");
	}

	private void readOne(MemberDTO session, Scanner inputStr) throws SQLException {
		// 제목을 입력하면 내용이 보이도록 select 처리
		System.out.println("확인할 게시글 제목을 입력하세요");
		System.out.print(">>>");
		String title = inputStr.next();
		
		boardDAO.readOne(title);
		System.out.println("============================");
		
		
	} // readOne메서드 종료

	private void insertBoard(MemberDTO session, Scanner inputStr) throws SQLException {
		// 키보드로 입력한 데이터를 dto를 사용하여 데이터베이스에 insert하자.
		BoardDTO boardDTO = new BoardDTO(); //빈객체 생성
        boardDTO.setBwriter(session.getId());
        
        System.out.print("작성자 : ");
		boardDTO.setBwriter(inputStr.next());
		
		System.out.print("제목 : ");
		boardDTO.setBtitle(inputStr.next());
		
		Scanner inputLine = new Scanner(System.in);
		System.out.print("내용 : ");
		boardDTO.setBcontent(inputLine.nextLine()); // 띄어쓰기가 있는 문장!!!! nextLine()
		
		boardDAO.insertBoard(boardDTO); // 위에서 만든 객체를 DAO에게 전달
		System.out.println("============================");
		
	} // insertBoard()메서드 종료

	private void selectAll() throws SQLException {
		// dao에게 전체보기 하는 서비스를 제공한다.
		System.out.println("=================================");
		System.out.println("====전체 게시판목록입니다====");
		boardDAO.selectAll();
		System.out.println("==================================");
		
	}
}
