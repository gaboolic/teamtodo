package tk.gbl.service;

import org.springframework.stereotype.Service;
import tk.gbl.constants.Resp;
import tk.gbl.constants.ResultType;
import tk.gbl.dao.BoardDao;
import tk.gbl.dao.TeamDao;
import tk.gbl.entity.User;
import tk.gbl.entity.board.Board;
import tk.gbl.pojo.BoardPojo;
import tk.gbl.pojo.request.board.AddBoardRequest;
import tk.gbl.pojo.request.board.DeleteBoardRequest;
import tk.gbl.pojo.request.board.UpdateBoardRequest;
import tk.gbl.pojo.response.AllBoardResponse;
import tk.gbl.pojo.response.BaseIdResponse;
import tk.gbl.pojo.response.BaseResponse;
import tk.gbl.util.TransUtil;
import tk.gbl.util.UserInfo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2015/4/7
 * Time: 11:30
 *
 * @author Tian.Dong
 */
@Service
public class BoardService {
  @Resource
  BoardDao boardDao;

  @Resource
  TeamDao teamDao;

  public BaseResponse addBoard(AddBoardRequest request) {
    User user = UserInfo.getUser();
    Board board = new Board();
    board.setName(request.getName());
    board.setAuth(request.getAuth());
    board.setUser(user);
    board.setTeam(user.getTeam());
    boardDao.save(board);
    BaseIdResponse response = new BaseIdResponse(ResultType.SUCCESS);
    response.setId(board.getId());
    return response;
  }

  public BaseResponse updateBoard(UpdateBoardRequest request) {
    User user = UserInfo.getUser();
    Board board = boardDao.get(request.getId());
    if (board == null) {
      return Resp.success;
    }
    User boardUser = board.getUser();
    if (!boardUser.getTeam().getId().equals(user.getTeam().getId())) {
      return Resp.noAuth;
    }
    board.setName(request.getName());
    board.setAuth(request.getAuth());
    boardDao.update(board);
    return Resp.success;
  }

  public BaseResponse delete(DeleteBoardRequest request) {
    User user = UserInfo.getUser();
    Board board = boardDao.get(request.getId());
    if (board == null) {
      return Resp.success;
    }
    User boardUser = board.getUser();
    if (!boardUser.getTeam().getId().equals(user.getTeam().getId())) {
      return Resp.noAuth;
    }
    boardDao.delete(board);
    return Resp.success;
  }

  public BaseResponse allBoard() {
    User user = UserInfo.getUser();
    List<Board> boards = boardDao.allBoardOfTeam(user.getTeam());
    List<BoardPojo> boardList = new ArrayList<BoardPojo>();
    for(Board dbBoard:boards) {
      if(dbBoard.getAuth().equals("-1") && !dbBoard.getUser().getId().equals(user.getId())) {
        continue;
      }
      boardList.add(TransUtil.gen(dbBoard,BoardPojo.class));
    }
    AllBoardResponse allBoardResponse = new AllBoardResponse(ResultType.SUCCESS);
    allBoardResponse.setBoardList(boardList);
    return allBoardResponse;
  }
}
