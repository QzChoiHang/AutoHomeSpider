package com.ch.autohome;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ch.pojo.WebSite;
import com.ch.util.MysqlOperation;
import com.ch.util.RedisConnection;

import redis.clients.jedis.Jedis;

public class AutoHome {
	public List<WebSite> collect() throws SQLException {
		MysqlOperation operation = new MysqlOperation();
		Connection conn = operation.getConnection();
		List<WebSite> list = operation.webSiteQuery(conn);
		WebSite webSite=list.get(0);
			try {				
				for (int i = 1; i <webSite .getPagenum(); i++) {
					System.out.println("第"+i+"页");
					//将网址入口中的@替换成页数
					String crawerurl = "";
					crawerurl = webSite.getCrawerurl();
					crawerurl = crawerurl.replaceAll("\\@\\@\\@\\@", ""+i+"");
					// 输出完整网址入口
					System.out.println(crawerurl);
					
					Document document = Jsoup.connect(crawerurl)
							.userAgent("Mozilla/5.0 (Windows NT 6.1; rv:22.0) Gecko/20100101 Firefox/22.0")
							.header("Accept-Encoding", "gzip, deflate").get();
					// 提取url链接模块
					String urlQuery = webSite.getUrlrule();
					Elements elements = document.select(urlQuery);
					//定义一个集合接受所有的信息
					List<Document> documents = new ArrayList<>();
					
					for (Element element : elements) {
						try {
							
							// 提取url链接
							String href = webSite.getPreurl() + element.attr("href");
							Jedis jedis = new RedisConnection().jedis;
							// 如果存在
							if (jedis.exists(href)) {
								System.out.println("网址已存在");
							} else {
								//利用Redis去重URL
								jedis.set(href, "");
								if (!href.contains("http"))
									continue;
								System.out.println("链接：" + href);
								document = Jsoup.connect(href)
										.userAgent("Mozilla/5.0 (Windows NT 6.1; rv:22.0) Gecko/20100101 Firefox/22.0")
										.get();
								// 标题
								String title = document.select(webSite.getTitlerule()).text();
								// 点击数
								String hits = document.select(webSite.getHitsrule()).text();
								// 评论数
								String comments = document.select(webSite.getCommentsrule()).text();
								// 发布时间
								String publishtime = document.select(webSite.getPublishtimerule()).text();
								System.out.println("标题:" + title);
								System.out.println("点击数：" + hits);
								System.out.println("评论数：" + comments);
								System.out.println("发布时间：" + publishtime);
								//将所得数据添加到MongoDB
								WebSite website=new WebSite();
								website.setTitlerule(title);
								website.setHitsrule(hits);
								website.setCommentsrule(comments);
								website.setPublishtimerule(publishtime);
	    	                    list.add(website);			
							}
						} catch (Exception ex) {
							// System.out.println("采集出错",ex);
						}

					}
					
				}
			} catch (Exception ex) {
				// System.out.println("采集出错",ex);
			}

		return list;
	}

 	public static void main(String[] args) throws SQLException {
		AutoHome ah = new AutoHome();
		ah.collect();
	}
}
