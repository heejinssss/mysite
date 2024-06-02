package com.poscodx.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.poscodx.mysite.vo.BoardVo;

public class BoardDao {

    private static Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            String url = "jdbc:mariadb://192.168.219.138:3306/webdb?charset=utf8";
            conn = DriverManager.getConnection(url, "webdb", "webdb");
        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로딩 실패:" + e);
        }
        return conn;
    }

    public Boolean insert(BoardVo vo) {
        boolean result = false;

        try (
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(
                        "insert into board(no, title, contents, reg_date, hit, g_no, o_no, depth, user_no) "
                       + "values(null, ?, ?, sysdate(), ?, ?, ?, ?, ?)")
        ) {
            pstmt.setString(1, vo.getTitle());
            pstmt.setString(2, vo.getContents());
            pstmt.setInt(3, vo.getHit());
            pstmt.setInt(4, vo.getGroupNo());
            pstmt.setInt(5, vo.getOrderNo());
            pstmt.setInt(6, vo.getDepth());
            pstmt.setLong(7, vo.getUserNo());

            int count = pstmt.executeUpdate();
            result = count == 1;

        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return result;
    }

    public BoardVo findByNo(Long findNo) {
        BoardVo vo = null;

        try(
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select a.no, a.title, a.contents, a.hit, a.reg_date, a.g_no, a.o_no, a.depth, b.no, b.name from board a, user b where a.user_no = b.no and a.no = ?")
		) {
            pstmt.setLong(1, findNo);

			try (
				ResultSet rs = pstmt.executeQuery()
			) {
				while (rs.next()) {
					Long no = rs.getLong(1);
					String title = rs.getString(2);
					String content = rs.getString(3);
					int hit = rs.getInt(4);
					String regDate = rs.getString(5);
					int groupNo = rs.getInt(6);
					int orderNo = rs.getInt(7);
					int depths = rs.getInt(8);
					Long userNo = rs.getLong(9);
					String userName = rs.getString(10);

					vo = new BoardVo();
					vo.setNo(no);
					vo.setTitle(title);
					vo.setContents(content);
					vo.setHit(hit);
					vo.setRegDate(regDate);
					vo.setGroupNo(groupNo);
					vo.setOrderNo(orderNo);
					vo.setDepth(depths);
					vo.setUserNo(userNo);
					vo.setUserName(userName);
				}
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e);
		}
		return vo;
	}

    public Boolean delete(Long no) {
        boolean result = false;

        try (
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement("delete from board where no = ?");
        ) {
            pstmt.setLong(1, no);
            int count = pstmt.executeUpdate();
            result = count == 1;
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return result;
    }

    public int findSearchCount(String keyword) {
        int count = 0;

        try (
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select count(*) from board a, user b where a.user_no = b.no and a.title like ?")
        ) {
            pstmt.setString(1, "%" + keyword + "%");

            try (
        		ResultSet rs = pstmt.executeQuery()
    		) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return count;
    }

    public List<BoardVo> findSearch(String keyword, int first, int second) {
        List<BoardVo> result = new ArrayList<>();

		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select a.no, a.title, a.contents, a.reg_date, a.depth, a.hit, b.no as user_no, b.name "
														  + "from board a, user b "
														  + "where a.user_no = b.no and a.title like '%" + keyword + "%' "
														  + "order by  a.g_no  desc, a.o_no asc limit ? , ?")
		) {
            pstmt.setInt(1, first);
            pstmt.setInt(2, second);

            try (
        		ResultSet rs = pstmt.executeQuery()
    		) {
                while (rs.next()) {
                    Long no = rs.getLong(1);
                    String title = rs.getString(2);
                    String contents = rs.getString(3);
                    String regDate = rs.getString(4);
                    int depth = rs.getInt(5);
                    int hit = rs.getInt(6);
                    Long userNo = rs.getLong(7);
                    String userName = rs.getString(8);

                    BoardVo vo = new BoardVo();
                    vo.setNo(no);
                    vo.setTitle(title);
                    vo.setContents(contents);
                    vo.setRegDate(regDate);
                    vo.setDepth(depth);
                    vo.setHit(hit);
                    vo.setUserNo(userNo);
                    vo.setUserName(userName);

                    result.add(vo);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return result;
    }

    public BoardVo updateView(Long no, String title, String contents) {
        BoardVo vo = null;
        boolean result = false;
        try (
    		Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement("update board set title = ?, contents = ? where no = ?");
        ) {
            pstmt.setString(1, title);
            pstmt.setString(2, contents);
            pstmt.setLong(3, no);

            int count = pstmt.executeUpdate();
            result = count == 1;
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return vo;
    }

    public int findMaxGroupNo() {
        int result = 0;

        try (
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select ifnull(max(g_no),1) from board");
		) {
            try (
        		ResultSet rs = pstmt.executeQuery()
    		) {
                if (rs.next()) {
                    result = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return result;
    }

    public void updateHit(Long no) {
        boolean result = false;

        try (
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement("update board set hit = hit + 1 where no = ?");
        ) {
            pstmt.setLong(1, no);

            int count = pstmt.executeUpdate();
            result = count == 1;
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
    }

    public static int findDepth(int groupNo, int depth) {
        int result = 0;

        try (
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select count(*) from board where g_no = ? and depth = ?");
        ) {
            pstmt.setLong(1, groupNo);
            pstmt.setLong(2, depth);

            try (
        		ResultSet rs = pstmt.executeQuery()
    		) {
                if (rs.next()) {
                    result = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return result;
    }

    public static int findNextDepth(int groupNo, int depth) {
        int result = 0;

        try (
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select count(*) from board where g_no = ? and depth = ?");
        ) {
            pstmt.setLong(1, groupNo);
            pstmt.setLong(2, (depth + 1));

            try (
        		ResultSet rs = pstmt.executeQuery()
        	) {
                if (rs.next()) {
                    result = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return result;
    }

    public boolean updateOrder(int groupNo, int orderNo) {
        boolean result = false;

        try (
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement("update board set o_no = o_no + 1 where g_no = ? and o_no > ?");
        ) {
            pstmt.setLong(1, groupNo);
            pstmt.setLong(2, orderNo);

            int count = pstmt.executeUpdate();
            result = count == 1;
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return result;
    }
}
