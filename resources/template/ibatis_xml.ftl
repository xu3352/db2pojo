<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE sqlMap PUBLIC "-//ibatis.apache.org//DTD SQL Map 2.0//EN" "http://ibatis.apache.org/dtd/sql-map-2.dtd">

<sqlMap namespace="${class_name?cap_first}_PLAT">
	<!--table:${table_name}-->
	<typeAlias alias="${class_name?cap_first}" type="${package_path}.pojo.${class_name?cap_first}" />
	<typeAlias alias="${class_name?cap_first}Search" type="${package_path}.search.${class_name?cap_first}Search" />
	<resultMap id="${class_name?cap_first}Result" class="${class_name?cap_first}">
		<#list table_column as c>
		<result property="${c.nameJ}" column="${c.name}" />
		</#list>
	</resultMap>
	
	<sql id="${table_name?upper_case}_columns">
		<#list table_column as c>
		${c.name}<#if c_has_next>,</#if>
		</#list>
        </sql>

	<sql id="${table_name?upper_case}_properties">
		<#list table_column as c>
		#${c.nameJ}#<#if c_has_next>,</#if>
		</#list>
	</sql>

	<insert id="create${class_name?cap_first}" parameterClass="${class_name?cap_first}">
		insert into ${table_name} ( <include refid="${class_name?cap_first}_PLAT.${table_name?upper_case}_columns" /> ) 
		values ( <include refid="${class_name?cap_first}_PLAT.${table_name?upper_case}_properties" /> )
		<selectKey resultClass="int" type="post" keyProperty="id">
			<include refid="public_sql.increment_sql" />
		</selectKey>
	</insert>

	<delete id="delete${class_name?cap_first}ById" parameterClass="int">
		delete from ${table_name}
		where id = #value#
	</delete>

	<delete id="delete${class_name?cap_first}ByIds" parameterClass="String">
		delete from ${table_name}
		where id in ($value$)
	</delete>

	<update id="update${class_name?cap_first}" parameterClass="${class_name?cap_first}">
		update ${table_name} set
		<#list table_column as c><#if (c_index>=1)>
		<isNotNull property="${c.nameJ}">
			<isNotEmpty property="${c.nameJ}">${c.name} = #${c.nameJ}#,</isNotEmpty>
		</isNotNull>
		</#if></#list>
		id = id 
		where id = #id#
    </update>

	<select id="get${class_name?cap_first}ById" resultMap="${class_name?cap_first}_PLAT.${class_name?cap_first}Result"
		parameterClass="int">
		select <include refid="${class_name?cap_first}_PLAT.${table_name?upper_case}_columns" />
		from ${table_name}
		where id = #value#
	</select>

	<select id="get${class_name?cap_first}ListByIds" resultMap="${class_name?cap_first}_PLAT.${class_name?cap_first}Result"
		parameterClass="String">
		select <include refid="${class_name?cap_first}_PLAT.${table_name?upper_case}_columns" />
		from ${table_name} where id in ($value$)
	</select>

	<select id="get${class_name?cap_first}ListByNativeSql" resultMap="${class_name?cap_first}_PLAT.${class_name?cap_first}Result" parameterClass="String">
		$value$
	</select>

	<select id="get${class_name?cap_first}CountByNativeSql" resultClass="int">
		$value$
	</select>

	<select id="getPage${class_name?cap_first}ListByNativeSql" resultMap="${class_name?cap_first}_PLAT.${class_name?cap_first}Result" parameterClass="${class_name?cap_first}Search">
		<include refid="public_sql.page_begin" />
		$listSql$
		<include refid="public_sql.page_end" />
	</select>

	<select id="get${class_name?cap_first}List" resultMap="${class_name?cap_first}_PLAT.${class_name?cap_first}Result" parameterClass="${class_name?cap_first}Search">
		select <include refid="${class_name?cap_first}_PLAT.${table_name?upper_case}_columns" />
		from ${table_name}
	</select>

	<select id="get${class_name?cap_first}Count" resultClass="int">
		select count(*) from ${table_name}
    </select>

	<!-- 分页条件 -->
	<sql id="page_where">
		 where 1=1 
		<#list table_column as c><#if (c_index>=1)>
		<isNotNull property="${c.nameJ}">
			<isNotEmpty property="${c.nameJ}">and ${c.name} = #${c.nameJ}#</isNotEmpty>
		</isNotNull>
		</#if></#list>
	</sql>

	<!-- 使用like用法：columnName like concat('%',#columnName#,'%') -->
	<select id="getPage${class_name?cap_first}List" resultMap="${class_name?cap_first}_PLAT.${class_name?cap_first}Result" parameterClass="${class_name?cap_first}Search">
		<include refid="public_sql.page_begin" />
		select <include refid="${class_name?cap_first}_PLAT.${table_name?upper_case}_columns" />
		from ${table_name} 
		<include refid="${class_name?cap_first}_PLAT.page_where" />
		<include refid="public_sql.page_end" />
	</select>

	<select id="get${class_name?cap_first}ListCount" resultClass="int" parameterClass="${class_name?cap_first}Search">
		select count(*) from ${table_name} 
		<include refid="${class_name?cap_first}_PLAT.page_where" />
	</select>

	<select id="getAll${class_name?cap_first}List" resultMap="${class_name?cap_first}_PLAT.${class_name?cap_first}Result" parameterClass="String">
		select <include refid="${class_name?cap_first}_PLAT.${table_name?upper_case}_columns" />
		from ${table_name}
	</select>
	<!-- 自定义语句添加 -->
	
	
</sqlMap>