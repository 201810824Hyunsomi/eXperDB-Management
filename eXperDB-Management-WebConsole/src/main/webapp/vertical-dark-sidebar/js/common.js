(function($) {
	/* 알람 호출*/
	showToastPosition = function(position, msg, titleMsg) {
		'use strict';
		resetToastPosition();
		$.toast({
			heading: titleMsg,
			text: msg,
			position: String(position),
			icon: 'info',
			stack: false,
			loaderBg: '#f96868'
		})
	}
	
	/* 경고 호출*/
	showDangerToast = function(position, msg, titleMsg) {
		'use strict';
		resetToastPosition();
		$.toast({
			heading: titleMsg,
			text: msg,
			showHideTransition: 'slide',
			position: String(position),
			icon: 'error',
			loaderBg: '#f2a654'
		})
	}

	showToastInCustomPosition_login = function() {
		'use strict';
		resetToastPosition();
		$.toast({
			heading: 'Custom positioning',
			text: 'Specify the custom position object or use one of the predefined ones',
			icon: 'info',
			position: {
				left: 120,
				top: 120
			},
			stack: false,
			loaderBg: '#f96868'
		})
	}

	resetToastPosition = function() {
		$('.jq-toast-wrap').removeClass('bottom-left bottom-right top-left top-right mid-center'); // to remove previous position class
		$(".jq-toast-wrap").css({
			"top": "",
			"left": "",
			"bottom": "",
			"right": ""
		}); //to remove previous position style
	}
	
	//alert 기본 js
	showSwal = function(msg, btnText) {
		'use strict';
		swal({
			text: msg,
			button: {
				text: btnText,
				value: true,
				visible: true,
				className: "btn btn-primary"
			}
		})
	}
	
	//title 있는 alert
	showSwalTtl = function(msg, btnText, titleText) {
		'use strict';
		swal({
			title: titleText,
			text: msg,
			button: {
				text: btnText,
				value: true,
				visible: true,
				className: "btn btn-primary"
			}
		})
	}
	
	//alert icon 추가 관련 js
	showSwalIcon = function(msg, btnText, titleText, iconText) {
		'use strict';
		swal({
			title: titleText,
			text: msg,
			icon: iconText,
			button: {
				text: btnText,
				value: true,
				visible: true,
				className: "btn btn-primary"
			}
		})
	}

	//alert icon 추가 관련 js -- 결과가 있는 alert
	showSwalIconRst = function(msg, btnText, titleText, iconText, rstGbn) {
		'use strict';
		swal({
			title: titleText,
			text: msg,
			icon: iconText,
			button: {
				text: btnText,
				value: true,
				visible: true,
				className: "btn btn-primary"
			}
		}).then(function(){
			if (rstGbn == "his") {
				history.go(-1);
			} else if (rstGbn == "top") {
				top.location.href = "/";
			} else if (rstGbn == "reload") {
				location.reload();
			}
        });
	}

	//confirm 관련 js
	showSwalCfm = function(msg, cBtnText, conBtnText, titleText, iconText) {
		'use strict';
		swal({
			title: titleText,
			text: msg,
			icon: iconText,
			showCancelButton: true,
			confirmButtonColor: '#3f51b5',
			cancelButtonColor: '#ff4081',
			confirmButtonText: 'Great ',
			buttons: {
				cancel: {
					text: cBtnText,
					value: false,
					visible: true,
					className: "btn btn-danger",
					closeModal: true,
				},
				confirm: {
					text: conBtnText,
					value: true,
					visible: true,
					className: "btn btn-primary",
					closeModal: true
				}
			}
		})
	}
	
	// initializing inputmask
	$(":input").inputmask();

})(jQuery);

