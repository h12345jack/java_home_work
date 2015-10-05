package testanalyzer;
import java.io.*; //1行
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.lucene.analysis.*; 
import org.apache.lucene.analysis.standard.StandardAnalyzer;   
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer; 
import org.apache.lucene.analysis.tokenattributes.*; //5行
import org.apache.lucene.util.Version;   
import org.wltea.analyzer.lucene.IKAnalyzer;
import dealthehtml.mysql_it;
import java.sql.*;
/*
 * 该程序测试在test1的基础上，进一步从数据库中提取一定数量的conten进行解析
 * 并根据解析结果，进一步定制停用词表，在哈工大的停用词表的基础上， 加入包括nbsp在内的符合
 * 程序特点的停用词，为之后解析网页做好准备工作
 * */

public class test2 {
	public static void main(String[] args)throws Exception  {
        test1 ad = new test1(); 
        //File t1=new File("E:/zy/jar/test.txt");
        mysql_it huang=new mysql_it();
        String sql="select content from dzy";
        ResultSet now=null;
        Statement stmt=null;
		try {
			stmt = huang.conn.createStatement();
			now=stmt.executeQuery(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		for(int i=0;i<10;i++)//检验10个
		{
				now.next();
			 	String chS=now.getString("content");
		       // System.out.println(chS);
		        ad.showTokensInfo(chS); 
		        System.out.println("------------"+i+"---------------");
		}
		stmt.close();
		now.close();
		huang.conn.close();
	}
	public void showTokensInfo(String text) 
  				throws IOException {//20行
		StringBuffer token=new StringBuffer();//切分项
		Analyzer analyzer=new IKAnalyzer(true);
		TokenStream ts = analyzer.tokenStream("", new StringReader(text)); //对文本进行分析                                   
		//添加属性
		CharTermAttribute termAtt = ts.addAttribute(CharTermAttribute.class); 
		int f=1;//添加切分项文本属性
		while(ts.incrementToken()) { //遍历切分项及其属性 			
				token.append(termAtt.toString()+"||||");//获得切分项文本，并添加到字串中
		}
		System.out.println("切分结果："+token);//显示切分结果//50行
		ts.close();//关闭切分项流    //55行
		analyzer.close();
	}  
}
