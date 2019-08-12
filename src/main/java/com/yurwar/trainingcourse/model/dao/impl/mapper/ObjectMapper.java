package com.yurwar.trainingcourse.model.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * @param <T> object that will be mapped
 */
public interface ObjectMapper<T> {
    /**
     * Extract object from result set
     *
     * @param rs Result set of query
     * @return Object extracted from result set
     */
    T extractFromResultSet(ResultSet rs) throws SQLException;

    /**
     * Make object unique using map
     *
     * @param cache  map with unique values of object
     * @param object object that must be unique
     * @return unique object
     */
    T makeUnique(Map<Long, T> cache, T object);
}
