

<html lang="en">

<link href="css/main_style.css" rel="stylesheet">

<!-- DataTables CSS -->
<link rel="stylesheet"
	href="//cdn.datatables.net/1.10.4/css/jquery.dataTables.css">

<!-- jQuery -->
<script type="text/javascript" charset="utf8"
	src="//code.jquery.com/jquery-1.10.2.min.js"></script>

<!-- DataTables -->
<script type="text/javascript" charset="utf8"
	src="//cdn.datatables.net/1.10.4/js/jquery.dataTables.js"></script>

<%@ page import="java.sql.*"%>
<%
	Class.forName("com.mysql.jdbc.Driver");
%>
<%@ page import="java.util.*"%>

<head>






<script language="javascript" type="text/javascript">
	
</script>

<script src="//cdn.datatables.net/1.10.4/js/jquery.dataTables.js"
	type="text/javascript"></script>
<script src="//cdn.datatables.net/1.10.4/js/jquery.dataTables.min.js"
	type="text/javascript"></script>



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

	<%
		String userName = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("user"))
					userName = cookie.getValue();
			}
		}
		if (userName == null)
			response.sendRedirect("index.jsp");
	%>

	<div class="container">


		<div style="height: 775px; width: 800px" class="flat-form">



			<header class="tabs" style="margin-bottom: 0px; background: #c0392b;">
				<a
					style="background: #c0392b; font-size: 25px; padding-bottom: 0px; padding-top: 5px; padding-right: 100px">BetTest</a>
			</header>
			<ul class="tabs">
				<li><h class="xihaga"><%=userName%>'s Betting Page</h></li>
			</ul>

			<!--/#login.form-action-->


			<div id="place_bet" class="form-action show">


				<h1 style="padding-left: 10px">Bet Details</h1>
				<p style="padding-left: 10px">Enter your Bet Details.</p>
				<br>
				<form id="place_bet_form" method="post" action="placeBet">
					
						<div style="padding: 10px">
							<select id="risk" name="risk">
							<%
							String free;
								if(userName.equals("LoginFreeDroid"))
									free = "disabled";
								else
									free = "";
								
							%>	
									<option value="" selected disabled>Risk Level</option>
									<option value="low" selected="selected">Low</option>

									<option value="medium" <%=free%>>Medium</option>
									<option value="high" <%=free%>>High</option>
							</select>
						</div>
						<div style="padding: 10px">
							<input id="amount" type="number" name="amount" placeholder="Amount"  step="any" />
						</div>
						<div style="padding: 10px"><input
							style="display: inline-block" type="submit" id="place_bet_submit"
							value="Place Bet" class="button" /> <span
							style="font-size: 30px; padding-left: 490px;">&nbsp;</span> <input
							style="display: inline-block" type="submit" id="logout"
							value="Log Out" class="button" /></div>
							<input type="hidden" name="userName" value="<%=userName%>" />
					
				</form>
				<input type="hidden" id="isfree" name="isfree" value="<%=free%>" />

				<%

				%>

				<div
					style="border: 2px solid; border-color: lightgray; background-color: white">
					<h style="background: #136899; width: 93.5%">Betting History</h>
					<table id="example" class="display"
						style="padding: 10px; width: 90%">
						<thead>
							<tr>
								<th>Bet ID</th>
								<th>Risk Level</th>
								<th>Amount</th>
							</tr>
						</thead>
						<tfoot>



							<tr>
								<th>Bet ID</th>
								<th>Risk Level</th>
								<th>Amount</th>
							</tr>
						</tfoot>
						<tbody>

						</tbody>
					</table>
				</div>
			</div>



			<!--/#place_bet.form-action-->
		</div>
	</div>


</body>

<script src="js/index.js"></script>
<script type="text/javascript" src="js/jquery.tipsy.js"></script>

</html>
