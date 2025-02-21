<#macro compress_single_line>
  <#local captured><#nested></#local>
  <#local trimmed>
    <#list captured?split("\\r|\\n","r") as line>
      <#if line?is_first>
        ${line}<#lt>
      <#else>
        ${line?replace("^\\s+", "", "r")}<#lt>
      </#if>
    </#list>
  </#local>
${trimmed?replace("\\r|\\n", "", "rm")}
</#macro>
