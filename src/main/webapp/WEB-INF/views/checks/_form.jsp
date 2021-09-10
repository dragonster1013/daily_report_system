<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>

<c:if test="${errors != null}">
    <div id="flush_error">
        評価中にエラーが発生しました。<br />

    </div>
</c:if>

<label for="${AttributeConst.CHE_CONTENT.getValue()}">内容</label><br />
<textarea name="${AttributeConst.CHE_CONTENT.getValue()}" rows="10" cols="50">${check.content}</textarea>
<br /><br />
<input type="hidden" name="${AttributeConst.CHE_ID.getValue()}" value="${check.id}" />
<input type="hidden" name="${AttributeConst.REP_ID.getValue()}" value="${check.reportId}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit">投稿</button>