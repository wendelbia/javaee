<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!--faço aqui os import's  -->
<%@page import="model.JavaBeans" %>
<%@page import="java.util.ArrayList" %>
<% 
//é mostrado um alerta de erro dizendo que não houve uma boa performance de execução do java na linha desse array
//para tirar esse alerta uso o @ SuppressWarnings...
@ SuppressWarnings ("unchecked")
	ArrayList<JavaBeans> lista = (ArrayList<JavaBeans>)
	request.getAttribute("contatos");
	/*teste
	for(int i = 0; i < lista.size(); i++){
		out.println(lista.get(i).getIdcon());
		out.println(lista.get(i).getNome());
		out.println(lista.get(i).getFone());
		out.println(lista.get(i).getEmail());
	}*/
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="utf-8">
<title>Agenda de Contatos</title>
<link rel="icon" href="imagens/calendar.png">
<link rel="stylesheet" href="style.css">
<title>Agenda de Contato</title>
</head>
<body>
	<h1>Agenda de Contatos</h1>
	<a href="novo.html" class="Botao1">Novo contato</a>
	<a href="report" class="Botao2">Relatório</a>
	<table id="tabela" border="1">
		<thead>
		<tr>
		<th>Id</th>
		<th>Nome</th>
		<th>Fone</th>
		<th>E-mail</th>
		<th>Opções</th>
		</tr>
		</thead>
		<tbody>
			<%for(int i = 0; i < lista.size(); i++){%>
				<tr>
				<td><%=lista.get(i).getIdcon() %></td>
				<td><%=lista.get(i).getNome() %></td>
				<td><%=lista.get(i).getFone() %></td>
				<td><%=lista.get(i).getEmail() %></td>
				<td>
				<!-- link que vai chamar a página para edição buscando por idcon -->
					<a href="select?idcon=<%=lista.get(i).getIdcon() %>" class="Botao1">Editar</a>
					<a href="javascript: confirmar(<%=lista.get(i).getIdcon()%>)" class="Botao2">Excluir</a>
				</td>
				</tr>
			<%} %>
		</tbody>
	</table>
	<script src="scripts/confirmardor.js"></script>
</body>
</html>