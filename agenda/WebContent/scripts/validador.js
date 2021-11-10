function validar() {
	//declaro duas variável denominada nome e fone que recebe o nome do formulário e chama o valor nome e fone
	let nome = frmContato.nome.value;
	let fone = frmContato.fone.value;
	//se nome for igual a vazio
	if (nome === "") {
		//entao
		alert('Preencha o campo Nome');
		frmContato.nome.focus();
		return false
	} else if (fone === "") {
		alert('Preencha o campo Fone');
		frmContato.fone.focus();
		return false
	} else {
		//senao
		document.forms["frmContato"].submit();
	}
}