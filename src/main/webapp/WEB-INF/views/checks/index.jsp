<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actChe" value="${ForwardConst.ACT_Che.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="confirm">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>確認サイン 一覧</h2>
        <table id="check_list">
            <tbody>
                <tr>
                    <th class="check_name">氏名</th>
                    <th class="check_date">日付</th>
                    <th class="check_title">タイトル</th>
                    <th class="check_action">操作</th>
                </tr>
                <c:forEach var="check" items="${checks}" varStatus="status">
                    <fmt:parseDate value="${report.reportDate}" pattern="yyyy-MM-dd" var="reportDay" type="date" />

                    <tr class="row${status.count % 2}">
                        <td class="check_name"><c:out value="${check.report.name}" /></td>
                        <td class="check_date"><fmt:formatDate value='${checkDay}' pattern='yyyy-MM-dd' /></td>
                        <td class="check_title">${check.title}</td>
                        <td class="check_action"><a href="<c:url value='?action=${actChe}&command=${commShow}&id=${check.id}' />">詳細を見る</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${checks_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((checks_count - 1) / maxRow) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='?action=${actChe}&command=${commIdx}&page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='?action=${actChe}&command=${commNew}' />">新規確認サインの登録</a></p>

    </c:param>
</c:import>