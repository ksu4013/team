package kosta.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kosta.model.Board;
import kosta.model.BoardService;
import kosta.model.ListModel;

public class ListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BoardService service = BoardService.getInstance();
		ActionForward forward = new ActionForward();
		
		ListModel listModel = service.listBoardService(request);		
		request.setAttribute("listModel", listModel);
		
		forward.setPath("/list.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

}
