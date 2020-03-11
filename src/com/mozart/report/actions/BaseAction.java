package com.mozart.report.actions;

import java.sql.Connection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.util.JRProperties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.PrincipalAware;
import org.apache.struts2.interceptor.PrincipalProxy;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.util.ServletContextAware;

import com.mozart.report.dao.MozartReportDAO;
import com.mozart.report.util.MozartReportsConstantes;
import com.mozart.report.util.MozartUtil;
import com.mozart.report.vo.HotelVO;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("unchecked")
public class BaseAction extends ActionSupport implements MozartReportsConstantes, PrincipalAware, ServletResponseAware, ServletRequestAware, ServletContextAware {

    /**
	* 
	*/
	private static final long serialVersionUID = -408182901789847198L;
	protected PrincipalProxy proxy;
    protected HttpServletResponse response;
    protected HttpServletRequest request;
    protected ServletContext servletContext;
    protected Logger log = Logger.getLogger(this.getClass());
    protected Long[] idHoteis;    
	protected Map parametros = new HashMap();
    protected MozartReportDAO dao;
    private Connection conexao;
    
    private static Map mapHotel = Collections.synchronizedMap( new HashMap() );
    
    private String strParametros;
    
    
    public BaseAction() {
    	dao = new MozartReportDAO();
        log.setLevel(Level.ALL);
    }

    public void setPrincipalProxy(PrincipalProxy principalProxy) {
        this.proxy = principalProxy;
    }

    public void setServletResponse(HttpServletResponse httpServletResponse) {
        this.response = httpServletResponse;
    }

    public void setServletRequest(HttpServletRequest httpServletRequest) {
        this.request = httpServletRequest;
    }

	
	public Map getParametros() {
		return parametros;
	}

	public void setParametros(Map parametros) {
		this.parametros = parametros;
	}
	
	public Connection getConexao(){
		try{
			conexao = dao.newConnection(); 	
			return conexao;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	public void fecharRecurso(){
		dao.fecharRecurso(conexao, null, null);
	}
	
    protected void warn(String mensagem){
        String user = MozartUtil.getLogin(request);
        MozartUtil.warn(user, mensagem, log);
    }

    
	public void setParameters() throws Exception {
		
		JRProperties.setProperty("net.sf.jasperreports.awt.ignore.missing.font", "true");
		JRProperties.setProperty("net.sf.jasperreports.export.csv.field.delimiter", ";");
		//JRProperties.setProperty("net.sf.jasperreports.extension.registry.factory.fonts", "net.sf.jasperreports.extensions.SpringExtensionsRegistry");
		//JRProperties.setProperty("net.sf.jasperreports.extension.fonts.spring.beans.resource", "true");
		//		=/home/jason/svn/trunk/classes/fonts.xml
		
		parametros = new HashMap();
		/*Cookie[] arr  = request.getCookies();
		for (int x=0;x<arr.length;x++){
			if ("MOZART_REPORT".equals(arr[x].getName())){
				strParametros = arr[x].getValue();
				break;
			}
		}*/
		
		
		strParametros = request.getParameter("strParametros");
		warn("Decriptografando: "+strParametros+".");
		//strParametros = new MozartCryptoReport().decryptString(strParametros);
		
		String[] arrPar = strParametros.split(";");
		
		warn("Setando parametros: "+strParametros+".");
		
		String filtro = "Filtro: ";
		Long idHotel = null;
		for (int x=0;x<arrPar.length;x++){
			String[] vals = arrPar[x].split("@"); 
			String nomeParametro = vals[0];
			String valorParametro = vals.length==2?vals[1]:"";
			valorParametro = "ALL".equals(valorParametro)?"%":valorParametro;
			warn("par: "+nomeParametro+"="+valorParametro);
			parametros.put(nomeParametro, valorParametro);
			if (nomeParametro.startsWith("P_")){
				String parametro = nomeParametro.substring(2);
				parametro = parametro.toLowerCase();
				filtro+= parametro + "="+ valorParametro+", ";
			}
			if  ("IDH".equals(nomeParametro)){
				idHotel = new Long (valorParametro);	
			}
		}
		
		String path = servletContext.getRealPath("WEB-INF/reports");
		warn("real path: " + path);
		
		parametros.put("TEXT_FIELD", filtro.indexOf(",")>0?filtro.substring(0, filtro.length()-2):filtro);
		parametros.put("SUBREPORT_DIR", path+"/");
		
		HotelVO hotelVO = null;
		if (mapHotel.containsKey(idHotel) && (HotelVO)mapHotel.get(idHotel) != null){
			hotelVO = (HotelVO)mapHotel.get(idHotel);
		}else{
			hotelVO = dao.obterDadosHotel(idHotel);
			mapHotel.put(idHotel, hotelVO);
		}
		
		if(hotelVO.getNomeHotel() != null) {
			parametros.put("NOME_HOTEL", hotelVO.getNomeHotel());
		}
		//@<s:property value="#session.HOTEL_SESSION.enderecoLogotipo.startsWith(\"http\")?#session.HOTEL_SESSION.enderecoLogotipo:(#session.URL_BASE+#session.HOTEL_SESSION.enderecoLogotipo)"/>';
		
		String  base = request.getRequestURL().toString().substring(0, request.getRequestURL().toString().indexOf(request.getContextPath()));
		
		parametros.put("LOGO_HOTEL", hotelVO.getLogoHotel().startsWith("http")?hotelVO.getLogoHotel():base+"/Web/"+hotelVO.getLogoHotel());
		parametros.put("LOGO_MOZART", base+"/Web/imagens/Mozart_topo.png");
		parametros.put("IDRH", hotelVO.getIdRedeHotel());
		parametros.put("FRONT_OFFICE", hotelVO.getFrontOffice());

	}

	public String getStrParametros() {
		return strParametros;
	}

	public void setStrParametros(String strParametros) {
		this.strParametros = strParametros;
	}

	public void setServletContext(ServletContext arg0) {
		this.servletContext = arg0;
		
	}
	
}