$(window).ready(function(){
	var htmlLoad = '<div id="loading"><div class="flip-square-loader mx-auto" style="border: 0px !important;z-index:999;"></div></div>';
	
	$("#contentsDiv").append(htmlLoad);
	$('#loading').hide();
	
	$( document ).ajaxStart(function() {	
	      $('#loading').css('position', 'absolute');
	      $('#loading').css('left', '50%');
	      $('#loading').css('top', '50%');
	      $('#loading').css('transform', 'translate(-50%,-50%)');	  
	      $('#loading').show();	
	});
	
	//AJAX 통신 종료
	$( document ).ajaxStop(function() {
		$('#loading').hide();
	});
});

//About eXperDB
function fn_aboutExperdb(version){
	$("#version").html(version);
}


/* 로그아웃 */
function fn_logout(){
	sessionStorage.removeItem('cssId');

	var frm = document.treeView;
	frm.action = "/logout.do";
	frm.submit();
}

/* cookie 저장 */
function fn_cookie(url) {
	var cssID = sessionStorage.getItem('cssId');

/* 		$("#"+cssID).css("background-color","");
	$("#"+cssID+"c").css("color","");
	$("#"+cssID).css("border","");	

	if(url != null){
		$("#"+url).css("background-color","#f58220");
		$("#"+url+"c").css("color","white");
		$("#"+url).css("border","2px solid #f58220");	
	} */

	sessionStorage.setItem('cssId',url);
}

/* null 값 변경 */
function nvlPrmSet(val, subVal) {
	var strValue = val;
	if( strValue == null || strValue == '') {
		strValue = subVal;
	}
	
	return strValue;
}

//profile chk
function fn_profileChk(id) {
	if ($("#" + id).hasClass("menu-arrow_user")) {
		$("#" + id).attr('class', 'menu-arrow_user_af');
	} else {
		$("#" + id).attr('class', 'menu-arrow_user');
	}
}

//글자수 체크
function fn_checkWord(obj, maxlength) { 
	var str = obj.value; 
	var str_length = str.length;     
	var max_length = maxlength;  
	if (str_length == max_length) { 
		showSwalIcon(max_length+message_msg211, closeBtn, '', 'error');
	}    
	obj.focus(); 
}

//숫자 체크
function chk_Number(object){
	$(object).keyup(function(){
		$(this).val($(this).val().replace(/[^0-9]/g,""));
	});   
}

//작업 로그정보 출력
function fn_fixLog(exe_sn){
	$.ajax({
		url : "/selectFixRsltMsg.do",
		data : {
			exe_sn : exe_sn
		},
		dataType : "json",
		type : "post",
		beforeSend: function(xhr) {
	        xhr.setRequestHeader("AJAX", true);
	     },
		error : function(xhr, status, error) {
			if(xhr.status == 401) {
				showSwalIconRst(message_msg02, closeBtn, '', 'error', 'top');
			} else if(xhr.status == 403) {
				showSwalIconRst(message_msg03, closeBtn, '', 'error', 'top');
			} else {
				showSwalIcon("ERROR CODE : "+ xhr.status+ "\n\n"+ "ERROR Message : "+ error+ "\n\n"+ "Error Detail : "+ xhr.responseText.replace(/(<([^>]+)>)/gi, ""), closeBtn, '', 'error');
			}
		},
		success : function(result) {
			$("#exe_sn").val(result[0].exe_sn);
			
			var fix_rsltcd_val = "";
			
			if (result[0].fix_rsltcd != null) {
				fix_rsltcd_val = result[0].fix_rsltcd;
			} else {
				fix_rsltcd_val = "TC002001";
			}
			
			$('input:radio[name=rdo]:input[value=' + fix_rsltcd_val + ']').attr("checked", true);

			
			if (result[0].fix_rslt_msg != null) {
				$("#fix_rslt_msg").html(result[0].fix_rslt_msg);
			} else {
				$("#fix_rslt_msg").html("");
			}

			$("#lst_mdfr_id").val(result[0].lst_mdfr_id);
			$("#lst_mdf_dtm").val(result[0].lst_mdf_dtm);
				
			$("#pop_layer_fix_rslt_msg").modal("show");					
		}
	});	
}

