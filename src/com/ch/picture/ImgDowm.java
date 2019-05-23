package com.ch.picture;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.FileUtils;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

public class ImgDowm extends Thread{

	//文件存储的文件夹
	private String folder;
	//文件名
	private int fileName;
	//图片地址
	private String url;
	
	public ImgDowm(){
		
	}
	public ImgDowm(String folder,int fileName,String url) {
		// TODO Auto-generated constructor stub
	this.fileName=fileName;
	this.folder=folder;
	this.url=url;
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		try {
			Response document=Jsoup.connect(url).ignoreContentType(true).timeout(10000).execute();
			//创建文件输出流

			URL url2 = document.url();
			System.out.println(url2);
			//FileOutputStream fos=new FileOutputStream(new File(folder+"/"+fileName+".mp4"));
			FileOutputStream fos=new FileOutputStream(new File(folder+"/"+fileName+url.substring(url.lastIndexOf("."))));
			//内容区域直接作为字节数组
			fos.write(document.bodyAsBytes());
			fos.close();
//			File file = new File("/Users/henrywang/pictures/test1/bb.mp4");
//			FileUtils.copyURLToFile(url2,file);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
}