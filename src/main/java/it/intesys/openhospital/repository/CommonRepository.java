package it.intesys.openhospital.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class CommonRepository {
    protected final JdbcTemplate db;
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final int BATCH_SIZE = 100;

    public CommonRepository(JdbcTemplate db) {
        this.db = db;
    }

    protected String pagingQuery(StringBuilder query, Pageable pageable) {
        String orderSep = "";
        Sort sort = pageable.getSort();
        if (!sort.isEmpty()) {
            query.append(" order by ");
            for (Sort.Order order: sort) {
                query.append(orderSep)
                        .append(order.getProperty())
                        .append(' ')
                        .append(order.getDirection().isDescending() ? "desc" : "")
                        .append(' ');
                orderSep = ", ";
            }
        }

        query.append("limit ")
                .append(pageable.getPageSize())
                .append(' ')
                .append("offset ")
                .append(pageable.getOffset());

        return query.toString();
    }

    protected <T> List<T> subtract(List<T> from, List<T> what) {
        ArrayList<T> clone = new ArrayList<>(from);
        clone.removeAll(what);
        return clone;
    }
}