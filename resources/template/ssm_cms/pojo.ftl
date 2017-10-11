package ${package_path};

<#if (hasDateColumn)>
import java.util.Date;
</#if>
import java.io.Serializable;

/**
 * POJO:${class_name}
 *
 * author: ${author}
 * date: ${sysDate}
 */
public class ${class_name} implements Serializable {
	
	<#list table_column as c>
	private ${c.type} ${c.nameJ};       <#if (c.remark?exists && c.remark!="")> // ${c.remark} </#if>
	</#list>

	// Constructor
	public ${class_name}() {
	}
	
	/**
	 * full Constructor
	 */
	public ${class_name}(<#list table_column as c>${c.type} ${c.nameJ}<#if c_has_next>, </#if></#list>) {
		<#list table_column as c>
		this.${c.nameJ} = ${c.nameJ};
		</#list>
	}

	// getter && setter
	// 在setter方法最后加上"return this;"并把返回参数改为${class_name}可以实现连缀设置属性
	<#list table_column as c>
	<#if (c.type=="Date")>@JsonSerialize(using = ShortDateSerializer.class)</#if>
	public ${c.type} get${c.nameJ?cap_first}() {
		return ${c.nameJ};
	}

	public ${class_name} set${c.nameJ?cap_first}(${c.type} ${c.nameJ}) {
		this.${c.nameJ} = ${c.nameJ}; return this;
	}
	</#list>

	@Override
	public String toString() {
		return "${class_name} [" + <#list table_column as c>"<#if (c_index>=1)>, </#if>${c.nameJ}=" + ${c.nameJ} + </#list> "]";
	}
}
