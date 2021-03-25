<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
	/**
	* @Class Name : proxyListenRegForm.jsp
	* @Description : proxyListenRegForm 화면
	* @Modification Information
	*
	*   수정일         		수정자                   	수정내용
	*  ----------	-------		--------    ---------------------------
	*  2021.03.18	김민정  		최초 생성
	*
	* author 김민정
	* since 2021.03.18
	*
	*/
%>

<script type="text/javascript">
var serverListTable = null;
/* ********************************************************
 * Listener Server List 초기화
 ******************************************************** */
function fn_serverListTable_init() {
	
	 serverListTable = $('#serverList').DataTable({
		scrollY : "100px",
		bSort: false,
		scrollX: true,	
		searching : false,
		paging : false,
		deferRender : true,
		columns : [	{data : "db_con_addr", className : "dt-center", defaultContent : "",
					render : function(data, type, full, meta) {
							if(full.lsn_svr_id == ""){
								var html = '<input type="text" class="form-control form-control-xsm" maxlength="30"';
								html +='id="db_con_addr_'+meta.row+'" value="'+full.db_con_addr+'" name="db_con_addr_'+meta.row+'" onkeyup="fn_edit_serverList('+meta.row+',\'db_con_addr\');fn_checkWord(this,21);" onblur="this.value=this.value.trim()" placeholder="DBMS접속 IP : Port 형식으로 입력하세요." />';
								return html;
							}else{
								return full.db_con_addr;
							}
						}
						
					},
					{data : "chk_portno", className : "dt-center",  defaultContent : "",
						render : function(data, type, full, meta) {
							var html = '<input type="number" class="form-control form-control-xsm" maxlength="30"';
							html +='id="chk_portno_'+meta.row+'" value="'+full.chk_portno+'" name="chk_portno_'+meta.row+'" onkeyup="fn_edit_serverList('+meta.row+',\'chk_portno\');fn_checkWord(this,5);" onblur="this.value=this.value.trim()" placeholder="5자리까지 입력 가능합니다." />';
							return html;
						}
					},
					{data : "backup_yn", defaultContent : "",
						render : function(data, type, full, meta) {
							var html = "";
							html += '<div class="onoffswitch-pop">';
							if(full.backup_yn == "Y"){
								html += '<input type="checkbox" class="onoffswitch-pop-checkbox" id="backup_yn_'+ meta.row +'" onclick="fn_backupYn_click(\''+ meta.row +'\')" checked>';
							}else{
								html += '<input type="checkbox" class="onoffswitch-pop-checkbox" id="backup_yn_'+ meta.row +'" onclick="fn_backupYn_click(\''+ meta.row +'\')">';
							}
							html += '<label class="onoffswitch-pop-label" for="backup_yn_'+ meta.row +'">';
							html += '<span class="onoffswitch-pop-inner_YN"></span>';
							html += '<span class="onoffswitch-pop-switch"></span></label>';
							html += '</div>';

							return html;	
						}
					},
					{data : "lsn_svr_id", className : "dt-center", defaultContent : "", visible: false},
					{data : "pry_svr_id", className : "dt-center", defaultContent : "", visible: false},	
					{data : "lsn_id", className : "dt-center", defaultContent : "", visible: false},
					{data : "edit_yn", className : "dt-center", defaultContent : "N", visible: false},
		]
	});

	serverListTable.tables().header().to$().find('th:eq(0)').css('min-width', '200px');
	serverListTable.tables().header().to$().find('th:eq(1)').css('min-width', '100px');
	serverListTable.tables().header().to$().find('th:eq(2)').css('min-width', '100px');
	serverListTable.tables().header().to$().find('th:eq(3)').css('min-width', '0px');
	serverListTable.tables().header().to$().find('th:eq(4)').css('min-width', '0px');
	serverListTable.tables().header().to$().find('th:eq(5)').css('min-width', '0px');

	$('#serverList tbody').on('click','tr',function() {
		if ( !$(this).hasClass('selected') ){	        	
			serverListTable.$('tr.selected').removeClass('selected');
	           $(this).addClass('selected');	            
		} 
	});	
}

	$(window.document).ready(function() {
	
		fn_serverListTable_init();
		
		$.validator.addMethod("validatorIpFormat3", function (str, element, param) {
			var ipformat = /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
		    if (ipformat.test(str) || str.indexOf("*") >= 0) {
		        return true;
		    }
		    return false;
		});
		
		$.validator.addMethod("validatorIpPortFormat", function (str, element, param) {
			var ip = str.substring(0,str.indexOf(":"));
			var port = str.substring(str.indexOf(":")+1);
			var ipformat = /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
		    if (ipformat.test(ip) && (port >= 0 && port < 65536)) {
		        return true;
		    }
		    return false;
		});
		
		$.validator.addMethod("validatorPort", function (port, element, param) {
			if ((port >= 0 && port < 65536)) {
		        return true;
		    }
		    return false;
		});
		
		$("#insProxyListenForm").validate({
	        rules: {
	        	lstnReg_lsn_nm: {
					required:true
				},
				lstnReg_con_bind_ip: {
					required: true,
					validatorIpFormat3 :true
				},
				lstnReg_con_bind_port: {
					required: true
				},
				lstnReg_lsn_desc: {
					required:true
				},
				lstnReg_db_usr_id: {
					required: true
				},
				lstnReg_db_nm: {
					required: true
				},
				lstnReg_con_sim_query: {
					required: true
				},
				lstnReg_field_nm: {
					required: true
				},
				lstnReg_field_val: {
					required: true
				}
	        },
	        messages: {
	        	lstnReg_lsn_nm: {
					required: '<spring:message code="eXperDB_proxy.msg2"/>'
				},
	        	lstnReg_con_bind_ip: {
					required: '<spring:message code="eXperDB_proxy.msg2"/>',
					validatorIpFormat3 : '<spring:message code="errors.format" arguments="'+ 'IP주소' +'"/>'
				},
				lstnReg_con_bind_port: {
					required: '<spring:message code="eXperDB_proxy.msg2"/>'
				},
				lstnReg_lsn_desc: {
					required: '<spring:message code="eXperDB_proxy.msg2"/>'
				},
				lstnReg_db_usr_id: {
					required: '<spring:message code="eXperDB_proxy.msg2"/>'
				},
				lstnReg_db_nm: {
					required: '<spring:message code="eXperDB_proxy.msg2"/>'
				},
				lstnReg_con_sim_query: {
					required: '<spring:message code="eXperDB_proxy.msg2"/>'
				},
				lstnReg_field_nm: {
					required: '<spring:message code="eXperDB_proxy.msg2"/>'
				},
				lstnReg_field_val: {
					required: '<spring:message code="eXperDB_proxy.msg2"/>'
				}
	        },
			submitHandler: function(form) { //모든 항목이 통과되면 호출됨 ★showError 와 함께 쓰면 실행하지않는다★
				var dataLen = serverListTable.rows().data().length;
				if(dataLen == 0){
					showSwalIcon('최소 한개 이상의 DBMS를 등록해주세요.', '<spring:message code="common.close" />', '', 'error');
					return;	
				}else{
					if($("#lstnReg_mode", "#insProxyListenForm").val() == "reg"){
						lstnReg_add_listener();
					}else{
						lnstReg_mod_listener();
					}
				}
			},
	        errorPlacement: function(label, element) {
	          label.addClass('mt-2 text-danger');
	          label.insertAfter(element);
	        },
	        highlight: function(element, errorClass) {
	          $(element).parent().addClass('has-danger');
	          $(element).addClass('form-control-danger');
	        }
		});
		
	});
