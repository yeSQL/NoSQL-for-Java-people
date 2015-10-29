# PostgreSQL - JSONB
[http://www.postgresql.org/](http://www.postgresql.org/docs/9.4/static/datatype-json.html)

<dl>
    <dt>Licence</dt>
    <dd><a href="http://opensource.org/licenses/postgresql">The PostgreSQL Licence</a></dd>
    <dt>CAP<dt>
    <dd>CA</dd>
    <dt>Data</dt>
    <dd>Document - JSONB</dd>
    <dt>DB API</dt>
    <dd>SQL driver</dd>
    <dt>Queries<dt>
    <dd>SQL</dd>
    <dt>Atomicity</dt>
    <dd>ACID</dd>
    <dt>Source code</dt>
    <dd>C</dd>
    <dt>Default port</dt>
    <dd>5432</dd>
</dl>

# About

* it is only store for json, for un-structured or semi-structured data in one column
* there are no other no-sql benefits

# Indexes

```
CREATE INDEX idx_genus_name ON animal ((animal->>'genusName'));
CREATE INDEX idx_species_name ON animal ((animal->>'speciesName'));
CREATE INDEX idx_weight ON animal ((animal->>'weight'));
CREATE INDEX idx_length ON animal ((animal->>'length'));
CREATE INDEX idx_areas ON animal ((animal->>'areas'));
```
