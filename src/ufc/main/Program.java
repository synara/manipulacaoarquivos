package ufc.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import ufc.model.Usuario;
import ufc.negocio.UsuarioArquivo;

public class Program {
	private static String arquivo = "\\V01\\\\Usuarios.csv";
	private static UsuarioArquivo usuarioArquivo;

	public static void main(String[] args) {
		try {
			usuarioArquivo = new UsuarioArquivo(arquivo);
			alterarUsuario();
			//inserirUsuario();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void inserirUsuario() {
		ArrayList<Usuario> usuarios = new ArrayList<>();
		Usuario u = new Usuario();
		u.setLogin("leonardo");
		u.setNome("Leonardo Oliveira Moreira");
		u.setSenha("ufc123");
		usuarios.add(u);

		Usuario u2 = new Usuario();
		u2.setLogin("emanuel");
		u2.setNome("Emanuel Ferreira Coutinho");
		u2.setSenha("ufc456");
		usuarios.add(u2);

		try {
			for (Usuario usuario : usuarios) {
				usuarioArquivo.inserir(usuario);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void obterTodos() throws IOException {
		UsuarioArquivo usuarioArquivo = new UsuarioArquivo(arquivo);

		ArrayList<Usuario> todosUsuarios = usuarioArquivo.obterTodos();
		for (Usuario usuario : todosUsuarios) {
			System.out.println(usuario.getNome() + ", " + usuario.getLogin() + ", " + usuario.getSenha());
		}
	}

	public static void obterUsuario() throws IOException {
		Optional<Usuario> usuario = usuarioArquivo.obterUsuario("gilherbson");

		if (usuario.isPresent()) {
			System.out.println(
					usuario.get().getNome() + ", " + usuario.get().getLogin() + ", " + usuario.get().getSenha());
		}
	}

	public static void alterarUsuario() {
		Usuario usuario = new Usuario();
		usuario.setLogin("gilherbon");
		usuario.setNome("Gilherbson");
		usuario.setSenha("ufc2344");

		try {
			usuarioArquivo.alterarUsuario(usuario, "leonardo");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
