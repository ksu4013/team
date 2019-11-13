package kosta.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import kosta.model.Board;
import kosta.model.Search;

public interface BoardMapper {
	
	int insertBoard(Board board);
	List<Board> listBoard(Search search, RowBounds row);
	Board detailBoard(int seq);
	int countBoard(Search search);

}
