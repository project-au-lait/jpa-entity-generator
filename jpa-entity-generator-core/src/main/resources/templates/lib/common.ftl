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
