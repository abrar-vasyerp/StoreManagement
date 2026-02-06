<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <title>System Logs</title>
    <style>
        body { font-family: monospace; background: #111; color: #0f0; }
        .log { padding: 4px; border-bottom: 1px solid #333; }
        .ERROR { color: #ff4c4c; }
        .WARN  { color: #ffcc00; }
        .INFO  { color: #00ffcc; }
    </style>
</head>

<body>
<h2>🛠️ System Logs (Admin)</h2>

<c:forEach var="log" items="${logs}">
    <div class="log
        <c:if test='${log.contains("ERROR")}'>ERROR</c:if>
        <c:if test='${log.contains("WARN")}'>WARN</c:if>
        <c:if test='${log.contains("INFO")}'>INFO</c:if>
    ">
            ${log}
    </div>
</c:forEach>

</body>
</html>
