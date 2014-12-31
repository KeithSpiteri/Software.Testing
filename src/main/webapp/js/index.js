$(function() {

	// constants
	var SHOW_CLASS = 'show', HIDE_CLASS = 'hide', ACTIVE_CLASS = 'active';

	$('.tabs').on(
			'click',
			'li a',
			function(e) {
				e.preventDefault();
				var $tab = $(this), href = $tab.attr('href');

				$('.active').removeClass(ACTIVE_CLASS);
				$tab.addClass(ACTIVE_CLASS);

				if ($('.active')[0].innerHTML == 'Register')
					$('.flat-form').height(725);
				else
					$('.flat-form').height(375);

				$('.show').removeClass(SHOW_CLASS).addClass(HIDE_CLASS).hide();

				$(href).removeClass(HIDE_CLASS).addClass(SHOW_CLASS).hide()
						.fadeIn(550);
			});



	$('#user').blur(function(event) {
		var input = $(this);
		var message = $(this).val();
		if (message) {
			input.tipsy({
				opacity : 0
			});
			input.removeClass("invalid").addClass("valid");
		} else {
			input.removeClass("valid").addClass("invalid");
		}
	});

	$('#pass').blur(function(event) {
		var input = $(this);
		var message = $(this).val();
		if (message.length > 7) {
			input.tipsy({
				opacity : 0
			});
			input.removeClass("invalid").addClass("valid");
		} else {
			input.removeClass("valid").addClass("invalid");
		}
	});

	$('#name').blur(function(event) {
		var input = $(this);
		var message = $(this).val();
		var valid = /^[A-Za-z\s]+$/.test(message);
		if (valid) {
			input.tipsy({
				opacity : 0
			});
			input.removeClass("invalid").addClass("valid");
		} else {
			input.removeClass("valid").addClass("invalid");
		}
	});

	$('#surname').blur(function(event) {
		var input = $(this);
		var message = $(this).val();
		var valid = /^[A-Za-z\s]+$/.test(message);
		if (valid) {
			input.tipsy({
				opacity : 0
			});
			input.removeClass("invalid").addClass("valid");
		} else {
			input.removeClass("valid").addClass("invalid");
		}
	});

	$('#date').blur(function(event) {
		var input = $(this);
		var date = input.val();
		var actual = new Date(date);
		if (determineAge(actual) >= 18) {
			input.tipsy({
				opacity : 0
			});
			input.removeClass("invalid").addClass("valid");
		} else {
			input.addClass("invalid").removeClass("valid");
		}
	});

	function determineAge(date) {
		var curr_date = new Date();
		var dob = new Date(date);
		var age = curr_date.getFullYear() - dob.getFullYear();
		var m = curr_date.getMonth() - dob.getMonth();
		if (m < 0 || (m == 0 && curr_date.getDate() < dob.getDate()))
			age--;
		return age;
	}

	$('#CCN').blur(function(event) {
		var input = $(this);
		var ccn = input.val();
		if (ccn != 0 && ccn && luhnValidation(ccn)) {
			input.tipsy({
				opacity : 0
			});
			input.removeClass("invalid").addClass("valid");
		} else {
			input.addClass("invalid").removeClass("valid");
		}
	});

	$("#CCN").keydown(
			function(e) { // Don't allow any inputs which are not numbers
				// Allow: backspace delete tab escape enter and .
				if ($.inArray(e.keyCode, [ 46, 8, 9, 27, 13, 110, 190 ]) != -1
						||
						// Allow: Control + Characters
						((e.keyCode == 65 || e.keyCode == 97 || e.keyCode == 67
								|| e.keyCode == 99 || e.keyCode == 86
								|| e.keyCode == 166 || e.keyCode >= 35
								&& e.keyCode <= 39)) && e.ctrlKey == true) {
					return;
				}
				// Otherwise Don't allow keypresses
				if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57))
						&& (e.keyCode < 96 || e.keyCode > 105)) {
					e.preventDefault();
				}
			});

	$("#CVV").keydown(
			function(e) { // Don't allow any inputs which are not numbers
				// Allow: backspace delete tab escape enter and .
				if ($.inArray(e.keyCode, [ 46, 8, 9, 27, 13, 110, 190 ]) != -1
						||
						// Allow: Control + Characters
						((e.keyCode == 65 || e.keyCode == 97 || e.keyCode == 67
								|| e.keyCode == 99 || e.keyCode == 86
								|| e.keyCode == 166 || e.keyCode >= 35
								&& e.keyCode <= 39)) && e.ctrlKey == true) {
					return;
				}
				// Otherwise Don't allow keypresses
				if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57))
						&& (e.keyCode < 96 || e.keyCode > 105)) {
					e.preventDefault();
				}
			});

	$('#CVV').blur(function(event) {
		var input = $(this);
		var message = $(this).val();
		var valid = /^[0-9]{3}$/.test(message);
		if (valid) {
			input.tipsy({
				opacity : 0
			});
			input.removeClass("invalid").addClass("valid");
		} else {
			input.removeClass("valid").addClass("invalid");
		}
	});

	function luhnValidation(ccn) {
		var string = 0;
		var bool = false;
		var length = ccn.length;

		while (length > 0) {
			length--;
			string += "01234567890246813579".charAt(ccn.charAt(length) * 1
					+ ((bool = !bool) ? 0 : 10)) * 1;
		}
		return (string % 10 == 0);
	}

	$('#expiry')
			.blur(
					function(event) {
						var input = $(this);
						var date = input.val();
						var actual = new Date(date);
						var curr_date = new Date();
						if (determineAge(actual) < 0
								|| (determineAge(actual) == 0 && (actual
										.getMonth() - curr_date.getMonth()) >= 0)) {
							input.tipsy({
								opacity : 0
							});
							input.removeClass("invalid").addClass("valid");
						} else {
							input.addClass("invalid").removeClass("valid");
						}
					});

	$("#register_submit")
			.click(
					function(event) {
						var form_data = $("#contact").serializeArray();
						var error_free = true;
						for ( var input in form_data) {
							var element = $('#' + form_data[input]['name']);
							var valid = element.hasClass("valid");
							element.focus().blur();
							var error_element = $("span", element.parent());
							if (!valid && form_data[input]['name'] != "subscription") {
								error_element.removeClass("error").addClass(
										"error_show");
								var message;
								message = "Invalid!";
								if (input == 0)
									message = "Invalid Username!";
								else if (input == 1)
									message = "Invalid Password - Must be more than 8 characters long!";
								else if (input == 2)
									message = "Invalid Name!";
								else if (input == 3)
									message = "Invalid Surname!";
								else if (input == 4)
									message = "Invalid Date of Birth!";
								else if (input == 5)
									message = "Invalid Credit Card Number!";
								else if (input == 6)
									message = "Invalid Expiry Date!";
								else if (input == 7)
									message = "Invalid CVV!";

								element.tipsy({
									fallback : message,
									opacity : 0.8,
									gravity : 'w'
								});
								error_free = false;
								error_element.tipsy();
								// element.tipsy({opacity:0});
							} else {
								error_element.removeClass("error_show")
										.addClass("error");
							}
						}
						if (!error_free) {
							event.preventDefault();
						}
					
					});
	
	$('#amount')
	.blur(
			function(event) {
				var input = $(this);
				var amount = input.val();
				
				if(!isNaN(parseFloat(amount)) && amount > 0) {
					input.tipsy({
						opacity : 0
					});
					input.removeClass("invalid").addClass("valid");
				} else {
					input.addClass("invalid").removeClass("valid");
				}
			});
	
	$("#place_bet_submit")
	.click(
			function(event) {
				var form_data = $("#place_bet_form").serializeArray();
				var error_free = true;
					var element = $('#' + form_data[1]['name']);
					var valid = element.hasClass("valid");
					element.focus().blur();
					var error_element = $("span", element.parent());
					if (!valid ) {
						error_element.removeClass("error").addClass(
								"error_show");
						var message = "Invalid Amount!";


						element.tipsy({
							fallback : message,
							opacity : 0.8,
							gravity : 'w'
						});
						error_free = false;
						error_element.tipsy();
						// element.tipsy({opacity:0});
					} else {
						error_free = true;
						error_element.removeClass("error_show")
								.addClass("error");
					}
				
				if (!error_free) {
					event.preventDefault();
				}
			
			});
	
	$(document).ready(function() {
	    $("#example").dataTable({
	    	"lengthMenu": [[5], [5]]
	    });
	    $(".dataTables_length").hide();
	    $("#example_filter").hide();
	} );

});