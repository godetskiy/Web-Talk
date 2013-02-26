<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="style.css" type="text/css" />
    <title>Login page</title>

    <script type="text/javascript">
        function validate(){
            document.getElementById("usr").innerHTML = "";
            document.getElementById("pwd").innerHTML = "";
            var usr = document.forms["form"]["username"].value;
            var pwd = document.forms["form"]["password"].value;
            var e = true;
            if (usr.length == 0) {
                document.getElementById("usr").innerHTML="* Username обязательно";
                e = false;
            }
            if (pwd.length == 0) {
                document.getElementById("pwd").innerHTML = "* Password обязательно";
            e = false;
            }
            return e;
        }
    </script>
</head>

<body>

<form name="form" method="post" action="/login" onsubmit="return validate(); ">
    <center>
        <table>
            <tr align="center"><td colspan="2">
                <ul class="errorlist">
                    <li>${err}</li>
                    <li><span id = "usr"></span></li>
                    <li><span id = "pwd"></span></li>
                </ul>
            </td></tr>
            <tr>
                <td><b>Username</b></td>
                <td><input type="text" maxlength="49" name="username"></td>
            </tr>
            <tr>
                <td><b>Password</b></td>
                <td><input type="password" maxlength="49" name="password"></td>
            </tr>
            <tr align="center" >
                <td colspan="2"><input type="submit" value="Войти" >
                <input type="button" value="Назад" onclick="location.href='/'"></td>
            </tr>
        </table>
    </center>
</form>
</body>

</html> 

