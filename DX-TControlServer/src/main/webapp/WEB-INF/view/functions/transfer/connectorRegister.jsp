<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	* @Class Name : connectorRegister.jsp
	* @Description : connectorRegister 화면
	* @Modification Information
	*
	*   수정일         수정자                   수정내용
	*  ------------    -----------    ---------------------------
	*  2017.06.07     최초 생성
	*
	* author 김주영 사원
	* since 2017.06.07
	*
	*/
%>
<script>
	var table = null;

	function fn_init() {
		table = $('#connectorTable').DataTable({
			scrollY : "300px",
			processing : true,
			searching : false,
			deferRender : true,
			scrollX: true,
			columns : [ 
						{ data : "rownum", defaultContent : "", targets : 0, orderable : false, checkboxes : {'selectRow' : true}},
			            { data : "idx", className : "dt-center", defaultContent : ""}, 
			            { data : "cnr_nm", className : "dt-center", defaultContent : ""}, 
			            { data : "cnr_ipadr", className : "dt-center", defaultContent : ""},
			            { data : "cnr_portno", className : "dt-center", defaultContent : ""}, 
			            { data : "cnr_cnn_tp_cd", className : "dt-center", defaultContent : ""}, 
			            { data : "frst_regr_id", className : "dt-center", defaultContent : ""}, 
			            { data : "frst_reg_dtm", className : "dt-center", defaultContent : ""}, 
			            { data : "lst_mdfr_id", className : "dt-center", defaultContent : ""}, 
			            { data : "lst_mdf_dtm", className : "dt-center", defaultContent : ""},
			         	//cnr_id
						{ data: "cnr_id" , visible: false}
			         ]
		});
		
		table.tables().header().to$().find('th:eq(0)').css('min-width', '10px');
		table.tables().header().to$().find('th:eq(1)').css('min-width', '20px');
		table.tables().header().to$().find('th:eq(2)').css('min-width', '120px');
		table.tables().header().to$().find('th:eq(3)').css('min-width', '100px');
		table.tables().header().to$().find('th:eq(4)').css('min-width', '70px');
		table.tables().header().to$().find('th:eq(5)').css('min-width', '70px');
		table.tables().header().to$().find('th:eq(6)').css('min-width', '70px');
		table.tables().header().to$().find('th:eq(7)').css('min-width', '120px');
		table.tables().header().to$().find('th:eq(8)').css('min-width', '70px');
		table.tables().header().to$().find('th:eq(9)').css('min-width', '120px');
	    $(window).trigger('resize'); 
	    
		//더블 클릭시 -> 쓰기 권한이 Y일 경우
		if("${wrt_aut_yn}" == "Y"){
			$('#connectorTable tbody').on('dblclick','tr',function() {
				var data = table.row(this).data();
				var cnr_id = data.cnr_id;
				var popUrl = "/popup/connectorRegForm.do?act=u&cnr_id=" + cnr_id; // 서버 url 팝업경로
				var width = 955;
				var height = 400;
				var left = (window.screen.width / 2) - (width / 2);
				var top = (window.screen.height /2) - (height / 2);
				var popOption = "width="+width+", height="+height+", top="+top+", left="+left+", resizable=no, scrollbars=yes, status=no, toolbar=no, titlebar=yes, location=no,";
						
				window.open(popUrl,"",popOption);	
			});
		}
	}

	$(window.document).ready(function() {
		fn_buttonAut();
		fn_init();
		$.ajax({
			url : "/selectConnectorRegister.do",
			data : {},
			dataType : "json",
			type : "post",
			error : function(request, status, error) {
				alert("ERROR CODE : "+ request.status+ "\n\n"+ "ERROR Message : "+ error+ "\n\n"+ "Error Detail : "+ request.responseText.replace(/(<([^>]+)>)/gi, ""));
			},
			success : function(result) {
				table.clear().draw();
				table.rows.add(result).draw();
			}
		});
	});

	function fn_buttonAut(){
		var btnSelect = document.getElementById("btnSelect"); 
		var btnInsert = document.getElementById("btnInsert"); 
		var btnUpdate = document.getElementById("btnUpdate"); 
		var btnDelete = document.getElementById("btnDelete"); 
		
		if("${wrt_aut_yn}" == "Y"){
			btnInsert.style.display = '';
			btnUpdate.style.display = '';
			btnDelete.style.display = '';
		}else{
			btnInsert.style.display = 'none';
			btnUpdate.style.display = 'none';
			btnDelete.style.display = 'none';
		}
			
		if("${read_aut_yn}" == "Y"){
			btnSelect.style.display = '';
		}else{
			btnSelect.style.display = 'none';
		}
	}	
	
	/* 조회버튼 클릭시*/
	function fn_select() {
		$.ajax({
			url : "/selectConnectorRegister.do",
			data : {
				cnr_nm : "%" + $("#cnr_nm").val() + "%",
				cnr_ipadr : "%" + $("#cnr_ipadr").val() + "%",
			},
			dataType : "json",
			type : "post",
			error : function(request, status, error) {
				alert("ERROR CODE : "+ request.status+ "\n\n"+ "ERROR Message : "+ error+ "\n\n"+ "Error Detail : "+ request.responseText.replace(/(<([^>]+)>)/gi, ""));
			},
			success : function(result) {
				table.clear().draw();
				table.rows.add(result).draw();
			}
		});
	}

	/* 등록버튼 클릭시*/
	function fn_insert() {
		var popUrl = "/popup/connectorRegForm.do?act=i"; // 서버 url 팝업경로
		var width = 955;
		var height = 400;
		var left = (window.screen.width / 2) - (width / 2);
		var top = (window.screen.height /2) - (height / 2);
		var popOption = "width="+width+", height="+height+", top="+top+", left="+left+", resizable=no, scrollbars=yes, status=no, toolbar=no, titlebar=yes, location=no,";
			
		window.open(popUrl,"",popOption);	
	}

	/* 수정버튼 클릭시*/
	function fn_update() {
		var rowCnt = table.rows('.selected').data().length;
		if (rowCnt == 1) {
			var cnr_id = table.row('.selected').data().cnr_id;
			
			var popUrl = "/popup/connectorRegForm.do?act=u&cnr_id=" + cnr_id; // 서버 url 팝업경로
			var width = 955;
			var height = 400;
			var left = (window.screen.width / 2) - (width / 2);
			var top = (window.screen.height /2) - (height / 2);
			var popOption = "width="+width+", height="+height+", top="+top+", left="+left+", resizable=no, scrollbars=yes, status=no, toolbar=no, titlebar=yes, location=no,";
				
			window.open(popUrl,"",popOption);	
			
		} else {
			alert("하나의 항목을 선택해주세요.");
			return false;
		}
	}

	/* 삭제버튼 클릭시*/
	function fn_delete() {
		var datas = table.rows('.selected').data();
		if (datas.length <= 0) {
			alert("하나의 항목을 선택해주세요.");
			return false;
		}else{
			if (!confirm("삭제하시겠습니까?"))
				return false;
			var rowList = [];
			for (var i = 0; i < datas.length; i++) {
				rowList += datas[i].cnr_id + ',';
			}

			$.ajax({
				url : "/deleteConnectorRegister.do",
				data : {
					cnr_id : rowList,
				},
				dataType : "json",
				type : "post",
				error : function(request, status, error) {
					alert("ERROR CODE : "+ request.status+ "\n\n"+ "ERROR Message : "+ error+ "\n\n"+ "Error Detail : "+ request.responseText.replace(/(<([^>]+)>)/gi, ""));
				},
				success : function(result) {
					if (result) {
						alert("삭제되었습니다.");
						location.reload();
					} else {
						alert("커넥터삭제를 실패하였습니다.");
					}
				}
			});
		}
	}

