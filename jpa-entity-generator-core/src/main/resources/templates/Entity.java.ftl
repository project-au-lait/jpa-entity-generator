<#import "lib/common.ftl" as common>
<#-- -->
<#if root.pkg?has_content>package ${root.pkg};</#if>

<#list root.imports as import>
import ${import};
</#list>

@Generated("dev.aulait.jeg:jpa-entity-generator")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(name = "${root.tableName}")
public class ${root.name}<#if root.baseClass?has_content> extends ${root.baseClass}</#if>
    implements java.io.Serializable {
<#if root.embeddedId?has_content>

  @EqualsAndHashCode.Include @EmbeddedId private ${root.embeddedId.name} id;
</#if>
<#-- -->
<#list root.fields as field>

  <#if field.id>
  @EqualsAndHashCode.Include
  @Id
  </#if>
  @Column(name = "${field.columnName}")
  <#list field.annotations as annotation>
  @${annotation.type}(<#list annotation.attributes?keys as key>${key} = ${annotation.attributes[key]}</#list>)
  </#list>
  private ${field.type} ${field.name};
</#list>
<#-- -->
<#list root.oneToOnes as oneToOne>

  <@common.compress_single_line>
  @OneToOne(fetch = FetchType.LAZY
    <#if oneToOne.mappedBy?has_content>, mappedBy = ${oneToOne.mappedBy}</#if>
    <#list oneToOne.cascades as cascade>
      <#if cascade?is_first>, cascade = {</#if>
      CascadeType.${cascade}
      <#if cascade?is_last>}<#else>, </#if>
    </#list>
  )
  </@common.compress_single_line>
  <#if !oneToOne.mappedBy?has_content>@PrimaryKeyJoinColumn</#if>
  <#list oneToOne.annotations as annotation>
  @${annotation.type}(<#list annotation.attributes?keys as key>${key} = ${annotation.attributes[key]}</#list>)
  </#list>
  private ${oneToOne.type} ${oneToOne.fieldName};
</#list>
<#-- -->
<#list root.oneToManies as oneToMany>

  @Builder.Default
  <@common.compress_single_line>
  @OneToMany(fetch = FetchType.LAZY
    <#list oneToMany.cascades as cascade>
      <#if cascade?is_first>, cascade = {</#if>
      CascadeType.${cascade}
      <#if cascade?is_last>}<#else>, </#if>
    </#list>
  )
  </@common.compress_single_line>
  <#if oneToMany.joinColumns?size == 1>
  @JoinColumn(name = "${oneToMany.joinColumns[0].name}", referencedColumnName = "${oneToMany.joinColumns[0].referencedColumnName}", insertable = false, updatable = false)
  <#elseif oneToMany.joinColumns?size gt 1>
  @JoinColumns({
    <#list oneToMany.joinColumns as joinColumn>
    @JoinColumn(name = "${joinColumn.name}", referencedColumnName = "${joinColumn.referencedColumnName}", insertable = false, updatable = false)<#if !joinColumn?is_last>,</#if>
    </#list>
  })
  </#if>
  <#list oneToMany.annotations as annotation>
  @${annotation.type}(<#list annotation.attributes?keys as key>${key} = ${annotation.attributes[key]}</#list>)
  </#list>
  private Set<${oneToMany.type}> ${oneToMany.fieldName} = new HashSet<>();
</#list>
<#-- -->
<#list root.manyToOnes as manyToOne>

  @ManyToOne(fetch = FetchType.LAZY)
  <#if manyToOne.joinColumns?size == 1>
  @JoinColumn(name = "${manyToOne.joinColumns[0].name}", referencedColumnName = "${manyToOne.joinColumns[0].referencedColumnName}")
  <#elseif manyToOne.joinColumns?size gt 1>
  @JoinColumns({
    <#list manyToOne.joinColumns as joinColumn>
    <@common.compress_single_line>
    @JoinColumn(
      name = "${joinColumn.name}", referencedColumnName = "${joinColumn.referencedColumnName}"
      <#if root.name == manyToOne.type && joinColumn.name == joinColumn.referencedColumnName>, insertable = false, updatable = false</#if>
    )
    <#if !joinColumn?is_last>,</#if>
    </@common.compress_single_line>
    </#list>
  })
  </#if>
  <#list manyToOne.annotations as annotation>
  @${annotation.type}(<#list annotation.attributes?keys as key>${key} = ${annotation.attributes[key]}</#list>)
  </#list>
  private ${manyToOne.type} ${manyToOne.fieldName};
</#list>
<#-- -->
<#list root.manyToManies as manyToMany>

  @Builder.Default
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "${manyToMany.joinTable}",
      joinColumns = {
      <#list manyToMany.joinColumns as joinColumn>
        @JoinColumn(name = "${joinColumn.name}", referencedColumnName = "${joinColumn.referencedColumnName}", nullable = false)<#if !joinColumn?is_last>,</#if>
      </#list>
      },
      inverseJoinColumns = {
      <#list manyToMany.inverseJoinColumns as inverseJoinColumn>
        @JoinColumn(name = "${inverseJoinColumn.name}", referencedColumnName = "${inverseJoinColumn.referencedColumnName}", nullable = false)<#if !inverseJoinColumn?is_last>,</#if>
      </#list>
      })
  <#list manyToMany.annotations as annotation>
  @${annotation.type}(<#list annotation.attributes?keys as key>${key} = ${annotation.attributes[key]}</#list>)
  </#list>
  private Set<${manyToMany.type}> ${manyToMany.fieldName} = new HashSet<>();
</#list>
}
