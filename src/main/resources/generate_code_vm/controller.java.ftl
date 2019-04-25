package ${package.Controller};

import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
<#if superControllerClassPackage?? >
import ${superControllerClassPackage};
</#if>

/** 
 * @Description: [${table.comment} 接口API]</p>
 * @author  ${author}
 * @version 1.0
 * Created on ${date}
 */
@Slf4j
@RestController
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if><#if package.SubModuleName??>/${package.SubModuleName}</#if>/${table.viewName}")
@Api(value = ${table.comment}接口, description = ${table.comment}接口, tags = {${table.comment}})
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    /**
     * <p>Description:[${table.comment}服务]</p>
     */
    @Autowired
    private ${table.serviceName} ${table.serviceName?uncap_first};


}

