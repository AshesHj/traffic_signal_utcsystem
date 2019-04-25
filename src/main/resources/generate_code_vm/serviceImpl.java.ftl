package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import org.springframework.stereotype.Service;

/**
* @Description: [${table.comment}service实现]</p>
* @author ${author}
* @version 1.0
* Created on ${date}
*/
@Service
public class ${table.serviceImplName} extends ${superServiceImplClass}<${entity}, ${table.mapperName}> implements ${table.serviceName} {
}
