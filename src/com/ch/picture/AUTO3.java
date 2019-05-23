package com.ch.picture;

import java.io.File;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class AUTO3 {
	public static void main(String[] args) throws Exception {
		// 创建默认的httpclient实例
		CloseableHttpClient httpclient = HttpClients.createDefault();
		int name = 1;
		for (int i = 1; i <= 2; i++) {
			// 网址
			String url0 = "https://v.autohome.com.cn/general/5-"+i+"-1";
			// 创建get请求
			HttpGet httpget = new HttpGet(url0);
			// 发送请求获取源代码
			HttpResponse httpResponse = httpclient.execute(httpget);

			// 转成字符串
			String content = EntityUtils.toString(httpResponse.getEntity(), "utf-8");

			Document doc = Jsoup.parse(content);
			// 使用jsoup选择器获取元素节点
			Elements eles = doc.select("div.video-list-pic picture img");
			//// div[@ id="waterfall"]/div/a/@href
			for (Element element : eles) {
				String url = element.attr("src");
				System.out.println(url);
				File file = new File("D:\\汽车之家");
				if (!file.exists()) {
					file.mkdirs();
				}
				String url1 = "https:" + url;
				ImgDowm thread = new ImgDowm("D:\\汽车之家", name, url1);
				thread.start();
				name++;
			}
		}
	}
}
