package tk.gbl.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.gbl.anno.ValidField;
import tk.gbl.pojo.request.board.AddBoardRequest;
import tk.gbl.pojo.request.board.DeleteBoardRequest;
import tk.gbl.pojo.request.board.UpdateBoardRequest;
import tk.gbl.service.BoardService;

import javax.annotation.Resource;

/**4
 * Date: 2015/4/1
 * Time: 15:04
 *
 * @author Tian.Dong
 */
@Controller
@RequestMapping("board")
public class BoardController {

  @Resource
  BoardService boardService;

  @RequestMapping(value = "all", method = RequestMethod.GET) //
  @ResponseBody
  public String all() {
    return boardService.allBoard().toString();
  }

  @RequestMapping("add") //
  @ResponseBody
  public String add(@ValidField @RequestBody AddBoardRequest request) {
    return boardService.addBoard(request).toString();
  }

  @RequestMapping("delete") //
  @ResponseBody
  public String delete(@ValidField DeleteBoardRequest request) {
    return boardService.delete(request).toString();
  }

  @RequestMapping("update") //
  @ResponseBody
  public String update(@ValidField UpdateBoardRequest request) {
    return boardService.updateBoard(request).toString();
  }
}
