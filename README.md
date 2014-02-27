sql-layer-adapter-hibernate
===========================

Hibernate ORM specific files for the FoundationDB SQL Layer 


This project contains the specific files required to use the [Hibernate ORM](http://hibernate.org/orm/) with the [FoundationDB SQL Layer](https://github.com/FoundationDB/sql-layer). The `FoundationDBSQLDialect` informs the Hibernate code of the specific details of the SQL used by the FoundationDB SQL Layer. To include this in your project, add the SQL Layer hibernate jar to your Maven dependencies: 

        <dependency>
          <groupId>com.foundationdb</groupId>
          <artifactId>fdb-sql-hibernate</groupId>
          <version>1.9.4-SNAPSHOT</version>
        </dependency>

Second, update your hibernate.properties file for your project to the following:

    hibernate.dialect org.hibernate.dialect.FoundationDBSQLDialect
    hibernate.connection.driver_class com.foundationdb.sql.jdbc.Driver
    hibernate.connection.url jdbc:fdbsql:test
    hibernate.connection.username test
    hibernate.connection.password test

    hibernate.ejb.naming_strategy org.hibernate.cfg.DialectSensitiveNamingStrategy

We strongly recommend you use the included `DialectSenstitiveNamingStrategy` to avoid issues regarding conflicts between your object names and the SQL keywords used by FoundationDB SQL Layer. If you have already implemented your own naming strategy don't replace it with this one, but be aware you may need to extend it for the same reasons. 
