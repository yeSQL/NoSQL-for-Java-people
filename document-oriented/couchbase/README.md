# Couchbase Server
[http://www.couchbase.com/](http://www.couchbase.com/)

<dl>
    <dt>Licence</dt>
    <dd>Apa</dd>
    <dt>Licence CE</dt>
    <dd><a href="http://www.couchbase.com/community">Couchbase, Inc. Community Edition License</a></dd>
    <dt>Licence EE</dt>
    <dd><a href="http://www.couchbase.com/agreement/subscription">Couchbase, Inc. Enterprise Subscription License Agreement</a></dd>
    <dt>CAP<dt>
    <dd>CP or AP with multiple clusters</dd>
    <dt>Data</dt>
    <dd>Document - JSON</dd>
    <dt>DB API</dt>
    <dd>HTTP, REST</dd>
    <dt>Queries<dt>
    <dd>Map-reduce or N1QL query</dd>
    <dt>Replication</dt>
    <dd>Master-master (shared nothing)</dd>
    <dt>Sharding</dt>
    <dd>True</dd>
    <dt>Source code</dt>
    <dd>C and Erlang</dd>
    <dt>Default port</dt>
    <dd>
    8091 - Console,
    8083 - REST API
    </dd>
</dl>

* Couchbase supports join operation
* Query by views or with query language [N1QL](http://query.pub.couchbase.com/tutorial)
* It keeps frequently accessed documents, metadata, and indexes in RAM
* Couchbase supports joins and references. Reference has pattern ```person::id```

## Install

* download - [http://www.couchbase.com/nosql-databases/downloads](http://www.couchbase.com/nosql-databases/downloads)
* test service - [http://localhost:8091/](http://localhost:8091/)

## Creating database

* GUI management - [http://localhost:8091/](http://localhost:8091/)

## Indexes

* for N1QL queries primary index is required
    * run CLI tool cbq witch is located in couchbase/server/bin
    * run script ```CREATE PRIMARY INDEX idx_id ON animals(`id`) USING GSI;```

## Java interfaces

* create views from your code using annotation [couchbase-auto-views](https://github.com/biins/couchbase-auto-views)
* [**Couchbase Java SDK** 2.2+](http://docs.couchbase.com/developer/java-2.1/overview.html)
    * see Compatibility section for features overview
    * dao using synch. blocking implementation, asyn. nonblocking implementation and N1QL dao is implemented
* [Spring Data Couchbase](http://projects.spring.io/spring-data-couchbase/)
    * useful for blocking daos and basic POJO access

## View map example

```
function (doc, meta) {
  if(meta.type == "json") {
    if(doc.doc_type && doc.doc_type == "user") {
      if(doc.join_date) {
       emit(dateToArray(doc.join_date)); // value is optinal
      }
    }
  }
}
```