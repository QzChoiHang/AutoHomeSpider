package com.ch.video;

import org.apache.commons.io.FileUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.File;
import java.net.URL;

/**
 * Title:           VideoDown
 * JDK:             10
 * Encoding:        UTF-8
 */
public class VideoDown extends Thread{
    //文件存储的文件夹
    private String folder;
    //图片地址
    private String url;
    public VideoDown(){
    }
    public VideoDown(String folder,String url) {
        // TODO Auto-generated constructor stub
        this.folder=folder;
        this.url=url;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        super.run();
        try {
            Connection.Response document=Jsoup.connect(url).ignoreContentType(true).timeout(10000).execute();
            URL url2 = document.url();
            System.out.println(url2);
            File file = new File("D:\\汽车之家视频/视频.mp4");
            FileUtils.copyURLToFile(url2,file);
        } catch (Exception e) {
            // TODO: handle exception
        }


    }
}
