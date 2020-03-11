package com.mozart.report.util;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class MozartUtil {

    public MozartUtil() {
    }
     
    public static void warn(String login, String mensagem, Logger log){
        log.warn(formatLogin(login) + ":" + mensagem);
    }

    public static void info(String login, String mensagem, Logger log){
        log.info(formatLogin(login) + ":" + mensagem);
    }

    public static void error(String login, String mensagem, Logger log){
        log.error(formatLogin(login) + ":" + mensagem);
    }
    
    private static String formatLogin(String login){
        return "["+login+"]";
    }
    
    public static String getLogin(HttpServletRequest request){
    	return "system";
    }
    
    
}
