<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>B</title>
</head>

<body>
    <h1>/redirect/B.jsp</h1>
    <hr>

    <!-- A.jsp 쪽에서 준, 쪽지의 색깔과 우선순위값을 확인하자! -->
    <%
        String color = request.getParameter("color");
        String priority = request.getParameter("priority");        
    %>

    <!-- <h2>1. color: <%= color %></h2>
    <h2>2. priority: <%= priority %></h2> -->

    <!-- --------------- -->

    <% // Scriptlet tag

        // 2nd. method (Session Scope 공유영역을 활용)
        session.setAttribute("color", "BLUE");
        session.setAttribute("priority", "1");

        response.sendRedirect("/redirect/C.jsp");   // 302응답코드를 가진 응답문서가 전송
    %>


</body>
</html>