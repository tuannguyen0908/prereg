$(function() {
	// DataTable setup
    // ------------------------------

    // Setting datatable defaults
    $.extend($.fn.dataTable.defaults, {
        autoWidth: false,
        dom: '<"H"l>t<"F"ip>',
        /*language : {
			decimal : '<spring:message code="datatable.decimal" />',
			emptyTable: '<spring:message code="datatable.empty.table.msg" />',
			info: '<spring:message code="datatable.info.msg" />',
			infoEmpty: '<spring:message code="datatable.info.empty.msg" />',
			infoFiltered: '<spring:message code="datatable.info.filterred.msg" />',
			infoPostFix: '<spring:message code="datatable.info.postfix" />',
			thousands: '<spring:message code="datatable.thousands" />',
			lengthMenu: '<spring:message code="datatable.lenght.menu.msg" />',
			loadingRecords: '<spring:message code="datatable.loading.records.msg" />',
			processing: '<spring:message code="datatable.processing.msg" />',
			search: '<spring:message code="datatable.search.label" />',
			zeroRecords: '<spring:message code="datatable.zero.records.msg" />',
			paginate : {
				first: '<spring:message code="datatable.paginate.first.label" />',
				last: '<spring:message code="datatable.paginate.last.label" />',
				next: '<spring:message code="datatable.paginate.next.label" />',
				previous: '<spring:message code="datatable.paginate.previous.label" />'
			},
			aria : {
				sortAscending : ': activate to sort column ascending',
				sortDescending : ': activate to sort column descending'
			}
		},*/
		initComplete: function(settings) {
			
		},
        drawCallback: function () {
			$('[data-popup=tooltip]').tooltip({
				container: 'body'
			});
        },
        preDrawCallback: function() {
        	$('a[data-popup=tooltip]').tooltip('destroy');
        }
    });
    
    /*
     * CSRF
     */
	var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
});

var tbl;
var currentFilters = [];
var currentSorters = [];
var JSON_URL = $('#json-list-url').val();
var CSV_URL = $('#csv-list-url').val();
$(function() {
	var mobile_no_txt = $("input[name='mobile']");
	var refer_txt = $("input[name='refer']");
	var search_btn = $('.btn-search-custom');
	var from_txt = $("input[name='from']");
	var from_clear = $('#from-clear');
	var to_txt = $("input[name='to']");
	var to_clear = $('#to-clear');
	
	// Setup phone number mask on inputs
	phone_options = {
		mask: '[9][9][9][9][9][9][9][9][9][9]',
		greedy: false
	};
	mobile_no_txt.inputmask(phone_options);
	refer_txt.inputmask(phone_options);
	
	// Setup date picker to search
	from_txt.daterangepicker({
		singleDatePicker: true,
		showDropdowns: true,
		autoUpdateInput: false
	}).on('apply.daterangepicker', function(event, picker) {
		from_txt.val(moment(picker.startDate._d.getTime()).format('MM/DD/YYYY'));
		from_clear.show();
	});
	from_txt.val(null);
	from_clear.hide();
	from_clear.on('click', function(event) {
		event.preventDefault();
		$(event.target).hide();
		from_txt.val(null);
	});
	
	to_txt.daterangepicker({
		singleDatePicker: true,
		showDropdowns: true,
		autoUpdateInput: false
	}).on('apply.daterangepicker', function(event, picker) {
		to_txt.val(moment(picker.startDate._d.getTime()).format('MM/DD/YYYY'));
		to_clear.show();
	});;
	to_txt.val(null);
	to_clear.hide();
	to_clear.on('click', function(event) {
		event.preventDefault();
		$(event.target).hide();
		to_txt.val(null);
	});
	
	// Setup table
	tbl = $('.table').DataTable({
		serverSide: true,
		ajax: function(data, callback, settings) {
			var dataTable = {};
			dataTable.draw = data.draw;
			dataTable.length = data.length;
			dataTable.start = data.start / data.length;
			
			currentSorters = [];
			for (var i = 0; i < data.order.length; ++i) {
				var sortColumn = {};
				sortColumn.column = data.columns[data.order[i].column].name;
				sortColumn.dir = data.order[i].dir;
				currentSorters.push(sortColumn);
			}
			var found = false;
			for (var i = 0; i < data.order.length; ++i) {
				if( data.columns[data.order[i].column].name === 'registeredTime') {
					found = true;
					break;
				};
			}
			if (!found) {
				currentSorters.push({column: 'registeredTime', dir: 'desc'});
			}
			found = false;
			for (var i = 0; i < data.order.length; ++i) {
				if( data.columns[data.order[i].column].name === 'mobileNo') {
					found = true;
					break;
				};
			}
			if (!found) {
				currentSorters.push({column: 'mobileNo', dir: 'desc'});
			}
			
			dataTable.sorts = currentSorters;
			
			currentFilters = [];
			var mobile_no = mobile_no_txt.inputmask('unmaskedvalue');
			var refer_by = refer_txt.inputmask('unmaskedvalue');
			if (mobile_no) {
				currentFilters.push({column: 'mobileNo', value: mobile_no, operator: 5});
			}
			if (refer_by) {
				currentFilters.push({column: 'referredBy', value: refer_by, operator: 5});
			}
			if (from_txt.val()) {
				var from_date = from_txt.data('daterangepicker').startDate._d.getTime();
				currentFilters.push({column: 'registeredTime', value: from_date, operator: 2});
			};
			if (to_txt.val()) {
				var to_date = to_txt.data('daterangepicker').endDate._d.getTime();
				currentFilters.push({column: 'registeredTime', value: to_date, operator: 4});
			};
			dataTable.filters = currentFilters;
			
			$.ajax({
				url : JSON_URL,
				type : 'post',
				contentType: 'application/json; charset=utf-8;',
				data: JSON.stringify(dataTable),
				success : function(jsonResponse) {
					callback({
						draw: jsonResponse.draw,
		                recordsTotal: jsonResponse.recordsTotal,
		                recordsFiltered: jsonResponse.recordsFiltered,
		                data: jsonResponse.data
		            });
				},
				error : function() {
					callback({
						draw: data.draw,
		                recordsTotal: 0,
		                recordsFiltered: 0,
		                data: []
		            });
				}
			});
		},
		columns: [{
			data: 'id',
			name: 'id',
			render: function(id, type, row) {
				return '<span></span>';
			},
			sortable: false,
			visible: false
		}, {
			data: 'mobileNo',
			name: 'mobileNo',
			render: function(mobileNo, type, row) {
				return ['<span>',
				        	mobileNo.substr(0, 3), ' ', mobileNo.substring(3, mobileNo.length),
				        '</span>'].join('');
			}
		}, {
			data: 'referredBy',
			name: 'referredBy',
			render: function(referredBy, type, row) {
				if (referredBy) {
					return ['<span>',
					        referredBy.substr(0, 3), ' ', referredBy.substring(3, referredBy.length),
					        '</span>'].join('');
				};
				return '<span></span>';
				
			}
		}, {
			data: 'numberOfRefer',
			name: 'numberOfRefer'
		}, {
			data: 'registeredTime',
			name: 'registeredTime',
			render: function(registerd_time, type, row) {
				return ['<span>',,
				        	moment(registerd_time).format('MM/DD/YYYY HH:mm:ss'),
				        '</span>'].join('');
			}
		}],
		order: [],
		lengthMenu: [10, 50, 100, 1000, 5000],
        drawCallback: function () {
			$('[data-popup=tooltip]').tooltip({
				container: 'body'
			});
			
			$('<span class="dataTable_additionInfo"> in total of <span class="dataTable_recordsTotal">' + tbl.page.info().recordsTotal + '</span> entries found </span>').appendTo('.dataTables_length > label');
        },
        preDrawCallback: function() {
        	$('a[data-popup=tooltip]').tooltip('destroy');
        	
        	$('.dataTable_additionInfo').remove();
        }
	});
	
	// Setup search button
	search_btn.on('click', function(event) {
		tbl.ajax.reload();
	})
});

