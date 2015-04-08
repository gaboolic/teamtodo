package tk.gbl.pojo.response;

import tk.gbl.constants.ResultType;

import java.util.List;

/**
 * Date: 2015/4/8
 * Time: 17:12
 *
 * @author Tian.Dong
 */
public class ShowStarResponse extends BaseResponse {
  private List<String> dateList;

  public ShowStarResponse(ResultType type) {
    super(type);
  }

  public void setDateList(List<String> dateList) {
    this.dateList = dateList;
  }

  public List<String> getDateList() {
    return dateList;
  }
}
