<html lang="en">
<link href="css/main_style.css" rel="stylesheet">

<head>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script language="javascript" type="text/javascript">
	
</script>
<!-- Basic Page Needs
  ================================================== -->
<meta charset="utf-8">
<title>Place a Bet</title>
<meta name="description" content="">
<meta name="author" content="">

<!-- Mobile Specific Metas
  ================================================== -->
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">

<!-- CSS
  ================================================== -->

<!--[if lt IE 9]>
		<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->
</head>
<body>

	<div class="container">
		<div style="height:400px" class="flat-form">
			<header class="tabs" style="margin-bottom: 0px; background: #c0392b;">
				<a
					style="background: #c0392b; font-size: 25px; padding-bottom: 0px; padding-top: 5px; padding-right: 100px">BetTest</a>
			</header>
			<ul class="tabs">
				<li><h class="xihaga">Place Bet</h></li>
			</ul>
			
			<!--/#login.form-action-->
			<div id="place_bet" class="form-action show">
				<h1 style="padding-left: 10px">Bet Details</h1>
				<p style="padding-left: 10px">Enter your Bet Details.</p>
				<br>
				<form>
					<ul>
						<li style="padding:10px">
							<select>						
								<option value="" selected disabled>Risk Level</option>
								<option value="low">Low</option>
								<option value="medium">Medium</option>
								<option value="high">High</option>
							</select>
						</li>
						<li style="padding:10px"><input type="number" placeholder="Amount" /></li>
						<li style="padding:10px"><input type="submit" value="Place Bet" class="button" /></li>
					</ul>
				</form>
			</div>
			<!--/#place_bet.form-action-->
		</div>
	</div>
	<script class="cssdeck"
		src="//cdnjs.cloudflare.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>
</body>

<script src="js/index.js"></script>
</html>
