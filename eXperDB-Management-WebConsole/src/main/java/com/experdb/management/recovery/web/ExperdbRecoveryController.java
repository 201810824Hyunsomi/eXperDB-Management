package com.experdb.management.recovery.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.experdb.management.backup.cmmn.CmmnUtil;
import com.experdb.management.backup.history.service.BackupJobHistoryVO;
import com.experdb.management.backup.history.service.ExperdbBackupHistoryService;
import com.experdb.management.recovery.service.ExperdbRecoveryService;
import com.k4m.dx.tcontrol.admin.accesshistory.service.AccessHistoryService;
import com.k4m.dx.tcontrol.admin.menuauthority.service.MenuAuthorityService;
import com.k4m.dx.tcontrol.cmmn.CmmnUtils;
import com.k4m.dx.tcontrol.common.service.HistoryVO;

@Controller
public class ExperdbRecoveryController {
	
	@Autowired
	private ExperdbRecoveryService experdbRecoveryService;
	
	@Autowired
	private ExperdbBackupHistoryService experdbBackupHistoryService;
	
	@Autowired
	private MenuAuthorityService menuAuthorityService;
	
	@Autowired
	private AccessHistoryService accessHistoryService;
	
	private List<Map<String, Object>> menuAut;
	/**
	 * 완전 복구 View page
	 * @param historyVO, request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/experdb/completeRecovery.do")
	public ModelAndView completeRecovery(@ModelAttribute("historyVO") HistoryVO historyVO, HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		
		try {
			CmmnUtils cu = new CmmnUtils();
			menuAut = cu.selectMenuAut(menuAuthorityService, "MN0002101");
		
			if (menuAut.get(0).get("read_aut_yn").equals("N")) {
				mv.setViewName("error/autError");
			}else{			
				mv.addObject("read_aut_yn", menuAut.get(0).get("read_aut_yn"));
				mv.addObject("wrt_aut_yn", menuAut.get(0).get("wrt_aut_yn"));
				// 화면접근이력 이력 남기기
				CmmnUtils.saveHistory(request, historyVO);
				historyVO.setExe_dtl_cd("DX-T0178");
				historyVO.setMnu_id(57);
				accessHistoryService.insertHistory(historyVO);
				mv.setViewName("/eXperDB_Recovery/completeRecovery");
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return mv;
	}
	
	
	@RequestMapping(value = "/experdb/nodeInfoList.do")
	public @ResponseBody JSONObject getNodeInfoList(){
		JSONObject result = new JSONObject();
		
		result = experdbRecoveryService.getNodeInfoList();
		return result;
	}
	
	
	@RequestMapping(value = "/experdb/recStorageList.do")
	public @ResponseBody JSONObject getStorageList(HttpServletRequest request){
		JSONObject result = new JSONObject();
		
		result = experdbRecoveryService.getStorageList(request);
		
		return result;
	}
	
	@RequestMapping(value = "/experdb/recoveryDBList.do")
	public @ResponseBody JSONObject getRecoveryDBList(HttpServletRequest request){
		System.out.println("@@ getRecoveryDBList CONTROLLER @@");
		JSONObject result = new JSONObject();
		
		result = experdbRecoveryService.getRecoveryDBList();
		
		return result;
	}
	
	@RequestMapping(value = "/experdb/serverInfoFileRead.do")
	public @ResponseBody JSONObject serverInfoFileRead(MultipartHttpServletRequest request){
		JSONObject result = new JSONObject();
		
		try {
			result = experdbRecoveryService.serverInfoFileRead(request);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value = "/experdb/recoveryDBReg.do")
	public @ResponseBody JSONObject recoveryDBInsert(HttpServletRequest request){
		JSONObject result = new JSONObject();
		
		result = experdbRecoveryService.recoveryDBInsert(request);
		
		return result;
	}
	
	@RequestMapping(value = "/experdb/recoveryDBDelete.do")
	public @ResponseBody JSONObject recoveryDBDelete(HttpServletRequest request){
		System.out.println("### recoveryDBDelete CONTROLLER ##");
		JSONObject result = new JSONObject();
		
		result = experdbRecoveryService.recoveryDBDelete(request);
		
		return result;
	}
	
	@RequestMapping(value = "/experdb/completeRecoveryRun.do")
	public @ResponseBody JSONObject completeRecoveryRun(HttpServletRequest request){
		JSONObject result = new JSONObject();
		
		try {
			result = experdbRecoveryService.completeRecoveryRun(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		return result;
	}
	
	
	
	/**
	 * 시점 복구 View page
	 * @param historyVO, request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/experdb/timeRecovery.do")
	public ModelAndView timeRecovery(@ModelAttribute("historyVO") HistoryVO historyVO, HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		
		try {
			CmmnUtils cu = new CmmnUtils();
			menuAut = cu.selectMenuAut(menuAuthorityService, "MN0002102");
		
			if (menuAut.get(0).get("read_aut_yn").equals("N")) {
				mv.setViewName("error/autError");
			}else{			
				mv.addObject("read_aut_yn", menuAut.get(0).get("read_aut_yn"));
				mv.addObject("wrt_aut_yn", menuAut.get(0).get("wrt_aut_yn"));
				// 화면접근이력 이력 남기기
				CmmnUtils.saveHistory(request, historyVO);
				historyVO.setExe_dtl_cd("DX-T0179");
				historyVO.setMnu_id(58);
				accessHistoryService.insertHistory(historyVO);
				mv.setViewName("/eXperDB_Recovery/timeRecovery");
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return mv;
	}	
	
	@RequestMapping(value = "/experdb/recoveryTimeList.do")
	public @ResponseBody JSONObject recoveryTimeList(HttpServletRequest request){
		JSONObject result = new JSONObject();

		result = experdbRecoveryService.getRecoveryTimeListList(request);
		
		return result;
	}
	
	@RequestMapping(value = "/experdb/getRecoveryTimeOption.do")
	public @ResponseBody JSONObject getRecoveryTimeOption(HttpServletRequest request){
		JSONObject result = new JSONObject();
		
		result = experdbRecoveryService.getRecoveryTimeOption(request);
		
		return result;
	}
	
	@RequestMapping(value="/experdb/timeRecoveryRun.do")
	public @ResponseBody JSONObject timeRecoveryRun(HttpServletRequest request){
		JSONObject result = new JSONObject();
		
		try {
			result = experdbRecoveryService.timeRecoveryRun(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	
	
	@RequestMapping(value = "/experdb/getRecoveryPoint.do")
	public @ResponseBody JSONObject getRecoveryPoint(HttpServletRequest request){
		JSONArray jArr = new JSONArray();
		JSONObject recoveryPoint = new JSONObject();
		
		List<BackupJobHistoryVO> resultSet = null;
		
		Map<String, Object> param = new HashMap<String, Object>();         
		CmmnUtil cmmn = new CmmnUtil();
		JSONObject result = new JSONObject();
		
		String storagePath = request.getParameter("storagePath");
		String ipadr = request.getParameter("ipadr");

		
		try {
			result = cmmn.getRecoveryPoint(storagePath, ipadr);
			String[] strArr = result.get("RESULT_DATA").toString().split("\n", 0);

			int limit_cnt = strArr.length;
			
			param.put("ipadr", ipadr);
			param.put("storagePath", storagePath);
			param.put("limit_cnt", limit_cnt);
			
			resultSet = experdbBackupHistoryService.selectRecoveryPoint(param);
			

			for(int i=0; i<resultSet.size(); i++){
				for(int j=0; j<strArr.length; j++){
					if(resultSet.get(i).getRpoint().equals(strArr[j].toString())){
						JSONObject obj = new JSONObject();
						
						Date endDate = new Date(Long.parseLong(resultSet.get(i).getFinishtime())* 1000L);
						
						SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String endTime = transFormat.format(endDate);	
						
						obj.put("recoveryTime", endTime);
						obj.put("recoveryPoint", resultSet.get(i).getRpoint());
						jArr.add(obj);
					}
				}
			}
			recoveryPoint.put("result", jArr);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return recoveryPoint;
	}
	
	
	
}