/* ********************************************************
 * Proxy Server의  연결 DBMS List 검색
******************************************************** */		
	function fn_listener_svr_list_search(){
		$.ajax({
			url : "/selectListenServerList.do",
			data : {
				pry_svr_id : parseInt($("#lstnReg_pry_svr_id", "#insProxyListenForm").val()),
				lsn_id : parseInt($("#lstnReg_lsn_id", "#insProxyListenForm").val())
			},
			dataType : "json",
			type : "post",
			beforeSend: function(xhr) {
				xhr.setRequestHeader("AJAX", true);
			},
			error : function(xhr, status, error) {
				if(xhr.status == 401) {
					showSwalIconRst('<spring:message code="message.msg02" />', '<spring:message code="common.close" />', '', 'error', 'top');
				} else if(xhr.status == 403) {
					showSwalIconRst('<spring:message code="message.msg03" />', '<spring:message code="common.close" />', '', 'error', 'top');
				} else {
					showSwalIcon("ERROR CODE : "+ xhr.status+ "\n\n"+ "ERROR Message : "+ error+ "\n\n"+ "Error Detail : "+ xhr.responseText.replace(/(<([^>]+)>)/gi, ""), '<spring:message code="common.close" />', '', 'error');
				}
			},
			success : function(result) {
				serverListTable.rows({selected: true}).deselect();
				serverListTable.clear().draw();
			
				if (result != null) {
					serverListTable.rows.add(result).draw();
				}
			}
		});
	}
	/* ********************************************************
	 * Listener 추가
	 ******************************************************** */
	function lstnReg_add_listener(){
		//입력받은 데이터를 Table에 저장하지 않고,DataTable에만 입력 
		showSwalIcon('상단의 [설정 적용]을 실행해야 변경 사항에 대해 저장/적용 됩니다.', '<spring:message code="common.close" />', '', 'success');
		$("#modYn").val("Y");
		proxyListenTable.row.add({
			"lsn_nm" : $("#lstnReg_lsn_nm", "#insProxyListenForm").val(),
			"con_bind_port" : $("#lstnReg_con_bind_ip", "#insProxyListenForm").val()+":"+$("#lstnReg_con_bind_port", "#insProxyListenForm").val(),
			"lsn_desc" : $("#lstnReg_lsn_desc", "#insProxyListenForm").val(),
			"db_usr_id" : parseInt($("#lstnReg_db_usr_id", "#insProxyListenForm").val()),
			"db_id" : parseInt($("#lstnReg_db_id", "#insProxyListenForm").val()),
			"db_nm" : $("#lstnReg_db_nm", "#insProxyListenForm").val(),
			"con_sim_query" : $("#lstnReg_con_sim_query", "#insProxyListenForm").val(),
			"field_val" : $("#lstnReg_field_val", "#insProxyListenForm").val(),
			"field_nm" : $("#lstnReg_field_nm", "#insProxyListenForm").val(),
			"pry_svr_id" : parseInt($("#lstnReg_pry_svr_id", "#insProxyListenForm").val()),
			"lsn_id" : parseInt($("#lstnReg_lsn_id", "#insProxyListenForm").val())
		}).draw();
		$('#pop_layer_proxy_listen_reg').modal("hide");
	}
	/* ********************************************************
	 * Listener 수정
	 ******************************************************** */
	function lnstReg_mod_listener(){
		//입력받은 데이터를 Table에 저장하지 않고,DataTable에만 입력 
		showSwalIcon('상단의 [설정 적용]을 실행해야 변경 사항에 대해 저장/적용 됩니다.', '<spring:message code="common.close" />', '', 'success');
		$("#modYn").val("Y");
		
		//수정된 내용 반영
		var dataLen = proxyListenTable.rows().data().length;
		var oriData = proxyListenTable.rows().data();
		for(var i=0; i<dataLen; i++){
			if(oriData[i].lsn_id ==  $("#lstnReg_lsn_id", "#insProxyListenForm").val()){
				oriData[i].con_bind_port = $("#lstnReg_con_bind_ip", "#insProxyListenForm").val()+":"+$("#lstnReg_con_bind_port", "#insProxyListenForm").val();
				oriData[i].lsn_desc = $("#lstnReg_lsn_desc", "#insProxyListenForm").val();
				oriData[i].db_usr_id = $("#lstnReg_db_usr_id", "#insProxyListenForm").val();
				oriData[i].db_nm = $("#lstnReg_db_nm", "#insProxyListenForm").val();
				oriData[i].db_id = parseInt($("#lstnReg_db_id", "#insProxyListenForm").val());
				oriData[i].field_val = $("#lstnReg_field_val", "#insProxyListenForm").val();
				oriData[i].field_nm = $("#lstnReg_field_nm", "#insProxyListenForm").val();
				oriData[i].con_sim_query = $("#lstnReg_con_sim_query", "#insProxyListenForm").val();
				var svrListLen = serverListTable.rows().data().length;
				var svrListDatas = new Array();
				for(var j =0; j<svrListLen ; j++){
					if(serverListTable.row(j).data().edit_yn=="Y"){
						svrListDatas[svrListDatas.length] = serverListTable.row(j).data();
					}
				}
				oriData[i].lsn_svr_edit_list = svrListDatas; 
				oriData[i].lsn_svr_del_list = delListnerSvrRows;
				
				var tempData = proxyListenTable.rows().data();
				proxyListenTable.clear().draw();
				proxyListenTable.rows.add(tempData).draw();
				delListnerSvrRows = new Array();
			}
		}
		$('#pop_layer_proxy_listen_reg').modal("hide");
	}
	/* ********************************************************
	 * Listener Server List 값 변경 event
	 ******************************************************** */
	function fn_edit_serverList(index,id){
		$("#modYn").val("Y");
		
		$("#db_con_addr_"+index).rules( "add", {
			required: true,
			validatorIpPortFormat: true,
			messages: {
				required: '<spring:message code="eXperDB_proxy.msg2"/>',
				validatorIpPortFormat: '<spring:message code="errors.format" arguments="'+ 'IP주소 : port' +'"/>'
			}
		});
		
		$("#chk_portno_"+index).rules( "add", {
			required: true,
			validatorPort: true,
			messages: {
				required: '<spring:message code="eXperDB_proxy.msg2"/>',
				validatorPort: '0~65535 사이의 숫자를 입력해야합니다.'
			}
		});
		
		if(id == "db_con_addr"){
			serverListTable.row(index).data().db_con_addr = $("#db_con_addr_"+index).val();
		}else if(id == "chk_portno"){
			serverListTable.row(index).data().chk_portno = parseInt($("#chk_portno_"+index).val());
		}
		serverListTable.row(index).data().edit_yn = "Y";
	}
	/* ********************************************************
	 * Listener Server List 추가
	 ******************************************************** */
	function fn_add_server_list(){
		serverListTable.row.add({
			"backup_yn" : "N",
			"chk_portno" : "",
			"db_con_addr" : "",
			"lsn_svr_id" : "",
			"lsn_id" : parseInt($("#lstnReg_lsn_id", "#insProxyListenForm").val()),
			"pry_svr_id" : parseInt($("#lstnReg_pry_svr_id", "#insProxyListenForm").val()),
			"edit_yn" : "N"
		}).draw();
		
		var tempData =serverListTable.rows().data();
		serverListTable.clear();
		serverListTable.rows.add(tempData).draw(); // row 인덱스 재정비, 삭제 후 row 생성 하면 index 중복 발생하여 생성 시 새로 그려줌
	}
	/* ********************************************************
	 * Listener Server List 삭제
	 ******************************************************** */
	function fn_del_server_list(){
		if(serverListTable.rows('.selected').data().length==0){
			showSwalIcon('<spring:message code="message.msg35" />', '<spring:message code="common.close" />', '', 'error');
			return;
		}else{
			$("#modYn").val("Y");
			if(serverListTable.row('.selected').data().lsn_svr_id!=""){
				delListnerSvrRows[delListnerSvrRows.length] = serverListTable.row('.selected').data(); 
			}
			serverListTable.row('.selected').remove().draw();
		}
	}
	/* ********************************************************
	 *  backup 여부 클릭 이벤트
	 ******************************************************** */
	function fn_backupYn_click(index){
		if(serverListTable.row('.selected').data().backup_yn == 'N'){
			$("input:checkbox[id=backup_yn_" + index +"]").prop("checked", true);
			serverListTable.row('.selected').data().backup_yn = "Y";
		}else{
			$("input:checkbox[id=backup_yn_" + index + "]").prop("checked", false);
			serverListTable.row('.selected').data().backup_yn = "N";
		}
		serverListTable.row('.selected').data().edit_yn = "Y";
	}
