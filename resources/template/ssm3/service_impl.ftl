package ${package_path};

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.${project}.common.service.MyBatisServiceSupport;
import com.${project}.${alias}.service.spi.I${class_name}Service;

/**
 * Service Implementation:${class_name}
 * @author ${author}
 * @date ${sysDate?date}
 */
@Service
@Transactional
public class ${class_name}ServiceImpl extends MyBatisServiceSupport implements I${class_name}Service {
	
}
