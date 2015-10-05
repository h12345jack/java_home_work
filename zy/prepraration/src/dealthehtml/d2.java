package dealthehtml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
/*程序实现了对于给定起点的本地的文件的宽度优先的Html遍历与解析
 * 将解析后的Html录入到数据库中，之后对于数据库的字段用Lucene做索引
 * */
/*在程序中，利用了开源的htmlparse解析器，不过没有对于content做进一步的优化，是比较遗憾的地方
 * 限于时间和精力，进一步将content中的&nbsp去掉，换行符去掉，进一步压缩空间是有必要的。
 * */

public class d2 {
	static String thebegin="E:/chinese.pku.edu.cn/";
	String URL;
	String tittle;
	String date;
	String content;
	void saveit() throws SQLException
	{
		String sql="INSERT INTO dzy(URL,Tittle,date,content)VALUES("+"'"+URL+"'"
	    +","+"'"+tittle+"'"+",'"+date+"'"+",'"+content+"');";
		mysql_it huang=new mysql_it();
		Statement stmt;
		try {
			stmt = huang.conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		huang.conn.close();
	}
	d2(String URL) throws FileNotFoundException, ParserException, SQLException
	{
		File dir=new File(thebegin+URL);
		this.URL=URL;
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
	    date=node_date.toPlainTextString();//获取时间
	    String regex="[\\w[-]]+";//使用正则表达式获取时间
	    Pattern pattern=Pattern.compile(regex);
	   // System.out.println("date"+date);
	    Matcher matcher=pattern.matcher(date);
	    if(matcher.find())
	    {
	    	date=matcher.group();
	    }
	    System.out.println("date:"+date);
	    it.reset();
	    nodelist=it.parse(filter3);
	    tittle=nodelist.elementAt(0).toPlainTextString();
	    System.out.println("tittle:"+tittle);//获取标题
	    it.reset();
	    nodelist=it.parse(filter4);
	    content=nodelist.elementAt(0).toPlainTextString();
	    //System.out.println("content"+content);
	    content=content.trim();
	    System.out.println(URL);
	    /*if(URL.equals("/jgyd/txry/4025.htm"))//好像对于这样的特殊字符，插入到mysql有问题，具体问题需要排查
	    	System.out.println(content);
	    	*/
	    saveit();
	}
	public static void main(String[] args)
	{
		
		String URL="";
		List<String> deque=new ArrayList<String>();
		File first=new File(URL);//
		deque.add(URL);
		do
		{
			//System.out.println(">>>>>");调试用
			String fstr=deque.remove(0);
			String now=thebegin+fstr;
			File now1=new File(now);
			File[] filelist=now1.listFiles();
			System.out.println(filelist.length);
			for(File i:filelist)
			{
				if(i.isDirectory())//如果是目录
				{
					deque.add(fstr+"/"+i.getName());
				}
				if(i.isFile())//如果是文件，只要判断是都以htm结尾，并不是Index开始的文件进行解析
				{
					String name=i.getName();
					if(name.endsWith("htm")&&(!name.startsWith("index")))
					{
						String tmp=fstr+"/"+name;
						try {
							d2 one=new d2(tmp);
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParserException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			
		}while(!deque.isEmpty());
	}
}
