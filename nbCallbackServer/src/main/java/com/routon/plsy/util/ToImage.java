package com.routon.plsy.util;

import java.io.FileOutputStream;
import java.io.OutputStream;
import sun.misc.BASE64Decoder;

public class ToImage {
	/**
	* base64字符串转化成图片  
	* @param imgStr
	* @return
	*/
	public static boolean GenerateImage(String imgStr,String saveImgFilePath)  
	{   
	    //对字节数组字符串进行Base64解码并生成图片  
	    if (imgStr == null) //图像数据为空  
	    return false; 

	    OutputStream out = null;
	    try   
	    {  
	        //Base64解码  
	        BASE64Decoder decoder = new BASE64Decoder();  
	        byte[] b = decoder.decodeBuffer(imgStr);  
	        for(int i=0;i<b.length;++i)  
	        {  
	            if(b[i]<0)  
	              b[i]+=256;//调整异常数据  
	        }  
	        //生成jpeg图片  
	        out = new FileOutputStream(saveImgFilePath);      
	        out.write(b);  
	        out.flush();  
	        out.close();  
	        return true;  
	    }   
	    catch (Exception e)   
	    {  
	        e.printStackTrace();
	        try 
	        {
	            if(out!=null)
	            {
	                out.flush();  
	                out.close();
	            }
	        } 
	        catch (Exception e2) 
	        {
	            e2.printStackTrace();
	        }
	        return false;  
	    }  
	}  
}
