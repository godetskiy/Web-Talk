<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="style.css" type="text/css" />
    <title>Сообщение</title>

    <script type="text/javascript">
        function validate(){
            document.getElementById("toi").innerHTML = "";
            document.getElementById("sbj").innerHTML = "";
            document.getElementById("ms").innerHTML = "";
            var to = document.forms["form"]["toi"].value;
            var subject = document.forms["form"]["subject"].value;
            var msg = document.forms["form"]["msg"].value;
            var e = true;
            if (to.length == 0) {
                document.getElementById("to").innerHTML=" Выберите получателя";
                e = false;
            }
            if (subject.length == 0) {
                document.getElementById("sbj").innerHTML=" Введите тему сообщения";
                e = false;
            }
            if (msg.length == 0) {
                document.getElementById("ms").innerHTML = "Введите сообщение";
                e = false;
            }
            return e;
        }
    </script>
</head>
<body>
<a href="/">&lArr;Назад</a>
    <div id="msg">
        <form id="fm" name="form" method="post" action="${action}" onsubmit="return validate();">
        <table>
            <tr align="center"><td colspan="2">
                <ul class="errorlist">
                    <li>${err}</li>
                    <li><span id = "toi"></span></li>
                    <li><span id = "sbj"></span></li>
                    <li><span id = "ms"></span></li>
                </ul>
            </td></tr>
            <tr>
                <td>Кому: </td>
                <td><select name="to" size="1">
                    <%! String options = ""; %>
                    <% options = request.getAttribute("options").toString(); %>
                    <%= options%>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Тема: </td>
                <td><input type="text" name="subject" value="${subject}" maxlength="29"></td>
            </tr>
            <tr>
                <td colspan="2"><textarea name="msg" cols="30                                                                                                                                                                                                                                                                    " rows="20" form="fm">${msg}</textarea></td>
            </tr>
            <tr align="center">
                <td colspan="2"><input type="submit" value="Отправить"></td>
            </tr>
        </table>
        </form>
    </div>
</body>
</html>