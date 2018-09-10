package ufc.negocio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Optional;
import ufc.model.Usuario;

public class UsuarioArquivo {

	private static FileWriter writer;
	private static FileReader reader;
	private static BufferedReader br;
	private static BufferedWriter bw;
	private static PrintWriter pw;

	public UsuarioArquivo(String arquivo) throws IOException {
		writer = new FileWriter(arquivo, true);
		reader = new FileReader(arquivo);
		br = new BufferedReader(reader);
		bw = new BufferedWriter(writer);
		pw = new PrintWriter(bw);
	}

	public void inserir(Usuario u) {
		try {
			if (obterUsuario(u.getLogin().trim()) == null) {
				String linha = u.getNome() + ";" + u.getLogin() + ";" + u.getSenha();
				pw.println(linha);
				pw.close();
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static ArrayList<Usuario> obterTodos() throws IOException {
		ArrayList<Usuario> todosUsuarios = new ArrayList<Usuario>();
		String linha = br.readLine();
		String[] linhas = linha.split(";");
		Usuario usuario = new Usuario();
		try {
			while ((linha = br.readLine()) != null) {
				usuario.setNome(linhas[0]);
				usuario.setLogin(linhas[1]);
				usuario.setSenha(linhas[2]);
				todosUsuarios.add(usuario);
				linha = br.readLine();

				if (!linha.equals(null) && !linha.equals(""))
					linhas = linha.split(";");
			}
		} catch (Exception e) {
			System.out.println("");
		}

		return todosUsuarios;
	}

	public Optional<Usuario> obterUsuario(String login) throws IOException {
		ArrayList<Usuario> usuarios = obterTodos();

		for (Usuario u : usuarios) {
			if (u.getLogin().trim().equals(login))
				return Optional.of(u);
		}
		return Optional.empty();
	}

	public void alterarUsuario(Usuario usuario, String loginAlterar) throws IOException {
		
		//NAO TA FUNCIONANDO
		String original = "\\\\V01\\Usuarios.csv";
		String temporario = "\\\\\\\\V01\\\\Usuarios-Temp.csv";
		String linha = br.readLine();
		String[] linhas = linha.split(";");

		while ((linha = br.readLine()) != null) {
			if (linhas[1].trim().equals(loginAlterar.trim())) {
				String linhaAlterada = usuario.getNome() + ";" + usuario.getLogin() + ";" + usuario.getSenha();
				linha = linha.replace(linha, linhaAlterada);
			}
			writer.write(linha);
			linha = br.readLine();
			linhas = linha.split(";");
		}

		writer.close();
		br.close();

		new File(original).delete();
		new File(temporario).renameTo(new File(original));
	}
}
