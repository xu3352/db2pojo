package ${package_path};

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.plat.common.action.SystemAction;
import ${package_path}.pojo.${class_name?cap_first};
import ${package_path}.search.${class_name?cap_first}Search;
import ${package_path}.service.spi.I${class_name?cap_first}Service;

/**
 * 业务控制层：${class_name?cap_first}Action
 * 
 * @author Admin
 * @date ${sysDate?date}
 */
public class ${class_name?cap_first}Action extends SystemAction {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private I${class_name?cap_first}Service ${class_name}Service;
	private ${class_name?cap_first} ${class_name};
	private List<${class_name?cap_first}> ${class_name}List;
	private ${class_name?cap_first}Search ${class_name}Search;
	
	private String jumpUrl = "${class_name}List";
	
	/**
	 * 列表
	 */
	public String list() {
		try {
			${class_name}List = ${class_name}Service.findAll(${class_name?cap_first}.class);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 条件分页
	 */
	public String page() {
		try {
			pageResult = ${class_name}Service.findPageResult(${class_name?cap_first}.class, get${class_name?cap_first}Search());
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 添加
	 */
	public String add() {
		try {
			${class_name}Service.save(${class_name});
			this.newResult("添加成功", getJumpUrl(jumpUrl));
		} catch (Exception e) {
			e.printStackTrace();
			this.newResult("添加失败!");
		}
		return JSONRESULT;
	}
	
	/**
	 * 修改
	 */
	public String edit() {
		try {
			${class_name}Service.update(${class_name});
			this.newResult("修改成功", getJumpUrl(jumpUrl));
		} catch (Exception e) {
			e.printStackTrace();
			this.newResult("修改失败!");
		}
		return JSONRESULT;
	}
	
	/**
	 * 删除
	 */
	public String del() {
		try {
			${class_name}Service.delete(${class_name?cap_first}.class, ${class_name}.getId());
			this.newResult("删除成功", getJumpUrl(jumpUrl));
		} catch (Exception e) {
			e.printStackTrace();
			this.newResult("删除失败!");
		}
		return JSONRESULT;
	}
	
	// getter && setter
	public ${class_name?cap_first} get${class_name?cap_first}() {
		return ${class_name};
	}

	public void set${class_name?cap_first}(${class_name?cap_first} ${class_name}) {
		this.${class_name} = ${class_name};
	}
	
	public List<${class_name?cap_first}> get${class_name?cap_first}List() {
		return ${class_name}List;
	}

	public void set${class_name?cap_first}List(List<${class_name?cap_first}> ${class_name}List) {
		this.${class_name}List = ${class_name}List;
	}
	
	public ${class_name?cap_first}Search get${class_name?cap_first}Search() {
		return ${class_name}Search;
	}
	
	public void set${class_name?cap_first}Search(${class_name?cap_first}Search ${class_name}Search) {
		final int pageSize = 15;
		if (${class_name}Search == null) {
			${class_name}Search = new ${class_name?cap_first}Search();
			${class_name}Search.setPageSize(pageSize);
		}
		${class_name}Search.setCurrentPage(pageResult.getCurrentPage());
		this.${class_name}Search = ${class_name}Search;
	}
}
