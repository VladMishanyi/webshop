# Test task

The webshop price converter

### Before to start
Instal on your PC: `JDK 1.8` `Maven` `Docker`  
##

#### How to run
Run docker container form folder: */docker*
```sql
docker-compose up -d;
```
Build application into: *jar*
```sql
mvn clean install; 
```
After build check `target` folder there should be jar `webshop-1.0-SNAPSHOT-jar-with-dependencies.jar`
```sql
webshop-1.0-SNAPSHOT-jar-with-dependencies.jar 
```
Try to run application with different args
```sql
java -jar webshop-1.0-SNAPSHOT-jar-with-dependencies.jar 15 23.4 book --input-currency=NOK --output-currency=EUR --vat=DE 
```
```sql
java -jar webshop-1.0-SNAPSHOT-jar-with-dependencies.jar 15 23.4 book --input-currency=NOK --output-currency=EUR 
```
```sql
java -jar webshop-1.0-SNAPSHOT-jar-with-dependencies.jar 15 23.4 book --input-currency=NOK 
```
```sql
java -jar webshop-1.0-SNAPSHOT-jar-with-dependencies.jar 15 23.4 book 
```

