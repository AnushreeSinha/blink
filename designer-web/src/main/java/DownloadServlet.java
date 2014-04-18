import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
 
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 

public class DownloadServlet extends javax.servlet.http.HttpServlet implements
javax.servlet.Servlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2954403040489130821L; 
	private static final int BUFSIZE = 4096;
    private String filePath;
    
    public void init() {
       
    }
    
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	String filename=request.getParameter("fileName")+".war";
    	String filePath="/Users/asinha1/blink/"+request.getParameter("fileName")+"/"+request.getParameter("fileName")+"/target/";
    	
        File file = new File(filePath+filename);
        int length   = 0;
        ServletOutputStream outStream = response.getOutputStream();
        ServletContext context  = getServletConfig().getServletContext();
        String mimetype = context.getMimeType(filePath);
        
        // sets response content type
        if (mimetype == null) {
            mimetype = "application/zip";
        }
        response.setContentType(mimetype);
        response.setContentLength((int)file.length());
       // response.setHeader("Content-Encoding", "gzip");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        
        byte[] byteBuffer = new byte[BUFSIZE];
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        
        // reads the file's bytes and writes them to the response stream
        while ((in != null) && ((length = in.read(byteBuffer)) != -1))
        {
            outStream.write(byteBuffer,0,length);
        }
        
        in.close();
        outStream.close();
    }
    

}
