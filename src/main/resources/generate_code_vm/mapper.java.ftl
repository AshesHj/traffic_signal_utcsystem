package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
import org.springframework.stereotype.Repository;

/**
* @Description: [${table.comment}持久层实现]</p>
* @author ${author}
* @version 1.0
* Created on ${date}
*/
@Repository
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {
}