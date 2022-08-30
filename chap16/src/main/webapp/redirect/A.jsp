<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>A</title>
</head>

<body>
    <h1>/redirect/A.jsp</h1>
    <hr>

    <!-- 웹브라우저에게 302/307/308 상태코드의 리다이렉트 응답을 전송 -->

    <%  // scriptlet tag : 자바코드를 작성할 때 사용하는 태그
        // 빨간색 용지에 최우선순위값 1을 기재해서, B.jsp 에게 보여주자!!!
        // 방법2가지(B.jsp에게 데이터를 보내는 방법):
        //     (1) 전송파라미터로 보내자! (즉, Query String 형태로 구성해서...)
        //     (2) 공유영역인 Session Scope에 올려놔서 보내자!(공유시킴)

        // 1st. method
        response.sendRedirect("/redirect/B.jsp?priority=1&color=red");
    %>


</body>
</html>