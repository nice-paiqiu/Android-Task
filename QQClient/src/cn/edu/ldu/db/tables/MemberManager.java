package cn.edu.ldu.db.tables;

import cn.edu.ldu.db.beans.Member;
import cn.edu.ldu.db.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MemberManager {  
    public static void displayAllRows() throws SQLException{
        String sql="SELECT * FROM MEMBER";
        ResultSet rs=null; 
        try (
            Connection conn=DBUtils.getConnection(); 
            Statement st=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ){
            rs=st.executeQuery(sql); 
            rs.last();
            int nRows=rs.getRow();
            if (nRows==0) {
                System.out.println("没有找到满足查询条件的记录！\n");
            }else {
                rs.beforeFirst(); 
                StringBuilder buffer=new StringBuilder(); 
                while (rs.next()) { 
                    buffer.append(rs.getInt("id")).append(",");
                    buffer.append(rs.getString("name")).append(",");
                    buffer.append(rs.getString("password")).append(",");
                    buffer.append(rs.getString("email")).append(",");
                    buffer.append(rs.getString("headimage")).append(",");
                    buffer.append(rs.getTimestamp("time")).append("\n");
                }
                System.out.println(buffer.toString());
            }
        }catch (SQLException ex) {
        }finally {
            if (rs!=null) rs.close();
        }
    }
    public static Member getRowById(int id) throws SQLException {
        String sql="SELECT * FROM member WHERE id=?";
        ResultSet rs=null; 
        try (
            Connection conn=DBUtils.getConnection(); 
            PreparedStatement st=conn.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ){     
            st.setInt(1, id); 
            rs=st.executeQuery(); 
            if (rs.next()) { 
                Member bean=new Member(); 
                bean.setId(rs.getInt("id"));
                bean.setName(rs.getString("name"));
                bean.setPassword(rs.getString("password"));
                bean.setEmail(rs.getString("email"));
                bean.setHeadImage(rs.getString("headimage"));
                bean.setTime(rs.getTimestamp("time"));
                return bean; 
            }else { 
                return null;
            }
        } catch (SQLException ex) {
            return null;
        }finally {
            if (rs!=null) rs.close();
        }
    }
    
    public static boolean registerUser(Member bean) throws SQLException{
        if (getRowById(bean.getId())!=null) return false;
        String sql="INSERT INTO member (id,name,password,email,headimage,time) VALUES (?,?,?,?,?,?)";
        try (
            Connection conn=DBUtils.getConnection(); 
            PreparedStatement st=conn.prepareStatement(sql);
            ){
            
            st.setInt(1,bean.getId()); 
            st.setString(2, bean.getName());
            st.setString(3, bean.getPassword());
            st.setString(4, bean.getEmail());
            st.setString(5, bean.getHeadImage());
            st.setTimestamp(6,bean.getTime());
            int affected=st.executeUpdate();
            return affected==1; 
        } catch (SQLException ex) {
            return false;
        }finally {
        }
    }
    
    public static boolean userLogin(Member bean) throws SQLException {
        String sql="SELECT * FROM member WHERE id=? AND password=?";
        ResultSet rs=null;
        try (
            Connection conn=DBUtils.getConnection(); 
            PreparedStatement st=conn.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ){
            st.setInt(1,bean.getId()); 
            st.setString(2, bean.getPassword());
            rs=st.executeQuery(); 
            return rs.next(); 
        } catch (SQLException ex) {
            return false;
        }finally {
            if (rs!=null) rs.close();
        }
    }
    public static boolean updateUser(Member bean) {      
        String sql="UPDATE member SET name=? , password= ? ,"
            + " email=? , headimage= ? , time= ? WHERE id=?";
        try (
            Connection conn=DBUtils.getConnection(); 
            PreparedStatement st=conn.prepareStatement(sql);
            ){           
          
            st.setString(1, bean.getName());
            st.setString(2, bean.getPassword());
            st.setString(3, bean.getEmail());
            st.setString(4,bean.getHeadImage());
            st.setTimestamp(5, bean.getTime());
            st.setInt(6,bean.getId());            
            int affected=st.executeUpdate();
            return affected==1; 
        } catch (SQLException ex) {
            return false;
        }
    }
    
    public static boolean deleteUser(int id) {      
        String sql="DELETE FROM member WHERE id=?";
        try (
             Connection conn=DBUtils.getConnection(); 
             PreparedStatement st=conn.prepareStatement(sql);
            ){                        
            st.setInt(1,id);         
            int affected=st.executeUpdate();
            return affected==1; 
        } catch (SQLException ex) {
            return false;
        }
    } 
}
