package com.experdb.proxy.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
* @author 박태혁
* @see
* 
*      <pre>
* == 개정이력(Modification Information) ==
*
*   수정일       수정자           수정내용
*  -------     --------    ---------------------------
*  2018.04.23   박태혁 최초 생성
*      </pre>
*/
public class ProxyRunCommandExec extends Thread {

	private Logger socketLogger = LoggerFactory.getLogger("socketLogger");
	
	public String strCmd = null;
	public String retVal = null;
	public String agtCndtCd = null;
	public int iMode;
	
	private String returnMessage = "";

	ApplicationContext context;
	
	public ProxyRunCommandExec(){}
	
	public ProxyRunCommandExec(String _strCmd, String _agtCndtCd, int _iMode){
		this.strCmd=_strCmd; //cmd값
		this.iMode=_iMode; //구분값
		this.agtCndtCd = _agtCndtCd;

		context = new ClassPathXmlApplicationContext(new String[] { "context-tcontrol.xml" });
	}

	public String call(){
		return this.retVal;
	}
	
	public String getMessage() {
		return this.returnMessage;
	}

	@Override
	public void run(){
		String strResult = "";
		String strReturnVal = "";
		String strResultErrInfo = "";
		
        BufferedReader successBufferReaderRe = null; // 성공 버퍼
        BufferedReader errorBufferReaderRe = null; // 오류 버퍼


        //    ScaleServiceImpl service = (ScaleServiceImpl) context.getBean("ScaleService");
            Map<String, Object> logParam = new HashMap<String, Object>();	
            ProcessBuilder runBuilder = null;
            socketLogger.info("iMode --> " + iMode);
    		if(iMode == 0) {
    			Process p = null;
    			try {
    				String path = FileUtil.getPropertyValue("context.properties", "agent.path");
    				strCmd = strCmd + " >/dev/null 2>1 &";
    				List runCmd = new ArrayList();
    				runCmd.add("/bin/bash");
    				runCmd.add("-c");
    				runCmd.add(strCmd);
    				
    				runBuilder = new ProcessBuilder(runCmd);
    				runBuilder.directory(new File(path));
    				
    				p = runBuilder.start();
    				
    			//	Runtime runtime = Runtime.getRuntime();
    			//	String[] cmdPw = {"/bin/sh","-c", strCmd};

    //socketLogger.info("strCmdstrCmdstrCmdstrCmdstrCmdstrCmdstrCmd: " + Arrays.toString(cmdPw));
    			//	p = runtime.exec(cmdPw);
    				socketLogger.info("out.ready() --> ");
    			//	logParam = logSetting(timeId, scaleSet, loginId, "insert", strResult, retVal, scaleCount);
    			//	service.insertScaleLog_G(logParam);
    /*
    				p.waitFor();

    				if ( p.exitValue() != 0 ) {   
    					successBufferReaderRe = new BufferedReader(new InputStreamReader(p.getInputStream()));
    					while ( successBufferReaderRe.ready() ) {
    						strResultErrInfo += successBufferReaderRe.readLine();
    					}
    	
    					errorBufferReaderRe = new BufferedReader ( new InputStreamReader ( p.getErrorStream() ) );
    					while ( errorBufferReaderRe.ready() ) {
    						strResult += errorBufferReaderRe.readLine();
    					}
    						
    					strResult += strResultErrInfo;
    					strReturnVal = "failed";
    	
    					socketLogger.info("err.ready() --> " + strResult);
    						
    				} else {
    					successBufferReaderRe = new BufferedReader ( new InputStreamReader ( p.getInputStream() ) );
    	
    					while ( successBufferReaderRe.ready() ) {
    						strResult += successBufferReaderRe.readLine();
    						
    						socketLogger.info("out.ready() --> " + strResult);
    					}
    							
    					strReturnVal = "success";		
    				}

    				this.returnMessage = strResult;
    				this.retVal = strReturnVal;*/
    				socketLogger.info("out.ready()2 --> ");
    				strReturnVal = "success";	
    				
    			//	logParam = logSetting(timeId, scaleSet, loginId, "update", strResult, retVal, scaleCount);
    		//		service.insertScaleLog_G(logParam);

    			}catch(IOException e){
    				System.out.println(e);
    				this.retVal = "IOException" + e.toString();
    				this.returnMessage = "IOException" + e.toString();
    			}catch(Exception e) {
    				System.out.println(e);
    				this.retVal = "Exception" + e.toString();
    				this.returnMessage = "Exception" + e.toString();
    			}finally {
    				p.destroy();
    				if (successBufferReaderRe != null) {
    					try {
    						successBufferReaderRe.close();
    					} catch (IOException e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}
    				}
    	
    				if (errorBufferReaderRe != null) {
    					try {
    						errorBufferReaderRe.close();
    					} catch (IOException e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}
    				}
    						
    				System.out.println("Exec End");
    			}
    				
    		} else if(iMode == 1) { //scale 진행현황 조회
    			for(int i = 0 ; i < 1000 ; i++) {
    				try {
    					Thread.sleep(1000);
    				} catch (InterruptedException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    					socketLogger.info("Proxy Process End");
    				}

    				Process pw = null;
    				BufferedReader successBufferReader = null; // 성공 버퍼

    				try {
    					Runtime runtimeW = Runtime.getRuntime();
    					String[] cmdPw = {"/bin/sh","-c", strCmd};

    					pw = runtimeW.exec(cmdPw);

    					String strRstCnt = null;  
    					successBufferReader = new BufferedReader(new InputStreamReader(pw.getInputStream()));

    					int iCount = 0;
        				while ((strRstCnt = successBufferReader.readLine()) != null) {
        					socketLogger.info("---------------------------------------------------------------------------------------------------------");
        					socketLogger.info(strRstCnt);
        					socketLogger.info("---------------------------------------------------------------------------------------------------------");

            				strRstCnt = strRstCnt.trim();
            				
            				if ("TC001101".equals(agtCndtCd)) {
                				if(strRstCnt.equals("0") ) //
                				{
                					retVal = "success";
                				//	logParam = logSetting(timeId, scaleSet, loginId, "update", strResult, retVal, scaleCount);
                				//	service.insertScaleLog_G(logParam);
                					
                					iCount = 1;
                				}
            				} else {
                				if(strRstCnt.equals("1") ) //
                				{
                					retVal = "success";
                				//	logParam = logSetting(timeId, scaleSet, loginId, "update", strResult, retVal, scaleCount);
                				//	service.insertScaleLog_G(logParam);
                					
                					iCount = 1;
                				}
            				}

            				
            				if (iCount == 1) {
            					return;
            				}
        	            }

    					pw.waitFor();
    				}catch(IOException e) {
    					System.out.println("Scale Process End-IOException");
    					e.printStackTrace();
    				}catch(RuntimeException e) {
    					System.out.println("Scale Process End-RuntimeException");
    					e.printStackTrace();
    				}catch(Exception e) {
    					System.out.println("Scale Process End");
    					e.printStackTrace();
    				} finally {      	
    		            try {
    		            	pw.destroy();
    		                if (successBufferReader != null) successBufferReader.close();
    		            } catch (IOException e1) {
    		                e1.printStackTrace();
    		            }
    		        }
        		}

    		}
	}

