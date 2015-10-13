# Document oriented databases

[Wikipedia](https://en.wikipedia.org/wiki/Document-oriented_database)

* document oriented databases are superset of key-value dbs

**When use document databases**

* you need to store whole data structure (e.g. from historical reason), no links with only current stored data
    * for example in system with invoices and address on invoice, address could by changed during time but each entry has own address
* you need to store complex structure (from integrated system) where data structures is not in your hands (may you want to search by some data field)
* you need store data whit unknown structures
    * for example product properties which is different for each product type
    * if you have columns ID | KEY | VALUE in relation db world
* you need better performance for data access
    * for example CouchDB uses key-value strengths and precomputation of views
* you need to raise resiliency by replication

**When DON'T use document databases**

* you don't have reason, don't solve problem which you haven't yet
* you have lots of ad-hoc queries on database

**Finally** you should benefit from pos and cons of both databases (no-sql and sql)

## [Couchbase](https://github.com/yeSQL/NoSQL-for-Java-people/tree/master/document-oriented/couchbase)

## [CouchDB](https://github.com/yeSQL/NoSQL-for-Java-people/tree/master/document-oriented/couchdb)

## Elasticsearch
Coming soon

## MongoDB
Coming soon

## Postgres (JSON Store)
Coming soon
