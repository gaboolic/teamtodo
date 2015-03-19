package tk.gbl.dao;

import java.util.List;

public interface BaseDao<Entry> {
	/**
	 * 根据ID查找实体
	 * @param id
	 * @return
	 */
	Entry get(int id);
	
	/**
	 * 根据主键字段查找实体
	 * @param str
	 * @return
	 */
	Entry get(String str);
	
	/**
	 * 全部列表
	 * @return
	 */
	List<Entry> getAll();

	/**
	 * 插入实体
	 * @param e
	 * @return
	 */
	boolean save(Entry e);

	/**
	 * 删除实体
	 * @param e
	 * @return
	 */
	boolean delete(Entry e);

	/**
	 * 根据ID删除实体
	 * @param id
	 * @return
	 */
	boolean delete(int id);
	
	/**
	 * 根据主键删除实体
	 * @param str
	 * @return
	 */
	boolean delete(String str);
	
	boolean deleteAll();

	/**
	 * 更新
	 * @param e
	 * @return
	 */
	boolean update(Entry e);
}
