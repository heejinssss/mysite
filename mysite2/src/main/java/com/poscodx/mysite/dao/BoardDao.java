package com.poscodx.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.GuestbookVo;

public class BoardDao {

	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mariadb://192.168.0.208:3306/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}

		return conn;
	}

	/* 새 게시글 & 답글을 구분하는 기능 추가 필요 */
	public int insert(BoardVo vo) {
		int result = 0;

		try (
			Connection conn = getConnection();
			PreparedStatement pstmt1 = conn.prepareStatement("insert into board(title, contents, reg_date, hit, g_no, o_no, depth, user_no) "
														   + "values(?, ?, sysdate(), 0, ?, ?, ?, ?)");
			PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id() from dual");
		) {	
            pstmt1.setString(1, vo.getTitle());
            pstmt1.setString(2, vo.getContents());
            pstmt1.setLong(3, vo.getgNo());
            pstmt1.setLong(4, vo.getoNo());
            pstmt1.setLong(5, vo.getDepth());
            pstmt1.setLong(6, vo.getUserNo());
            
            result = pstmt1.executeUpdate();

			ResultSet rs = pstmt2.executeQuery();
			vo.setNo(rs.next() ? rs.getLong(1) : null);

		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		return result;
	}

	public List<BoardVo> findAll() {
		List<BoardVo> result = new ArrayList<>();

		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select b.no, b.title, u.name, b.hit, b.reg_date "
															  + "from board b, user u "
															  + "where b.user_no = u.no order by b.no desc");
			ResultSet rs = pstmt.executeQuery();
		) {
			while (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String name = rs.getString(3);
				Long hit = rs.getLong(4);
				String regDate = rs.getString(5);

				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setWriter(name);
				vo.setHit(hit);
				vo.setRegDate(regDate);

				result.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		return result;
	}

	public long getNextGroupNo() {
		int result = 0;

		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select coalesce(max(g_no), 0) + 1 as nextgroupno from board");
			ResultSet rs = pstmt.executeQuery();
		) {
			while (rs.next()) {
				result = rs.getInt("nextGroupNo");
			}
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		return result;
	}

	public List<BoardVo> getBoardByNo(String no) {
		List<BoardVo> result = new ArrayList<>();

		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select no, title, contents, hit, reg_date, g_no, o_no, depth, user_no from board where no = ?");
			ResultSet rs = pstmt.executeQuery();
		) {
			while (rs.next()) {
				Long no1 = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				Long hit = rs.getLong(4);
				String regDate = rs.getString(5);
				Long gNo = rs.getLong(6);
				Long oNo = rs.getLong(7);
				Long depth = rs.getLong(8);
				Long userNo = rs.getLong(9);

				BoardVo vo = new BoardVo();
				vo.setNo(no1);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setgNo(gNo);
				vo.setoNo(oNo);
				vo.setDepth(depth);
				vo.setUserNo(userNo);				
			}
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		return result;
	}

	public int Update(Long gNo, Long gNos) {
		int result = 0;

		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("update board set o_no = o_no + 1 where g_no = ? and o_no >= ?");
			) {
			pstmt.setLong(1, gNo);
			pstmt.setLong(2, gNos);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		
		return result;
	}
	
	public int deleteByNo(Long no) {
		int result = 0;

		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("delete from board where no = ?");
		) {
			pstmt.setLong(1, no);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return result;
	}	
}
