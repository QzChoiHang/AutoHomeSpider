package com.ch.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;

import com.ch.pojo.WebSite;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;


public class MysqlOperation {
	private Statement state = null;
	private Connection con = null;
	private MongoClient client = null;
	private MongoDatabase database = null;
	private MongoCollection<Document> Collection = null;

	/**
	 * 
	 * 连接Mysql数据库
	 * 
	 * @return
	 */
	public Connection getConnection() {
		String url = "jdbc:mysql://localhost/autohome?characterEncoding=utf8&useSSL=true";
		String user = "root";
		String password = "1234";
		try {
			Class.forName("com.mysql.jdbc.Driver");// 加载MySQL驱动
			con = DriverManager.getConnection(url, user, password);
			if (con != null) {
				System.out.println("mysql数据库连接成功！");
			} else {
				System.out.println("mysql数据库连接失败！");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
	/**
	 * 
	 * 连接mongdb数据库
	 */
	public void mongdb() {
		// 连接mongdb服务器
		client = new MongoClient("127.0.0.1", 27017);
		// 通过数据库名获取数据库
		database = client.getDatabase("autohome");
		// 通过集合名获取集合
		Collection = database.getCollection("content");
	}
	/**
	 * 查询数据库中的website数据
	 * 
	 * @param con
	 * @return
	 */
	public ArrayList<WebSite> webSiteQuery(Connection con) {
		String sql = "select * from website";
		ArrayList<WebSite> list = new ArrayList<WebSite>();
		try {
			getStatement(con);
			ResultSet rs = state.executeQuery(sql);
			while (rs.next()) {
				WebSite site = new WebSite();
				site.setId(rs.getInt("id"));
				site.setCrawerurl(rs.getString("crawerurl"));
				site.setPreurl(rs.getString("preurl"));
				site.setUrlrule(rs.getString("urlrule"));
				site.setTitlerule(rs.getString("titlerule"));
				site.setHitsrule(rs.getString("hitsrule"));
				site.setCommentsrule(rs.getString("commentsrule"));
				site.setPublishtimerule(rs.getString("publishtimerule"));
				site.setPagenum(rs.getInt("pagenum"));
				list.add(site);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * mysql数据库查询
	 * 
	 * @param conn
	 * @param sql
	 * @return
	 */
	public Statement mysqlQuery(Connection conn) {
		Statement st = null;
		try {
			st = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return st;
	}
	
	/**
	 * 
	 * 获取statement对象
	 * 
	 * @param con
	 * @return
	 */
	public Statement getStatement(Connection con) {
		if (state == null) {
			try {
				state = con.createStatement();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return state;
		} else {
			return state;
		}
	}
	/**
	 * 
	 * 往集合中插入多条数据
	 * @param test 
	 */
	
	public void addList(List<Document> documents) {
		// TODO Auto-generated method stub
		
	    	 	        
		Collection.insertMany(documents);       
        MongoCursor<Document> cursor=Collection.find().iterator();  
        while (cursor.hasNext()) {  
            System.out.println(cursor.next());  
        }  
	}
	/**
	 * 关闭mongdb数据库
	 * 
	 */
	public void cloesConmongdb(){
			client.close();
	}
	/**
	 * 
	 * 关闭mysql连接
	 */
	public void cloesConmysql() {
		if (state != null) {
			try {
				state.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

}
