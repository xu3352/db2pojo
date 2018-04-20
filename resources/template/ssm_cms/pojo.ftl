package ${package_path};

<#if (hasDateColumn)>
import java.util.Date;
import ${project}.common.utils.FormatDate;
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
	<#if (c.type=="Date")>// @JsonSerialize(using = ShortDateSerializer.class)</#if>
	public ${c.type} get${c.nameJ?cap_first}() {
		return ${c.nameJ};
	}

	public ${class_name} set${c.nameJ?cap_first}(${c.type} ${c.nameJ}) {
		this.${c.nameJ} = ${c.nameJ}; return this;
	}
	</#list>

    // 其他辅助方法
    <#list table_column as c>
        <#if (c.remarkDict.size>1 && c.type == "int")>
    public String get${c.nameJ?cap_first}Txt() {
        switch (${c.nameJ}) {
            <#list c.remarkDict.list as d>
            case ${d.k}:
                return "${d.v}";
            </#list>
        }
        return "";
    }
        </#if>
        <#if (c.type == "Date")>
    public String get${c.nameJ?cap_first}Txt() {
        return FormatDate.formatDate(${c.nameJ}, "yyyy-MM-dd HH:mm:ss");
    }
        </#if>
    </#list>

	@Override
	public String toString() {
		return "${class_name} [" + <#list table_column as c>"<#if (c_index>=1)>, </#if>${c.nameJ}=" + ${c.nameJ} + </#list> "]";
	}
}
