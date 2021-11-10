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
/*main é uma requisição que o servlet vai trabalhar */
//essas url's são caminhos que definimos através de link's, botões e etc
//quando adciono em urlPatterns a camada controller consegue receber os dado do formulário 
/**
 * The Class Controller.
 */
//**********com essa palavra "/insert" o servlet recebe requisições do formulário
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

	// vou redirecionar as requisições que foram configuradas no
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	// @WebServlet(urlPatterns... para métodos específicos
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// teste de conexão
		// response.getWriter().append("Served at:").append(request.getContextPath());
		// dao.testeConexao();

		// declaro uma variável que vai armazenar o caminho da requisição
		String action = request.getServletPath();
		// teste que vai imprimir a variavel action
		// System.out.println(action);
		// se action for igual a /main, se o método doget receber a requisição
		// /main então
		// eu quero redirecionar ao método que irá trabalhar especificadamente
		// essa requisição
		// ***** aqui foi criado uma variável que armazena uma requisição,
		// dentro dessa estrutura if else e
		// ***** se for main será redirecionado para o metodo contatos(request,
		// response) se for verdadeira essa
		// ***** condição então iguinoro as outras se não for /main a
		// ação(action) então irá analizar a segunda
		// ***** que no caso é /insert e então irá invocar esse novo metodo que
		// é novoContato(request, response)
		if (action.equals("/main")) {
			contatos(request, response);
			// quando a action é igual a insert vou redirecionar ao método
			// responsável por essa requisição a camada
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
			// ****caso não seja nem /main nem /insert
			response.sendRedirect("index.html");
		}
	}

	// vou pedir para redirecionar para agenda.jsp que é o método para listar
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
		// criando um objeto que irá receber os dados JavaBeans do tipo
		// ArrayList o resulta irá para
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
		// agora armazeno esses dados nas variáveis do arquivo javabeans e essas
		// variáveis estão encapsuladas
		// portanto preciso acessá-las instânceando e antes fazendo a importação
		/*
		 * System.out.println(request.getParameter("nome"));
		 * System.out.println(request.getParameter("fone"));
		 * System.out.println(request.getParameter("email"));
		 */
		// setar as variáveis javabeans
		// o objeto cantato através desta linha consegue armazenar na variável
		// nome o valor que ele está recebendo
		// do formulário contato
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		// vindo do método Dao invoco o método inserirContato
		dao.inserirContato(contato);
		// retorno no Dao para trabalhar esse método
		// redireciono ao doc. agenda.jsp
		// a requisição main mostra os contatos
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
		// elimino esse linha e subtituo pela de baixo, pois por uma questão
		// didática foi usada para saber se foi usado pelo
		// servlet
		// String idcon = request.getParameter("idcon");
		// através objeto contato chamo o método setIdcon que chama os outros
		// atributos
		contato.setIdcon(request.getParameter("idcon"));
		// método que buscar o contato através do idcon
		dao.selecionarContato(contato);
		// request é o que vem da caixa de texto da página jsp setAttribute
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
	// editar contato método para edição dos valores do banco
	protected void editarContato(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// objeto contato chama o método que altera o valor da variável que tem
		// como parâmetro request que pede
		// por outro método que pega o parâmetro que tem como parâmetro a
		// variável alterada
		contato.setIdcon(request.getParameter("idcon"));
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		// chamo pelo objeto dao ,que fora instânciado lá em cima, que chama o
		// método alterarContato() que tem como
		// parâmetro o objeto contato que recebe todas as variáveis alteradas
		dao.alterarContato(contato);
		// e como resposta peço para redirecionar para a página main
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
	// gerar relatório em pdf gerarRelatorio
	protected void gerarRelatorio(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// crio um objeto de nome documento
		Document documento = new Document();// control + shift + o para importar
		try {
			// tipo de conteúdo/nesse linha digo qual o tipo de documento
			response.setContentType("apllication/pdf");
			// nome do documento
			response.addHeader("Content-Disposition",
					"inline; filename=contatos.pdf");
			// criar documento
			PdfWriter.getInstance(documento, response.getOutputStream());
			// abrimos o documento para -> conteúdo
			documento.open();
			documento.add(new Paragraph("Lista de contatos: "));
			// associemos a lista de contatos ao documento contatos.pdf
			documento.add(new Paragraph(" "));
			// criar uma tabela, preciso criar outro objeto pdfTable, 3 é o
			// número de colunas
			PdfPTable tabela = new PdfPTable(3);
			// cabeçalho
			PdfPCell col1 = new PdfPCell(new Paragraph("Nome"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Fone"));
			PdfPCell col3 = new PdfPCell(new Paragraph("Email"));
			tabela.addCell(col1);
			tabela.addCell(col2);
			tabela.addCell(col3);
			// popular dos dados da tabela com os dados, é o mesmo método usado
			// logo a cima para gerar a tabela
			ArrayList<JavaBeans> lista = dao.listarContatos();
			// para percorrer uso o laço for
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
