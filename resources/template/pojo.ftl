package ${package_path};

<#list table_column as c><#if (c.type=="Date")>import java.util.Date;</#if></#list>
import com.plat.common.pojo.BaseEntity;

/**
 * 实体:${class_name?cap_first}
 * 
 * @author Admin
 * @date ${sysDate?date}
 */
public class ${class_name?cap_first} extends BaseEntity {
	private static final long serialVersionUID = 1L;
	<#list table_column as c>
	<#if (c.name!="id")>private ${c.type}	${c.nameJ};		<#if (c.remark?exists && c.remark!="")> /* ${c.remark} */ </#if></#if>
	</#list>
	
	// Constructor
	public ${class_name?cap_first}() {
	}
	
	/**
	 * full Constructor
	 */
	public ${class_name?cap_first}(<#list table_column as c>${c.type} ${c.nameJ}<#if c_has_next>, </#if></#list>) {
		<#list table_column as c>
		<#if (c.name!="id")>this.${c.nameJ} = ${c.nameJ};<#else>setId(id);</#if>
		</#list>
	}
	
	// getter && setter
	// 在setter方法最后加上"return this;"并把返回参数改为${class_name?cap_first}可以实现连缀设置属性
	<#list table_column as c><#if (c.name!="id")>
	public ${c.type} get${c.nameJ?cap_first}() {
		return ${c.nameJ};
	}

	public void set${c.nameJ?cap_first}(${c.type} ${c.nameJ}) {
		this.${c.nameJ} = ${c.nameJ};
	}
	
	</#if></#list>
	// ToString()
	@Override
	public String toString() {
		return "${class_name?cap_first} [" + <#list table_column as c>"<#if (c_index>=1)>, </#if>${c.nameJ}=" + <#if (c.name!="id")>${c.nameJ}<#else>getId()</#if> + </#list> "]";
	}
}