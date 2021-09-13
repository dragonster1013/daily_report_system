<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.AttributeConst" %>

<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>
<fmt:parseDate value="${schedule.scheduleDate}" pattern="yyyy-MM-dd" var="scheduleDay" type="date" />
<label for="${AttributeConst.SCHE_DATE.getValue()}">日付</label><br />
<input type="date" name="${AttributeConst.SCHE_DATE.getValue()}" value="<fmt:formatDate value='${scheduleDay}' pattern='yyyy-MM-dd' />" />
<br /><br />

<label for="name">氏名</label><br />
<c:out value="${sessionScope.login_employee.name}" />
<br /><br />

<label for="${AttributeConst.SCHE_TITLE.getValue()}">タイトル</label><br />
<input type="text" name="${AttributeConst.SCHE_TITLE.getValue()}" value="${schedule.title}" />
<br /><br />

<label for="${AttributeConst.SCHE_CONTENT.getValue()}">内容</label><br />
<textarea name="${AttributeConst.SCHE_CONTENT.getValue()}" rows="10" cols="50">${schedule.content}</textarea>
<br /><br />
<input type="hidden" name="${AttributeConst.SCHE_ID.getValue()}" value="${schedule.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit">投稿</button>