package ${package_path};

import com.${project}.common.page.Page;

/**
* POJO:${class_name}Page
* author: ${author}
* date: ${sysDate}
*/
public class ${class_name}Page extends Page {

    public ${class_name}Page(){
        this.setPageSize(10);
        this.setQueryRowCountSql("${instance}.query${class_name}PageCount");
        this.setQueryPageListSql("${instance}.query${class_name}ByPage");

        this.setPageActionUrl("/${instance}/list${class_name}");
    }
}
