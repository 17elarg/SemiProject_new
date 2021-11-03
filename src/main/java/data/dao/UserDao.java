package data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Vector;

import data.dto.UserDto;
import data.dto.UserDto;
import mysql.db.DbConnect;

public class UserDao {
	
	DbConnect db=new DbConnect();
	
	///ȸ�����԰���/////////////////////////////////////////////////////////////////
	//���̵� �ߺ� üũ_boolean(String id)
	public boolean isIdCheck(String user_id)
	{
		boolean isid=false;
		
		Connection conn=db.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		String sql="select*from user where user_id=?";
		
		try {
			pstmt=conn.prepareStatement(sql);
			
			//���ε�
			pstmt.setString(1, user_id);
			
			//����
			rs=pstmt.executeQuery();
			
			if(rs.next()) //�ش���̵� �����Ұ�� true
				isid=true;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return isid;
		
	}
	//���̵� ���� ȸ������ ��ü ��ȸ(String id)
	public String getName(String user_id)
	{
		
		String name="";
		
		Connection conn=db.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		String sql="select*from user where user_id=?";
		
		try {
			pstmt=conn.prepareStatement(sql);
			
			//���ε�
			pstmt.setString(1, user_id);
			
			//����
			rs=pstmt.executeQuery();
			
			if(rs.next()) //�ش���̵� �����Ұ�� true
				name=rs.getString("user_name");
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			db.dbClose(rs, pstmt, conn);
		}

		return name;
	}
	
	//insert�޼���
	public void insertMember(UserDto dto)
	{
		Connection conn=db.getConnection();
		PreparedStatement pstmt=null;
		
		String sql="insert into user (user_name,user_id,user_pw,user_hp,user_addr,user_point,user_joinday) values (?,?,?,?,?,?,now())";
		
		try {
			pstmt=conn.prepareStatement(sql);
			
			//���ε�
			pstmt.setString(1, dto.getUser_name());
			pstmt.setString(2, dto.getUser_id());
			pstmt.setString(3, dto.getUser_pw());
			pstmt.setString(4, dto.getUser_hp());
			pstmt.setString(5, dto.getUser_addr());
			pstmt.setInt(6, dto.getUser_point());
			
			//����
			pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			db.dbClose(pstmt, conn);
		}
			
		
	}
	
	///�α��ΰ���/////////////////////////////////////////////////////////////////
	//�α����ҋ� ���̵� ��� üũ
			
	public boolean isIdPass(String user_id,String user_pw)
	{
		boolean b=false;
		
		Connection conn=db.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
				
		String sql="select * from user where user_id=? and user_pw=?";
				
		try {
			pstmt=conn.prepareStatement(sql);
					
			//���ε�
			pstmt.setString(1, user_id);
			pstmt.setString(2, user_pw);
			
			//����
			rs=pstmt.executeQuery();
			
			if(rs.next())
				{
					b=true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				db.dbClose(rs, pstmt, conn);
			}
				
				
				
			return b;
		}

	//(������ �����Ҷ�)��й�ȣ üũ -> id�� �´� ��������� Ȯ��
	public boolean isPassEqual(String user_id,String user_pw)
	{
		boolean b=false;
		
		Connection conn=db.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		String sql="select * from user where useR_id=? and user_pw=?";
		
		try {
			pstmt=conn.prepareStatement(sql);
			
			//���ε�
			pstmt.setString(1, user_id);
			pstmt.setString(2, user_pw);
			
			rs=pstmt.executeQuery();
			
			if(rs.next())
			{
				b=true;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			db.dbClose(rs, pstmt, conn);
		}
		
		
		return b;
	}
	

	
	///����,��������/////////////////////////////////////////////////////////////////
	//id�� �´� dto ��ȯ
	public UserDto getUser(String user_id)
	{
		Connection conn=db.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		UserDto dto=new UserDto();
		
		String sql="select * from user where user_id=?";
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, user_id);
			
			rs=pstmt.executeQuery();
			
			if(rs.next())
			{
				dto.setUser_id(rs.getString("user_id"));
				dto.setUser_pw(rs.getString("user_pw"));
				dto.setUser_name(rs.getString("user_name"));
				dto.setUser_hp(rs.getString("user_hp"));
				dto.setUser_addr(rs.getString("user_addr"));
				dto.setUser_joinday(rs.getTimestamp("user_joinday"));
				dto.setIs_admin(rs.getString("is_admin"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			db.dbClose(rs, pstmt, conn);
		}
		
		
		
		return dto;
	}
	
	//�����ϴ� �޼��� // ���̵� ��Ī�Ǵ� �ŷ� ���, ��ȭ��ȣ, �̸�, �ּ� ����
	public void updateUser(UserDto dto)
	{

		Connection conn=db.getConnection();
		PreparedStatement pstmt=null;
		//�̸�,�ڵ���,�ּ�,�̸���
		String sql="update user set user_name=?,user_pw=?,user_hp=?,user_addr=? where user_id=?";
		
		try {
			pstmt=conn.prepareStatement(sql);
			
			//���ε�
			pstmt.setString(1, dto.getUser_name());
			pstmt.setString(2, dto.getUser_pw());
			pstmt.setString(3, dto.getUser_hp());
			pstmt.setString(4, dto.getUser_addr());
			pstmt.setString(5, dto.getUser_id());
			
			//����
			pstmt.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			db.dbClose(pstmt, conn);
		}
		
		
	}
	
	//admin�� ���� or ȸ��Ż�� (id�� ��ȸ)
	public void deleteUser(String user_id)
	{
		Connection conn=db.getConnection();
		PreparedStatement pstmt=null;
		
		String sql="delete from user where user_id=?";
		
		try {
			pstmt=conn.prepareStatement(sql);
			
			//���ε�
			pstmt.setString(1, user_id);
			
			//����
			pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			db.dbClose(pstmt, conn);
		}
		
	}
	
	


}
	
	
			