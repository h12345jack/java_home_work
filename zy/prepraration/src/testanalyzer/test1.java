package testanalyzer;
import java.io.*; //1��
import org.apache.lucene.analysis.*; 
import org.apache.lucene.analysis.standard.StandardAnalyzer;   
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer; 
import org.apache.lucene.analysis.tokenattributes.*; //5��
import org.apache.lucene.util.Version;   
import org.wltea.analyzer.lucene.IKAnalyzer;
/*
 * �ó������IKAnalyzerͣ�ôʱ��ȹ����Ƿ�����
 * ��������Ӧ�����ֽ����дʲ鿴Ч���Ƿ����⣬��һ�������Լ��Ĵʱ�
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
		//System.out.println("ԭʼ�ı���"+text);//��ʾԭʼ�ı�
		System.out.println("�зֽ����"+token);//��ʾ�зֽ��//50��
		ts.close();//�ر��з�����    //55��
		analyzer.close();
	}  
}