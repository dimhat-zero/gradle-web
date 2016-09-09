#gradle web

学习gradle、groovy、spring-boot等技术

##gradle

##groovy

##spring-boot


##web.xml
<servlet>
    <servlet-name>seckill-dispatcher</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <init-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:spring/spring-*.xml</param-value>
    </init-param>
</servlet>
<servlet-mapping>
    <servlet-name>seckill-dispatcher</servlet-name>
    <url-pattern>/</url-pattern>
</servlet-mapping>