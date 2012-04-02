
<html>
  <head>
    <title>Welcome!</title>
  </head>
  <body>
    <table>
  	<#list userList as user>
  	<h1>userList ${user.userName}!, ${user.userEmail}, your id is ${user.userId}</h1>
 	</#list>
 	<#list userArray as user>
  	<h1>userArray ${user.userName}!, ${user.userEmail}, your id is ${user.userId}</h1>
 	</#list>
 	</table>
	<table>
    <h1>curPage:${pageArgument.curPage}!, pageSize:${pageArgument.pageSize}, totalRow:${pageArgument.totalRow}, totalPage:${pageArgument.totalPage}</h1>
    </table>
  </body>
</html>
