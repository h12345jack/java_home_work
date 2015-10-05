import org.htmlparser.*;
import org.htmlparser.util.ParserException;
import org.htmlparser.filters.*;
import org.htmlparser.util.*;
import org.htmlparser.visitors.*;
import java.io.*;
public class dd {

	public static void main(String[] args) throws ParserException, FileNotFoundException
	{
		String URL;
		String tittle;
		String date;
		String content;
		File dir=new File("E:/chinese.pku.edu.cn/jgyd/gdwxxjys1/3996.htm");
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
	    System.out.println("date"+date);
	    it.reset();
	    nodelist=it.parse(filter3);
	    tittle=nodelist.elementAt(0).toPlainTextString();
	    System.out.println("tittle:"+tittle);
	    it.reset();
	    nodelist=it.parse(filter4);
	    content=nodelist.elementAt(0).toPlainTextString();
	    //System.out.println("content"+content);

	}
}
