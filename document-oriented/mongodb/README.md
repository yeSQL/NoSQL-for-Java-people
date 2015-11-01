# MongoDb
[https://www.mongodb.org/](https://www.mongodb.org/)

<dl>
    <dt>Licence</dt>
    <dd><a href="http://www.gnu.org/licenses/agpl-3.0.html">GNU AGPL v3.0</a></dd>
    <dt>Driver licence</dt>
    <dd><a href="http://www.apache.org/licenses/LICENSE-2.0">Apache License v2.0</a></dd>
    <dt>Licencing</dt>
    <dd>The AGPL v3.0 as our open source license is to require that enhancements to MongoDB be released to the community. But driver is Apache 2.0. There is no limition of usage (except modification of MongoDB, after changes must be published). <a href="http://blog.mongodb.org/post/103832439/the-agpl">http://blog.mongodb.org/post/103832439/the-agpl</a></dd>
    <dt>CAP<dt>
    <dd>CP</dd>
    <dt>Data</dt>
    <dd>BSON</dd>
    <dt>DB API</dt>
    <dd>Driver, CLI</dd>
    <dt>Queries<dt>
    <dd>JSON, mapreduce</dd>
    <dt>Replication</dt>
    <dd>Master-Slave</dd>
    <dt>Sharding</dt>
    <dd>yes</dd>
    <dt>Source code</dt>
    <dd>C++</dd>
    <dt>Default port</dt>
    <dd>27017 <a href="https://docs.mongodb.org/manual/reference/default-mongodb-port/">default ports</a></dd>
</dl>

* Mongo supports DBRef's (similar to foreign-key)
* Mongo has full-text search support

# Install

* download & install - [https://www.mongodb.org/downloads](https://www.mongodb.org/downloads#production)

## Windows service
* Create
```
mongod --dbpath "d:\mongoDB\data --logpath "d:\mongoDB\logs.txt --install --serviceName "MongoDB"
net start MongoDB
```
* Remove
```
mongod --remove --serviceName "MongoDB"
```

## GUI

* cross platform GUI - [http://robomongo.org](http://robomongo.org)