</script>
<div id="contents">
	<div class="contents_wrap">
		<div class="contents_tit">
			<h4>커넥터 관리 <a href="#n"><img src="../images/ico_tit.png" class="btn_info"/></a></h4>
			<div class="infobox"> 
				<ul>
					<li>eXperDB에서 하둡 분산 파일 시스템으로 데이터 전송을 하기 위한 커넥터를 설정합니다.</li>
					<li>신규로 등록하거나 이미 등록된 커넥터를 조회, 수정, 삭제 합니다.</li>	
				</ul>
			</div>
			<div class="location">
				<ul>
					<li>Function</li>
					<li>데이터전송정보</li>
					<li class="on">커넥터 관리</li>
				</ul>
			</div>
		</div>

		<div class="contents">
			<div class="cmm_grp">
				<div class="btn_type_01">
					<span class="btn"><button onclick="fn_select()" id="btnSelect">조회</button></span>
					<span class="btn"><button onclick="fn_insert()" id="btnInsert">등록</button></span>
					<span class="btn"><button onclick="fn_update()" id="btnUpdate">수정</button></span>
					<span class="btn"><button onclick="fn_delete()" id="btnDelete">삭제</button></span>
				</div>
				<div class="sch_form">
					<table class="write">
						<caption>커넥터 조회</caption>
						<colgroup>
							<col style="width: 130px;" />
							<col />
							<col style="width: 70px;" />
							<col />
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" class="t2">커넥터 명</th>
								<td><input type="text" class="txt" name="cnr_nm" id="cnr_nm" /></td>
								<th scope="row" class="t3">아이피</th>
								<td><input type="text" class="txt" name="cnr_ipadr" id="cnr_ipadr" /></td>
							</tr>
						</tbody>
					</table>
				</div>
				<table id="connectorTable" class="display" cellspacing="0" width="100%">
					<thead>
						<tr>
							<th width="10"></th>
							<th width="20">NO</th>
							<th width="120">커넥터 명</th>
							<th width="100">아이피</th>
							<th width="70">포트</th>
							<th width="70">유형</th>
							<th width="70">등록자</th>
							<th width="120">등록일시</th>
							<th width="70">수정자</th>
							<th width="120">수정일시</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</div>
