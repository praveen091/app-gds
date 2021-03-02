# app-gds# Generic Data Services

 

Generic data service is a  generic API to support all the database related  operation without doing any coding for it.

This can be used as Interface in your application or can be use as centralized service as well which is totally depends on user choice.

Supported for microservices architecture as well as monolithic architecture.

 

 

It's recommended this to be run as a private microservices because it's niche layer service which  does not handle any type of authentication. That should be done by the application interfacing with this service.

 

## Feature Supported

 

Capable to manage heterogenous database -currently supported for ORACLE,MYSQL and POSTGRE database.

 

Flexibility to configure  multiple data source at runtime without downtime.

 

It maintain connection pool for every data Source created by user which is completely configurable- can create ,update and delete any  database connection at any point of time without downtime.

 

Supported for file based configuration as well config server based configuration- User choice.

 

Capable to do disaster management related to database connection, which means when database goes offline we don’t need to restart nor in the case when connection property has changed-It has capability to auto refresh/reconnect when connection available.

 

SAAS ,Multi-tenant supported.

 

All SQL query including joins procedure, function etc.. are supported.

 

Supported for plan SQL query as well as Named SQL parameter query.

 

Bulk processing supported.

 

Pagination and Lazy -loading supported.

 

It has it’s own sequence generator engine which is totally independent from database and capable to generate unique id even when running in distributed environment.

 

Has less memory consuming QueryMap which is very useful when transaction is more and when application is using named query.

 

 

## Usage

 

The easiest way to use this is to simply add generic-data-core as a dependency and call GenericDataServices interface in your application service Layer.

 

```

@Autowired

private GenericDataService genericDataservice;

```

NOTE:- Make sure to disable default spring configuration.

 

```

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,SpringAutoConfiguration.class})

```

 

Alternatively, you can use this as a service by running generic-data-services.jar along with applicable VM arguments.

 

```

java -jar generic-data-services.jar

```

 

 

## VM Variables

 

* `generic.data.monitor.location` default: null (required only for file based configuration which is folder location of file)

* `generic.data.monitor.filename` default: null (required only for file based configuration which is name of file)

* `sequence.generator.nodeId` default: null(when null nodeId will auto generated)

 

## DB Configuration

As it's  supporting multiple database so configuration must be on index starting with prefix db.config

 

Example format:-

 

```

for datasource1

 

db.config[0].url=jdbc:postgresql://localhost:5432/db_dev

db.config[0].userName=postgres

db.config[0].passWord=123

db.config[0].driverClassName=org.postgresql.Driver

db.config[0].dialect=or.hibernate.dialect.PostgreDialect

db.config[0].datasourceName=datasource1

 

for datasource2

 

db.config[1].url=jdbc:postgresql://localhost:5432/db_test

db.config[1].userName=postgres

db.config[1].passWord=123

db.config[1].driverClassName=org.postgresql.Driver

db.config[1].dialect=or.hibernate.dialect.PostgreDialect

db.config[1].datasourceName=datasource2

 

```

Note:- Please refer API/interface doc to get details about mandatory and optional field.

 

## Support

 

```

Please drop email at <praveen85kmr@gmail.com> for any support.

```
