package com.k4m.dx.tcontrol.transfer.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.k4m.dx.tcontrol.transfer.service.TransDbmsVO;
import com.k4m.dx.tcontrol.transfer.service.TransMappVO;
import com.k4m.dx.tcontrol.transfer.service.TransRegiVO;
import com.k4m.dx.tcontrol.transfer.service.TransVO;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository("TransDAO")
public class TransDAO extends EgovAbstractMapper{

	/**
	 * 소스시스템 전송설정 조회
	 * 
	 * @param transVO
	 * @return List<Map<String, Object>>
	 * @throws 
	 */
	public List<Map<String, Object>> selectTransSetting(TransVO transVO) {
		List<Map<String, Object>>  sl = null;
		sl = (List<Map<String, Object>>) list("transSQL.selectTransSetting",transVO);
		return sl;
	}

	/**
	 * trans DBMS시스템 리스트 조회
	 * 
	 * @param transDbmsVO
	 * @return List<TransDbmsVO>
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<TransDbmsVO> selectTransDBMS(TransDbmsVO transDbmsVO) {
		List<TransDbmsVO> sl = null;
		sl = (List<TransDbmsVO>) list("transSQL.selectTransDBMS", transDbmsVO);
		return sl;
	}

	/**
	 * trans DBMS시스템 명 체크
	 * 
	 * @param trans_sys_nm
	 * @return int
	 * @throws Exception
	 */
	public int trans_sys_nmCheck(String trans_sys_nm) {
		int resultSet = 0;
		resultSet = (int) getSqlSession().selectOne("transSQL.trans_sys_nmCheck", trans_sys_nm);
		return resultSet;
	}

	/**
	 * trans DBMS시스템 등록
	 * 
	 * @param transDbmsVO
	 * @throws Exception
	 */
	public void insertTransDBMS(TransDbmsVO transDbmsVO) {
		insert("transSQL.insertTransDBMS", transDbmsVO);
	}
	
	/**
	 * trans DBMS시스템 사용여부 확인
	 * 
	 * @param transDbmsVO
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object> selectTransDbmsIngChk(TransDbmsVO transDbmsVO) throws SQLException {
		return (Map<String, Object>) selectOne("transSQL.selectTransDbmsIngChk", transDbmsVO);
	}

	/**
	 * trans DBMS시스템 수정
	 * 
	 * @param transDbmsVO
	 * @throws Exception
	 */
	public void updateTransDBMS(TransDbmsVO transDbmsVO) {
		update("transSQL.updateTransDBMS", transDbmsVO);
	}

	/**
	 * trans DBMS시스템 삭제
	 * 
	 * @param transDbmsVO
	 * @throws Exception
	 */
	public void deleteTransDBMS(TransDbmsVO transDbmsVO) {
		delete("transSQL.deleteTransDBMS", transDbmsVO);
	}

	/**
	 * 타겟시스템 전송설정 조회
	 * 
	 * @param transVO
	 * @return List<Map<String, Object>>
	 * @throws 
	 */
	public List<Map<String, Object>> selectTargetTransSetting(TransVO transVO) {
		List<Map<String, Object>>  sl = null;
		sl = (List<Map<String, Object>>) list("transSQL.selectTargetTransSetting",transVO);
		return sl;
	}

	/**
	 * 소스시스템 전송설정 단건 조회
	 * 
	 * @param trans_id
	 * @return List<Map<String, Object>>
	 * @throws 
	 */
	public List<Map<String, Object>> selectTransInfo(int trans_id) {
		List<Map<String, Object>>  sl = null;
		sl = (List<Map<String, Object>>) list("transSQL.selectTransInfo",trans_id);
		return sl;
	}

	/**
	 * 테이블 매핑정보 조회
	 * 
	 * @param trans_exrt_trg_tb_id
	 * @return List<Map<String, Object>>
	 * @throws 
	 */
	public List<Map<String, Object>> selectMappInfo(int trans_exrt_trg_tb_id) {
		List<Map<String, Object>>  sl = null;
		sl = (List<Map<String, Object>>) list("transSQL.selectMappInfo",trans_exrt_trg_tb_id);
		return sl;
	}

	/**
	 * 타겟시스템 전송설정 단건 조회
	 * 
	 * @param trans_id
	 * @return List<Map<String, Object>>
	 * @throws 
	 */
	public List<Map<String, Object>> selectTargetTransInfo(int trans_id) {
		List<Map<String, Object>>  sl = null;
		sl = (List<Map<String, Object>>) list("transSQL.selectTargetTransInfo",trans_id);
		return sl;
	}

