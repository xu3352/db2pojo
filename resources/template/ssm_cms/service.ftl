package ${package_path};

import ${project}.common.page.Page;
import ${project}.pojo.${alias}.${class_name};

import java.util.List;

/**
 * Service Interface:${class_name}
 * author: ${author}
 * date: ${sysDate}
 */
public interface ${class_name}Service {

    List<${class_name}> list${class_name}(Page page);

    Boolean save(${class_name} ${instance});

    Boolean update(${class_name} ${instance});

    Boolean deleteById(String id);

    ${class_name} findById(String id);
}