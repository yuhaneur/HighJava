package kr.or.ddit.mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import kr.or.ddit.mvc.vo.MemberVO;
import kr.or.ddit.util.DBUtil3;

public class MemberDaoImpl implements IMemberDao{

	private Logger logger = Logger.getLogger(MemberDaoImpl.class);
	
	private static MemberDaoImpl dao;
	
	private MemberDaoImpl() {}
	
	public static MemberDaoImpl getInstance() {
		if(dao ==null) dao = new MemberDaoImpl();
		
		return dao;
	}
	
	@Override
	public int insertMember(MemberVO memVo) {
		int cnt = 0;	// 반환값이 저장될 변수
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBUtil3.getConnection();
			logger.debug("Connection객체 생성...");
			
			String sql = "insert into mymember values(?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memVo.getMem_id());
			pstmt.setString(2, memVo.getMem_pass());
			pstmt.setString(3, memVo.getMem_name());
			pstmt.setString(4, memVo.getMem_tel());
			pstmt.setString(5, memVo.getMem_addr());
			
			logger.debug("PreparedStatement객체 생성");
			logger.debug("실행 SQL : " + sql);
			logger.debug("사용 데이터 : [" + memVo.getMem_id() + "," + memVo.getMem_pass()
			+ "," + memVo.getMem_name() + "," + memVo.getMem_tel() + ", " + memVo.getMem_addr());
			
			
			cnt = pstmt.executeUpdate();
			logger.info("insert 작업 성공!!!");
		} catch (SQLException e) {
			logger.error("insert 작업 실패~~~",e);
			e.printStackTrace();
		} finally {
			if(conn!=null)try {conn.close();}catch(SQLException e ) {}
			if(pstmt!=null)try {pstmt.close();}catch(SQLException e ) {}
			logger.info("사용했던 자원 반납...");
		}
		return cnt;
	}

	@Override
	public int deleteMember(String memId) {
		Connection conn =null;
		PreparedStatement pstmt = null;
		int cnt = 0;
		try {
			conn = DBUtil3.getConnection();
			logger.debug("conn 객체 연결");
			String sql = "delete from mymember where mem_id= ? ";
			pstmt= conn.prepareStatement(sql);
			logger.debug("실행 SQL : " + sql);
			pstmt.setString(1, memId);
			
			cnt = pstmt.executeUpdate();
			logger.debug("delete 작업 성공");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("에러 : " + e );
		}finally {
			if(conn!=null)try {conn.close();}catch(SQLException e ) {}
			if(pstmt!=null)try {pstmt.close();}catch(SQLException e ) {}
			logger.debug("사용했던 자원 반납...");
		}
		return cnt;
	}

	@Override
	public int updateMember(MemberVO memVo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int cnt = 0;
		try {
			conn= DBUtil3.getConnection();
			logger.debug("conn 객체 연결 성공!!");
			String sql = "update MYMEMBER SET MEM_NAME=?,MEM_TEL=?,MEM_ADDR=? where MEM_ID=?";
			pstmt = conn.prepareStatement(sql);
			logger.info("실행 SQL : "  + sql);
			pstmt.setString(1, memVo.getMem_name());
			pstmt.setString(2, memVo.getMem_tel());
			pstmt.setString(3, memVo.getMem_addr());
			pstmt.setString(4, memVo.getMem_id());
			
			cnt = pstmt.executeUpdate();
			logger.debug("update 작업 성공!!");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("에러 !!: " + e );
		}finally {
			if(conn!=null)try {conn.close();}catch(SQLException e ) {}
			if(pstmt!=null)try {pstmt.close();}catch(SQLException e ) {}
			logger.debug("사용했던 자원 반납...");
		}
		return cnt;
	}

	@Override
	public List<MemberVO> getAllMember() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<MemberVO> list = new ArrayList<MemberVO>();
		try {
			conn = DBUtil3.getConnection();
			logger.debug("conn 객체 연결 !!");
			String sql = "select * from mymember";
			logger.debug("실행 SQL : " + sql);
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MemberVO memVo = new MemberVO(); 
				
				memVo.setMem_id(rs.getString(1));
				memVo.setMem_pass(rs.getString(2));
				memVo.setMem_name(rs.getString(3));
				memVo.setMem_tel(rs.getString(4));
				memVo.setMem_addr(rs.getString(5));
				
				list.add(memVo);
				
			}
			logger.debug("select 작업 성공!!");
		} catch (SQLException e) {
			logger.error("에러 !! " + e);
			e.printStackTrace();
			
		}finally {
			if(conn!=null)try {conn.close();}catch(SQLException e ) {}
			if(pstmt!=null)try {pstmt.close();}catch(SQLException e ) {}
			if(rs!=null)try {rs.close();}catch(SQLException e ) {}
			logger.debug("사용 자원 반납");
		}
		return list;
	}

	@Override
	public int getMemberCount(String memId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int cnt= 0;
		try {
			conn = DBUtil3.getConnection();
			String sql = "select count(*) from mymember where mem_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memId);
			rs= pstmt.executeQuery();
			
			
			if(rs.next()) {
				cnt = rs.getInt(1);				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn!=null)try {conn.close();}catch(SQLException e ) {}
			if(pstmt!=null)try {pstmt.close();}catch(SQLException e ) {}
			if(rs!=null)try {rs.close();}catch(SQLException e ) {}
		}
		return cnt;
	}

	@Override
	public int updateMember2(HashMap<String, String> updateData) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		int cnt =0;
		try {
			String sql = "update mymember set " + updateData.get("field") + " = ? where mem_id = ?";
			conn=DBUtil3.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, updateData.get("data"));
			pstmt.setString(2, updateData.get("id"));
			cnt = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(conn!=null)try {conn.close();}catch(SQLException e ) {}
			if(pstmt!=null)try {pstmt.close();}catch(SQLException e ) {}
		}
		return cnt;
	}

}
