<html lang="en">
<link href="css/main_style.css" rel="stylesheet">

<head>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/jquery.tipsy.js"></script>

<!-- DataTables CSS -->
<link rel="stylesheet"
	href="//cdn.datatables.net/1.10.4/css/jquery.dataTables.css">

<script src="//cdn.datatables.net/1.10.4/js/jquery.dataTables.js"
	type="text/javascript"></script>
<script src="//cdn.datatables.net/1.10.4/js/jquery.dataTables.min.js"
	type="text/javascript"></script>

<script language="javascript" type="text/javascript">
	
</script>

<!-- DataTables -->
<script type="text/javascript" charset="utf8"
	src="//cdn.datatables.net/1.10.4/js/jquery.dataTables.js"></script>
<!-- Basic Page Needs
  ================================================== -->
<meta charset="utf-8">
<title>Test365</title>
<meta name="description" content="">
<meta name="author" content="">

<!-- Mobile Specific Metas
  ================================================== -->
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">

<!-- CSS
  ================================================== -->

</head>

<script src="//cdn.datatables.net/1.10.4/js/jquery.dataTables.js"
	type="text/javascript"></script>
<script src="//cdn.datatables.net/1.10.4/js/jquery.dataTables.min.js"
	type="text/javascript"></script>

<body>

	<div class="container">
		<div class="flat-form">
			<header class="tabs" style="margin-bottom: 0px; background: #c0392b;">
				<a
					style="background: #c0392b; font-size: 25px; padding-bottom: 0px; padding-top: 5px; padding-right: 100px">Test365</a>
			</header>
			<ul class="tabs">
				<li><a id="login_tab" href="#login" class="active">Login</a></li>
				<li><a id="reg_tab" href="#register">Register</a></li>
			</ul>
			<div id="login" class="form-action show">
				<h1 style="padding-bottom: 50px">Login</h1>
				<form id="user_login" method="post" action="login">
					<ul>
						<li><input type="text" id ="login_user" placeholder="Username" name="user"/></li>
						<li><input type="password" id = "login_pass" placeholder="Password" name="pass"/></li>
						<li><input type="submit" id="login_submit" value="Login" class="button" /></li>
					</ul>
				</form>
			</div>
			<!--/#login.form-action-->
			<div id="register" class="form-action hide">
				<h1>Register</h1>
				<p>Enter your Registration Details.</p>
				<br>
		<form id="contact" method="post" action="register">
		    <!-- Username -->
		    <div>
		        <input type="text" name="user" id="user" placeholder="Username" />
		    </div>
		    <!-- Password -->
		    <div>
		        <input type="password" name="pass" id="pass" placeholder="Password" />
		    </div>             
		    <!-- Name -->
		    <div>
		        <input type="text" name="name" id="name" placeholder="Name" />
		    </div> 
		    <!-- Surname -->
		    <div>
		        <input type="text" name="surname" id="surname" placeholder="Surname" />
		    </div>   
		    <!-- Date -->
		    <div>
		    	<label for="date" style="text-align: left;">Date of Birth: </label>
		        <input type="date" name="date" id="date"
							style="-webkit-inner-spin-button: none" placeholder="Date of Birth YYYY-MM-DD" />
		    </div>     
		    <!-- CC Number -->
		    <div>
		        <input type="number" name="CCN" id="CCN" placeholder="Credit Card Number" />
		    </div> 
		    <!-- CC Expiry -->
		    <div>
		    	<label for="expiry" style="text-align: left;">Expiry Date: </label>
		        <input type="month" name="expiry" id="expiry" placeholder="Credit card expiry date YYYY-MM" />
		    </div> 
		    <!-- CVV -->
		    <div>
		        <input type="text" id="CVV" name ="CVV"  placeholder="CVV" />
		    </div> 
		    <!-- Free/Premium -->
		    <div style= "margin-bottom:20px; margin-top:15px">
		        <input type="radio" id="subscription" name="subscription" value="free" checked="checked" >Free
				<input type="radio" id="subscription" name="subscription" value="premium">     Premium
		    </div> 
		    <!-- Submit -->
		    <div>
				<input type="submit" id="register_submit" value="Sign Up" class="button" />
		    </div> 
           
		</form>
			</div>
			<!--/#register.form-action-->
		</div>
	</div>
	<script class="cssdeck"
		src="//cdnjs.cloudflare.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</body>

<script src="js/index.js"></script>
<script type="text/javascript" src="js/jquery.tipsy.js"></script>
</html>
