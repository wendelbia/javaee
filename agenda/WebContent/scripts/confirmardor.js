/**
 * Confirmar a exclus�o de um contato
 */

function confirmar(idcon){
	let resposta = confirm("Confirmar a exclus�o deste contato?")
	if(resposta === true) {
		window.location.href = "delete?idcon=" + idcon
	}
}