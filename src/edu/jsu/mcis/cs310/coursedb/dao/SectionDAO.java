package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class SectionDAO {
    
    private static final String QUERY_FIND = "SELECT * FROM section WHERE termid = ? AND subjectid = ? AND num = ? ORDER BY crn";
    
    private final DAOFactory daoFactory;
    
    SectionDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public String find(int termid, String subjectid, String num) {
        
        String result = "[]";
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            System.out.println("DATABASE: " + conn.getCatalog());
            System.out.println("USER: " + conn.getMetaData().getUserName());
            System.out.println("VALID: " + conn.isValid(0));
            
            if (conn.isValid(0)) {
                
                ps = conn.prepareStatement(QUERY_FIND);

                ps.setInt(1, termid);
                ps.setString(2, subjectid);
                ps.setString(3, num);
                
                rs = ps.executeQuery();
                
                System.out.println("HAS ROW: " + rs.isBeforeFirst());
                
                result = DAOUtility.getResultSetAsJson(rs);
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }
    
}