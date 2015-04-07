package tk.gbl.service;

import org.springframework.stereotype.Service;
import tk.gbl.dao.BoardDao;
import tk.gbl.pojo.request.board.AddBoardRequest;
import tk.gbl.pojo.request.board.DeleteBoardRequest;
import tk.gbl.pojo.request.board.UpdateBoardRequest;
import tk.gbl.pojo.response.BaseResponse;

import javax.annotation.Resource;

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

  public BaseResponse addBoard(AddBoardRequest request) {
    return null;
  }

  public BaseResponse updateBoard(UpdateBoardRequest request) {
    return null;
  }

  public BaseResponse delete(DeleteBoardRequest request) {
    return null;
  }
}
