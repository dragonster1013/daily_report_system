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

<input type="hidden" name="${AttributeConst.REV_CONTENT.getValue()}" value="確認しました" />
<button type="submit">確認しました</button>

<input type="hidden" name="${AttributeConst.REV_CONTENT.getValue()}" value="再提出してください" />
<button type="submit">再提出してください</button>