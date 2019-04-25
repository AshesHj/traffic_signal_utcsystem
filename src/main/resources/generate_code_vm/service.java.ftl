package ${package.Service};

import ${package.Entity}.${entity};
import org.springframework.stereotype.Repository;

/**
* @Description: [${table.comment}service]</p>
* @author ${author}
* @version 1.0
* Created on ${date}
*/
@Repository
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {
}
