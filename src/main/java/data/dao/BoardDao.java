package data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import data.dto.BoardDto;
import mysql.db.DbConnect;

public class BoardDao {
	DbConnect db = new DbConnect();
	
	//total Count
		//����¡ó��_1. ��ü ���� ���ϱ�
		public int getTotalCount() {

			int n = 0;
			Connection conn = db.getConnection();
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			String sql = "select count(*) from board";

			try {
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();

				if(rs.next()) {
					n = rs.getInt(1);				
				}


			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				db.dbClose(rs, pstmt, conn);
			}		

			return n;
		}


		//����¡ó��_2. ����¡�� �ʿ��� ����Ʈ�� ������
		//limit ���� -> limit A, B (A��°���� B�� ����/ ù��°�� 0���� ������)
		
		//����Ʈ �̱� - ī�װ� ������ �������
		public List<BoardDto> getList(int start, int perpage, int category){

			List<BoardDto> list = new Vector<>();

			Connection conn = db.getConnection();
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			String sql = "select * from board where category_id=? order by board_id desc limit ?,?";

			try {

				pstmt = conn.prepareStatement(sql);

				pstmt.setInt(1, category);
				pstmt.setInt(2, start);
				pstmt.setInt(3, perpage);

				rs = pstmt.executeQuery();

				while(rs.next()) {
					BoardDto dto = new BoardDto();

					dto.setBoardId(rs.getInt("board_id"));
					dto.setUserId(rs.getString("user_id"));
					dto.setCategoryId(rs.getInt("category_id"));
					dto.setSubject(rs.getString("subject"));
					dto.setContent(rs.getString("content"));
					dto.setWriteday(rs.getTimestamp("writeday"));

					list.add(dto);
				}


			} catch (Exception e) {
				// TODO: handle exception
			}

			return list;
		}
		
		//����Ʈ �̱� - ī�װ� ������ ������ ���(��ü �� �̱�)
		public List<BoardDto> getList(int start, int perpage){

			List<BoardDto> list = new Vector<>();

			Connection conn = db.getConnection();
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			String sql = "select * from board order by board_id desc limit ?,?";

			try {

				pstmt = conn.prepareStatement(sql);

				pstmt.setInt(1, start);
				pstmt.setInt(2, perpage);

				rs = pstmt.executeQuery();

				while(rs.next()) {
					BoardDto dto = new BoardDto();

					dto.setBoardId(rs.getInt("board_id"));
					dto.setUserId(rs.getString("user_id"));
					dto.setCategoryId(rs.getInt("category_id"));
					dto.setSubject(rs.getString("subject"));
					dto.setContent(rs.getString("content"));
					dto.setWriteday(rs.getTimestamp("writeday"));

					list.add(dto);
				}


			} catch (Exception e) {
				// TODO: handle exception
			}

			return list;
		}

		//QnA �Խ��� �� �ֱ�
		public void insertBoard(BoardDto dto) {
			Connection conn = db.getConnection();
			PreparedStatement pstmt = null;

			String sql = "insert into board values(null,?,?,?,?,,now())";

			try {
				pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, dto.getUserId());
				pstmt.setInt(2, dto.getCategoryId());
				pstmt.setString(3, dto.getSubject());
				pstmt.setString(4, dto.getContent());
				

				pstmt.execute();			

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				db.dbClose(pstmt, conn);
			}		
		}
	
}
