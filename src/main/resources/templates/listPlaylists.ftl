<#import "include/pageTemplate.ftl" as page>
<@page.pageTemplate>
    <#list listObject as item>
        <p>${item.getName()}</p>
        <a href="${item.getHref()}">${item.getId()}</a>
    </#list>
</@page.pageTemplate>
