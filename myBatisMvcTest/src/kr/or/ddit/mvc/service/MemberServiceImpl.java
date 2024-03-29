package kr.or.ddit.mvc.service;

import java.util.HashMap;
import java.util.List;

import kr.or.ddit.mvc.dao.IMemberDao;
import kr.or.ddit.mvc.dao.MemberDaoImpl;
import kr.or.ddit.mvc.vo.MemberVO;

public class MemberServiceImpl implements IMemberService {
	
	private static MemberServiceImpl service;
	
	
	//DAO 객체의 참조값이 저장될 변수 선언
	private IMemberDao dao;
	// 생성자
	private MemberServiceImpl() {
//		dao = new MemberDaoImpl(); // DAO객체 생성
		dao = MemberDaoImpl.getInstance(); // DAO객체 생성
	}
	
	public static MemberServiceImpl getInstance() {
		if(service ==null) service = new MemberServiceImpl();
		return service;
	}
	
	@Override
	public int insertMember(MemberVO memVo) {
		return dao.insertMember(memVo);
	}

	@Override
	public int deleteMember(String memId) {
		return dao.deleteMember(memId);
	}

	@Override
	public int updateMember(MemberVO memVo) {
		return dao.updateMember(memVo);
	}

	@Override
	public List<MemberVO> getAllMember() {
		return dao.getAllMember();
	}

	@Override
	public int getMemberCount(String memId) {
		return dao.getMemberCount(memId);
	}

	@Override
	public int updateMember2(HashMap<String, String> updateData) {
		return dao.updateMember2(updateData);
	}

}
