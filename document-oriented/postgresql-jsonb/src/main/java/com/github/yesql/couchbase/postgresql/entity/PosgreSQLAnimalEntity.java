package com.github.yesql.couchbase.postgresql.entity;

import com.github.yesql.couchbase.postgresql.dialect.types.JsonbType;
import com.github.yesql.couchbase.postgresql.model.PostgreSQLAnimal;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Martin Janys
 */
@Entity
@Table(name = "ANIMAL")
@TypeDefs({
        @TypeDef(name = JsonbType.NAME, typeClass = JsonbType.class)
})
public class PosgreSQLAnimalEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "animal")
    @Type(type = JsonbType.NAME, parameters = {@Parameter(name = JsonbType.CLASS, value = "com.github.yesql.couchbase.postgresql.model.PostgreSQLAnimal")})
    private PostgreSQLAnimal animal;

    @PostPersist
    @PostLoad
    public void postLoad(){
        animal.setId(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PosgreSQLAnimalEntity that = (PosgreSQLAnimalEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PostgreSQLAnimal getAnimal() {
        return animal;
    }

    public void setAnimal(PostgreSQLAnimal animal) {
        this.animal = animal;
    }
}
