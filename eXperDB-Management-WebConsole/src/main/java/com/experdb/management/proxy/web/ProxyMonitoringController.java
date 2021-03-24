package com.experdb.management.proxy.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.experdb.management.proxy.service.ProxyLogVO;
import com.experdb.management.proxy.service.ProxyMonitoringService;
import com.k4m.dx.tcontrol.admin.menuauthority.service.MenuAuthorityService;
import com.k4m.dx.tcontrol.cmmn.CmmnUtils;
import com.k4m.dx.tcontrol.cmmn.client.ClientAdapter;
import com.k4m.dx.tcontrol.cmmn.client.ClientProtocolID;
import com.k4m.dx.tcontrol.cmmn.client.ClientTranCodeType;
import com.k4m.dx.tcontrol.common.service.HistoryVO;

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
	
	private List<Map<String, Object>> menuAut;
	
	/**
	 * Proxy 모니터링 화면
	 * @param historyVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/monitoring.do")
	public ModelAndView proxyMonitoring(@ModelAttribute("historyVO") HistoryVO historyVO, HttpServletRequest request) {

		//권한 조회 (공통메소드호출),
		CmmnUtils cu = new CmmnUtils();
		menuAut = cu.selectMenuAut(menuAuthorityService, "MN0001801");
		ModelAndView mv = new ModelAndView();

		String dtlCd = "DX-T0160";
		int mnu_id = 44;
		try {
			//읽기 권한이 없는경우 에러페이지 호출 
			if(menuAut.get(0).get("read_aut_yn").equals("N")){
				mv.setViewName("error/autError");
			} else {
				// 화면접근이력 이력 남기기
				proxyMonitoringService.monitoringSaveHistory(request, historyVO, dtlCd, mnu_id);
				List<Map<String, Object>> proxyServerTotInfo = proxyMonitoringService.selectProxyServerList();
				int pry_svr_id = Integer.parseInt(String.valueOf(proxyServerTotInfo.get(0).get("pry_svr_id")));
				List<Map<String, Object>> proxyServerByMasId = proxyMonitoringService.selectProxyServerByMasterId(pry_svr_id);
				List<Map<String, Object>> dbServerConProxy = proxyMonitoringService.selectDBServerConProxy(pry_svr_id);
				List<ProxyLogVO> proxyLogList = proxyMonitoringService.selectProxyLogList(pry_svr_id);
				mv.addObject("proxyServerTotInfo", proxyServerTotInfo);
				mv.addObject("proxyServerByMasId", proxyServerByMasId);
				mv.addObject("dbServerConProxy", dbServerConProxy);
				mv.addObject("proxyLogList",proxyLogList);
				mv.setViewName("proxy/monitoring/proxyMonitoring");
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mv;
	}
	
	/**
	 * 리스너 통계 정보
	 * @param request, pry_svr_id
	 * @return
	 */
	@RequestMapping("/listenerstatistics.do")
	public ModelAndView selectListenerStatisticsInfo(@ModelAttribute("historyVO") HistoryVO historyVO,HttpServletRequest request){
		
		//권한 조회 (공통메소드호출),
		CmmnUtils cu = new CmmnUtils();
		menuAut = cu.selectMenuAut(menuAuthorityService, "MN0001801");
		ModelAndView mv = new ModelAndView();
		
		System.out.println("pry_svr_id : " + request.getParameter("pry_svr_id"));
		
		String dtlCd = "DX-T0160_01";
		int mnu_id = 44;
		
		try {
			//읽기 권한이 없는경우 에러페이지 호출 
			if(menuAut.get(0).get("read_aut_yn").equals("N")){
				mv.setViewName("error/autError");
			} else {
				// 화면접근이력 이력 남기기
				proxyMonitoringService.monitoringSaveHistory(request, historyVO, dtlCd, mnu_id);
			}
			String strPrySvrId = request.getParameter("pry_svr_id");
			int pry_svr_id = Integer.parseInt(strPrySvrId); 
			List<Map<String, Object>> proxyStatisitcInfo = proxyMonitoringService.selectProxyStatisticsInfo(pry_svr_id);
			mv.addObject("proxyStatisitcInfo",proxyStatisitcInfo); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
		
	}
	
	/**
	 * proxy / keepalived config 파일
	 * @param request, pry_svr_id
	 * @return
	 */
	@RequestMapping("/configView.do")
	public ModelAndView configView(@ModelAttribute("historyVO") HistoryVO historyVO, HttpServletRequest request){

		//권한 조회 (공통메소드호출),
		CmmnUtils cu = new CmmnUtils();
		menuAut = cu.selectMenuAut(menuAuthorityService, "MN0001801");
		ModelAndView mv = new ModelAndView();
		System.out.println("111111111111111111111111111111111111111");
		System.out.println("pry_svr_id : " + request.getParameter("pry_svr_id"));
		System.out.println("type : " + request.getParameter("type"));
		
		String dtlCd = "DX-T0160_02";
		int mnu_id = 44;
		try {
			//읽기 권한이 없는경우 에러페이지 호출 
			if(menuAut.get(0).get("read_aut_yn").equals("N")){
				mv.setViewName("error/autError");
			} else {
				// 화면접근이력 이력 남기기
				proxyMonitoringService.monitoringSaveHistory(request, historyVO, dtlCd, mnu_id);
				int pry_svr_id = Integer.parseInt(request.getParameter("pry_svr_id"));
				String type = request.getParameter("type");
				
				Map<String, Object> selectConfigBySysType = proxyMonitoringService.selectConfiguration(pry_svr_id, type);
//				System.out.println(selectConfigBySysType.get(0).toString());
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
	
	@RequestMapping("/configViewAjax.do")
	public HashMap configViewAjax(@ModelAttribute("historyVO") HistoryVO historyVO, HttpServletRequest request){
		HashMap result = new HashMap<>();
		String strBuffer = "";
		System.out.println("2222222222222222222222222222222222222222222222");
		System.out.println("pry_svr_id : " + request.getParameter("pry_svr_id"));
		System.out.println("type : " + request.getParameter("type"));
		try {
			String strPrySvrId = request.getParameter("pry_svr_id");
			String type = request.getParameter("type");
			int pry_svr_id = Integer.parseInt(strPrySvrId);
			
			Map<String, Object> selectConfigBySysType = proxyMonitoringService.selectConfiguration(pry_svr_id, type);
			
			
			String strIpAdr = (String) selectConfigBySysType.get("ipadr");
			String strPrySvrNm = (String) selectConfigBySysType.get("pry_svr_nm");
			String strConfigFilePath = (String) selectConfigBySysType.get("path");
			String strDirectory = strConfigFilePath.substring(0, strConfigFilePath.lastIndexOf("/"));
			String strFileName = strConfigFilePath.substring(strConfigFilePath.lastIndexOf("/")+1);
			String strPort = String.valueOf(selectConfigBySysType.get("socket_port"));
			
			JSONObject serverObj = new JSONObject();
			
			serverObj.put(ClientProtocolID.SERVER_NAME, strPrySvrNm);
			serverObj.put(ClientProtocolID.SERVER_IP, strIpAdr);
//			serverObj.put(ClientProtocolID.SERVER_PORT, dbServerVO.getPortno());
			
			JSONObject jObj = new JSONObject();
			jObj.put(ClientProtocolID.DX_EX_CODE, ClientTranCodeType.DxT015);
			jObj.put(ClientProtocolID.SERVER_INFO, serverObj);
			jObj.put(ClientProtocolID.COMMAND_CODE, ClientProtocolID.COMMAND_CODE_V);
			jObj.put(ClientProtocolID.FILE_DIRECTORY, strDirectory);
			jObj.put(ClientProtocolID.FILE_NAME, strFileName);
//			jObj.put(ClientProtocolID.SEEK, strSeek);
//			jObj.put(ClientProtocolID.DW_LEN, dwLen);
//			jObj.put(ClientProtocolID.READLINE, strReadLine);
			System.out.println("jObj : " + jObj.toJSONString());
			String IP = strIpAdr;
			int PORT = Integer.parseInt(strPort);
			System.out.println("IP : " + IP + ": " + PORT);
			//IP = "127.0.0.1";
			ClientAdapter CA = new ClientAdapter(IP, PORT);
//			CA.open(); 
			System.out.println("CA : " + CA.toString());
			result.put("data", jObj);
//			JSONObject objList = CA.dxT015_V(jObj);
//			CA.close();
//			System.out.println("objList : " + objList.toJSONString());
//			
//			String strErrMsg = (String)objList.get(ClientProtocolID.ERR_MSG);
//			String strErrCode = (String)objList.get(ClientProtocolID.ERR_CODE);
//			String strDxExCode = (String)objList.get(ClientProtocolID.DX_EX_CODE);
//			String strResultCode = (String)objList.get(ClientProtocolID.RESULT_CODE);
//			System.out.println("RESULT_CODE : " +  strResultCode);
//			System.out.println("ERR_CODE : " +  strErrCode);
//			System.out.println("ERR_MSG : " +  strErrMsg);
//			
//			String strEndFlag = (String)objList.get(ClientProtocolID.END_FLAG);
//			strBuffer = (String)objList.get(ClientProtocolID.RESULT_DATA);
//			
//			int intDwlen = (int)objList.get(ClientProtocolID.DW_LEN);
//			
//			Long lngSeek= (Long)objList.get(ClientProtocolID.SEEK);
//			
//			result.put("data", strBuffer);
//			result.put("fSize", strBuffer.length());
			//hp.put("fChrSize", intLastLength - intFirstLength);
//			hp.put("seek", lngSeek.toString());
//			hp.put("dwLen", Integer.toString(intDwlen));
//			hp.put("endFlag", strEndFlag);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@RequestMapping("/log")
	public ModelAndView selectLogBySysTypeAndDate(HttpServletRequest request, int pry_svr_id){
		ModelAndView mv = new ModelAndView();
		return mv;
	}
}