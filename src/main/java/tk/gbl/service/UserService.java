package tk.gbl.service;

import org.springframework.stereotype.Service;
import tk.gbl.constants.ResultType;
import tk.gbl.dao.UserDao;
import tk.gbl.entity.User;
import tk.gbl.pojo.request.SignRequest;
import tk.gbl.pojo.response.BaseResponse;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Date: 2015/4/8
 * Time: 15:48
 *
 * @author Tian.Dong
 */
@Service
public class UserService {
  @Resource
  UserDao userDao;

  public BaseResponse sign(SignRequest request, HttpSession session) {
    User user = userDao.get(Integer.valueOf(request.getSign()));
    session.setAttribute("user",user);
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    return response;
  }
}