//ERROR 로그 정보 출력
function fn_failLog(exe_sn){
	$.ajax({
		url : "/selectWrkErrorMsg.do",
		data : {
			exe_sn : exe_sn
		},
		dataType : "json",
		type : "post",
		beforeSend: function(xhr) {
	        xhr.setRequestHeader("AJAX", true);
	     },
		error : function(xhr, status, error) {
			if(xhr.status == 401) {
				showSwalIconRst(message_msg02, closeBtn, '', 'error', 'top');
			} else if(xhr.status == 403) {
				showSwalIconRst(message_msg03, closeBtn, '', 'error', 'top');
			} else {
				showSwalIcon("ERROR CODE : "+ xhr.status+ "\n\n"+ "ERROR Message : "+ error+ "\n\n"+ "Error Detail : "+ xhr.responseText.replace(/(<([^>]+)>)/gi, ""), closeBtn, '', 'error');
			}
		},
		success : function(result) {
			$("#wrkLogInfo").html(result[0].rslt_msg);

			$("#pop_layer_wrkLog").modal("show");						
		}
	});	
}

//ScriptWORK정보
function fn_scriptLayer(wrk_id){
	$.ajax({
		url : "/selectSciptExeInfo.do",
		data : {
			wrk_id : wrk_id
		},
		dataType : "json",
		type : "post",
		beforeSend: function(xhr) {
	        xhr.setRequestHeader("AJAX", true);
	    },
	    error : function(xhr, status, error) {
			if(xhr.status == 401) {
				showSwalIconRst(message_msg02, closeBtn, '', 'error', 'top');
			} else if(xhr.status == 403) {
				showSwalIconRst(message_msg03, closeBtn, '', 'error', 'top');
			} else {
				showSwalIcon("ERROR CODE : "+ xhr.status+ "\n\n"+ "ERROR Message : "+ error+ "\n\n"+ "Error Detail : "+ xhr.responseText.replace(/(<([^>]+)>)/gi, ""), closeBtn, '', 'error');
			}
		},
		success : function(result) {
			if(result.length==0){
				showSwalIcon(wrk_chk_del_msg, closeBtn, '', 'error');
			}else{
				$("#info_exe_cmd").html(nvlPrmSet(result[0].exe_cmd, ""));

				$("#pop_layer_script").modal("show");
			}
		}
	});	
}

