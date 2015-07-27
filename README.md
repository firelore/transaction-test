Test project to illustrate an issue with transactions on TomEE. 

Support thread at: http://tomee-openejb.979440.n4.nabble.com/Transaction-issues-using-TomEE-JPA-and-Spring-td4675588.html

The DataSource definition in `$CATALINA_HOME/conf/tomee.xml` is as follows:

```xml
<Resource id="Transaction Test" type="DataSource" jndi="jdbc/transaction-ds">
	DataSourceCreator=dbcp
	JtaManaged=true

	DriverClassName=com.microsoft.sqlserver.jdbc.SQLServerXADataSource
	UserName=<username>
	Password=<password>
	Url=<MS SQL 2005 connection string>
	ValidationQuery=SELECT 1
	DefaultTransactionIsolation=SERIALIZABLE
</Resource>
```