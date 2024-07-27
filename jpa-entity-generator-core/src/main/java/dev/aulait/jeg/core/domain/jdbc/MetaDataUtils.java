package dev.aulait.jeg.core.domain.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbutils.handlers.BeanHandler;

@Slf4j
public class MetaDataUtils {

  public static <T> List<T> extract(ResultSet rs, Class<T> type) throws SQLException {
    BeanHandler<T> handler = new BeanHandler<>(type);
    List<T> result = new ArrayList<>();

    T t;
    do {
      t = handler.handle(rs);
      if (t != null) {
        result.add(t);
      }
    } while (t != null);

    return result;
  }
}