function _download(event) {
	event.preventDefault();
	
	var inputTags = [];
	inputTags.push('<input type="hidden" name="draw" value="0" />');
	inputTags.push('<input type="hidden" name="start" value="0" />');
	inputTags.push('<input type="hidden" name="length" value="4294" />');
	$.each(currentSorters, function(index, value) {
		inputTags.push('<input type="hidden" name="sorts[' + index + '].column" value="' + value.column + '" />');
		inputTags.push('<input type="hidden" name="sorts[' + index + '].dir" value="' + value.dir + '" />');
	});
	$.each(currentFilters, function(index, value) {
		inputTags.push('<input type="hidden" name="filters[' + index + '].column" value="' + value.column + '" />');
		inputTags.push('<input type="hidden" name="filters[' + index + '].value" value="' + value.value + '" />');
		inputTags.push('<input type="hidden" name="filters[' + index + '].operator" value="' + value.operator + '" />');
	});
	
	var crsfToken = $("meta[name='_csrf']").attr("content");
	jQuery('<form action="' + CSV_URL +'?' + '_csrf' + '=' + crsfToken + '" method="get">' + inputTags.join('') + '</form>')
		.appendTo('body').submit().remove();
}/*

function _JSONToCSVConvertor(JSONData, fields, file_name, header) {
	var data = typeof JSONData != 'object' ? JSON.parse(JSONData) : JSONData;

	var CSV = '';
	var head = '';
	for (var i = 0; i < header.length; ++i) {
		head += header[i];
		head += ',';
	}
	head = head.slice(0, -1);

	CSV += head;
	CSV += '\r\n';
	for (var i = 0; i < data.length; i++) {
		var row = '';
		for ( var index in data[i]) {
			if ($.inArray(index, fields) !== -1) {
				switch (index) {
				case 'mobileNo':
					row += '\'' + data[i][index] + '\',';
					break;
				case 'registeredTime':
					row += '\''
							+ moment(data[i][index]).format(
									'MM/DD/YYYY HH:mm:ss') + '\',';
					break;
				case 'referredBy':
					row += '\'' + data[i][index] + '\',';
					break;
				case 'numberOfRefer':
					row += data[i][index] + ',';
					break;
				}
			}
		}

		row.slice(0, row.length - 1);

		CSV += row;
		CSV += '\r\n';
	}

	if (!CSV) {
		alert();
		return;
	}

	var uri = 'data:text/csv;charset=utf-8,' + escape(CSV);

	var link = document.createElement("a");
	link.href = uri;

	link.style = "visibility:hidden";
	link.download = file_name + ".csv";

	document.body.appendChild(link);
	link.click();
	document.body.removeChild(link);
}*/