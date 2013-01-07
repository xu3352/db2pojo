package ${package_path}.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plat.common.service.BaseService;
import ${package_path}.service.spi.I${class_name?cap_first}Service;

/**
 * 业务接口实现:${class_name?cap_first}
 * 
 * @author Admin
 * @date ${sysDate?date}
 */
@Service
@Transactional
public class ${class_name?cap_first}ServiceImpl extends BaseService implements I${class_name?cap_first}Service {
	
}
