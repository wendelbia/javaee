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

	//modo de conexão
	/** The driver. */
	//parâmetros de conexão
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
	//método de conexão
	private Connection conectar() {
		Connection con = null;
		try {
			//essa linha lê o driver
			Class.forName(driver);
			//classe que gerencia o drive
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	/*teste de conexão
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
	//crud create metpdo sem retorno que contém os dados criados na classe javabeans
	public void inserirContato(JavaBeans contato) {
		//retorno a classe controller no método novoContato
		String create = "insert into contatos (nome,fone,email) values (?,?,?)";
		try {
			//abrir a conexão com o banco
			Connection con = conectar();
			//preparar a query para ser executada, para isso uso um recurso JDBC
			PreparedStatement pst = (PreparedStatement) con.prepareStatement(create);
			//substituir os parâmetros (?) peo conteúdo das var javaBeans
			//só consigo alcançar esses vari´veis através dos métodos de acesso
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			//tendo substituindo os parâmetros pelas variáveis que vem da classe JavaBeans
			//executo a query que executa definitivamente esses dados
			pst.executeUpdate();
			//encerrar a conexão com o banco, se não fizer, teremos problemas de performance
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
			//e o executeQury que é armazenado no rs executa a query
			ResultSet rs = pst.executeQuery();
			//vamos varrer a tabela que será executado enquanto houve contatos
			//next() é usado para recuperar todos os contatos, não sabemos quantos contatos temos no banco
			//por isso enquanto houver contatos então irá executar esse laço de repetição
			while(rs.next()){
				//criamos variáveis de apoio que recebem os dados do banco
				//é assim que capturamos os dados vindo do banco
				//a variável idcon = recebe através do objeto rs que através do metodo getString 
				//pega como parâmetro na ordem 1 da tabela 
				String idcon = rs.getString(1);
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);
				//vou popular dentro do vetor dinâmico(ArrayList) que é referênciado dentro da classe JavaBeans
				contatos.add(new JavaBeans(idcon, nome, fone, email));
			}
			//quando não houver mais contatos vindos do banco fechamos a conexão com o banco
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
	//método que vai selecionar a linha para alterar na página alterar para edição
	public void selecionarContato(JavaBeans contato) {
		//query de pesquisa
		String read2 = "select * from contatos where idcon = ?";
		try {
			//conexão com o banco
			Connection con = conectar();
			//delcaração da query preparada
			PreparedStatement pst = con.prepareStatement(read2);
			//objeto PreparedStatement que enviará a query buscando através do idcon a pesquisa no banco
			pst.setString(1, contato.getIdcon());
			//pst chama o executeQuery() que faz a pesquisa na tabela e armazena no objeto rs do resultset
			ResultSet rs = pst.executeQuery();
			//atráves do while que faz a iteração(processo iterativo é aquele que faz progresso através 
			//de tentativas sucessivas de refinamento)
			//next chamado pelo rs objeto do Result faz essa pesquisa até não houver mais onde pesquisar
			while(rs.next()) {
				//objeto contato do JavaBeans através do método setIdon altera o objeto da pesquisa 
				//que no caso é o rs.getString(1) o 1 é o que está na posição um no caso idcon e assim por diante
				contato.setIdcon(rs.getString(1));
				contato.setNome(rs.getString(2));
				contato.setFone(rs.getString(3));
				contato.setEmail(rs.getString(4));
			}
			//fecha a conexão
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
	//tem como parâmetro contato do tipo JavaBeans
	public void alterarContato(JavaBeans contato){
		//update recebe a query 
		String update = "update contatos set nome=?,fone=?,email=? where idcon=?";
		try {
			//con do tipo Connection que recebe o método que faz a conexão com o banco de dados
			Connection con = conectar();
			//prepara a query para enviar para o banco con chama o método preparedStatement quem tem como parâmetro
			//a variável que recebe a query
			PreparedStatement pst = con.prepareStatement(update);
			//o objeto pst que recebe o objeto que faz a conexão com o bd e que através dele chama o método 
			//que prepara a declaração da query que tem como parâmetro o update que é a própria query que
			//chama o método setString que altera o valor da variável que recebe como primeiro parâmetro
			//o número que representa a ordem da query que representa a coluna da tabela do banco de dados
			//e em segundo parâmetro o objeto contato da classe JavaBeans que pega o nome alterado
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			pst.setString(4, contato.getIdcon());
			//pst chama o método que executa os comando armazenados no objeto 
			pst.executeUpdate();
			//fecho a conexão
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
