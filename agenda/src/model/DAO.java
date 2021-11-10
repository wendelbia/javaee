package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class DAO.
 */
public class DAO {

	//modo de conex�o
	/** The driver. */
	//par�metros de conex�o
	private String driver = "com.mysql.cj.jdbc.Driver";
	
	/** The url. */
	private String url = "jdbc:mysql://127.0.0.1:3306/dbagenda?useTimezone=true&serverTimezone=UTC";
	
	/** The user. */
	private String user = "root";
	
	/** The password. */
	private String password = "root";
	
	/**
	 * Conectar.
	 *
	 * @return the connection
	 */
	//m�todo de conex�o
	private Connection conectar() {
		Connection con = null;
		try {
			//essa linha l� o driver
			Class.forName(driver);
			//classe que gerencia o drive
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	/*teste de conex�o
	public void testeConexao(){
		try {
			Connection con = conectar();
			System.out.println(con);
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		//vou no controller
	}*/
	/**
	 * Inserir contato.
	 *
	 * @param contato the contato
	 */
	//crud create metpdo sem retorno que cont�m os dados criados na classe javabeans
	public void inserirContato(JavaBeans contato) {
		//retorno a classe controller no m�todo novoContato
		String create = "insert into contatos (nome,fone,email) values (?,?,?)";
		try {
			//abrir a conex�o com o banco
			Connection con = conectar();
			//preparar a query para ser executada, para isso uso um recurso JDBC
			PreparedStatement pst = (PreparedStatement) con.prepareStatement(create);
			//substituir os par�metros (?) peo conte�do das var javaBeans
			//s� consigo alcan�ar esses vari�veis atrav�s dos m�todos de acesso
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			//tendo substituindo os par�metros pelas vari�veis que vem da classe JavaBeans
			//executo a query que executa definitivamente esses dados
			pst.executeUpdate();
			//encerrar a conex�o com o banco, se n�o fizer, teremos problemas de performance
			con.close();
			//retorno a classe controller
		} catch (SQLException e) {
			System.out.println(e);
		}	
	}
	
	/**
	 * Listar contatos.
	 *
	 * @return the array list
	 */
	public ArrayList<JavaBeans> listarContatos(){
		//criando um objeto para acessar a classe JavaBeans
		ArrayList<JavaBeans> contatos = new ArrayList<>();
		String read = "select * from contatos order by nome";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			//ResultSetusuado para retornar temporariamente o retorno de um objeto 
			//e o executeQury que � armazenado no rs executa a query
			ResultSet rs = pst.executeQuery();
			//vamos varrer a tabela que ser� executado enquanto houve contatos
			//next() � usado para recuperar todos os contatos, n�o sabemos quantos contatos temos no banco
			//por isso enquanto houver contatos ent�o ir� executar esse la�o de repeti��o
			while(rs.next()){
				//criamos vari�veis de apoio que recebem os dados do banco
				//� assim que capturamos os dados vindo do banco
				//a vari�vel idcon = recebe atrav�s do objeto rs que atrav�s do metodo getString 
				//pega como par�metro na ordem 1 da tabela 
				String idcon = rs.getString(1);
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);
				//vou popular dentro do vetor din�mico(ArrayList) que � refer�nciado dentro da classe JavaBeans
				contatos.add(new JavaBeans(idcon, nome, fone, email));
			}
			//quando n�o houver mais contatos vindos do banco fechamos a conex�o com o banco
			con.close();
			return contatos;
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return null;
	}
	
	/**
	 * Selecionar contato.
	 *
	 * @param contato the contato
	 */
	//m�todo que vai selecionar a linha para alterar na p�gina alterar para edi��o
	public void selecionarContato(JavaBeans contato) {
		//query de pesquisa
		String read2 = "select * from contatos where idcon = ?";
		try {
			//conex�o com o banco
			Connection con = conectar();
			//delcara��o da query preparada
			PreparedStatement pst = con.prepareStatement(read2);
			//objeto PreparedStatement que enviar� a query buscando atrav�s do idcon a pesquisa no banco
			pst.setString(1, contato.getIdcon());
			//pst chama o executeQuery() que faz a pesquisa na tabela e armazena no objeto rs do resultset
			ResultSet rs = pst.executeQuery();
			//atr�ves do while que faz a itera��o(processo iterativo � aquele que faz progresso atrav�s 
			//de tentativas sucessivas de refinamento)
			//next chamado pelo rs objeto do Result faz essa pesquisa at� n�o houver mais onde pesquisar
			while(rs.next()) {
				//objeto contato do JavaBeans atrav�s do m�todo setIdon altera o objeto da pesquisa 
				//que no caso � o rs.getString(1) o 1 � o que est� na posi��o um no caso idcon e assim por diante
				contato.setIdcon(rs.getString(1));
				contato.setNome(rs.getString(2));
				contato.setFone(rs.getString(3));
				contato.setEmail(rs.getString(4));
			}
			//fecha a conex�o
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}
	
	//alterar contato
	/**
	 * Alterar contato.
	 *
	 * @param contato the contato
	 */
	//tem como par�metro contato do tipo JavaBeans
	public void alterarContato(JavaBeans contato){
		//update recebe a query 
		String update = "update contatos set nome=?,fone=?,email=? where idcon=?";
		try {
			//con do tipo Connection que recebe o m�todo que faz a conex�o com o banco de dados
			Connection con = conectar();
			//prepara a query para enviar para o banco con chama o m�todo preparedStatement quem tem como par�metro
			//a vari�vel que recebe a query
			PreparedStatement pst = con.prepareStatement(update);
			//o objeto pst que recebe o objeto que faz a conex�o com o bd e que atrav�s dele chama o m�todo 
			//que prepara a declara��o da query que tem como par�metro o update que � a pr�pria query que
			//chama o m�todo setString que altera o valor da vari�vel que recebe como primeiro par�metro
			//o n�mero que representa a ordem da query que representa a coluna da tabela do banco de dados
			//e em segundo par�metro o objeto contato da classe JavaBeans que pega o nome alterado
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			pst.setString(4, contato.getIdcon());
			//pst chama o m�todo que executa os comando armazenados no objeto 
			pst.executeUpdate();
			//fecho a conex�o
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Deletar contato.
	 *
	 * @param contato the contato
	 */
	//excluir 
	public void deletarContato(JavaBeans contato){
		String delete = "delete from contatos where idcon=?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(delete);
			pst.setString(1, contato.getIdcon());
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
}