	/**
	 * log값 setting
	 * 
	 * @return String
	 * @throws Exception
	 */
	public Map<String, Object> logSetting(String timeId, String scaleSet, String loginId, String saveGbn, String strResult, String retVal, String scaleCount){
		Map<String, Object> logParam = new HashMap<String, Object>();
/*
		if ("insert".equals(saveGbn)) {
			if ("scaleIn".equals(scaleSet)) {  //scale_type setting
				logParam.put("scale_type", "1");
			} else {
				logParam.put("scale_type", "2");
			}

			logParam.put("db_svr_id", dbSvrId);
			logParam.put("db_svr_ipadr_id", dbSvrIpadrId);
			logParam.put("wrk_type", wrkType);
			logParam.put("auto_policy", autoPolicy);
			logParam.put("auto_level", autoLevel);
			logParam.put("auto_policy_set_div", autoPolicySetDiv);

			if ("".equals(autoPolicyTime)) {
				autoPolicyTime = "0";	
			}
			logParam.put("auto_policy_time", autoPolicyTime);


			//클러스터는 여기서 셋팅 - 등록시 셋팅		
			logParam.put("wrk_id", 1);
			
			//차후 추가
			logParam.put("process_id", timeId);
		    logParam.put("exe_rslt_cd", "TC001701");

			if (strResult != null) {
				if (strResult.length() > 1000) {
					logParam.put("rslt_msg", strResult.substring(0, 1000));
				} else {
					logParam.put("rslt_msg", strResult);
				}
			} else {
				logParam.put("rslt_msg", "");
			}
			
			logParam.put("fix_rslt_msg", "");
			logParam.put("frst_regr_id", loginId);
			logParam.put("lst_mdfr_id", loginId);

		    logParam.put("saveGbn", saveGbn);
		    logParam.put("return_val", "");

			//CLUSTERS 갯수
		    logParam.put("scale_exe_cnt", scaleCount);
		} else {
			logParam.put("login_id", loginId);
			logParam.put("process_id", timeId);
			logParam.put("wrk_id", 2);
			logParam.put("wrk_end_dtm", nowTime());
		    logParam.put("saveGbn", saveGbn);
		    logParam.put("return_val", retVal);

			if (!retVal.isEmpty()) {
				if (!"success".equals(retVal)) {
					logParam.put("exe_rslt_cd", "TC001702");
					logParam.put("rslt_msg", strResult);
				} else {
					logParam.put("exe_rslt_cd", "TC001701");
					logParam.put("rslt_msg", "");
				}
			}

			logParam.put("process_id", timeId);
		}*/

		return logParam;
	}

	/**
	 * 현재시간 조회
	 * 
	 * @return String
	 * @throws Exception
	 */
	public static String nowTime(){
		Calendar calendar = Calendar.getInstance();				
        java.util.Date date = calendar.getTime();
        String today = (new SimpleDateFormat("yyyyMMddHHmmss").format(date));
		return today;
	}

	public static void main(String[] args) throws Exception {
		ProxyRunCommandExec runCommandExec = new ProxyRunCommandExec();
		
		String path = runCommandExec.getClass().getResource("/").getPath();
		
		System.out.println(path);

		
	}
}
