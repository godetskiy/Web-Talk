<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="style.css" type="text/css" />
    <title>Web-Talk Main page</title>

    <script type="text/javascript">
        function validate(){
            var usr = document.forms["form"]["username"].value;
            var pwd = document.forms["form"]["password"].value;
            if (usr.length == 0)
                document.getElementById("usr").innerHTML="* Username is a required field";
            if (pwd.length == 0)
                document.getElementById("pwd").innerHTML = "* Password is a required field";
            if (usr.length == 0 || pwd.length == 0) {
                return false;
            }
        }
    </script>
</head>

<body>
<form name="form" method="post" action="login" onsubmit="return validate(); ">
    <center>
        <table>
            <tr align="center">
                <td colspan="2">
                    <a href="/"><h1>Web-Talk</h1></a>
                </td>
            </tr>
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
                <td colspan="2"><input type="submit" value="Sign In" >
                    <input type="submit" value="Registration" onclick="location.replace('/registration')"></td>
            </tr>
        </table>
    </center>
</form>
</body>

</html> 

