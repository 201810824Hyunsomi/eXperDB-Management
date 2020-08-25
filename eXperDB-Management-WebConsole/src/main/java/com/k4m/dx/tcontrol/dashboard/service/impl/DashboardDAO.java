package com.k4m.dx.tcontrol.dashboard.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.k4m.dx.tcontrol.admin.dbserverManager.service.DbServerVO;
import com.k4m.dx.tcontrol.dashboard.service.DashboardVO;
import com.k4m.dx.tcontrol.scale.service.InstanceScaleVO;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository("dashboardDAO")
public class DashboardDAO extends EgovAbstractMapper{
	
	
	/**
	 * Dashboard 스케쥴 정보 조회
	 * 
	 * @param param
	 * @throws SQLException
	 */
	public DashboardVO selectDashboardScheduleInfo() throws SQLException{
		return (DashboardVO) selectOne("dashboardSql.selectDashboardScheduleInfo");
	}
	
	public DashboardVO selectDashboardBackupInfo() throws SQLException{
		return (DashboardVO) selectOne("dashboardSql.selectDashboardBackupInfo");
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<DashboardVO> selectDashboardServerInfoNew(DashboardVO vo) throws SQLException{
		return (List<DashboardVO>) list("dashboardSql.selectDashboardServerInfoNew", vo);
	}
	
	
	
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<DashboardVO> selectDashboardServerInfo(DashboardVO vo) throws SQLException{
		return (List<DashboardVO>) list("dashboardSql.selectDashboardServerInfo", vo);
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<DashboardVO> selectDashboardBackupDumpInfo(DashboardVO vo) throws SQLException{
		return (List<DashboardVO>) list("dashboardSql.selectDashboardBackupDumpInfo",vo);
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<DashboardVO> selectDashboardBackupRmanInfo(DashboardVO vo) throws SQLException{
		return (List<DashboardVO>) list("dashboardSql.selectDashboardBackupRmanInfo",vo);
	}

	public int selectDashboardScheduleTotal() throws SQLException{
		return (int) selectOne("dashboardSql.selectDashboardScheduleTotal");
	}

	public int selectDashboardScheduleFail() throws SQLException{
		return (int) selectOne("dashboardSql.selectDashboardScheduleFail");
	}

	public int selectDashboardServerTotal() throws SQLException{
		return (int) selectOne("dashboardSql.selectDashboardServerTotal");
	}

	public int selectDashboardServerUse() throws SQLException{
		return (int) selectOne("dashboardSql.selectDashboardServerUse");
	}
	
	public int selectDashboardServerDeath() throws SQLException{
		return (int) selectOne("dashboardSql.selectDashboardServerDeath");
	}

	public int selectDashboardBackupTotal() throws SQLException{
		return (int) selectOne("dashboardSql.selectDashboardBackupTotal");
	}

	public int selectDashboardBackupFail() throws SQLException{
		return (int) selectOne("dashboardSql.selectDashboardBackupFail");
	}

	public int selectDashboardBackupNouse() throws SQLException{
		return (int) selectOne("dashboardSql.selectDashboardBackupNouse");
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<DbServerVO> selectDashboardServer() throws SQLException{
		return (List<DbServerVO>) list("dashboardSql.selectDashboardServer", null);
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Map<String, Object>> selectDashboardScaleInfo() throws SQLException{
		return (List<Map<String, Object>>) list("dashboardSql.selectDashboardScaleInfo", null);
	}

	/**
	 * 백업, 배치 스케줄 조회
	 * 
	 * @param DashboardVO
	 * @throws SQLException
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Map<String, Object>>  selectDashboardScdList(DashboardVO vo) throws SQLException{
		return (List<Map<String, Object>> ) list("dashboardSql.selectDashboardScdList", vo);
	}

	/**
	 * 스케줄 이력 목록 조회
	 * 
	 * @param DashboardVO
	 * @throws SQLException
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Map<String, Object>>  selectDashboardScheduleHistory(DashboardVO vo) throws SQLException{
		return (List<Map<String, Object>> ) list("dashboardSql.selectDashboardScheduleHistory", vo);
	}

	/**
	 * 스케줄 이력 chart 조회
	 * 
	 * @param instanceScaleVO
	 * @throws Exception
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public Map<String, Object> selectDashboardScheduleHistoryChart(DashboardVO vo) {
		return (Map<String, Object>) selectOne("dashboardSql.selectDashboardScheduleHistoryChart", vo);
	}
	
	
}
