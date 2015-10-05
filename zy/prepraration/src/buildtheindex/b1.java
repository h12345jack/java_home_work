package buildtheindex;
import java.io.*; //1行
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.lucene.analysis.*; 
import org.apache.lucene.analysis.tokenattributes.*; //5行
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;   
import org.wltea.analyzer.lucene.IKAnalyzer;
import dealthehtml.mysql_it;
import java.sql.*;

public class b1 {
	public static void main(String[] args) throws IOException
	{
		Directory d=FSDirectory.open(new File(".\\index"));
		Analyzer analyzer=new IKAnalyzer(true);
		IndexWriterConfig conf=new IndexWriterConfig(Version.LUCENE_35, analyzer);//15行
	    b1 bid=new b1();
	    //创建索引
	    try {
			bid.CreateIndex(d, conf);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    analyzer.close();//关闭分析器 
	    d.close();//关闭目录	 //20行
	}
	void CreateIndex(Directory d, IndexWriterConfig conf) throws Exception{
		IndexWriter indexWriter=new  IndexWriter(d, conf);//25行
		mysql_it huang=new mysql_it();
        String sql="select * from dzy";
        System.out.println(sql);
        Statement stmt=null;
        ResultSet rs=null;
		try {
			stmt = huang.conn.createStatement();
			rs=stmt.executeQuery(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i=0;
		while(rs.next())//用前面的几个测试
		{
			int id=rs.getInt("id");
			String title=rs.getString("Tittle");
			String URL=rs.getString("URL");
			String date=rs.getString("date");
			String content=rs.getString("content");
			Document doc =new  Document();
			String id1=Integer.toString(id);
			Field id2=new Field("id",id1,Field.Store.YES,Field.Index.NO,
					  Field.TermVector.NO);
			doc.add(id2);
			Field title2=new Field("title",title,Field.Store.YES,Field.Index.ANALYZED,
					  Field.TermVector.WITH_POSITIONS_OFFSETS);
			doc.add(title2);
			Field URL2=new Field("URL",URL,Field.Store.YES,Field.Index.NO,Field.TermVector.NO);
			doc.add(URL2);
			Field date2=new Field("date",date,Field.Store.YES,Field.Index.NO,Field.TermVector.NO);
			doc.add(date2);
			Field content2=new Field("content",content,Field.Store.YES,Field.Index.ANALYZED,Field.TermVector.YES);
			doc.add(content2);
			indexWriter.addDocument(doc);
		}
		System.out.println("索引建立成功");
		stmt.close();
		rs.close();
		huang.conn.close();
		indexWriter.close();
	}

}
