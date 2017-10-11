package ${package_path};

import com.${project}.common.constants.Constants;
import com.${project}.common.exception.BizException;
import com.${project}.common.page.Page;

import com.${project}.page.${class_name}Page;
import com.${project}.pojo.${alias}.${class_name};
import com.${project}.service.${class_name}Service;
import com.${project}.pojo.User;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

/**
 * Controller: ${class_name}
 * author: ${author}
 * date: ${sysDate}
 */
@Controller
@RequestMapping("/${instance}")
public class ${class_name}Action {
    private static final Logger logger = Logger.getLogger(${class_name}Action.class);

    private static final String LIST_KEY   = "list${class_name}";
    private static final String LIST_INDEX = "/${instance}/" + LIST_KEY + "/1.htm";

	private ${class_name}Service ${instance}Service;

    public ${class_name}Service get${class_name}Service() {
        return ${instance}Service;
    }

    public void set${class_name}Service(${class_name}Service ${instance}Service) {
        this.${instance}Service = ${instance}Service;
    }

    /** 从 session 里获取 Page; 如果为null 则初始化 */
    private Page getPageBySession(HttpServletRequest request) {
        String key = this.getClass().getName() + LIST_KEY;
        Page page = (Page) request.getSession().getAttribute(key);
        if (page == null) page = new ${class_name}Page();
        return page;
    }

    /** 获取当前查询页的 URL */
    private String getRedirectUrl(HttpServletRequest request) {
        Page page = this.getPageBySession(request);
        String paramUri = page.getParamUri();
        return "redirect:" + LIST_INDEX.replaceAll("1", page.getPageNo() + "") + "?" + paramUri;
    }

    // 列表
    @RequestMapping(value = "/list${class_name}/{pageNo}")
    public String list${class_name}(@PathVariable("pageNo") String pageNo, String id, HttpServletRequest request, ModelMap model) {
        User cmsUser = (User) request.getSession().getAttribute(Constants.CURUSER);
        Page page = this.getPageBySession(request);
        page.setPageNo(Integer.parseInt(pageNo));

        page.addParams("id", id);

        List<${class_name}> list${class_name} = ${instance}Service.list${class_name}(page);

        request.getSession().setAttribute(this.getClass().getName() + LIST_KEY, page);
        model.put("page", page);
        model.put("list${class_name}", list${class_name});
        return "views/${instance}/list${class_name}";
    }

    // 添加/修改页面
	@RequestMapping(value="/modify", method = RequestMethod.GET)
	public String add(String id, HttpServletRequest request) {
        // 如果是修改, 拿 id 查询一个对象
		return "views/${instance}/modify";
	}

    // 添加
	@RequestMapping(value="/add", method = RequestMethod.POST)
	public String add(${class_name} ${instance}, HttpServletRequest request) {
		${instance}Service.save(${instance});
		return this.getRedirectUrl(request);
	}

    // 修改
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public String update(${class_name} ${instance}, HttpServletRequest request) {
		${instance}Service.update(${instance});
        return this.getRedirectUrl(request);
	}

    // 删除
	@RequestMapping(value="/del/{id}", method = RequestMethod.GET)
	public String del(@PathVariable String id, HttpServletRequest request, Model model) {
		${instance}Service.deleteById(id);
        return this.getRedirectUrl(request);
	}
}
