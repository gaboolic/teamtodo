package tk.gbl.dao;

import java.util.List;

/**
 * Date: 2014/7/31
 * Time: 13:37
 *
 * @author Tian.Dong
 */
public interface BaseDao<Entity> {

  public Entity get(int id);

  public List<Entity> getAll();

  public boolean save(Entity entity);

  public boolean update(Entity entity);

  public boolean delete(Entity entity);

  public List<Entity> find(String sql, Object... obj);
  
  public List<Entity> findByPage(Integer page, Integer size, String sql, Object... obj);
  
  public boolean exec(String sql, Object... obj);
}
