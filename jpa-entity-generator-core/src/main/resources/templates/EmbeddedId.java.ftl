package ${root.pkg};

<#list root.importStrings as import>
import ${import};
</#list>

@Generated("dev.aulait.jeg:jpa-entity-generator")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ${root.name} implements java.io.Serializable {
<#list root.fields as field>

  @Column(name = "${field.columnName}")
  private ${field.type} ${field.name};
</#list>
<#list root.embeddedFields as ef>

  @Embedded
  @AttributeOverrides({
    <#list ef.attributeOverrides as ao>
    @AttributeOverride(name = "${ao.name}", column = @Column(name = "${ao.column}"))<#if !ao?is_last>,</#if>
    </#list>
  })
  private ${ef.type} ${ef.fieldName};
</#list>
}
