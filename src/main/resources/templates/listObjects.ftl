<#import "include/pageTemplate.ftl" as page>
    <@page.pageTemplate>
    <#list listObject as item>
        <p><#list item as prop>
            ${prop}
        </#list></p>

    </#list>
</@page.pageTemplate>
