package ${package_path[0..package_path?last_index_of(".")-1]};

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.base.BaseJunit4;
import ${package_path}.pojo.${class_name?cap_first};
import ${package_path}.service.spi.I${class_name?cap_first}Service;

/**
 * Test:${class_name?cap_first}<br/>
 * 支持事物回滚操作(引擎支持：InnoDB)
 * @author Admin
 * @date ${sysDate?date}
 */
public class ${class_name?cap_first}Test extends BaseJunit4 {
	@Resource(name = "${class_name}ServiceImpl")		// 按名称注入
	private I${class_name?cap_first}Service ${class_name}Service;
	
	@Test
	public void testQueryAll() {
		List<${class_name?cap_first}> list = ${class_name}Service.findAll(${class_name?cap_first}.class);
		System.out.println(list);
	}
	
	@Test
	@Transactional
	@Rollback(false)	// false为不回滚,存储引擎InnoDB
	public void testSave() {
		${class_name?cap_first} ${class_name} = new ${class_name?cap_first}();
		${class_name}Service.save(${class_name});
	}
	
	@Test
	@Transactional
	@Rollback(true)		// true为回滚,存储引擎InnoDB
	public void testUpdate() {
		${class_name?cap_first} ${class_name} = new ${class_name?cap_first}();
		${class_name}.setId(1);
		${class_name}Service.update(${class_name});
	}
	
	@Test
	@Transactional
	@Rollback(true)		// true为回滚,存储引擎InnoDB
	public void testDelete() {
		${class_name}Service.delete(${class_name?cap_first}.class, new Object[]{1});
	}
	
	// add other tests
	
}
