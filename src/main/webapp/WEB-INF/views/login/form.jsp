
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

    <title>Signin Template for Bootstrap</title>

    <!-- Bootstrap core CSS -->
 	<link href="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/resources/styles/signin.css" rel="stylesheet" type="text/css">
  </head>

  <body>

    <div class="container">

      <form class="form-signin" action="/login" method="post">
        <h4 class="form-signin-heading">EVER POLYMER CO., LTD.</h4>
		<div id="message" class="alert alert-danger" role="alert" style="display:none;">
		  <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
		  <span class="sr-only">Error:</span>
		  Enter a valid username or password
		</div>
        <label for="inputEmail" class="sr-only">Username</label>
        <input type="text" id="username" name="username" class="form-control" placeholder="Please enter the username for login" required autofocus>
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" id="password" name="password" class="form-control" placeholder="Please enter the password for login" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
      </form>
      <%=request.getAttribute("messageShow") %>

    </div> <!-- /container -->
  </body>
</html>
