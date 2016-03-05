<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
    <title>PessoaCRUD</title>
    <style type="text/css">
        .tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
        .tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
        .tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
        .tg .tg-4eph{background-color:#f9f9f9}
    </style>
    
    <spring:url value="/resources/core/js/jquery.1.10.2.min.js"
				var="jqueryJs" />
				
	<script src="${jqueryJs}"></script>
</head>
<body>
<h1>
    Adiciona uma Pessoa
</h1>
 
 
<c:url var="addAction" value="/pessoa/add" ></c:url>
 
<form:form id="update-form">
<table>
    <c:if test="${!empty pessoa.nome}">
    <tr>
        <td>
            <form:label path="id">
                <spring:message text="ID"/>
            </form:label>
        </td>
        <td>
            <form:input path="id" readonly="true" size="8"  disabled="true" id="input-id"/>
            <form:hidden path="id" />
        </td> 
    </tr>
    </c:if>
    <tr>
        <td>
            <form:label path="nome">
                <spring:message text="Nome"/>
            </form:label>
        </td>
        <td>
            <form:input path="nome" id="input-nome"/>
        </td> 
    </tr>
    <tr>
        <td colspan="2">
            <c:if test="${!empty pessoa.nome}">
                <input type="submit"
                    value="<spring:message text="Edit Pessoa"/>" id="button-edit"/>
            </c:if>
            <c:if test="${empty pessoa.nome}">
                <input type="submit"
                    value="<spring:message text="Add Pessoa"/>" id="button-add"/>
            </c:if>
        </td>
    </tr>
</table>  
</form:form>

<br>
<h3>Pessoas List</h3>
<c:if test="${!empty listaPessoas}">
    <table class="tg">
    <tr>
        <th width="80">Pessoa ID</th>
        <th width="120">Pessoa Nome</th>
        <th width="60">Editar</th>
        <th width="60">Deletar</th>
    </tr>
    <c:forEach items="${listaPessoas}" var="pessoa">
        <tr>
            <td>${pessoa.id}</td>
            <td>${pessoa.nome}</td>
            <td><a href="<c:url value='/edit/${pessoa.id}' />" >Edit</a></td>
            <td><a href="<c:url value='/remove/${pessoa.id}' />" >Delete</a></td>
        </tr>
    </c:forEach>
    </table>
</c:if>
 
 <script>
	jQuery(document).ready(function($) {
		$("#update-form").submit(function(event) {
			var action
			
			if(empty $("#input-id").val()){
				action = "add"
			}else{
				action = "update"
			}			
			
			// Disble the search button
			enableSearchButton(false);
			// Prevent the form from submitting via the browser.
			event.preventDefault();
			submitViaAjax(add);
		});
	});
	
	function submitViaAjax() {
		var pessoa = {}
		var action
		
		pessoa["id"] = $("#input-id").val();
		pessoa["nome"] = $("#input-nome").val();
		
		if(empty pessoa["id"]){
			action = "add"
		}else{
			action = "update"
		}
		
		
		
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "${home}search/api/getSearchResult",
			data : JSON.stringify(search),
			dataType : 'json',
			timeout : 100000,
			success : function(data) {
				console.log("SUCCESS: ", data);
				display(data);
			},
			error : function(e) {
				console.log("ERROR: ", e);
				display(e);
			},
			done : function(e) {
				console.log("DONE");
				enableSearchButton(true);
			}
		});
	}
	function enableSearchButton(flag) {
		$("#btn-search").prop("disabled", flag);
	}
	function display(data) {
		var json = "<h4>Ajax Response</h4><pre>"
				+ JSON.stringify(data, null, 4) + "</pre>";
		$('#feedback').html(json);
	}
</script>
 
 
</body>


</html>