package testanalyzer;
import java.io.*; //1��
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.lucene.analysis.*; 
import org.apache.lucene.analysis.standard.StandardAnalyzer;   
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer; 
import org.apache.lucene.analysis.tokenattributes.*; //5��
import org.apache.lucene.util.Version;   
import org.wltea.analyzer.lucene.IKAnalyzer;
import dealthehtml.mysql_it;
import java.sql.*;
/*
 * �ó��������test1�Ļ����ϣ���һ�������ݿ�����ȡһ��������conten���н���
 * �����ݽ����������һ������ͣ�ôʱ����ڹ������ͣ�ôʱ��Ļ����ϣ� �������nbsp���ڵķ���
 * �����ص��ͣ�ôʣ�Ϊ֮�������ҳ����׼������
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
	
		for(int i=0;i<10;i++)//����10��
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
  				throws IOException {//20��
		StringBuffer token=new StringBuffer();//�з���
		Analyzer analyzer=new IKAnalyzer(true);
		TokenStream ts = analyzer.tokenStream("", new StringReader(text)); //���ı����з���                                   
		//��������
		CharTermAttribute termAtt = ts.addAttribute(CharTermAttribute.class); 
		int f=1;//�����з����ı�����
		while(ts.incrementToken()) { //�����з�������� 			
				token.append(termAtt.toString()+"||||");//����з����ı��������ӵ��ִ���
		}
		System.out.println("�зֽ����"+token);//��ʾ�зֽ��//50��
		ts.close();//�ر��з�����    //55��
		analyzer.close();
	}  
}