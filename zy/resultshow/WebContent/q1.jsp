<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="gbk" import="java.util.Date"%>
<%@page import="rs1.final1" %>
<%request.setCharacterEncoding("UTF-8");%>
<jsp:useBean id="newBean2" class="rs1.final1" scope="application"/>
<jsp:setProperty property="*" name="newBean2" />
<%
int pageSize;//һҳ��ʾ�ļ�¼��
int totalItem=newBean2.getPage(newBean2.getFlag());//��¼����
int totalPage;//��ҳ��
int curPage=0;//����ʾҳ��
pageSize=10;
//totalItem
if(newBean2.getFlag()==1)
totalPage=totalItem%pageSize==0?totalItem/pageSize:totalItem/pageSize+1;
else
	totalPage=1;
String strPage=request.getParameter("page");//��ô���ʾҳ��
if(strPage==null)
{
 curPage=1;
}
else
{
 curPage=java.lang.Integer.parseInt(strPage);//���ַ���ת��������
}
if(curPage<1)
{
 curPage=1;
}

%>
<!doctype html>
<html>
<head>
 <meta charset="utf-8">
  <title>������ѧ����ϵվ�ڼ���</title>
  <link rel="stylesheet" href="css/style.css" type="text/css" media="all">
  <link rel="stylesheet" href="css/add1.css" type="text/css" media="all">
  <script type="text/javascript" src="js/jquery.min.js"></script>
  <link rel="stylesheet" href="css/jg.css" type="text/css" media="all">
  <link rel="stylesheet" href="css/jscss2.css" type="text/css" media="all">
  <style type="text/css">
.red{
	font-size:120%;
	color:red;
	text-shadow:rgb(255,0,0) 1px 1px;
}
</style>
  </head>
<body>
<!-- header -->
<header id="top">
  <div class="container">
    <h1>������ѧ����ϵվ�ڼ���</h1>
  </div>
</header>
<!-- header -->

<div class="main-box">
    <div class="container">
    <div class="inside">
     <div id="js">
      <form action="q1.jsp" method="post">
          <input type="text" name="query">
          <input class="button" type="submit" value="Search">
          <input type="radio" class="option-input radio" name="flag" value="0" checked="checked" />
          ����
          <input type="radio" class="option-input radio" name="flag" value="1" />
          ȫ��
        </form>

</div>
<%=newBean2.theresult(curPage-1) %>
      <div id="huanyihuan">
   		<p align="center">
  	 ��<%=totalItem%>����¼,��<%=totalPage%>ҳ��ʾ,��ǰҳ��:��<%=curPage%>ҳ<br />
   	<%if(curPage>1){%><a href="q1.jsp?page=1">��ҳ</a><%}%>&nbsp;&nbsp;
   	<%if(curPage>1){%><a href="q1.jsp?page=<%=curPage-1%>">��һҳ</a><%}%>&nbsp;&nbsp;
   	<%
   	if(newBean2.getFlag()==1)
   	 for(int j=curPage;j<=curPage+5&&j<totalPage;j++)
  	 {
  	  out.print("&nbsp;&nbsp;<a href='q1.jsp?page="+j+"'>"+j+"</a>");
  	 }
   %>
   <%if(curPage<totalPage){%><a href="q1.jsp?page=<%=curPage+1%>">��һҳ</a><%}%>&nbsp;&nbsp;
   <%if(totalPage>1){%><a href="q1.jsp?page=<%=totalPage%>">ĩҳ</a><%}%>
      </div>
      </div>
    </div>

  </div>
<!-- footer -->
<footer>
    <div class="container">
    <div class="wrapper">
         <div class="fleft">Copyright - �ƿ���,���ٿ�,ͯ����,��ΰ��,ţ�γ�</div>
        <div class="fright">Java�γ���ҵ</div>
      </div>
  </div>
  </footer>
</body>
</html>
