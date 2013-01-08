package ${package_path};

<#list table_column as c><#if (c.type=="Date")>import java.util.Date;</#if></#list>
import com.plat.common.search.BaseSearch;

/**
 * 搜索实体:${table_name?cap_first}
 * 
 * @author Admin
 * @date ${sysDate?date}
 */
public class ${class_name?cap_first}Search extends BaseSearch {
	
	<#list table_column as c>
	<#if (c.name!="id")>private ${c.type}	${c.nameJ};		<#if (c.remark?exists && c.remark!="")>/* ${c.remark} */</#if></#if>
	</#list>
	
	// getter && setter
	<#list table_column as c><#if (c.name!="id")>
	public ${c.type} get${c.nameJ?cap_first}() {
		return ${c.nameJ};
	}

	public void set${c.nameJ?cap_first}(${c.type} ${c.nameJ}) {
		this.${c.nameJ} = ${c.nameJ};
	}
	
	</#if></#list>
}