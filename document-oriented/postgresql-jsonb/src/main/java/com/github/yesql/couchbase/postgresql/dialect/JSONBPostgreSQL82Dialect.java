package com.github.yesql.couchbase.postgresql.dialect;

import com.github.yesql.couchbase.postgresql.dialect.types.JsonbType;
import org.hibernate.dialect.PostgreSQL82Dialect;

import java.sql.Types;

/**
 * @author Martin Janys
 */
public class JSONBPostgreSQL82Dialect extends PostgreSQL82Dialect {

    public JSONBPostgreSQL82Dialect() {
        super();
        registerColumnType(Types.JAVA_OBJECT, JsonbType.NAME);
    }
}

