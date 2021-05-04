package com.experdb.management.proxy.web;

import java.io.FileInputStream;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.experdb.management.proxy.service.ProxyLogVO;
import com.experdb.management.proxy.service.ProxyMonitoringService;
import com.k4m.dx.tcontrol.admin.menuauthority.service.MenuAuthorityService;
import com.k4m.dx.tcontrol.cmmn.CmmnUtils;
import com.k4m.dx.tcontrol.common.service.HistoryVO;
import com.k4m.dx.tcontrol.db2pg.history.web.DownloadView;
import com.k4m.dx.tcontrol.login.service.LoginVO;

/**
* @author 
* @see proxy 모니터링 관련 화면 Controller
* 
*      <pre>
* == 개정이력(Modification Information) ==
*
*   수정일                 수정자                   수정내용
*  -------     --------    ---------------------------
*  2021.03.02              최초 생성
*      </pre>
*/
@Controller
@RequestMapping("/proxyMonitoring")
public class ProxyMonitoringController {
	
	@Autowired
	private MenuAuthorityService menuAuthorityService;
	
	@Autowired
	private ProxyMonitoringService proxyMonitoringService;
	
	@Autowired
    MessageSource messageSource;
	
	private List<Map<String, Object>> menuAut;
	
	private String mnu_id = "51";
	
