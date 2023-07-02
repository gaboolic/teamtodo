package tk.gbl.service;

import org.springframework.stereotype.Service;
import tk.gbl.constants.Resp;
import tk.gbl.constants.ResultType;
import tk.gbl.dao.CardDao;
import tk.gbl.dao.TaskDao;
import tk.gbl.dao.UserDao;
import tk.gbl.entity.Task;
import tk.gbl.entity.User;
import tk.gbl.entity.board.Board;
import tk.gbl.entity.board.Card;
import tk.gbl.pojo.CardPojo;
import tk.gbl.pojo.TaskPojo;
import tk.gbl.pojo.request.card.AddCardRequest;
import tk.gbl.pojo.request.card.AllCardRequest;
import tk.gbl.pojo.request.card.DeleteCardRequest;
import tk.gbl.pojo.request.card.UpdateCardRequest;
import tk.gbl.pojo.response.AllCardResponse;
import tk.gbl.pojo.response.BaseIdResponse;
import tk.gbl.pojo.response.BaseResponse;
import tk.gbl.util.TransUtil;
import tk.gbl.util.UserInfo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2015/4/7
 * Time: 13:21
 *
 * @author Tian.Dong
 */
@Service
public class CardService {

  @Resource
  UserDao userDao;

  @Resource
  CardDao cardDao;

  @Resource
  TaskDao taskDao;

  public BaseResponse allCard(AllCardRequest request) {
    User user = UserInfo.getUser();
    List<Card> cards = cardDao.allCardOfBoardAndTeam(request.getBoardId(), user.getTeam().getId());
    List<CardPojo> cardList = new ArrayList<CardPojo>();
    for (Card dbCard : cards) {
      CardPojo cardPojo = TransUtil.gen(dbCard, CardPojo.class);
      cardList.add(cardPojo);

      List<Task> tasks = taskDao.getAllOfCard(dbCard);
      List<TaskPojo> taskList = new ArrayList<TaskPojo>();
      for(Task dbTask:tasks) {
        taskList.add(TransUtil.gen(dbTask,TaskPojo.class));
      }
      cardPojo.setTaskList(taskList);
    }
    AllCardResponse response = new AllCardResponse(ResultType.SUCCESS);
    response.setCardList(cardList);
    return response;
  }

  public BaseResponse addCard(AddCardRequest request) {
    User user = UserInfo.getUser();
    Card card = new Card();
    card.setName(request.getName());
    card.setTeam(user.getTeam());
    card.setUser(user);
    Board board = new Board();
    board.setId(request.getBoardId());
    card.setBoard(board);
    card.setSeqNo(cardDao.getMaxOfBoard(board)+1000000);
    cardDao.save(card);
    BaseIdResponse response = new BaseIdResponse(ResultType.SUCCESS);
    response.setId(card.getId());
    return response;
  }

  public BaseResponse delete(DeleteCardRequest request) {
    User user = UserInfo.getUser();
    Card card = cardDao.get(request.getId());
    if (card == null) {
      return Resp.success;
    }
    User cardUser = userDao.get(card.getUser().getId());
    if (!cardUser.getTeam().getId().equals(user.getTeam().getId())) {
      return Resp.noAuth;
    }
    cardDao.delete(card);
    return Resp.success;
  }

  public BaseResponse updateCard(UpdateCardRequest request) {
    User user = UserInfo.getUser();
    Card card = cardDao.get(request.getId());
    if (card == null) {
      return Resp.success;
    }
    User cardUser = userDao.get(card.getUser().getId());
    if (!cardUser.getTeam().getId().equals(user.getTeam().getId())) {
      return Resp.noAuth;
    }
    card.setName(request.getName());
    cardDao.update(card);
    return Resp.success;
  }


}
