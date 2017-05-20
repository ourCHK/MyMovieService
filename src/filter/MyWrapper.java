package filter;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class MyWrapper extends HttpServletRequestWrapper {

	public MyWrapper(HttpServletRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getParameter(String name) {
		// TODO Auto-generated method stub
		String value=this.getRequest().getParameter(name);  
	    String result=null;  
	    try {  
	         if(null!=value)  
	         result=new String(value.getBytes("iso-8859-1"),"utf8");  
	    } catch (UnsupportedEncodingException e) {  
	         // TODO Auto-generated catch block  
	         e.printStackTrace();  
	    }  
	    return result;  
	}	

}
