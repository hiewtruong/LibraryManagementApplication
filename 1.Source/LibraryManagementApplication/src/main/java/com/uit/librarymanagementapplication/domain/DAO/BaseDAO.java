package com.uit.librarymanagementapplication.domain.DAO;

import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author phuvn
 */
abstract public class BaseDAO<Entity, Key> 
{
    abstract public void insert(Entity entity);

    abstract public void update(Entity entity);

    abstract public void delete(Key id);

    abstract public Entity selectById(Key id);

    abstract public List<Entity> selectAll();

    abstract protected List<Entity> selectBySql(String sql, Object... args);

    abstract protected Entity mapResultSetToEntity(ResultSet rs) throws Exception;

    abstract protected Object[] mapEntityToParams(Entity entity);

    protected Object[] appendToArray(Object[] array, Object value) {
        Object[] newArray = new Object[array.length + 1];
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = value;
        return newArray;
    }
}
