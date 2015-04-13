package tk.gbl.pojo.response;

import tk.gbl.constants.ResultType;
import tk.gbl.pojo.CardPojo;

import java.util.List;

/**
 * Date: 2015/4/13
 * Time: 15:01
 *
 * @author Tian.Dong
 */
public class AllCardResponse extends BaseResponse {
  private List<CardPojo> cardList;

  public AllCardResponse(ResultType type) {
    super(type);
  }

  public void setCardList(List<CardPojo> cardList) {
    this.cardList = cardList;
  }

  public List<CardPojo> getCardList() {
    return cardList;
  }
}
