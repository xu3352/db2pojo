package ${package_path};

import ${project}.common.page.Page;
import ${project}.pojo.${alias}.${class_name};
import ${project}.service.${alias}.${class_name}Service;
import ${project}.dao.${alias}.${class_name}Dao;

import java.util.List;

/**
 * Service Implementation:${class_name}
 * author: ${author}
 * date: ${sysDate}
 */
public class ${class_name}ServiceImpl implements ${class_name}Service {
	private ${class_name}Dao ${instance}Dao;

	public ${class_name}Dao get${class_name}Dao() {
		return ${instance}Dao;
	}

	public void set${class_name}Dao(${class_name}Dao ${instance}Dao) {
		this.${instance}Dao = ${instance}Dao;
	}


    @Override
    public List<${class_name}> list${class_name}(Page page) {
        return ${instance}Dao.list${class_name}(page);
    }

    @Override
    public Boolean save(${class_name} ${instance}) {
        return ${instance}Dao.save(${instance});
    }

    @Override
    public Boolean update(${class_name} ${instance}) {
        return ${instance}Dao.update(${instance});
    }

    @Override
    public Boolean deleteById(String id) {
        return ${instance}Dao.deleteById(id);
    }
}
