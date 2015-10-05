package testanalyzer;
import java.io.*; //1行
import org.apache.lucene.analysis.*; 
import org.apache.lucene.analysis.standard.StandardAnalyzer;   
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer; 
import org.apache.lucene.analysis.tokenattributes.*; //5行
import org.apache.lucene.util.Version;   
import org.wltea.analyzer.lucene.IKAnalyzer;
/*
 * 该程序测试IKAnalyzer停用词表等功能是否正常
 * 并对于相应的文字进行切词查看效果是否满意，进一步定制自己的词表
 * */

public class test1 {
	public static void main(String[] args)throws Exception  {
        test1 ad = new test1(); 
        File t1=new File("E:/test.txt");
        FileInputStream t2=new FileInputStream(t1);
        int len=t2.available();
        byte br[]=new byte[len];
        t2.read(br);
        t2.close();
        String chS=new String(br);
       // System.out.println(chS);
        ad.showTokensInfo(chS); 
        
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
		//System.out.println("原始文本："+text);//显示原始文本
		System.out.println("切分结果："+token);//显示切分结果//50行
		ts.close();//关闭切分项流    //55行
		analyzer.close();
	}  
}
