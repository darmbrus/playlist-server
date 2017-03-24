<#import "include/pageTemplate.ftl" as page>
    <@page.pageTemplate>
    <#list listObject as item>
            <p>${item.toString()}</p>
    </#list>
</@page.pageTemplate>
