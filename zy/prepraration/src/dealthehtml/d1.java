package dealthehtml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
/*
 *��Ϊ���е���Դ��������ϵ������û������source�ֶ�
 *����תΪ���ݿ��ļ��е��ֶΣ������ݿ��ļ��н�������
 * ����������������Ե�һ�β����
 * 
 * 
 * 
 * 
 * */
public class d1 {
	static String thebegin="E:/chinese.pku.edu.cn/";
	public static void main(String[] args) throws ParserException, FileNotFoundException, SQLException
	{
		String URL="jgyd/gdwxxjys1/3996.htm";
		String tittle;
		String date;
		String content;
		File dir=new File(thebegin+URL);
		StringBuffer abstr = new StringBuffer();
		//if(dir.exists())
			//System.out.print("isexist");
		BufferedReader reader= new BufferedReader(new FileReader(dir));
		String temp="";
		try {
			while((temp=reader.readLine())!=null){
			    abstr.append(temp);
			    abstr.append("\r\n");
			   }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String result =abstr.toString(); // here you get all the string within your file.
	    Parser it = new Parser(dir.getPath());
	    NodeFilter filter1=new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class","content"));
	    NodeFilter filter2= new AndFilter(new TagNameFilter("p"),new HasAttributeFilter("class","detail-p"));
	    NodeFilter filter3= new CssSelectorNodeFilter("h2");
	    NodeFilter filter4=new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class","detail-size"));

	    NodeList nodelist=it.parse(filter1);
	    Node node_constellation=nodelist.elementAt(0);
	    
	    //System.out.println("all"+node_constellation.toPlainTextString()); 
	    it.reset();
	    nodelist=it.parse(filter2);
	    Node node_date=nodelist.elementAt(0);
	    date=node_date.toPlainTextString();//��ȡʱ��
	    String regex="[\\w[-]]+";//ʹ���������ʽ��ȡʱ��
	    Pattern pattern=Pattern.compile(regex);
	    System.out.println("date"+date);
	    Matcher matcher=pattern.matcher(date);
	    if(matcher.find())
	    {
	    	date=matcher.group();
	    }
	    System.out.println("date:"+date);
	    it.reset();
	    nodelist=it.parse(filter3);
	    tittle=nodelist.elementAt(0).toPlainTextString();
	    System.out.println("tittle:"+tittle);//��ȡ����
	    it.reset();
	    nodelist=it.parse(filter4);
	    content=nodelist.elementAt(0).toPlainTextString();
	    //System.out.println("content"+content);
	    //mysql_it huang=new mysql_it();
	    content.trim();
	    /*String sql="INSERT INTO dzy(URL,Tittle,date,content)VALUES("+"'"+URL+"'"
	    +","+"'"+tittle+"'"+",'"+date+"'"+",'"+content+"');";
		Statement stmt;
		try {
			stmt = huang.conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}