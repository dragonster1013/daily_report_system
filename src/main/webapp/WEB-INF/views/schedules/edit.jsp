<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.ForwardConst" %>

<c:set var="actSche" value="${ForwardConst.ACT_SCHE.getValue()}" />
<c:set var="commUpd" value="${ForwardConst.CMD_UPDATE.getValue()}" />
<c:set var="commDel" value="${ForwardConst.CMD_DESTROY.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

        <h2>予定表 編集ページ</h2>
        <form method="POST" action="<c:url value='?action=${actSche}&command=${commUpd}' />">
            <c:import url="_form.jsp" />
        </form>

        <p>
            <a href="<c:url value='?action=Schedule&command=index' />">一覧に戻る</a>
        </p>

        <p>
            <a href="#" onclick="confirmDestroy();">この予定表を削除する</a>
        </p>
        <form method="POST" action="<c:url value='?action=${actSche}&command=${commDel}' />">
            <input type="hidden" name="${AttributeConst.SCHE_ID.getValue()}" value="${schedule.id}" />
            <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
        </form>
        <script>
            function confirmDestroy() {
                if (confirm("本当に削除してよろしいですか？")) {
                    document.forms[1].submit();
                }
            }
        </script>
    </c:param>
</c:import>