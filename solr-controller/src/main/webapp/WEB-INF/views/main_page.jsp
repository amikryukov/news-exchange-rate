<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: amikryukov
  Date: 05.10.15
  Time: 1:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Simple demo page</title>

</head>
<body>

  <b>Topic: ${topic}</b><br>
  <b>Start Time: ${startDate}</b><br>
  <b>End Time: ${endDate}</b><br>
  <b>Time Elapse: ${timeElp}</b>
  <b>Total: ${searchResult.setOfNews.total}</b>
  <br>
  <br>

  <form action="/">
    Start time:<br>
    <input type="text" name="startDate" value="${startDate}">
    <br>
    End time:<br>
    <input type="text" name="endDate" value="${endDate}">
    <br>
    Topic:<br>
    <input type="text" name="topic">
    <br>
    <br>
    <input type="submit" value="Submit">
  </form>

  <span>
    <div>
      <h5>Topics:</h5> <br>
      <c:forEach var="entry" items="${searchResult.getTopicFacetValues().entrySet()}">
        <a href="/?startDate=${startDate}&endDate=${endDate}&topic=${entry.getKey()}">
          ${entry.getKey()} (${entry.getValue()})
        </a> <br>
      </c:forEach>
    </div>

    <div>
        <h5>Top News: </h5>
      <c:forEach var="doc" items="${searchResult.getSetOfNews().getNews()}">
        <b>${doc.title}</b><br>
        <i>${doc.topic} [${doc.date}]</i><br>
        <img src="${doc.thumbnail}"><br>
        <a href="${doc.webURL}">origin</a><br>
        <i>weight: ${doc.main}</i>
        <br><br>
      </c:forEach>
    </div>

  </span>

</body>
</html>
