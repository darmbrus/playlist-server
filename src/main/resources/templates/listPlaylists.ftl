<#import "include/pageTemplate.ftl" as page>
<@page.pageTemplate>
    <h1>Current User's Playlists</h1>
    <p>Click create a new playlist based off the original.</p>
    <ul>
        <#list listObject as item>
            <li><a style="color: black;" href="/playlists/${item.getId()}/random">${item.getName()}</a></li>
        </#list>
    </ul>
</@page.pageTemplate>
