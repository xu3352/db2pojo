<!-- ======================== -->
<!-- 其他配置(注解配置就不用这些了) -->
<!-- ======================== -->

<!-- ${instance}Action -->
<bean id="${instance}Action" class="${project}.web.${alias}.${class_name}Action">
    <property name="${instance}Service" ref="${instance}Service"/>
</bean>

<!-- ${instance}Service -->
<bean id="${instance}Service" class="${project}.service.${alias}.impl.${class_name}ServiceImpl">
    <property name="${instance}Dao" ref="${instance}Dao"/>
</bean>

<!-- ${instance}Dao 注意数据源 -->
<bean id="${instance}Dao" class="${project}.dao.${alias}.impl.${class_name}DaoImpl">
    <property name="template" ref="sqlSessionTemplatePlatform" />
</bean>


<!-- mybatis文件引入 -->
<mapper resource="sqlmap/${alias}/${class_name}Dao.xml"/>
