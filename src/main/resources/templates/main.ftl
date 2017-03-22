<#import "include/pageTemplate.ftl" as page>
    <@page.pageTemplate>
<ul>
    <li>${session.getCode()}</li>
    <li>${sessionCreatedAt}</li>
</ul>
</@page.pageTemplate>
