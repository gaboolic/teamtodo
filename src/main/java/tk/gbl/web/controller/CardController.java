package tk.gbl.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.gbl.anno.ValidField;
import tk.gbl.pojo.request.card.AddCardRequest;
import tk.gbl.pojo.request.card.DeleteCardRequest;
import tk.gbl.pojo.request.card.UpdateCardRequest;
import tk.gbl.service.CardService;

import javax.annotation.Resource;

/**
 * Date: 2015/4/1
 * Time: 15:04
 *
 * @author Tian.Dong
 */
@Controller
@RequestMapping("board/card")
public class CardController {

  @Resource
  CardService cardService;

  @RequestMapping("add")
  @ResponseBody
  public String add(@ValidField AddCardRequest request) {
    return cardService.addCard(request).toString();
  }

  @RequestMapping("delete")
  @ResponseBody
  public String delete(@ValidField DeleteCardRequest request) {
    return cardService.delete(request).toString();
  }

  @RequestMapping("update")
  @ResponseBody
  public String update(@ValidField UpdateCardRequest request) {
    return cardService.updateCard(request).toString();
  }
}
