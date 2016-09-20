$(function() {
	var login_validator = $('div.validation-error-label > #global-error');
	var error = _check_url_parameter('error');
	if (error) {
		//login_validator.show();
		login_validator.text($.messages['login_failure_msg']);
	}
});

function _check_url_parameter(s_param) {
    var s_page_URL = decodeURIComponent(window.location.search.substring(1)),
        s_URL_variables = s_page_URL.split('&'), 
        s_parameter_name,
        i;

    for (i = 0; i < s_URL_variables.length; i++) {
    	s_parameter_name = s_URL_variables[i].split('=');

        if (s_parameter_name[0] === s_param) {
            //return s_parameter_name[1] === undefined ? true : s_parameter_name[1];
        	return true;
        }
    }
    
    return false;
};