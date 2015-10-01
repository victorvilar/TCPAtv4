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
		//Cria servidor para conexão
		ServerSocket sSocket = new ServerSocket(1818);
		System.out.println("Servidor em execução...");
		
		Socket socket = null;
		String msgReceived;
		Votacao votacao = new Votacao();
		
		PrintWriter outPw;
		BufferedReader inBr;
		DataOutputStream outDos;
		String nome;
		
		while(true){
			//Aguarda o eleitor
			System.out.println("Aguardando eleitor...");
			socket = sSocket.accept();
			System.out.println("Eleitor conectado e pronto para votar...");

			//Envia a lista de candidatos
			outPw = new PrintWriter(socket.getOutputStream(),true);
			System.out.println("**** Status da Votacao ****");
			for(Candidato elemento: votacao.getListaCand()){
				outPw.println("Número: " + elemento.getId() + 
								   " - Nome: " + elemento.getNome() + 
								   " - Partido: " + elemento.getPartido());
				System.out.println(elemento.getNome() + ": " + elemento.getQtdVotos());
			}
			
			//Recebe voto
			inBr = new BufferedReader(new InputStreamReader (socket.getInputStream()));
			msgReceived = inBr.readLine();
			nome = votacao.searchCandidato(Integer.parseInt(msgReceived)).getNome();
			System.out.println("Voto recebido.\nCandidato " + msgReceived + ": " + nome);
			
			//Transforma a foto em bytes[]
			File fi = new File("imagensCandidatos/" + nome + ".jpg");
			Path path = Paths.get("imagensCandidatos/" + nome + ".jpg");
			byte[] fileContent = Files.readAllBytes(path);
			
			//Envia o tamanho da imagem primeiro
			outDos = new DataOutputStream(socket.getOutputStream());
			outDos.writeInt(fileContent.length);
			outDos.flush();
					
			//Envia a imagem
			outDos = new DataOutputStream(socket.getOutputStream());
			outDos.write(fileContent);
			outDos.flush();
				
			//Espera confirmação
			//inBr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			msgReceived = inBr.readLine();
			
			if(msgReceived.equals("1")){
				for(Candidato elemento: votacao.listaCand){
					if(elemento.getNome().equals(nome)){
						elemento.incVotos();
					}
				}
			}
			if(msgReceived.equals("sair")){
				break;
			}
		}
		sSocket.close();
	}
}