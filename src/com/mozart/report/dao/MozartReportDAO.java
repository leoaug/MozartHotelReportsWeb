package com.mozart.report.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mozart.report.exception.MozartReportException;
import com.mozart.report.vo.HotelVO;

/***/
public class MozartReportDAO {
	    public MozartReportDAO() {
	    }
	    
	    public Connection newConnection() throws MozartReportException{
	        String mensagem = "Obtendo concexao\n";
	        try{
	            return ((DataSource)new InitialContext().lookup("java:/XAMOZARTDS")).getConnection();
	        }catch(Exception ex){
	            ex.printStackTrace();
	            throw new MozartReportException(mensagem +"-"+ex.getMessage());
	        }

	    }
	    
	    public void fecharRecurso(Connection conexao, ResultSet rs, PreparedStatement st){
	        
	        try{
	            if (conexao != null){
	                conexao.close();
	            }
	        }catch(Exception ex){}
	        
	        try{
	            if (rs != null){
	                rs.close();
	            }
	        }catch(Exception ex){}
	        
	        try{
	            if (st != null){
	                st.close();
	            }
	        }catch(Exception ex){}
	    
	    }
	    
	    public HotelVO obterDadosHotel (Long idHotel){
	    	
	    	HotelVO hotelVO = null;
	    	Connection conexao = null;
	    	PreparedStatement pst = null;
	    	ResultSet rs = null;
	    	String sql = " SELECT H.ID_REDE_HOTEL, \n"
				    			+ " H.NOME_FANTASIA,  \n"
				    			+ " H.ENDERECO_LOGOTIPO, \n"
				    			+ " to_char(CD.FRONT_OFFICE, 'dd/mm/yyyy') FRONT_OFFICE \n"+
			    			" FROM HOTEL H, CONTROLA_DATA CD \n"+
			    			" WHERE H.ID_HOTEL = CD.ID_HOTEL \n"+
			    			" AND H.ID_HOTEL = "+ idHotel;
	    	
	    	try {
	    		conexao = newConnection();
	    		pst = conexao.prepareStatement(sql);
	    		//pst.setLong(1, idHotel);
	    		rs = pst.executeQuery();
	    		
	    		if (rs.next()){	    			
	    			hotelVO = new HotelVO (rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));	
	    		}	    		
	    	} catch (Exception ex){
	    		ex.printStackTrace();
	    		
	    	} finally {
	    		fecharRecurso(conexao, rs, pst);
	    		
	    	}
	    	return hotelVO;
	    	
	    	
	    	
	    }
	    
	    
	}

	

