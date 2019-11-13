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
		
		//���� ���ε�(���, ���� ũ�� ����, ���ڵ� ���, �����̸� ��ø��å)
		//���
		String uploadPath = request.getRealPath("upload");
		//���� ũ��
		int size = 20 * 1024 * 1024; //�� 20mbũ��
		
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
		
		//�˻��� ���(�˻���ư Ŭ��)
		if(request.getParameterValues("area") != null) {
			session.removeAttribute("search");
			search.setArea(request.getParameterValues("area"));
			search.setSearchKey("%" + request.getParameter("searchKey") + "%");
			session.setAttribute("search", search);
		}
		
		//�˻� �� �������� Ŭ���� ���
		else if(session.getAttribute("search") != null) {
			search = (Search)session.getAttribute("search");
		}
		
		
		
		//����¡ ó�� ����
		//����¡�� �۰���, �ѱ۰���, ����������, ����������
		//startPage, endPage, startRow, endRow
		
		//�ѱ۰���
		int totalCount = dao.countBoard(search);
		
		//����������
		int totalPageCount = totalCount/PAGE_SIZE;
		if(totalCount%PAGE_SIZE > 0) {
			totalPageCount++;
		}
		
		//����������
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) {
			pageNum = "1";
		}
		int requestPage = Integer.parseInt(pageNum);
		
		//startPage
		//startPage = ���������� - (���������� -1) % 5
		int startPage = requestPage - (requestPage - 1) % 5;
		
		//endPage
		int endPage = startPage + 4;
		if(endPage > totalPageCount) {
			endPage = totalPageCount;
		}
		
		//startRow
		//startRow = (���������� - 1) * �������� �۰���
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












