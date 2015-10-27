package com.github.yesql.couchbase.postgresql.entity;

import com.github.yesql.couchbase.postgresql.dialect.types.JsonbType;
import com.github.yesql.couchbase.postgresql.model.PostgreSQLAnimal;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Martin Janys
 */
@Entity
@Table(name = "ANIMAL")
@TypeDefs({
        @TypeDef(name = JsonbType.NAME, typeClass = JsonbType.class)
})
public class PosgreSQLAnimalEntity {

    @Id
    private Long id;

    @Column(name = "animal")
    @Type(type = JsonbType.NAME, parameters = {@Parameter(name = JsonbType.CLASS, value = "com.github.yesql.couchbase.postgresql.model.PostgreSQLAnimal")})
    private PostgreSQLAnimal animal;

    public PosgreSQLAnimalEntity() {
    }
}
