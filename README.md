## 社区

## 资料
[spring 文档] https://spring.io/guides
[spring web ] https://spring.io/guides/gs/serving-web-content/
[Github OAuth] https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/
[bootstrap] https://v3.bootcss.com/components/#navbar
[spring boot] https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/
[thymeleafspring] https://www.thymeleaf.org/doc/tutorials/2.1/thymeleafspring.html
## 工具 
[模拟访问工具 okhttp]https://square.github.io/okhttp/
git

## 脚本
```sql
CREATE TABLE user
( 
	id INT  AUTO_INCREMENT PRIMARY KEY,
	NAME VARCHAR(50),
	ACCOUT_ID VARCHAR(100),
	TOKEN VARCHAR(36),
	GMT_CREATE BIGINT,
	GMT_MODIFIED BIGINT
	);
```

mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate

