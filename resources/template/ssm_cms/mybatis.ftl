<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${instance}">

    <sql id="${table_name}_columns">
		<#list table_column as c>
		${c.name}<#if c_has_next>,</#if>
		</#list>
    </sql>

    <!-- 使用like用法：columnName like concat('%',#columnName#,'%') -->
    <sql id="page_where">
        <trim prefix="where" prefixOverrides="and | or ">
        <#list table_column as c><#if (c_index>=1)>
            <if test="${c.nameJ} != null">and ${c.name} = ${r"#"}{${c.nameJ}}</if>
        </#if></#list>
        </trim>
    </sql>

    <select id="query${class_name}PageCount" resultType="int" parameterType="map">
        select count(id) from ${dbName}.${table_name}
        <include refid="page_where" />
    </select>

    <select id="query${class_name}ByPage" parameterType="map" resultType="${project}.pojo.${alias}.${class_name}">
        select <include refid="${table_name}_columns" />
        from ${dbName}.${table_name}
        <include refid="page_where" />
        limit ${r"#"}{stat},${r"#"}{size}
    </select>

	<!-- 适用于主键自增类型 -->
	<insert id="save" parameterType="${project}.pojo.${alias}.${class_name}" useGeneratedKeys="true" keyProperty="id">
		insert into ${dbName}.${table_name} (
            <#list table_column as c>
                <#if (c.name!="id")>${c.name}<#if c_has_next>,</#if></#if>
            </#list>
        )
		values (
            <#list table_column as c>
                <#if (c.name!="id")>${r"#"}{${c.nameJ}}<#if c_has_next>,</#if></#if>
            </#list>
        )
	</insert>
	
	<delete id="deleteById" parameterType="map">
		delete from ${dbName}.${table_name}
		where id = ${r"#"}{id}
	</delete>
	
	<update id="update" parameterType="${project}.pojo.${alias}.${class_name}">
		update ${dbName}.${table_name}
		<trim prefix="set" suffixOverrides=",">
		<#list table_column as c><#if (c_index>=1)>
			<if test="${c.nameJ} != null">${c.name} = ${r"#"}{${c.nameJ}},</if>
		</#if></#list>
		</trim>
		<where>id = ${r"#"}{id}</where>
	</update>
	
	<select id="findById" resultType="${project}.pojo.${alias}.${class_name}" parameterType="String" >
		select <include refid="${table_name}_columns" />
		from ${dbName}.${table_name}
		where id = ${r"#"}{id}
	</select>
	
	<!-- 其他自定义SQL -->
	
</mapper>