	/**
	 * 소스시스템 전송설정 삭제
	 * 
	 * @param trans_id
	 * @return 
	 * @throws 
	 */
	public void deleteTransSetting(int trans_id) {
		delete("transSQL.deleteTransSetting",trans_id);	
	}

	/**
	 * 타겟시스템 전송설정 삭제
	 * 
	 * @param trans_id
	 * @return 
	 * @throws 
	 */
	public void deleteTransTargetSetting(int trans_id) {
		delete("transSQL.deleteTransTargetSetting",trans_id);	
	}

	/**
	 * trans connect명 중복체크
	 * 
	 * @param trans_id
	 * @return int
	 * @throws 
	 */
	public int connect_nm_Check(String connect_nm) {
		int resultSet = 0;
		resultSet = (int) getSqlSession().selectOne("transSQL.connect_nm_Check", connect_nm);
		return resultSet;
	}

	/**
	 * trans target connect명 중복체크
	 * 
	 * @param trans_id
	 * @return int
	 * @throws 
	 */
	public int connect_target_nm_Check(String connect_nm) {
		int resultSet = 0;
		resultSet = (int) getSqlSession().selectOne("transSQL.connect_target_nm_Check", connect_nm);
		return resultSet;
	}
	
	/**
	 * 포함대상 스키마,테이블 시퀀스 조회
	 * @param connect_nm
	 * @return int
	 * @throws Exception
	 */
	public int selectTransExrttrgMappSeq() {
		return (int) getSqlSession().selectOne("transSQL.selectTransExrttrgMappSeq");
	}

	/**
	 * 포함대상 스키마,테이블 등록
	 * @param transMappVO
	 * @return 
	 * @throws Exception
	 */
	public void insertTransExrttrgMapp(TransMappVO transMappVO) {
		insert("transSQL.insertTransExrttrgMapp",transMappVO);
	}

	/**
	 * 전송설정 등록
	 * @param TransVO
	 * @throws Exception
	 */
	public void insertConnectInfo(TransVO transVO) {
		insert("transSQL.insertConnectInfo",transVO);
	}

	/**
	 * 타겟시스템 전송설정 등록
	 * @param TransVO
	 * @throws Exception
	 */
	public void insertTargetConnectInfo(TransVO transVO) {
		insert("transSQL.insertTargetConnectInfo",transVO);
	}

	/**
	 * 포함대상 스키마,테이블 수정
	 * @param transMappVO
	 * @return 
	 * @throws Exception
	 */
	public void updateTransExrttrgMapp(TransMappVO transMappVO) {
		update("transSQL.updateTransExrttrgMapp",transMappVO);	
	}
	
	/**
	 * 전송설정 정보 수정
	 * @param transVO
	 * @return vo
	 * @throws Exception
	 */
	public void updateConnectInfo(TransVO transVO) {
		update("transSQL.updateConnectInfo",transVO);	
	}
	
	/**
	 * Target 전송설정 정보 수정
	 * @param transVO
	 * @return vo
	 * @throws Exception
	 */
	public void updateTargetConnectInfo(TransVO transVO) {
		update("transSQL.updateTargetConnectInfo",transVO);	
	}

	public void deleteTransExrttrgMapp(int trans_exrt_trg_tb_id) {
		delete("transSQL.deleteTransExrttrgMapp",trans_exrt_trg_tb_id);	
	}

	/**
	 * 소스시스템 auto 전송설정 단건 조회
	 * 
	 * @param trans_id
	 * @return List<Map<String, Object>>
	 * @throws 
	 */
	public List<Map<String, Object>> selectTransInfoAuto(int db_svr_id) {
		List<Map<String, Object>>  sl = null;
		sl = (List<Map<String, Object>>) list("transSQL.selectTransInfoAuto",db_svr_id);
		return sl;
	}

	/**
	 * 타겟시스템 auto 전송설정 단건 조회
	 * 
	 * @param trans_id
	 * @return List<Map<String, Object>>
	 * @throws 
	 */
	public List<Map<String, Object>> selectTargetTransInfoAuto(int db_svr_id) {
		List<Map<String, Object>>  sl = null;
		sl = (List<Map<String, Object>>) list("transSQL.selectTargetTransInfoAuto",db_svr_id);
		return sl;
	}

