<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.${model_package}.bean.${class_name}">
	<resultMap type="${class_name}" id="${class_name?uncap_first}ResultMap">
		<#list table_column as c>
		<result property="${c.nameJ}" column="${c.name}" />
		</#list>
	</resultMap>
	
	<sql id="table_columns">
		<#list table_column as c>
		${c.name}<#if c_has_next>,</#if>
		</#list>
    </sql>
	<sql id="entity_properties">
		<#list table_column as c>
		${r"#"}{${c.nameJ}}<#if c_has_next>,</#if>
		</#list>
	</sql>
	
	<!-- 适用于主键自增类型 -->
	<insert id="save" parameterType="${class_name}" useGeneratedKeys="true" keyProperty="id">
		insert into ${table_name}( <include refid="table_columns" /> ) 
		values ( <include refid="entity_properties" /> )
	</insert>
	
	<delete id="deleteByIds">
		delete from ${table_name}
		where id in
		<foreach item="item" collection="array" open="(" separator="," close=")">
			${r"#"}{item}
		</foreach>
	</delete>
	
	<update id="update" parameterType="${class_name}">
		update ${table_name} 
		<trim prefix="set" suffixOverrides=",">
		<#list table_column as c><#if (c_index>=1)>
			<if test="${c.nameJ} != null and ${c.nameJ} != ''">${c.name} = ${r"#"}{${c.nameJ}},</if>
		</#if></#list>
		</trim>
		<where>id = ${r"#"}{id}</where>
	</update>
	
	<select id="findById" resultMap="${class_name?uncap_first}ResultMap" parameterType="String" >
		select <include refid="table_columns" />
		from ${table_name}
		where id = ${r"#"}{id}
	</select>
	
	<select id="queryAll" resultMap="${class_name?uncap_first}ResultMap">
		select <include refid="table_columns" /> 
		from ${table_name}
	</select>
	
	<!-- 使用like用法：columnName like concat('%',#columnName#,'%') -->
	<sql id="page_where">
		<trim prefix="where" prefixOverrides="and | or ">
			<#list table_column as c><#if (c_index>=1)>
			<if test="${c.nameJ} != null and ${c.nameJ} != ''">and ${c.name} = ${r"#"}{${c.nameJ}}</if>
			</#if></#list>
		</trim>
	</sql>
	
	<select id="page" resultMap="${class_name?uncap_first}ResultMap" parameterType="${class_name}Search">
		select <include refid="table_columns" />
		from ${table_name}
		<include refid="page_where" />
		limit ${r"#"}{firstIndex},${r"#"}{pageSize}
	</select>
	
	<select id="pageCount" resultType="int" parameterType="${class_name}Search">
		select count(id) from ${table_name}
		<include refid="page_where" />
	</select>
	
	<!-- 其他自定义SQL -->
	
</mapper>