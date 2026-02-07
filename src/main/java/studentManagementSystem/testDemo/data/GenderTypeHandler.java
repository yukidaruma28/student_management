package studentManagementSystem.testDemo.data;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

/**
 * Gender EnumとデータベースのENUM('男性','女性','その他')の間の変換を行うTypeHandler
 */
@MappedTypes(Gender.class)
public class GenderTypeHandler extends BaseTypeHandler<Gender> {

  /**
   * PreparedStatementにパラメータを設定(Java → DB)
   * Gender.MALE → "男性"
   */
  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, Gender parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setString(i, parameter.getDisplayName());
  }

  /**
   * ResultSetから値を取得(DB → Java)
   * "男性" → Gender.MALE
   */
  @Override
  public Gender getNullableResult(ResultSet rs, String columnName) throws SQLException {
    String displayName = rs.getString(columnName);
    return displayName == null ? null : Gender.fromDisplayName(displayName);
  }

  @Override
  public Gender getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    String displayName = rs.getString(columnIndex);
    return displayName == null ? null : Gender.fromDisplayName(displayName);
  }

  @Override
  public Gender getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    String displayName = cs.getString(columnIndex);
    return displayName == null ? null : Gender.fromDisplayName(displayName);
  }
}
