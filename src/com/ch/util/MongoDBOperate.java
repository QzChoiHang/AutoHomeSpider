package com.ch.util;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;  
import java.util.List;

import com.ch.autohome.AutoHome;
import com.ch.pojo.WebSite;
import com.mongodb.BasicDBObject;  
import com.mongodb.BasicDBObjectBuilder;  
import com.mongodb.DB;  
import com.mongodb.DBCollection;  
import com.mongodb.DBObject;  
import com.mongodb.Mongo;


public class MongoDBOperate {  
  
    public static void main(String[] args) throws UnknownHostException, SQLException{  
    	
    	AutoHome autohome = new AutoHome();
    	List<WebSite> list = autohome.collect();		
    	ruku(list);
    	
    }
    	public static void ruku(List<WebSite> list){
    	
    	for(WebSite site:list){
        /** 
         * Mongo类代表与MongoDB服务器的连接，有多种构造函数。无参构造函数默认连接localhost:27017. 
         */  
        Mongo connection = new Mongo("localhost:27017");  
        /** 
         * DB类代表数据库，如果当前服务器上没有该数据库，会默认创建一个 
         */  
        DB db = connection.getDB("autohome");  
        /** 
         * DBCollection代表集合，如果数据库中没有该集合，会默认创建一个 
         */  
        DBCollection fruitShop = db.getCollection("content");  
        /** 
         *  创建网页文档对象 
         */  
        DBObject web = new BasicDBObject();  
        web.put("url", "autohome");  
          
        // 数组通过List表示  
        List<DBObject> webs = new ArrayList<DBObject>();  
        // 数组中的每一个文档，我们通过BasicDBObjectBuilder来构造  
        webs.add(BasicDBObjectBuilder.start().add("title",site.getTitlerule()).add("hits", site.getHitsrule())
        		.add("comments", site.getCommentsrule()).add("publishtime", site.getPublishtimerule()).get());  
          
        web.put("content", webs);  
          System.out.println("---");
        fruitShop.insert(web);  
    	}
    }  
  
}