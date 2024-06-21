package it.intesys.rookie.repository;

import it.intesys.rookie.domain.Doctor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RookieRepository {
    protected static JdbcTemplate db = null;
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public RookieRepository(JdbcTemplate db) {
        this.db = db;
    }

    protected static <T> List<T> subtract(List<T> from, List<T> what) {
        ArrayList<T> clone = new ArrayList<>(from);
        clone.removeAll(what);
        return clone;
    }

    protected static String pagingQuery(StringBuilder query, Pageable pageable) {
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
}
