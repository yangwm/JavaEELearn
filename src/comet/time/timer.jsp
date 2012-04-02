<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Comet Weather</title>
        <SCRIPT TYPE="text/javascript">
            function go(){
                var url = "http://localhost:8080/testcomet/time"
                var request =  new XMLHttpRequest();
                //request.open("GET", url, true);
                //request.setRequestHeader("Content-Type","application/x-javascript;");
                request.open("POST",url,false);
				request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
                request.onreadystatechange = function() {
                	var dt = new Date();
                	document.getElementById("timer").innerHTML = dt + ", " + request.responseText + ", " + request.readyState + ", " + request.status;
                    if (request.readyState == 4) {
                        if (request.status == 200){
                            if (request.responseText) {
                                document.getElementById("timer").innerHTML = request.responseText;
                            }
                        }
                        //var dt = new Date();
						//while ((new Date()) - dt <= milliseconds) { /* Do nothing */ }
                        var delay = setTimeout("go()", 3000);
                        //go();
                    }
                };
                request.send(null);
            }
        </SCRIPT>
    </head>
    <body>
        <h1>Clock Timer</h1>
        <input type="button" onclick="go()" value="Go!"></input>
        <div id="timer">timer</div>
    </body>
</html>
