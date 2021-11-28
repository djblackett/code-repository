<html lang="en">
<head>
    <link rel=stylesheet
           target="_blank" href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
        <title>Code</title>
    </head>

<body>
    <span id="load_date">
                ${codeSnippet.date}
    </span>
    <pre id="code_snippet">
        <code>
               ${codeSnippet.code}
                </code> </pre>

    <#if codeSnippet.time gt 0 >
    <span id="time_restriction">
        Time: ${codeSnippet.time}
    </span>
        <br>
    </#if>

    <#if codeSnippet.views gte 0 && codeSnippet.viewPresent>
        <span id="views_restriction">
        Views: ${codeSnippet.views}
        </span>
    </#if>


</body>
</html>