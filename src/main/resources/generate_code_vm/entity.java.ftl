package ${package.Entity};


<#list table.importPackages as pkg>
import ${pkg};
</#list>
import lombok.Data;

import javax.persistence.*;

/**
 * @Description: [${table.comment}实体类]</p>
 * @author ${author}
 * @version 1.0
 * Created on ${date}
 */
@Data
<#if tableAnnotation??&&tableAnnotation >
@Table(name = "${table.name}")
</#if>
<#if superEntityClass?? >
public class ${entity} extends ${superEntityClass}<#if activeRecord ><${entity}></#if> {
<#elseif activeRecord>
public class ${entity} extends Model<${entity}> {
<#else>
public class ${entity} {
</#if>

<#list table.fields as field>
	/**
	 * <#if field.comment??&& field.comment!= "">${field.comment}</#if>
	 **/
	<#if field.keyFlag >
		<#assign  keyPropertyName=field.propertyName/>
	@Id
	</#if>
	<#if field.convert >
	@Column(name = "${field.name}")
	</#if>
	private ${field.propertyType} ${field.propertyName};
	<#if cfg.rangeDateFileds?seq_contains(field.propertyName)>
    /**
    * <p>${field.comment}范围查询-开始</p>
    */
	private String ${field.propertyName}Start;

    /**
    * <p>${field.comment}范围查询-结束</p>
    */
	private String ${field.propertyName}End;
	</#if>

</#list>

}
