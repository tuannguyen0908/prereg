var mobile_no_txt = $('#mobile-no');
var mobile_no_validator = $('div.validation-error-label > #mobile-no-error');
var refer_txt = $('#refer-mobile');
var refer_validator = $('div.validation-error-label > #refer-mobile-error');
var submit_btn = $('.submit');
var global_validator = $('div.validation-error-label > #global-error');

(function($) {
	// Setup phone number mask on inputs
	phone_options = {
		mask: '999 9999999',
		greedy: false
	};
	mobile_no_txt.inputmask(phone_options);
	mobile_no_txt.on("focusout", function(event) {
		_validate();
	});
	refer_txt.inputmask(phone_options);
	refer_txt.on("focusout", function(event) {
		_validate();
	});
	
	// Exucute register when submit button is clicked
	submit_btn.on('click', function(event) {
		var mobile_no = mobile_no_txt.inputmask('unmaskedvalue');
		var refer_mobile_no = refer_txt.inputmask('unmaskedvalue');
		if (_validate_when_submit(mobile_no, refer_mobile_no)) {
			_resetUI();
			$.ajax({
				url: '/regs',
				type: 'post',
				contentType: 'application/json',
				dataType: 'json',
				data: JSON.stringify({
					mobileNo: mobile_no,
					referredMobileNo: refer_mobile_no ? refer_mobile_no : null
				}),
				success: function(responseJson, textStatus, jqXHR) {
					window.location = '/success';
				},
				error: function(jqXHR, textStatus, errorThrown) {
					console.log(jqXHR);
					var responseJson = jqXHR.responseJSON;
					var exception = responseJson.exception;
					var exceptionType = exception.substring(exception.lastIndexOf('.') + 1, exception.length);
					switch (exceptionType) {
					case 'IllegalReferMobileException':
						mobile_no_txt.addClass('validation-error')
						refer_txt.addClass('validation-error')
						global_validator.show();
						break;
					case 'MobileAlreadyExistedException':
						window.location = '/failure';
					case 'MethodArgumentNotValidException':
						var errors = responseJson.errors;
						$.each(errors, function(index, error) {
							switch (error.field) {
							case 'mobileNo':
								mobile_no_txt.addClass('validation-error')
								mobile_no_validator.show();
								break;
							case 'referredMobileNo':
								refer_txt.addClass('validation-error')
								refer_validator.show();
								break;
							default: break;
							}
						});
					default: break;
					}
				}
			});
		}
	});
})(jQuery);

// Support functions

/*
 * Validate phone number function
 */
function _validate() {
	var flag = true;
	var mobile_no = mobile_no_txt.inputmask('unmaskedvalue');
	var refer_mobile_no = refer_txt.inputmask('unmaskedvalue');
	
	if (mobile_no && !mobile_no_txt.inputmask('isComplete')) {
		mobile_no_txt.addClass('validation-error')
		//mobile_no_validator.show();
		mobile_no_validator.text($.messages['reg_mobile_invalid_msg']);
		flag = false;
	} else {
		mobile_no_txt.removeClass('validation-error');
		//mobile_no_validator.hide();
		mobile_no_validator.text($.messages['empty']);
	}
	
	if (refer_mobile_no && !refer_txt.inputmask('isComplete')) {
		refer_txt.addClass('validation-error')
		//refer_validator.show();
		refer_validator.text($.messages['reg_refer_mobile_invalid_msg']);
		flag = false;
	} else {
		refer_txt.removeClass('validation-error');
		//refer_validator.hide();
		refer_validator.text($.messages['empty']);
	}
	
	if (flag) {
		if (mobile_no && refer_mobile_no && mobile_no === refer_mobile_no) {
			mobile_no_txt.addClass('validation-error')
			refer_txt.addClass('validation-error')
			//global_validator.show();
			global_validator.text($.messages['reg_refer_invalid_msg']);
			flag = false;
		} else {
			mobile_no_txt.removeClass('validation-error')
			refer_txt.removeClass('validation-error')
			//global_validator.hide();
			global_validator.text($.messages['empty']);
		}
	} else {
		//global_validator.hide();
		global_validator.text($.messages['empty']);
	}
	
	return flag;
};

function _validate_when_submit() {
	var flag = true;
	var mobile_no = mobile_no_txt.inputmask('unmaskedvalue');
	var refer_mobile_no = refer_txt.inputmask('unmaskedvalue');
	
	if (mobile_no && !mobile_no_txt.inputmask('isComplete')) {
		mobile_no_txt.addClass('validation-error')
		//mobile_no_validator.show();
		mobile_no_validator.text($.messages['reg_mobile_invalid_msg']);
		flag = false;
	} else {
		mobile_no_txt.removeClass('validation-error')
		//mobile_no_validator.hide();
		mobile_no_validator.text($.messages['empty']);
	}
	
	if (refer_mobile_no && !refer_txt.inputmask('isComplete')) {
		refer_txt.addClass('validation-error')
		//refer_validator.show();
		refer_validator.text($.messages['reg_refer_mobile_invalid_msg']);
		flag = false;
	} else {
		refer_txt.removeClass('validation-error')
		//refer_validator.hide();
		refer_validator.text($.messages['empty']);
	}
	
	if (flag) {
		if (mobile_no && refer_mobile_no && mobile_no === refer_mobile_no) {
			mobile_no_txt.addClass('validation-error')
			refer_txt.addClass('validation-error')
			//global_validator.show();
			global_validator.text($.messages['reg_refer_invalid_msg']);
			flag = false;
		} else {
			mobile_no_txt.removeClass('validation-error')
			refer_txt.removeClass('validation-error')
			//global_validator.hide();
			global_validator.text($.messages['empty']);
		}
	} else {
		//global_validator.hide();
		global_validator.text($.messages['empty']);
	}
	
	if (flag) {
		if (!mobile_no) {
			mobile_no_txt.addClass('validation-error')
			//mobile_no_validator.show();
			mobile_no_validator.text($.messages['reg_mobile_invalid_msg']);
			flag = false;
		} else {
			mobile_no_txt.removeClass('validation-error')
			//mobile_no_validator.hide();
			mobile_no_validator.text($.messages['empty']);
		}
	}
	
	return flag;
};

function _resetUI() {
	mobile_no_txt.removeClass('validation-error')
	//mobile_no_validator.hide();
	mobile_no_validator.text($.messages['empty']);
	refer_txt.removeClass('validation-error')
	//refer_validator.hide();
	refer_validator.text($.messages['empty']);
	//global_validator.hide();
	global_validator.text($.messages['empty']);
}

/*
 * CSRF
 */
$(function() {
	var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
});