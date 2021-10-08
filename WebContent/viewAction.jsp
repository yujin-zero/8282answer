<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bbs.BbsDAO" %>
<%@ page import="t_member.UserDAO" %>
<%@ page import="t_answer.AnswerDAO" %>
<%@ page import="java.io.PrintWriter" %>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="bbs" class="bbs.Bbs" scope="page"/>
<jsp:setProperty name="bbs" property="title" />
<jsp:setProperty name="bbs" property="content_q" />
<jsp:setProperty name="bbs" property="category" />
<jsp:setProperty name="bbs" property="num_q" />
<jsp:useBean id="user" class="t_member.User" scope="page"/>
<jsp:setProperty name="user" property="num_m" />
<jsp:useBean id="answer" class="t_answer.Answer" scope="page"/>
<jsp:setProperty name="answer" property="content_a" />
<%@ page import="bbs.Bbs" %>
<%@ page import="recommend.RecommendDAO" %>


<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>8282응답하라</title>
</head>
<body>
	<%
		String id=null;
		if (session.getAttribute("id") != null ){
			id = (String) session.getAttribute("id");
		}
		if (id == null) {
			PrintWriter script=response.getWriter();
			script.println("<script>");
			script.println("alert('로그인을 하세요.')");
			script.println("location.href='login.jsp'");
			script.println("</script>");
		} else {
			if (answer.getContent_a() == null ) {
				PrintWriter script=response.getWriter();
				script.println("<script>");
				script.println("alert('입력이 안되었습니다.')");
				script.println("history.back()");
				script.println("</script>");
			} else {
				BbsDAO bbsDAO = new BbsDAO();
				AnswerDAO answerDAO = new AnswerDAO();
				int num_m= bbsDAO.idnum(id);
				int qqq = (Integer)session.getAttribute("num_q");
				int result = answerDAO.write(answer.getContent_a(), num_m,qqq);
				if (result == -1 ){
					PrintWriter script=response.getWriter();
					script.println("<script>");
					script.println("alert('답변작성에 실패했습니다.')");
					script.println("history.back()");
					script.println("</script>");
				}
				else {
					UserDAO userDAO = new UserDAO();
					userDAO.apoint(id);
					PrintWriter script=response.getWriter();
					script.println("<script>");
					script.println("location.href = 'main.jsp'");
					script.println("</script>");
				}
				
				//RecommendDAO recommendDAO = new RecommendDAO();
				//recommendDAO.input(result,id);
				
			}
		}
	
		
		
		
		
	%>
</body>
</html>