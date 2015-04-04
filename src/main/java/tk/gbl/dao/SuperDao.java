package tk.gbl.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import tk.gbl.util.log.LoggerUtil;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Date: 2014/7/31
 * Time: 13:38
 *
 * @author Tian.Dong
 */
public class SuperDao<Entity> extends HibernateDaoSupport implements BaseDao<Entity> {

  @Resource
  public void setHibernateTemplate0(HibernateTemplate hibernateTemplate) {
    super.setHibernateTemplate(hibernateTemplate);
  }

  @Override
  public Entity get(int id) {
    return getHibernateTemplate().get(getEntityClass(), id);
  }

  @Override
  public List<Entity> getAll() {
    String tableName = getEntityClass().getSimpleName();
    return (List<Entity>) getHibernateTemplate().find("from " + tableName);
  }

  @Override
  public boolean save(Entity entity) {
    try {
      getHibernateTemplate().save(entity);
      return true;
    } catch (Exception e) {
      LoggerUtil.error("保存失败:" + entity, e);
      return false;
    }
  }

  @Override
  public boolean update(Entity entity) {
    try {
      getHibernateTemplate().update(entity);
      return true;
    } catch (Exception e) {
      LoggerUtil.error("修改失败:" + entity, e);
      return false;
    }
  }

  @Override
  public boolean delete(Entity entity) {
    try {
      getHibernateTemplate().delete(entity);
      return true;
    } catch (Exception e) {
      LoggerUtil.error("删除失败:" + entity, e);
      return false;
    }
  }

  @Override
  public List<Entity> find(String sql, Object... obj) {
    return (List<Entity>) this.getHibernateTemplate().find(sql, obj);
  }

  public Entity findFirst(String sql, Object... obj) {
    List<Entity> list = (List<Entity>) this.getHibernateTemplate().find(sql, obj);
    if(list!=null && list.size()>0) {
      return list.get(0);
    }
    return null;
  }

  public List<Entity> findByPage(Integer page,Integer size,String sql, Object... obj){
    Session session = this.getSessionFactory().getCurrentSession();
    Query query = session.createQuery(sql);
    for(int i=0;i<obj.length;i++){
      query.setParameter(i,obj[i]);
    }
    query.setFirstResult((page-1)*size).setMaxResults(size);
    return query.list();
  }

  protected Class<Entity> getEntityClass() {
    Type genType = getClass().getGenericSuperclass();
    Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
    Class<Entity> entityClass = (Class<Entity>) params[0];
    return entityClass;
  }

  @Override
  public boolean exec(String sql, Object... obj) {
	 Session session = this.getSessionFactory().getCurrentSession();
	 Query query = session.createQuery(sql);
	 for(int i=0;i<obj.length;i++){
	    query.setParameter(i,obj[i]);
	 }
	 try {
		session.getTransaction().commit();
	 } catch (Exception e) {
		LoggerUtil.error("SuperDao   session.getTransaction().commit();", e);
		return false;
	 }
	 return true;
	
  }

}
