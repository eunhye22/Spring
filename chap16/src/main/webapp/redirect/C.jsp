<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>C</title>
</head>

<body>
    <h1>/redirect/C.jsp</h1>
    <hr>

    <!-- B.jsp 쪽에서 준, 쪽지의 색깔과 우선순위값을 확인하자! -->
    <%
        // Request Scope 공유영역에 올려 놓은 공유 데이터를 활용
        String color = (String) session.getAttribute("color");
        String priority = (String) session.getAttribute("priority");        
    %>

    <h2>1. color: <%= color %></h2>
    <h2>2. priority: <%= priority %></h2>



</body>
</html>