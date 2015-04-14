package tk.gbl.pojo.request.task;

import tk.gbl.pojo.request.BaseIdRequest;
import tk.gbl.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2015/4/14
 * Time: 14:00
 *
 * @author Tian.Dong
 */
public class AssignTaskRequest extends BaseIdRequest {
  private Integer ownerId;
  private List<Integer> joinIds;

  public Integer getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(Integer ownerId) {
    this.ownerId = ownerId;
  }

  public List<Integer> getJoinIds() {
    return joinIds;
  }

  public void setJoinIds(List<Integer> joinIds) {
    this.joinIds = joinIds;
  }

  public static void main(String[] args){
    AssignTaskRequest request = new AssignTaskRequest();
    request.setOwnerId(3);
    List<Integer> joinIds = new ArrayList<Integer>();
    joinIds.add(1);
    joinIds.add(2);
    request.setJoinIds(joinIds);

    System.out.println(JsonUtil.toJson(request));
  }
}
