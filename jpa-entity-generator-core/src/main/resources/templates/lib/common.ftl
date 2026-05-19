<#macro auto_wrap maxLineLength=100>
  <#local captured><#nested></#local>
  <#local normalized = captured?replace("\\s+", " ", "r")?trim>
  <#local implIdx = normalized?index_of(" implements ")>
  <#if implIdx < 0>
${normalized}
  <#else>
    <#local prefix = normalized?substring(0, implIdx)>
    <#local suffix = normalized?substring(implIdx + 1)>
    <#if prefix?length <= maxLineLength>
${prefix}
    ${suffix}
    <#else>
      <#local extendsIdx = prefix?index_of(" extends ")>
      <#if extendsIdx < 0>
${prefix}
    ${suffix}
      <#else>
        <#local classNamePart = prefix?substring(0, extendsIdx)>
        <#local extendsPart = prefix?substring(extendsIdx + 1)>
${classNamePart}
    ${extendsPart} ${suffix}
      </#if>
    </#if>
  </#if>
</#macro>

<#macro compress_single_line maxLineLength=100>
  <#local captured><#nested></#local>
  <#local preserved = captured?replace("^\\s*\\r?\\n", "", "r")?replace("\\r?\\n\\s*$", "", "r")>
  <#local normalized = "">
  <#local firstContentLine = true>
  <#list captured?split("\\r|\\n", "r") as line>
    <#local trimmedLine = line?replace("^\\s+", "", "r")>
    <#if trimmedLine?has_content>
      <#if firstContentLine>
        <#local normalized = line>
        <#local firstContentLine = false>
      <#else>
        <#local separator = " ">
        <#if trimmedLine?matches("^[,)}].*") || normalized?matches(".*[(\\{\\s]$")>
          <#local separator = "">
        </#if>
        <#local normalized = normalized + separator + trimmedLine>
      </#if>
    </#if>
  </#list>
  <#if normalized?length <= maxLineLength>
${normalized}
  <#else>
${preserved}
  </#if>
</#macro>
