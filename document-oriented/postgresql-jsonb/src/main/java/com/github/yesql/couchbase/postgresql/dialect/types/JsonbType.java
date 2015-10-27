package com.github.yesql.couchbase.postgresql.dialect.types;

import com.google.gson.Gson;
import org.apache.commons.lang.SerializationException;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;
import org.postgresql.util.PGobject;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

/**
 * @author Martin Janys
 */
public class JsonbType implements UserType, ParameterizedType {

    public static final String NAME = "jsonb";
    public static final String CLASS = "class";

    private static final Gson gson = new Gson();

    private Class<?> entityClass;

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.JAVA_OBJECT};
    }

    @Override
    public Class<?> returnedClass() {
        return entityClass;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return x.equals(y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        String json = rs.getString(names[0]);
        return json == null ? null : gson.fromJson(json, entityClass);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        String json = value == null ? null : gson.toJson(value);
        PGobject pgo = new PGobject();
        pgo.setType(NAME);
        pgo.setValue(json);
        st.setObject(index, pgo);
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return gson.fromJson(gson.toJson(value), entityClass);
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        Object deepCopy = deepCopy(value);

        if (!(deepCopy instanceof Serializable)) {
            throw new SerializationException(value.getClass() + " must be serializable.");
        }

        return (Serializable) deepCopy;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return deepCopy(cached);
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return deepCopy(original);
    }

    @Override
    public void setParameterValues(Properties parameters) {
        String className = parameters.getProperty(CLASS);
        if (className == null || className.isEmpty()) {
            throw new IllegalArgumentException("Missing @Parameter(name='class')");
        }
        else {
            try {
                entityClass = Class.forName(className);
            }
            catch (ClassNotFoundException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }
}
