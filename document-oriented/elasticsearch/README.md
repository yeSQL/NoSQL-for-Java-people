# CouchDB
[https://www.elastic.co/](https://www.elastic.co/)

<dl>
    <dt>Licence</dt>
    <dd><a href="http://www.apache.org/licenses/LICENSE-2.0">Apache License 2.0</a></dd>
    <dt>CAP<dt>
    <dd>closer to PC</dd>
    <dt>Data</dt>
    <dd>Document - JSON</dd>
    <dt>DB API</dt>
    <dd>REST</dd>
    <!--<dt>Queries<dt>
    <dd></dd>-->
    <dt>Replication</dt>
    <dd>Master-slave</dd>
    <dt>Sharding</dt>
    <dd>yes</dd>
    <dt>Source code</dt>
    <dd>Java</dd>
    <dt>Default port</dt>
    <dd>9200</dd>
</dl>

# Install

* download - [https://www.elastic.co/downloads/elasticsearch](https://www.elastic.co/downloads/elasticsearch)
* unzip and run

## Run

```
> elasticsearch/bin/elasticsearch[.bat]
```

### Run as service

```
> elasticsearch/bin/service.bat install

Installing service      :  "elasticsearch-service-x64"
Using JAVA_HOME (64-bit):  "c:\Program Files\Java\jdk1.8.0_45\jre"
The service 'elasticsearch-service-x64' has been installed.
```
```
> elasticsearch/bin/service.bat start elasticsearch-service-x64
The service 'elasticsearch-service-x64' has been started
```

## Config

* elasticsearch/conf/elasticsearch.yml

# GUI

* chrome plugin [Sense](https://chrome.google.com/webstore/detail/sense-beta/lhjgkmllcaadmopgmanpapmpjgmfcfig?utm_source=chrome-app-launcher-info-dialog)
* "admin console" plugin [elasticHQ](http://www.elastichq.org/app/index.php)
* "explain search" plugin [inquisitor](https://github.com/polyfractal/elasticsearch-inquisitor)

## Rest API

* check
    * curl
```
curl -XGET 'localhost:9200/_cluster/health?pretty=true'
```
    * Sense
```
GET /_cluster/health
```
* list indices
```
GET /_all
```
