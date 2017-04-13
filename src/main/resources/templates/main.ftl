<#import "include/pageTemplate.ftl" as page>
    <@page.pageTemplate>
<h1>Session Information</h1>
<ul>
    <li>Automation session set: ${sessionSet}</li>
    <li>Session created at: ${sessionCreatedAt}</li>
</ul>
</@page.pageTemplate>