	/**
	 * Proxy 모니터링 화면
	 * @param historyVO, request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/monitoring.do")
	public ModelAndView proxyMonitoring(@ModelAttribute("historyVO") HistoryVO historyVO, HttpServletRequest request) {

		//권한 조회 (공통메소드호출),
		CmmnUtils cu = new CmmnUtils();
		menuAut = cu.selectMenuAut(menuAuthorityService, "MN0001801");
		ModelAndView mv = new ModelAndView();

		try {
			//읽기 권한이 없는경우 에러페이지 호출 
			if(menuAut.get(0).get("read_aut_yn").equals("N")){
				mv.setViewName("error/autError");
			} else {
				// 화면접근이력 이력 남기기
				proxyMonitoringService.monitoringSaveHistory(request, historyVO, "DX-T0160", mnu_id);
				List<Map<String, Object>> proxyServerTotInfo = proxyMonitoringService.selectProxyServerList();
				
				HttpSession session = request.getSession();
				LoginVO loginVo = (LoginVO) session.getAttribute("session");
				int aut_id = loginVo.getAut_id();

				if(proxyServerTotInfo.size() > 0){
					int pry_svr_id = Integer.parseInt(String.valueOf(proxyServerTotInfo.get(0).get("pry_svr_id")));
					
					List<Map<String, Object>> proxyServerByMasId = proxyMonitoringService.selectProxyServerByMasterId(pry_svr_id);
					List<Map<String, Object>> dbServerConProxy = proxyMonitoringService.selectDBServerConProxy(pry_svr_id);
					List<ProxyLogVO> proxyLogList = proxyMonitoringService.selectProxyLogList(pry_svr_id);
					List<Map<String, Object>> selectProxyVipLsnList = proxyMonitoringService.selectProxyVipLsnList(pry_svr_id);
					mv.addObject("proxyServerByMasId", proxyServerByMasId);
					mv.addObject("dbServerConProxy", dbServerConProxy);
					mv.addObject("proxyLogList",proxyLogList);
					mv.addObject("proxyVipLsnList", selectProxyVipLsnList);
				}

				mv.addObject("proxyServerTotInfo", proxyServerTotInfo);
				mv.addObject("aut_id", aut_id);
				mv.setViewName("proxy/monitoring/proxyMonitoring");
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mv;
	}
	
	/**
	 * proxy server id에 따른 데이터 조회
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/selectInfoByPrySvrId.do")
	public ModelAndView proxyMonitoringInfo(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("jsonView");
		int pry_svr_id = Integer.parseInt(request.getParameter("pry_svr_id"));
		
		try {	
			List<Map<String, Object>> proxyServerByMasId = proxyMonitoringService.selectProxyServerByMasterId(pry_svr_id);
			List<Map<String, Object>> dbServerConProxy = proxyMonitoringService.selectDBServerConProxy(pry_svr_id);
			List<ProxyLogVO> proxyLogList = proxyMonitoringService.selectProxyLogList(pry_svr_id);
			List<Map<String, Object>> proxyChartCntList = proxyMonitoringService.selectProxyChartCntList(pry_svr_id);
			List<Map<String, Object>> selectPryCngList = proxyMonitoringService.selectPryCngList(pry_svr_id);
			mv.addObject("proxyServerByMasId", proxyServerByMasId);
			mv.addObject("dbServerConProxy", dbServerConProxy);
			mv.addObject("proxyLogList",proxyLogList);
			mv.addObject("proxyChartCntList",proxyChartCntList);
			mv.addObject("selectPryCngList", selectPryCngList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mv;
	}
	
	/**
	 * 리스너 상세 정보
	 * @param request, pry_svr_id
	 * @return ModelAndView
	 */
	@RequestMapping("/listenerstatistics.do")
	public ModelAndView selectListenerStatisticsInfo(@ModelAttribute("historyVO") HistoryVO historyVO,HttpServletRequest request){
		
		//권한 조회 (공통메소드호출),
		CmmnUtils cu = new CmmnUtils();
		menuAut = cu.selectMenuAut(menuAuthorityService, "MN0001801");
		ModelAndView mv = new ModelAndView("jsonView");
		
		try {
			//읽기 권한이 없는경우 에러페이지 호출 
			if(menuAut.get(0).get("read_aut_yn").equals("N")){
				mv.setViewName("error/autError");
			} else {
				// 화면접근이력 이력 남기기
				proxyMonitoringService.monitoringSaveHistory(request, historyVO, "DX-T0160_01", mnu_id);
			}
			String strPrySvrId = request.getParameter("pry_svr_id");
			int pry_svr_id = Integer.parseInt(strPrySvrId); 
			List<Map<String, Object>> proxyStatisticsInfo = proxyMonitoringService.selectProxyStatisticsInfo(pry_svr_id);
			mv.addObject("proxyStatisticsInfo",proxyStatisticsInfo); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	/**
	 * 리스너 통계 정보
	 * @param request, pry_svr_id
	 * @return ModelAndView
	 */
	@RequestMapping("/listenerStatisticsChart.do")
	public ModelAndView selectProxyStatisticsChartInfo(@ModelAttribute("historyVO") HistoryVO historyVO,HttpServletRequest request){
		
		//권한 조회 (공통메소드호출),
		CmmnUtils cu = new CmmnUtils();
		menuAut = cu.selectMenuAut(menuAuthorityService, "MN0001801");
		ModelAndView mv = new ModelAndView("jsonView");

		try {
			//읽기 권한이 없는경우 에러페이지 호출 
			if(menuAut.get(0).get("read_aut_yn").equals("N")){
				mv.setViewName("error/autError");
			} else {
				// 화면접근이력 이력 남기기
				proxyMonitoringService.monitoringSaveHistory(request, historyVO, "DX-T0160_03", mnu_id);
			}
			String strPrySvrId = request.getParameter("pry_svr_id");
			int pry_svr_id = Integer.parseInt(strPrySvrId); 
			
			List<Map<String, Object>>  proxySettingChartresult = new ArrayList<Map<String, Object>>();
			
			List<Map<String, Object>> proxyStatisticsInfoChart = proxyMonitoringService.selectProxyStatisticsChartInfo(pry_svr_id);

			if (proxyStatisticsInfoChart != null && proxyStatisticsInfoChart.size() > 0) {
				for (int i = 0; i < proxyStatisticsInfoChart.size(); i++) {
					Map<String, Object> chart = new HashMap<String, Object>();

					chart.put("exe_dtm_ss", proxyStatisticsInfoChart.get(i).get("exe_dtm_ss"));
					chart.put("byte_receive", proxyStatisticsInfoChart.get(i).get("byte_receive"));
					chart.put("byte_transmit", proxyStatisticsInfoChart.get(i).get("byte_transmit"));
					chart.put("cumt_sso_con_cnt", proxyStatisticsInfoChart.get(i).get("cumt_sso_con_cnt"));
					chart.put("fail_chk_cnt", proxyStatisticsInfoChart.get(i).get("fail_chk_cnt"));

					proxySettingChartresult.add(chart);
				}
			}

//			System.out.println("pry_svr_nm : " + proxyStatisitcInfo.get(0).get("pry_svr_nm"));
			mv.addObject("proxyStatisticsInfoChart",proxyStatisticsInfoChart); 
			mv.addObject("proxySettingChartresult",proxySettingChartresult); 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
		
	}
	
	/**
	 * proxy / keepalived config 파일 popup
	 * @param request, pry_svr_id
	 * @return ModelAndView
	 */
	@RequestMapping("/configView.do")
	public ModelAndView configView(@ModelAttribute("historyVO") HistoryVO historyVO, HttpServletRequest request){

		//권한 조회 (공통메소드호출),
		CmmnUtils cu = new CmmnUtils();
		menuAut = cu.selectMenuAut(menuAuthorityService, "MN0001801");
		ModelAndView mv = new ModelAndView();

		try {
			//읽기 권한이 없는경우 에러페이지 호출 
			if(menuAut.get(0).get("read_aut_yn").equals("N")){
				mv.setViewName("error/autError");
			} else {
				// 화면접근이력 이력 남기기
				proxyMonitoringService.monitoringSaveHistory(request, historyVO, "DX-T0160_02", mnu_id);
				int pry_svr_id = Integer.parseInt(request.getParameter("pry_svr_id"));
				String type = request.getParameter("type");
				
				Map<String, Object> selectConfigBySysType = proxyMonitoringService.selectConfigurationInfo(pry_svr_id, type);
				mv.addObject("selectConfigBySysType",selectConfigBySysType);
				mv.addObject("pry_svr_id", pry_svr_id);
				mv.addObject("type", type);
				mv.setViewName("proxy/popup/proxyConfigViewPop");
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	/**
	 * config 파일 불러오기
	 * @param historyVO, request
	 * @return
	 */
	@RequestMapping("/configViewAjax.do")
	public ModelAndView configViewAjax(@ModelAttribute("historyVO") HistoryVO historyVO, HttpServletRequest request){
		ModelAndView mv = new ModelAndView("jsonView");
		String strBuffer = "";
		try {
			String strPrySvrId = request.getParameter("pry_svr_id");
			String type = request.getParameter("type");
			int pry_svr_id = Integer.parseInt(strPrySvrId);
			String strSeek = request.getParameter("seek");
			String strReadLine = request.getParameter("readLine");
			String dwLen = request.getParameter("dwLen");
			
			Map<String, Object> param = new HashMap<>();
			param.put("seek", strSeek);
			param.put("readLine", strReadLine);
			param.put("dwLen", dwLen);
			Map<String, Object> result = proxyMonitoringService.getConfiguration(pry_svr_id, type, param);
			
			strBuffer = (String) result.get("RESULT_DATA"); 
			mv.addObject("data", strBuffer);
			mv.addObject("fSize", strBuffer.length());
			mv.addObject("pry_svr_nm", result.get("pry_svr_nm"));
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return mv;
	}
	
	/**
	 * proxy / keepavlied log popup view
	 * @param historyVO, request
	 * @return ModelAndView
	 */
	@RequestMapping("/logView.do")
	public ModelAndView logViewBySysTypeAndDate(@ModelAttribute("historyVO") HistoryVO historyVO, HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("proxy/popup/proxyLogView");
		return mv;
	}
	
	/**
	 * proxy / keepalived log file
	 * @return ModelAndView
	 */
	@RequestMapping("/proxyLogViewAjax.do")
	public ModelAndView logViewAjax(@ModelAttribute("historyVO") HistoryVO historyVO, HttpServletRequest request){
		ModelAndView mv = new ModelAndView("jsonView");
		String strBuffer = "";
		try {
			String strPrySvrId = request.getParameter("pry_svr_id");
			String type = request.getParameter("type");
			int pry_svr_id = Integer.parseInt(strPrySvrId);
			String strSeek = request.getParameter("seek");
			String strReadLine = request.getParameter("readLine");
			String dwLen = request.getParameter("dwLen");
			String strDate = request.getParameter("date").substring(0, 10).replace("-", "");
			System.out.println(strDate);
			String todayYN = request.getParameter("todayYN");
			Map<String, Object> param = new HashMap<>();
			param.put("seek", strSeek);
			param.put("readLine", strReadLine);
			param.put("dwLen", dwLen);
			param.put("date", strDate);
			param.put("todayYN", todayYN);
			Map<String, Object> result = proxyMonitoringService.getLogFile(pry_svr_id, type, param);
			strBuffer = (String) result.get("RESULT_DATA"); 
			System.out.println("strBuffer====" + strBuffer);
			if(strBuffer != null) {
				mv.addObject("fSize", strBuffer.length());
			}
//			strBuffer = strBuffer.replaceAll("\n", "<br>");
			mv.addObject("data", strBuffer);
			mv.addObject("pry_svr_nm", result.get("pry_svr_nm"));
			mv.addObject("dwLen", result.get("DW_LEN"));
			mv.addObject("file_name", result.get("file_name"));
			mv.addObject("status", result.get("status"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	/**
	 * proxy / keepalived 상태 변경
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/actExeCng.do")
	public @ResponseBody JSONObject actExeCng(HttpServletRequest request){
		JSONObject resultObj = new JSONObject();
		HttpSession session = request.getSession();
		try {
			LoginVO loginVo = (LoginVO) session.getAttribute("session");
			Map<String, Object> param = new HashMap<String, Object>();
		
			String strPrySvrId = request.getParameter("pry_svr_id");
			String type = request.getParameter("type");
			int pry_svr_id = Integer.parseInt(strPrySvrId);
			String status = request.getParameter("status");
			String act_exe_type = request.getParameter("act_exe_type");
		
			param.put("pry_svr_id", pry_svr_id);
			param.put("type", type);
			param.put("cur_status", status);
			param.put("act_exe_type", act_exe_type);
			param.put("lst_mdfr_id", loginVo.getUsr_id() == null ? "" : loginVo.getUsr_id().toString());
			resultObj = proxyMonitoringService.actExeCng(param);
			System.out.println(resultObj.toJSONString());
		} catch (ConnectException e) {
			e.printStackTrace();
			resultObj.put("errcd", -1);
			resultObj.put("errMsg","Proxy Agent와 연결이 불가능합니다.\nAgent 상태를 확인해주세요.");
		} catch (Exception e) {
			e.printStackTrace();
			resultObj.put("errcd", -1);
			resultObj.put("errmsg", "작업 중 오류가 발생하였습니다.");
		}
//		mv.addObject("result", result);
		return resultObj;
	}
	
	/**
	 * proxy / keepalived 기동-정지 실패 로그
	 * @param request
	 * @return
	 */
	@RequestMapping("/actExeFailLog.do")
	public ModelAndView actExeFailLog(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("jsonView");
		System.out.println("*************************** actExeFailLog ");
		int pry_act_exe_sn = Integer.parseInt(request.getParameter("pry_act_exe_sn"));
		System.out.println(pry_act_exe_sn);
		Map<String, Object> result = proxyMonitoringService.selectActExeFailLog(pry_act_exe_sn);
		System.out.println(result.size());
		System.out.println(result.toString());
		mv.addObject("actExeFailLog", result);
//		mv.setViewName("proxy/popup/exeFailMsg");
		return mv;
	}
	
	@RequestMapping("/logDownload.do")
	public  void logDownload(HttpServletRequest request, HttpServletResponse response){
		Properties props = new Properties();

		try {
			props.load(new FileInputStream(ResourceUtils.getFile("classpath:egovframework/tcontrolProps/globals.properties")));
			
			//파일경로입력
			String file_type = request.getParameter("file_type");
			String file_name = "";
			String file_path = "";
			
			if(file_type.equals("PROXY")) { 
				file_type = "haproxy";
			}
	
			if (file_type.equals("haproxy")) {
				file_name = "haproxy.log";
			} else {
				file_name = "keepalived.log";
			}
			
			if (props.get("proxy_path") != null) {
				file_path = props.get("proxy_path").toString() + "/";
			} else {
				file_path = "/home/experdb/app/eXperDB-Management/eXperDB-Proxy" + "/";
			}
			
			
			String viewFileNm = file_name;

			DownloadView fileDown = new DownloadView(); //파일다운로드 객체생성
			fileDown.filDown(request, response, file_path, file_name, viewFileNm); //파일다운로드 

		} catch (Exception e) {
			e.printStackTrace();
		}
	}		
	
	public int logDownload1(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("파일명=" + request.getParameter("file_name"));

		// System.out.println("파일경로=" +request.getParameter("path"));

		String filePath = "/var/log/" + request.getParameter("type").toLowerCase() + "/";
		String fileName = request.getParameter("file_name");
		String viewFileNm = request.getParameter("file_name");
		System.out.println(fileName);
//		DownloadView fileDown = new DownloadView(); // 파일다운로드 객체생성
//		fileDown.filDown(request, response, filePath, fileName, viewFileNm); // 파일다운로드
		return 1;
	}
}
