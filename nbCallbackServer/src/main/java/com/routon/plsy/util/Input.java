package com.routon.plsy.util;

import java.io.*;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Input {
	public static InputStream BaseToInputStream(String base64string){
		ByteArrayInputStream stream = null;
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] bytes1 = decoder.decodeBuffer(base64string);
			stream = new ByteArrayInputStream(bytes1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stream;
	}
	
	public static final InputStream byte2Input(byte[] buf) {
	        return new ByteArrayInputStream(buf);  
	    }
	
	public static InputStream GenerateImage(String imgStr, String filename)
    {   //对字节数组字符串进行Base64解码并生成图片    
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b = null;
        try   
        {  
            //Base64解码  
            b = decoder.decodeBuffer(imgStr);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            //生成jpeg图片
            /*
            String imgFilePath = "d://" + filename;//新生成的图片
            OutputStream out1 = new FileOutputStream(imgFilePath);
            out1.write(b);
            out1.flush();
            out1.close();

			String binaryFile = "d://post.txt";
			File f1 = new File(binaryFile);
			FileOutputStream fs = new FileOutputStream(f1);
			PrintStream p = new PrintStream(fs);
			String binaryString = conver2HexStr(b);
			p.println(binaryString);
			p.close();*/
        }   
        catch (Exception e){  
            e.printStackTrace();  
        }
		return byte2Input(b);
    }


	/**
	 * byte数组转换为二进制字符串,每个字节以","隔开
	 * **/
	public static String conver2HexStr(byte [] b)
	{
		StringBuffer result = new StringBuffer();
		for(int i = 0;i<b.length;i++)
		{
			result.append(Long.toString(b[i] & 0xff, 2)+",");
		}
		return result.toString().substring(0, result.length()-1);
	}


	public static String imageToBase64(InputStream in) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	    byte[] data = null;
	    // 读取图片字节数组
	    try {
	        data = new byte[in.available()];
	        in.read(data);
	        in.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    // 对字节数组Base64编码
	    BASE64Encoder encoder = new BASE64Encoder();
	    return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}


	
	public static void main(String[] args) throws IOException{
		InputStream is = null;
		OutputStream out = null;
		String result = null;
		try {
			StringBuffer buffer = new StringBuffer();
	        BufferedReader bf= new BufferedReader(new FileReader("D:/wanghong.txt"));
	        String s = null;
	        while((s = bf.readLine())!=null){
	            buffer.append(s.trim());
	        }

	        result = buffer.toString();
	        System.out.print(result);
			} catch (FileNotFoundException e) {
			e.printStackTrace();
			}
			GenerateImage(result, "");
/*			try {
			byte[] imgbyte = AndroidBase64Util.decode(result, AndroidBase64Util.NO_WRAP);
			InputStream in = byte2Input(imgbyte);
			File afterfile = new File("d://aaa.jpg");
			out = new FileOutputStream(afterfile);
			byte[] bytes= new byte[1024];
			while(in.read(bytes)!=-1){
			out.write(bytes);
			out.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			out.close();
		}*/
	}
}