//WORK정보
function fn_workLayer(wrk_id, contentsGbn){
	$.ajax({
		url : "/selectWrkInfo.do",
		data : {
			wrk_id : wrk_id
		},
		dataType : "json",
		type : "post",
		beforeSend: function(xhr) {
	        xhr.setRequestHeader("AJAX", true);
	    },
	    error : function(xhr, status, error) {
			if(xhr.status == 401) {
				showSwalIconRst(message_msg02, closeBtn, '', 'error', 'top');
			} else if(xhr.status == 403) {
				showSwalIconRst(message_msg03, closeBtn, '', 'error', 'top');
			} else {
				showSwalIcon("ERROR CODE : "+ xhr.status+ "\n\n"+ "ERROR Message : "+ error+ "\n\n"+ "Error Detail : "+ xhr.responseText.replace(/(<([^>]+)>)/gi, ""), closeBtn, '', 'error');
			}
		},
		success : function(result) {
			if(result.length==0){
				showSwalIcon(wrk_chk_del_msg, closeBtn, '', 'error');
			}else{				
				if(result[0].bsn_dscd == "TC001901"){
					// RMAN
					if(result[0].bck_bsn_dscd == "TC000201"){
						$("#r_bck_bsn_dscd_nm").html(result[0].bck_bsn_dscd_nm);
						$("#bck_opt_cd_nm").html(result[0].bck_opt_cd_nm);
						$("#r_wrk_nm").html(result[0].wrk_nm);
						$("#r_wrk_exp").html(result[0].wrk_exp);
						if(result[0].cps_yn == "N"){
							$("#cps_yn").html(agent_monitoring_no);
						}else{
							$("#cps_yn").html(agent_monitoring_yes);
						}
						//$("#cps_yn").html(result[0].cps_yn);
						$("#log_file_pth").html(result[0].log_file_pth);
						$("#data_pth").html(result[0].data_pth);
						$("#bck_pth").html(result[0].bck_pth);
						$("#r_file_stg_dcnt").html(result[0].file_stg_dcnt);
						$("#r_bck_mtn_ecnt").html(result[0].bck_mtn_ecnt);
						$("#acv_file_stgdt").html(result[0].acv_file_stgdt);
						$("#acv_file_mtncnt").html(result[0].acv_file_mtncnt);
						if(result[0].log_file_bck_yn == "N"){
							$("#log_file_bck_yn").html(agent_monitoring_no);
						}else{
							$("#log_file_bck_yn").html(agent_monitoring_yes);
						}
						//$("#log_file_bck_yn").html(result[0].log_file_bck_yn);
						$("#r_log_file_stg_dcnt").html(result[0].log_file_stg_dcnt);
						$("#r_log_file_mtn_ecnt").html(result[0].log_file_mtn_ecnt);

						$("#pop_layer_rman").modal("show");
					// DUMP
					}else{
						$("#d_bck_bsn_dscd_nm").html(result[0].bck_bsn_dscd_nm);
						$("#d_wrk_nm").html(result[0].wrk_nm);
						$("#d_wrk_exp").html(result[0].wrk_exp);
						$("#db_nm").html(result[0].db_nm);
						$("#save_pth").html(result[0].save_pth);
						$("#file_fmt_cd_nm").html(result[0].file_fmt_cd_nm);
						$("#cprt").html(result[0].cprt);
						$("#encd_mth_nm").html(result[0].encd_mth_nm);
						$("#usr_role_nm").html(result[0].usr_role_nm);
						$("#d_file_stg_dcnt").html(result[0].file_stg_dcnt);
						$("#d_bck_mtn_ecnt").html(result[0].bck_mtn_ecnt);
						
						fn_workOptionLayer(result[0].bck_wrk_id, result[0].db_svr_id, result[0].db_nm);
	
						$("#pop_layer_dump").modal("show");
					}
				}else if(result[0].bsn_dscd == "TC001902"){
					fn_scriptLayerWork(result[0].wrk_id, contentsGbn);
				}
			}
		}
	});	
}


//ScriptWORK정보
function fn_scriptLayerWork(wrk_id, contentsGbn){
	$.ajax({
		url : "/selectSciptExeInfo.do",
		data : {
			wrk_id : wrk_id
		},
		dataType : "json",
		type : "post",
		beforeSend: function(xhr) {
	        xhr.setRequestHeader("AJAX", true);
	    },
	    error : function(xhr, status, error) {
			if(xhr.status == 401) {
				showSwalIconRst(message_msg02, closeBtn, '', 'error', 'top');
			} else if(xhr.status == 403) {
				showSwalIconRst(message_msg03, closeBtn, '', 'error', 'top');
			} else {
				showSwalIcon("ERROR CODE : "+ xhr.status+ "\n\n"+ "ERROR Message : "+ error+ "\n\n"+ "Error Detail : "+ xhr.responseText.replace(/(<([^>]+)>)/gi, ""), closeBtn, '', 'error');
			}
		},
		success : function(result) {
			if(result.length==0){
				showSwalIcon(wrk_chk_del_msg, closeBtn, '', 'error');
			}else{
				$("#info_exe_cmd", "#rsltMsgWorkForm").html(nvlPrmSet(result[0].exe_cmd, ""));

				$("#contents_gbn", "#rsltMsgWorkForm").val(contentsGbn);

				$("#"+ contentsGbn).modal("hide");

				$("#pop_layer_script_work").modal("show");
			}
		}
	});	
}

