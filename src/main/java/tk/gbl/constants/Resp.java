package tk.gbl.constants;

import tk.gbl.pojo.response.BaseResponse;

/**
 * Date: 2015/4/10
 * Time: 15:52
 *
 * @author Tian.Dong
 */
public class Resp {
  public static BaseResponse success = new BaseResponse(ResultType.SUCCESS);
  public static BaseResponse noAuth = new BaseResponse(ResultType.NO_AUTH);
}
