package com.ch.video;



public class AUTO2 {
    public static void main(String[] args) throws Exception {
    	
        //视频网址
        String url1 = "https://n7-pl-agv.autohome.com.cn/video-2/E3BD4E39114FD258/2018-06-28/9411922D5FF53170-300.mp4?key=B83BA77E81CFC66B3D60947E15C0090F&time=1530242444";
        //设置存放路径
        VideoDown thread = new VideoDown("D:\\汽车之家视频", url1);
        thread.start();

    }
}
