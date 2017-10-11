package ${package_path};

import com.${project}.common.page.Page;
import com.${project}.pojo.${alias}.${class_name};

import java.util.List;

/**
 * Dao Interface:${class_name}
 * author: ${author}
 * date: ${sysDate}
 */
public interface ${class_name}Dao {

    List<${class_name}> list${class_name}(Page page);

    Boolean save(${class_name} ${instance});

    Boolean update(${class_name} ${instance});

    Boolean deleteById(String id);
}