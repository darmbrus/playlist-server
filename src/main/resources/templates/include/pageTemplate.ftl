<#macro pageTemplate>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF8">
    <title>Spotify Interface</title>
    <style>
    body {
        font-family: sans-serif;
        margin: 0;
        padding: 0;
    }

    #left {
      float: left;
      display: inline-block;
      background-color: #576CA8;
      position: absolute;
      top: 100px;
      bottom: 0;
      width: 200px;
    }

    #right {
      padding: 0 0 0 1em;
      display: inline-block;
      color: #302B27;
      background-color: #F5F3F5;
      position: absolute;
      top: 100px;
      right: 0;
      bottom: 0;
      left: 200px;
    }

    .clear {
      clear: both;
      background: #1B264F;
      color: #F5F3F5
    }

    #title {
      padding: 1em;
      margin: inherit;
    }
  </style>
</head>
<body>
    <div class="clear">
        <h1 id="title">playlist.</h1>
    </div>
    <div id="left">
        <#include "nav.ftl">
    </div>
    <div id="right">
        <#nested>
    </div>
</body>
</html>
</#macro>
