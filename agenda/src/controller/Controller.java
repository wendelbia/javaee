package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DAO;
import model.JavaBeans;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

// TODO: Auto-generated Javadoc
/*main � uma requisi��o que o servlet vai trabalhar */
//essas url's s�o caminhos que definimos atrav�s de link's, bot�es e etc
//quando adciono em urlPatterns a camada controller consegue receber os dado do formul�rio 
/**
 * The Class Controller.
 */
//**********com essa palavra "/insert" o servlet recebe requisi��es do formul�rio
@WebServlet(urlPatterns = { "/Controller", "/main", "/insert", "/select",
		"/update", "/delete", "/report" })
public class Controller extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The dao. */
	DAO dao = new DAO();
	
	/** The contato. */
	JavaBeans contato = new JavaBeans();

	// agora posso acessar os metodos dessa classe

	/**
	 * Instantiates a new controller.
	 */
	public Controller() {
		super();
	}

	// vou redirecionar as requisi��es que foram configuradas no
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	// @WebServlet(urlPatterns... para m�todos espec�ficos
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// teste de conex�o
		// response.getWriter().append("Served at:").append(request.getContextPath());
		// dao.testeConexao();

		// declaro uma vari�vel que vai armazenar o caminho da requisi��o
		String action = request.getServletPath();
		// teste que vai imprimir a variavel action
		// System.out.println(action);
		// se action for igual a /main, se o m�todo doget receber a requisi��o
		// /main ent�o
		// eu quero redirecionar ao m�todo que ir� trabalhar especificadamente
		// essa requisi��o
		// ***** aqui foi criado uma vari�vel que armazena uma requisi��o,
		// dentro dessa estrutura if else e
		// ***** se for main ser� redirecionado para o metodo contatos(request,
		// response) se for verdadeira essa
		// ***** condi��o ent�o iguinoro as outras se n�o for /main a
		// a��o(action) ent�o ir� analizar a segunda
		// ***** que no caso � /insert e ent�o ir� invocar esse novo metodo que
		// � novoContato(request, response)
		if (action.equals("/main")) {
			contatos(request, response);
			// quando a action � igual a insert vou redirecionar ao m�todo
			// respons�vel por essa requisi��o a camada
			// model que se chama novoContato()
		} else if (action.equals("/insert")) {
			adicionarContato(request, response);
		} else if (action.equals("/select")) {
			listarContato(request, response);
		} else if (action.equals("/update")) {
			editarContato(request, response);
		} else if (action.equals("/delete")) {
			removerContato(request, response);
		} else if (action.equals("/report")) {
			gerarRelatorio(request, response);
		} else {
			// ****caso n�o seja nem /main nem /insert
			response.sendRedirect("index.html");
		}
	}

	// vou pedir para redirecionar para agenda.jsp que � o m�todo para listar
	/**
	 * Contatos.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// contatos
	protected void contatos(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// response.sendRedirect("agenda.jsp");
		// criando um objeto que ir� receber os dados JavaBeans do tipo
		// ArrayList o resulta ir� para
		// dentro do objeto lista
		ArrayList<JavaBeans> lista = dao.listarContatos();
		/*
		 * teste de recebimento da lista for (int i = 0; i < lista.size(); i++)
		 * { System.out.println(lista.get(i).getIdcon());
		 * System.out.println(lista.get(i).getNome());
		 * System.out.println(lista.get(i).getFone());
		 * System.out.println(lista.get(i).getEmail()); }
		 */
		// encaminhar a lista ao documento agenda.jsp, para isso uso o request
		// que tem como classe modelo o servlet
		request.setAttribute("contatos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		// forward encaminha efetivamente o objeto lista ao jsp agenda
		rd.forward(request, response);
	}

	/**
	 * Adicionar contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// novo contato
	protected void adicionarContato(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// response.sendRedirect("agenda.jsp");
		// teste de recebimento
		// agora armazeno esses dados nas vari�veis do arquivo javabeans e essas
		// vari�veis est�o encapsuladas
		// portanto preciso acess�-las inst�nceando e antes fazendo a importa��o
		/*
		 * System.out.println(request.getParameter("nome"));
		 * System.out.println(request.getParameter("fone"));
		 * System.out.println(request.getParameter("email"));
		 */
		// setar as vari�veis javabeans
		// o objeto cantato atrav�s desta linha consegue armazenar na vari�vel
		// nome o valor que ele est� recebendo
		// do formul�rio contato
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		// vindo do m�todo Dao invoco o m�todo inserirContato
		dao.inserirContato(contato);
		// retorno no Dao para trabalhar esse m�todo
		// redireciono ao doc. agenda.jsp
		// a requisi��o main mostra os contatos
		response.sendRedirect("main");
	}

	/**
	 * Listar contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// listar contato
	protected void listarContato(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// elimino esse linha e subtituo pela de baixo, pois por uma quest�o
		// did�tica foi usada para saber se foi usado pelo
		// servlet
		// String idcon = request.getParameter("idcon");
		// atrav�s objeto contato chamo o m�todo setIdcon que chama os outros
		// atributos
		contato.setIdcon(request.getParameter("idcon"));
		// m�todo que buscar o contato atrav�s do idcon
		dao.selecionarContato(contato);
		// request � o que vem da caixa de texto da p�gina jsp setAttribute
		// altera o atributo
		request.setAttribute("idcon", contato.getIdcon());
		request.setAttribute("nome", contato.getNome());
		request.setAttribute("fone", contato.getFone());
		request.setAttribute("email", contato.getEmail());
		// dispacho para editar.jsp
		RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
		rd.forward(request, response);
	}

	/**
	 * Editar contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// editar contato m�todo para edi��o dos valores do banco
	protected void editarContato(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// objeto contato chama o m�todo que altera o valor da vari�vel que tem
		// como par�metro request que pede
		// por outro m�todo que pega o par�metro que tem como par�metro a
		// vari�vel alterada
		contato.setIdcon(request.getParameter("idcon"));
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		// chamo pelo objeto dao ,que fora inst�nciado l� em cima, que chama o
		// m�todo alterarContato() que tem como
		// par�metro o objeto contato que recebe todas as vari�veis alteradas
		dao.alterarContato(contato);
		// e como resposta pe�o para redirecionar para a p�gina main
		response.sendRedirect("main");

	}

	/**
	 * Remover contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// remover contato
	protected void removerContato(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		contato.setIdcon(request.getParameter("idcon"));
		dao.deletarContato(contato);
		response.sendRedirect("main");
	}

	/**
	 * Gerar relatorio.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// gerar relat�rio em pdf gerarRelatorio
	protected void gerarRelatorio(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// crio um objeto de nome documento
		Document documento = new Document();// control + shift + o para importar
		try {
			// tipo de conte�do/nesse linha digo qual o tipo de documento
			response.setContentType("apllication/pdf");
			// nome do documento
			response.addHeader("Content-Disposition",
					"inline; filename=contatos.pdf");
			// criar documento
			PdfWriter.getInstance(documento, response.getOutputStream());
			// abrimos o documento para -> conte�do
			documento.open();
			documento.add(new Paragraph("Lista de contatos: "));
			// associemos a lista de contatos ao documento contatos.pdf
			documento.add(new Paragraph(" "));
			// criar uma tabela, preciso criar outro objeto pdfTable, 3 � o
			// n�mero de colunas
			PdfPTable tabela = new PdfPTable(3);
			// cabe�alho
			PdfPCell col1 = new PdfPCell(new Paragraph("Nome"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Fone"));
			PdfPCell col3 = new PdfPCell(new Paragraph("Email"));
			tabela.addCell(col1);
			tabela.addCell(col2);
			tabela.addCell(col3);
			// popular dos dados da tabela com os dados, � o mesmo m�todo usado
			// logo a cima para gerar a tabela
			ArrayList<JavaBeans> lista = dao.listarContatos();
			// para percorrer uso o la�o for
			for (int i = 0; i < lista.size(); i++) {
				tabela.addCell(lista.get(i).getNome());
				tabela.addCell(lista.get(i).getFone());
				tabela.addCell(lista.get(i).getEmail());
			}
			documento.add(tabela);
			documento.close();

			documento.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			documento.close();
		}

	}

}
