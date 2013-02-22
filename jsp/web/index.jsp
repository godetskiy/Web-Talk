<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%
    String name = request.getParameter( "username" );
    session.setAttribute( "theName", name );
%>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="style.css" type="text/css" />
    <title>Web-Talk Main page</title>
</head>

<body>
<div id="logo">
    <a href="/"><h1>Web-Talk</h1></a>
</div>

<%!
    boolean bLogged = false;
    String stUsername = "";
%>
<%
    try {
        HttpSession hs = request.getSession();
        bLogged = Boolean.valueOf(hs.getAttribute("logged").toString());
        stUsername = hs.getAttribute("username").toString();
    } catch (Exception e) {

    }
%>
<%!
    String createMenu() {
        String htmlText = "";
        if (bLogged) {
            //Если вход выполнен
            htmlText += "<div>Привет, " + stUsername + "</div><br><ul><li><a href='/box'>Все сообщения</a></li>" +
                    "<li><a href='/message'>Новое сообщение</a></li>" +
                    "<li><a href='/logout'>Выход</a></li></ul>";

        } else {
            htmlText += "<ul><li><a href='/login.jsp'>Вход</a></li>" +
            "<li><a href='/registration.jsp'>Регистрация</a></li></ul>";
        }
        return htmlText;
    }
%>
<div id="menu">
    <%=createMenu() %>
</div>
</body>

</html> 

