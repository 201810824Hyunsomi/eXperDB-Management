package com.k4m.dx.tcontrol.db2pg.setting.service;

import java.util.List;
import java.util.Map;

public interface Db2pgSettingService {

	/**
	 * 공통 코드 조회
	 * 
	 * @param String
	 * @return CodeVO
	 * @throws Exception
	 */
	List<CodeVO> selectCode(String grp_cd) throws Exception;

	/**
	 * DDL WORK 리스트 조회
	 * 
	 * @param Map
	 * @return DDLConfigVO
	 * @throws Exception
	 */
	List<DDLConfigVO> selectDDLWork(Map<String, Object> param) throws Exception;

	/**
	 * Data WORK 리스트 조회
	 * 
	 * @param Map
	 * @return DDLConfigVO
	 * @throws Exception
	 */
	List<DataConfigVO> selectDataWork(Map<String, Object> param) throws Exception;
	
	/**
	 * 추출 대상 테이블 작업 ID SEQ 조회
	 * 
	 * @return int
	 * @throws Exception
	 */
	int selectExrttrgSrctblsSeq() throws Exception;

	/**
	 * 추출 제외 테이블 작업 ID SEQ 조회
	 * 
	 * @return int
	 * @throws Exception
	 */
	int selectExrtexctSrctblsSeq() throws Exception;

	/**
	 * DDL WORK 등록
	 * 
	 * @param ddlConfigVO
	 * @throws Exception
	 */
	void insertDDLWork(DDLConfigVO ddlConfigVO) throws Exception;
	
	/**
	 * Data WORK 등록
	 * 
	 * @param dataConfigVO
	 * @throws Exception
	 */
	void insertDataWork(DataConfigVO dataConfigVO) throws Exception;

}
