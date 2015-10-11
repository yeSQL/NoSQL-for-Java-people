# CauchDB
[http://couchdb.apache.org](http://couchdb.apache.org)

<dl>
    <dt>Licence</dt>
    <dd><a href="http://www.apache.org/licenses/LICENSE-2.0">Apache License 2.0</a></dd>
    <dt>CAP<dt>
    <dd>AP</dd>
    <dt>Data</dt>
    <dd>Document - JSON</dd>
    <dt>DB API</dt>
    <dd>REST</dd>
    <dt>Queries<dt>
    <dd>Map (predefined views) + Reduce</dd>
    <dt>Replication</dt>
    <dd>Master-Master</dd>
    <dt>Atomicity</dt>
    <dd>Multi Version Concurrency Control (MVCC)</dd>
    <dt>Source code</dt>
    <dd>Erlang</dd>
    <dt>Default port</dt>
    <dd>5984</dd>
    <dt>
</dl>

## Install

* download - [http://couchdb.apache.org/#download](http://couchdb.apache.org/#download)
* test service - [http://127.0.0.1:5984/](http://127.0.0.1:5984/)

## Creating database

* list DBs - [http://127.0.0.1:5984/_all_dbs](http://127.0.0.1:5984/_all_dbs)
* management (called *Futon*) - [http://127.0.0.1:5984/_utils/](http://127.0.0.1:5984/_utils/)
  * Create database ...
    * name: animals
  * View created database
  * Security (setup admin access)
    * go to /etc/couchdb/local.ini (on Windows in Program Files install dir)
    * add to section \[admins\]: admin = admin
    * restart

### Others commands

* restart:
    * curl -X POST http://localhost:5984/_restart -H"Content-Type: application/json"
    * (auth) curl -X POST http://admin:admin@localhost:5984/_restart -H"Content-Type: application/json"

## Java interfaces

* **Ektorp**
* JRelax
* jcouchdb
* CouchDB4J
* LightCouch

## Ektorp

### Client setup

* \[couchdb\]@/src/main/resources/couchdb.properties
* \[couchdb\]@com.github.yesql.CouchDbConfig

### Dao implementation

* \[couchdb\]@com.github.yesql.dao.AnimalCouchDbDao
