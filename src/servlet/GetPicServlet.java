package servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PicServlet
 */
@WebServlet("/GetPicServlet")
public class GetPicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPicServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//        String imagePath = "/home/chk/Downloads/pic/p0.jpg";
		String imagePath = "";
        imagePath = request.getParameter("path");
        if (imagePath == null || imagePath.isEmpty()) {		//判断地址是否为空
        	response.setContentType("text/html");
        	PrintWriter out = response.getWriter();
        	out.write("请确保path地址不为空");
        	out.flush();
        	out.close();
        	return;
        }      	
        FileInputStream fis = new FileInputStream(imagePath);  
        int size =fis.available(); //得到文件大小   
        byte data[]=new byte[size];   
        fis.read(data);  //读数据   
        fis.close();   
        response.setContentType("image/gif"); //设置返回的文件类型   
        OutputStream os = response.getOutputStream();  
        os.write(data);  
        os.flush();  
        os.close(); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
