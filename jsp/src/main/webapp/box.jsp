<%@ page import="WebTalk.Message" %>
<%@ page import="WebTalk.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="style.css" type="text/css" />
    <title>Ящик</title>
</head>
<body>
<a href="/"><h3>&lArr;Назад</h3></a>
<br>
<table border="1px solid black" width="100%">
    <tr><th>Дата</th><th>Информация</th><th>Тема</th></tr>
    <%!
        Message message[] = null;
        String htmlText = "";
        int usr_id = -1;
    %>
    <%
        try {
            message = (Message[]) request.getAttribute("messages");
            HttpSession hs = request.getSession();
            User tmpUser = (User) hs.getAttribute("user");
            usr_id = tmpUser.getUsr_id();
        } catch (NullPointerException e) {
          e.printStackTrace();
        }
        if (message == null) {
            //Если сообщений нет
            htmlText = "<tr style='text-align: center'><td colspan='3'>Сообщений нет</td></tr>";
        } else {
            htmlText += "<tr>";
            for (int i = 0; i < message.length; i++) {
                //1 столбец
                htmlText += "<td>" + message[i].getDate() + "</td>";
                //2 столбец
                htmlText += "<td>";
                if (message[i].getSender() == usr_id) {
                    //Исходящее сообщение
                    String str = User.getNameById(message[i].getTo());
                    htmlText += "Исходящее к " + str + ".";
                } else {
                    //Входящее сообщение
                    String str = User.getNameById(message[i].getSender());
                    htmlText += "Входящее от " + str + ".";
                }
                htmlText += "</td>";
                //3 столбец
                htmlText += "<td><a href='/view?msg_id=" +
                        message[i].getMsg_id() + "'> " + message[i].getSubject() + "</td></tr>";
            }
            htmlText += "</tr>";
        }

    %>
    <%=htmlText%>
</table>
</body>
</html>