</script>
<div class="modal fade" id="pop_layer_proxy_listen_reg" tabindex="-1" role="dialog" aria-labelledby="ModalLabel" aria-hidden="true" data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog  modal-xl-top" role="document" style="margin: 30px 330px;">
		<div class="modal-content" style="width:1000px;">		 
			<div class="modal-body" style="margin-bottom:-30px;">
				<h4 class="modal-title mdi mdi-alert-circle text-info" id="ModalLabel" style="padding-left:5px;">
					<spring:message code="eXperDB_proxy.listener_reg"/>
				</h4>
				
				<div class="card" style="margin-top:10px;border:0px;">
					<form class="cmxform" id="insProxyListenForm">
						<input type="hidden" name="lstnReg_pry_svr_id" id="lstnReg_pry_svr_id"/>
						<input type="hidden" name="lstnReg_lsn_id" id="lstnReg_lsn_id"/>
						<input type="hidden" name="lstnReg_db_id" id="lstnReg_db_id"/>
						<input type="hidden" name="lstnReg_mode" id="lstnReg_mode"/>
						<fieldset>
							<div class="card-body card-body-xsm card-body-border">
								<div class="form-group row">
									<label for="lstnReg_lsn_nm" class="col-sm-2 col-form-label-sm pop-label-index">
										<i class="item-icon fa fa-dot-circle-o"></i>
										<%-- <spring:message code="user_management.id" /> --%>
										Listener 명칭(*)
									</label>

									<div class="col-sm-4">
										<input type="text" class="form-control form-control-xsm lstnReg_lsn_nm" autocomplete="off" maxlength="15" id="lstnReg_lsn_nm" name="lstnReg_lsn_nm" onkeyup="fn_checkWord(this,15)" onblur="this.value=this.value.trim()"  placeholder="15<spring:message code='message.msg188'/>" tabindex=1 />
									</div>
									<label for="lstnReg_con_bind" class="col-sm-2 col-form-label-sm pop-label-index">
										<i class="item-icon fa fa-dot-circle-o"></i>
										<%-- <spring:message code="user_management.user_name" /> --%>
										bind(*)
									</label>
									<div class="col-sm-2_2">
										<input type="text" class="form-control form-control-xsm" maxlength="25" id="lstnReg_con_bind_ip" name="lstnReg_con_bind_ip" onkeyup="fn_checkWord(this,25)" onblur="this.value=this.value.trim()" placeholder="ip 주소 또는 *" tabindex=2 />
									</div>
									<div class="col-sm-auto col-form-label-sm">
										:
									</div>
									<div class="col-sm-1_5">
										<input type="number" class="form-control form-control-xsm" maxlength="25" id="lstnReg_con_bind_port" name="lstnReg_con_bind_port" onkeyup="fn_checkWord(this,25)" onblur="this.value=this.value.trim()" placeholder="port" tabindex=2 />
									</div>
								</div>
								<div class="form-group row row-last">
									<label for="lstnReg_lsn_desc" class="col-sm-2 col-form-label-sm pop-label-index">
										<i class="item-icon fa fa-dot-circle-o"></i>
										설명
									</label>
									<div class="col-sm-10">
										<input type="text" class="form-control form-control-xsm" maxlength="25" id="lstnReg_lsn_desc" name="lstnReg_lsn_desc" onkeyup="fn_checkWord(this,25)" onblur="this.value=this.value.trim()" placeholder="" tabindex=2 />
									</div>
								</div>
							</div>
							<br/>
							<div class="card-body card-body-xsm card-body-border">
								<div class="form-group row">
									<label for="lstnReg_db_usr_id" class="col-sm-2 col-form-label-sm pop-label-index">
										<i class="item-icon fa fa-dot-circle-o"></i>
										<%-- <spring:message code="user_management.position" /> --%>
										 계정(*)
									</label>
									<div class="col-sm-4">
										<input type="text" class="form-control form-control-xsm" maxlength="25" id="lstnReg_db_usr_id" name="lstnReg_db_usr_id" onkeyup="fn_checkWord(this,25)" onblur="this.value=this.value.trim()" placeholder="" tabindex=2 />
									</div>
									<label for="lstnReg_db_nm" class="col-sm-2 col-form-label-sm pop-label-index">
										<i class="item-icon fa fa-dot-circle-o"></i>
										<%-- <spring:message code="user_management.position" /> --%>
										Database(*)
									</label>
									<div class="col-sm-4">
										<input type="text" class="form-control form-control-xsm" maxlength="25" id="lstnReg_db_nm" name="lstnReg_db_nm" onkeyup="fn_checkWord(this,25)" onblur="this.value=this.value.trim()" placeholder="" tabindex=2 />
									</div>
								</div>
								<div class="form-group row">
									<label for="lstnReg_con_sim_query" class="col-sm-2 col-form-label-sm pop-label-index">
										<i class="item-icon fa fa-dot-circle-o"></i>
										Simple Query(*)
									</label>
									<div class="col-sm-10">
										<input type="text" class="form-control form-control-xsm" maxlength="25" id="lstnReg_con_sim_query" name="lstnReg_con_sim_query" onkeyup="fn_checkWord(this,25)" onblur="this.value=this.value.trim()" placeholder="" tabindex=2 />
									</div>
								</div>
								<div class="form-group row row-last">
									<label for="lstnReg_field_nm" class="col-sm-2 col-form-label-sm pop-label-index">
										<i class="item-icon fa fa-dot-circle-o"></i>
										<%-- <spring:message code="user_management.position" /> --%>
										필드명(*)
									</label>
									<div class="col-sm-4">
										<input type="text" class="form-control form-control-xsm" maxlength="25" id="lstnReg_field_nm" name="lstnReg_field_nm" onkeyup="fn_checkWord(this,25)" onblur="this.value=this.value.trim()" placeholder="" tabindex=2 />
									</div>
									<label for="lstnReg_field_val" class="col-sm-2 col-form-label-sm pop-label-index">
										<i class="item-icon fa fa-dot-circle-o"></i>
										<%-- <spring:message code="user_management.position" /> --%>
										필드값(*)
									</label>
									<div class="col-sm-4">
										<input type="text" class="form-control form-control-xsm" maxlength="25" id="lstnReg_field_val" name="lstnReg_field_val" onkeyup="fn_checkWord(this,25)" onblur="this.value=this.value.trim()" placeholder="" tabindex=2 />
									</div>
								</div>
							</div>
							
							<br/>
							
							<div class="card-body card-body-xsm card-body-border">
								<div class="form-group row">
										<label for="com_db_svr_nm" class="col-sm-12 col-form-label-sm" style="margin-bottom:-50px;">
											<i class="item-icon fa fa-dot-circle-o"></i>
											DBMS
										</label>
									</div>
									<div class="form-group row">
										<div class="col-sm-12">
											<a data-toggle="modal" href="#pop_layer_ip_reg"><span onclick="fn_add_server_list();" style="cursor:pointer"><img src="../images/popup/plus.png" alt="" style="margin-left: 88%;"/></span></a>
											<span onclick="fn_del_server_list();" style="cursor:pointer"><img src="../images/popup/minus.png" alt=""  /></span>
											<table id="serverList" class="table table-hover table-striped system-tlb-scroll input-table" style="width:100%;">
												<thead>
													<tr class="bg-info text-white">
														<th width="200">DBMS 접속 정보</th>
														<th width="100"><spring:message code="data_transfer.port" /></th>
														<th width="100">백업 여부</th>
														<th width="0"></th>
														<th width="0"></th>
														<th width="0"></th>			
														<th width="0"></th>							
													</tr>
												</thead>
											</table>
										
										</div>
									</div>
							</div>
							
							<br/>
							
							<div class="top-modal-footer" style="text-align: center !important; margin: -20px 0 0 -20px;" >
								<input class="btn btn-primary" width="200px"style="vertical-align:middle;" type="submit" id="lstnReg_save_submit" value='<spring:message code="common.save" />' />
								<button type="button" class="btn btn-light" data-dismiss="modal"><spring:message code="common.close"/></button>
							</div>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>