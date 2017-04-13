<#macro pageTemplate>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF8">
    <title>Spotify Interface</title>
    <link rel="stylesheet" type="text/css" href="assets/css/style.css"/>
</head>
<body>
    <header> <h1 id="title">playlist.</h1> </header>
    <div id="main">
        <nav> <#include "nav.ftl"> </nav>
        <article> <#nested> </article>
    </div>
    <footer> <p>Created by David Armbrust &#169;2017</p> </footer>
</body>
</html>
</#macro>
