<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="style.css" type="text/css" />
    <title>Сообщение</title>
</head>
<body>
<a href="/box">&lArr;Назад</a>
<table>
    <tr><td>От:</td> <td>${to}</td></tr>
    <tr><td>Тема:</td> <td>${subject}</td></tr>
    <tr><td>Сообщение:</td><td>${text}</td></tr>
    <tr align='center'>
        <td colspan='2'>
            <input type='${type}' value='Ответить' onclick="location.href='${action}'">
        </td>
    </tr>
</table>
</body>
</html>