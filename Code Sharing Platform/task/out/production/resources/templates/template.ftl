<html lang="en">
<head>
    <title>Latest</title>
    <link rel="stylesheet" href="/css/snippet-style.css">
    <link rel="stylesheet"
          target="_blank" href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
</head>
<body>
<#list codeSnippets as codeSnippet>
    <div id="code-block">
<span id="load-date">
      ${codeSnippet.date}
  </span>
<pre id="code_snippet"><code>
      ${codeSnippet.code}
    </code></pre>
    </div>
</#list>
</body>
</html>