package core.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DefaultPreparedStatementSetter implements PreparedStatementSetter {
    private static final PreparedStatementSetter EMPTY = new DefaultPreparedStatementSetter();

    private final List<Object> values = new ArrayList<>();


    public DefaultPreparedStatementSetter() {}


    public static PreparedStatementSetter empty() {
        return EMPTY;
    }

    public DefaultPreparedStatementSetter(List<Object> values) {
        this.values.addAll(values);
    }

    @Override
    public void setValues(PreparedStatement ps) throws SQLException {
        for (int i = 0; i < values.size(); i++) {
            appendStatement(i, ps);
        }
    }

    private void appendStatement(int idx, PreparedStatement ps) throws SQLException {
        assert Objects.nonNull(ps) && values.size() > idx ;

        Object value = values.get(idx);
        Class<?> clazz = value.getClass();

        if (Integer.class == clazz || clazz.equals(Integer.TYPE)) {
            ps.setInt(idx, (int) value);
            return;
        }

        if (clazz == Double.class || clazz.equals(Double.TYPE)) {
            ps.setDouble(idx, (double) value);
            return;
        }

        if (clazz == Long.class || clazz.equals(Long.TYPE)) {
            ps.setLong(idx, (long) value);
            return;
        }

        ps.setString(idx + 1, (String) value);
    }
}
