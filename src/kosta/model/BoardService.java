package kosta.model;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class BoardService {
	private static BoardService service = new BoardService();
	private static BoardDao2 dao;
	private static final int PAGE_SIZE = 2;
	
	public static BoardService getInstance() {
		dao = BoardDao2.getInstance();
		return service;
	}
	
	public int insertBoardService(HttpServletRequest request)throws Exception{
		request.setCharacterEncoding("utf-8");
		
		//파일 업로드(경로, 파일 크기 제한, 인코딩 방식, 파일이름 중첩정책)
		//경로
		String uploadPath = request.getRealPath("upload");
		//파일 크기
		int size = 20 * 1024 * 1024; //약 20mb크기
		
		MultipartRequest multi = new MultipartRequest(request, uploadPath, size, 
				"utf-8", new DefaultFileRenamePolicy());
		
		Board board = new Board();
		board.setContents(multi.getParameter("contents"));
		board.setTitle(multi.getParameter("title"));
		board.setWriter(multi.getParameter("writer"));
		
		if (multi.getFilesystemName("fname") != null) {
			String fname = (String)multi.getFilesystemName("fname");
			board.setFname(fname);
		}
				
		return dao.insertBoard(board);
	}
	
	public ListModel listBoardService(HttpServletRequest request)throws Exception{
		Search search = new Search();
		HttpSession session = request.getSession();
		
		//검색할 경우(검색버튼 클릭)
		if(request.getParameterValues("area") != null) {
			session.removeAttribute("search");
			search.setArea(request.getParameterValues("area"));
			search.setSearchKey("%" + request.getParameter("searchKey") + "%");
			session.setAttribute("search", search);
		}
		
		//검색 후 페이지를 클릭할 경우
		else if(session.getAttribute("search") != null) {
			search = (Search)session.getAttribute("search");
		}
		
		
		
		//페이징 처리 로직
		//페이징당 글갯수, 총글갯수, 총페이지수, 현재페이지
		//startPage, endPage, startRow, endRow
		
		//총글갯수
		int totalCount = dao.countBoard(search);
		
		//총페이지수
		int totalPageCount = totalCount/PAGE_SIZE;
		if(totalCount%PAGE_SIZE > 0) {
			totalPageCount++;
		}
		
		//현재페이지
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) {
			pageNum = "1";
		}
		int requestPage = Integer.parseInt(pageNum);
		
		//startPage
		//startPage = 현재페이지 - (현재페이지 -1) % 5
		int startPage = requestPage - (requestPage - 1) % 5;
		
		//endPage
		int endPage = startPage + 4;
		if(endPage > totalPageCount) {
			endPage = totalPageCount;
		}
		
		//startRow
		//startRow = (현재페이지 - 1) * 페이지당 글갯수
		int startRow = (requestPage - 1) * PAGE_SIZE;		
		
		List<Board> list = dao.listBoard(search, startRow);
		
		ListModel listModel = new ListModel(list, requestPage, totalPageCount, startPage, endPage);
		
		return listModel;
	}
	
	public Board detailBoardService(int seq) {
		Board board = dao.detailBoard(seq);
		
		return board;
	}
			
}