//스케줄정보
function fn_scdLayer(scd_id){
	$.ajax({
		url : "/selectScdInfo.do",
		data : {
			scd_id : scd_id
		},
		dataType : "json",
		type : "post",
		error : function(xhr, status, error) {
			if(xhr.status == 401) {
				showSwalIconRst(message_msg02, closeBtn, '', 'error', 'top');
			} else if(xhr.status == 403) {
				showSwalIconRst(message_msg03, closeBtn, '', 'error', 'top');
			} else {
				showSwalIcon("ERROR CODE : "+ xhr.status+ "\n\n"+ "ERROR Message : "+ error+ "\n\n"+ "Error Detail : "+ xhr.responseText.replace(/(<([^>]+)>)/gi, ""), closeBtn, '', 'error');
			}
		},
		beforeSend: function(xhr) {
	        xhr.setRequestHeader("AJAX", true);
	     },
		success : function(result) {
			if(result.length==0){
				showSwalIcon(message_msg209, closeBtn, '', 'error');
			}else{
				var hms = "";
				
				hms += result[0].exe_hms.substring(4,6)+schedule_our;	
				hms += result[0].exe_hms.substring(2,4)+schedule_minute;	
				hms += result[0].exe_hms.substring(0,2)+schedule_second;		
				
				var day = "";
				if(result[0].exe_perd_cd == "TC001602"){
					day += "(";
					if(result[0].exe_dt.substring(0,1)=="1"){
						day += schedule_sunday+", ";
					}
					if(result[0].exe_dt.substring(1,2)=="1"){
						day += schedule_monday+", ";
					}
					if(result[0].exe_dt.substring(2,3)=="1"){
						day += schedule_thuesday+", ";
					}
					if(result[0].exe_dt.substring(3,4)=="1"){
						day += schedule_wednesday+", ";
					}
					if(result[0].exe_dt.substring(4,5)=="1"){
						day += schedule_thursday+", ";
					}
					if(result[0].exe_dt.substring(5,6)=="1"){
						day += schedule_friday+", ";
					}
					if(result[0].exe_dt.substring(6,7)=="1"){
						day += schedule_saturday;
					}
					day += ")";
				}		

				$("#d_scd_nm_info").html(result[0].scd_nm);
				$("#d_scd_exp_info").html(result[0].scd_exp);

				var scd_cndt_html = "";
				
				if (result[0].scd_cndt == "TC001801") {
					scd_cndt_html += "<div class='badge badge-pill badge-success'>";
					scd_cndt_html += "	<i class='fa fa-minus-circle mr-2'></i>";
					scd_cndt_html += common_waiting;
					scd_cndt_html += "</div>";
				} else if (result[0].scd_cndt == "TC001801") {
					scd_cndt_html += "<div class='badge badge-pill badge-warning'>";
					scd_cndt_html += "	<i class='fa fa-spin fa-spinner mr-2'></i>";
					scd_cndt_html += dashboard_running;
					scd_cndt_html += "</div>";
				} else {
					scd_cndt_html += "<div class='badge badge-pill badge-danger'>";
					scd_cndt_html += "	<i class='ti-close mr-2'></i>";
					scd_cndt_html += schedule_stop;
					scd_cndt_html += "</div>";
				}
				$("#d_scd_cndt_info").html(scd_cndt_html);

				$("#d_exe_perd_cd_info").html(result[0].exe_perd_cd_nm + " " + day);
				$("#d_scd_exe_hms").html(hms);

				$("#pop_layer_scheduleInfo").modal("show");
			}
			
		}
	});
	
}


//br 변환
function fn_strBrReplcae(msg) {
	msg = msg.replace("<br/>", "\n");
	
	return msg;
}