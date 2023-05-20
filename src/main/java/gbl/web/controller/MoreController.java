package gbl.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Date: 2015/3/10
 * Time: 14:27
 *
 * @author Tian.Dong
 */
@Controller
@RequestMapping("more")
public class MoreController {

  @RequestMapping("lifeGame")
  public String lifeGame(){
    return "more/lifeGame";
  }
}
