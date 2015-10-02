package br.imd.ufrn.psi.TCP;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Urna {

	public static void main(String[] args) throws IOException, InterruptedException {
		// Cria servidor para conexão
		ServerSocket sSocket = new ServerSocket(1818);
		System.out.println("Servidor em execução...");

		Socket socket = null;
		String msgReceived, nome;
		Votacao votacao = new Votacao();

		while (true) {
			// Aguarda o eleitor
			System.out.println("Aguardando eleitor...");
			socket = sSocket.accept();
			System.out.println("Eleitor conectado e pronto para votar...");

			PrintWriter outPw = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader inBr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			DataOutputStream outDos = new DataOutputStream(socket.getOutputStream());
			
			// Envia a lista de candidatos
			System.out.println("**** Status da Votacao ****");
			for (Candidato elemento : votacao.getListaCand()) {
				outPw.println("Número: " + elemento.getId() + " - Nome: " + elemento.getNome() + " - Partido: "
						+ elemento.getPartido());
				System.out.println(elemento.getNome() + ": " + elemento.getQtdVotos());
			}

			// Recebe voto
			msgReceived = inBr.readLine();
			nome = votacao.searchCandidato(Integer.parseInt(msgReceived)).getNome();
			System.out.println("Voto recebido.\nCandidato " + msgReceived + ": " + nome);

			// Transforma a foto em bytes[]
			Path path = Paths.get("imagensCandidatos/" + nome + ".jpg");
			byte[] fileContent = Files.readAllBytes(path);

			// Envia o tamanho da imagem primeiro
			outDos.writeInt(fileContent.length);
			outDos.flush();

			// Envia a imagem
			outDos.write(fileContent);
			outDos.flush();

			// Espera confirmação
			msgReceived = inBr.readLine();

			if (msgReceived.equals("1")) {
				for (Candidato elemento : votacao.listaCand) {
					if (elemento.getNome().equals(nome)) {
						elemento.incVotos();
					}
				}
				break;
			}
			if (msgReceived.equals("sair")) {
				break;
			}
			
			System.out.println("**** Status da Votacao ****");
			for (Candidato elemento : votacao.getListaCand()) {
				outPw.println("Número: " + elemento.getId() + " - Nome: " + elemento.getNome() + " - Partido: "
						+ elemento.getPartido());
				System.out.println(elemento.getNome() + ": " + elemento.getQtdVotos());
			}
		}
		sSocket.close();
	}
}