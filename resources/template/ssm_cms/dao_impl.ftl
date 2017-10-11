package ${package_path};

import org.mybatis.spring.SqlSessionTemplate;

import com.${project}.common.page.Page;
import com.${project}.pojo.${alias}.${class_name};
import com.${project}.dao.${class_name}Dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dao Implementation:${class_name}
 * author: ${author}
 * date: ${sysDate}
 */
public class ${class_name}DaoImpl implements ${class_name}Dao {
	private SqlSessionTemplate template;

    public SqlSessionTemplate getTemplate() {
		return template;
	}
	public void setTemplate(SqlSessionTemplate template) {
		this.template = template;
	}

    @Override
    public List<${class_name}> list${class_name}(Page page) {
        Map<String, Object> param = page.buildQueryParam(false);
        int rowCount = (Integer) template.selectOne(page.getQueryRowCountSql(), param);
        page.setRowCount(rowCount);
        param.putAll( page.buildQueryParam(true) );
        return (List<${class_name}>) template.selectList(page.getQueryPageListSql(), param);
    }

    @Override
    public Boolean save(${class_name} ${instance}) {
        template.insert("${instance}.save", ${instance});
        return ${instance}.getId() > 0;
    }

    @Override
    public Boolean update(${class_name} ${instance}) {
        return template.update("${instance}.update", ${instance}) > 0;
    }

    @Override
    public Boolean deleteById(String id) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", id);
        return template.update("${instance}.deleteById", param) > 0;
    }
}