	/**
	 * 기본설정 등록 조회
	 * @param transVO, request, historyVO
	 * @return Map<String, Object>
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public Map<String, Object> selectTransComSettingCngInfo(TransVO transVO) {
		return (Map<String, Object>) selectOne("transSQL.selectTransComSettingCngInfo", transVO);
	}
	
	/**
	 * 기본설정 등록
	 * 
	 * @param instanceScaleVO
	 * @throws Exception
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public void insertTransCommonSetting(TransVO transVO) {
		insert("transSQL.insertTransCommonSetting", transVO);	
	}

	/**
	 * 기본설정 등록 수정
	 * 
	 * @param instanceScaleVO
	 * @throws Exception
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public void updateTransCommonSetting(TransVO transVO) {
		update("transSQL.updateTransCommonSetting", transVO);	
	}

	/**
	 * 기본설정 리스트 조회
	 * 
	 * @param transVO
	 * @return List<Map<String, Object>>
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Map<String, Object>> selectTransComConPopList(TransVO transVO) {
		List<Map<String, Object>> sl = null;
		sl = (List<Map<String, Object>>) list("transSQL.selectTransComConPopList", transVO);
		return sl;
	}

	/**
	 * 기본설정 삭제
	 * 
	 * @param transVO
	 * @throws Exception
	 */
	public void deleteTransComConSet(TransVO transVO) {
		delete("transSQL.deleteTransComConSet", transVO);
	}

	/**
	 * trans topic 수정
	 * 
	 * @param transVO
	 * @throws Exception
	 */
	public void updateTransTarTopic(TransVO transVO) {
		update("transSQL.updateTransTarTopic", transVO);
	}
	
	/**
	 * trans 타겟 커넥터 seq
	 * @param 
	 * @return int
	 * @throws
	 */
	public int selectTargetConnectSeq() {
		return (int) getSqlSession().selectOne("transSQL.selectTargetConnectSeq");
	}

	/**
	 * trans topic 초기화
	 * 
	 * @param transVO
	 * @throws Exception
	 */
	public void updateTransTarTopicChogihwa(TransVO transVO) {
		update("transSQL.updateTransTarTopicChogihwa", transVO);
	}
	
	/**
	 * trans topic 리스트 조회
	 * 
	 * @param transVO
	 * @return List<Map<String, Object>>
	 * @throws 
	 */
	public List<TransVO> selectTransTopicList(TransVO transVO) {
		List<TransVO>  sl = null;
		sl = (List<TransVO>) list("transSQL.selectTransTopicList",transVO);
		return sl;
	}

	/**
	 * 소스시스템 topic 삭제
	 * 
	 * @param trans_id
	 * @return 
	 * @throws 
	 */
	public void deleteTransTopicSetting(int trans_id) {
		delete("transSQL.deleteTransTopicSetting",trans_id);	
	}
	
	/**
	 * trans 타겟 전송관리 테이블 조회
	 * 
	 * @param transVO
	 * @return List<Map<String, Object>>
	 * @throws 
	 */
	public List<TransVO> selectTranExrtTrgList(TransVO transVO) {
		List<TransVO>  sl = null;
		sl = (List<TransVO>) list("transSQL.selectTranExrtTrgList",transVO);
		return sl;
	}
	
	/**
	 * trans topic 리스트  조회
	 * 
	 * @param transVO
	 * @return List<TransVO>
	 * @throws 
	 */
	public List<TransVO> selectTranIdTopicList(TransVO transVO) {
		List<TransVO>  sl = null;
		sl = (List<TransVO>) list("transSQL.selectTranIdTopicList",transVO);
		return sl;
	}
	
	/**
	 * trans target topic 리스트  조회
	 * 
	 * @param transVO
	 * @return List<TransVO>
	 * @throws 
	 */
	public List<TransVO> selectTranTargetIdTopicList(TransVO transVO) {
		List<TransVO>  sl = null;
		sl = (List<TransVO>) list("transSQL.selectTranTargetIdTopicList",transVO);
		return sl;
	}

	/**
	 * trans 타겟 전송관리 테이블 수정
	 * 
	 * @param transDbmsVO
	 * @throws Exception
	 */
	public void updateTranExrtTrgInfo(TransVO transVO) {
		update("transSQL.updateTranExrtTrgInfo", transVO);
	}

	/**
	 * 소스 / 타겟 connect log 삭제
	 * 
	 * @param transVO
	 * @throws Exception
	 */
	public void deleteTransConnectLog(TransVO transVO) {
		delete("transSQL.deleteTransConnectLog", transVO);
	}

	/**
	 * trans heatbeat 체크
	 * 
	 * @param transVO
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object> selectTransComCoIngChk(TransVO transVO) throws SQLException {
		return (Map<String, Object>) selectOne("transSQL.selectTransComCoIngChk", transVO);
	}
}