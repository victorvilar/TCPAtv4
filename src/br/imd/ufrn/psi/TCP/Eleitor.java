package br.imd.ufrn.psi.TCP;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Eleitor {

	public static void main(String[] args) throws IOException {
		
		BufferedReader inBr;
		String msgString;
		Socket socket = null;
		Votacao votacao = new Votacao();
		DataInputStream dis;
		PrintWriter outPw;
		int imageSize, bytesRead, bytesLeft, current;	
		byte[] msg;
		
		//Cria uma janela onde aparecera as informacoes enviadas
		JLabel windowImage = new JLabel();
        JFrame editorFrame = new JFrame("Eleicao TCP");
        editorFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        editorFrame.getContentPane().add(windowImage);
		
		while(true){
			//Se conecta na urna
			socket = new Socket("localhost", 1818);
			
			//Recebe a lista de candidatos
			inBr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			for(int i = 0; i < votacao.quantCand; i++){
				msgString = inBr.readLine();
				System.out.println(msgString);
			}
			
			//Votacao - Envio do voto
			System.out.print("Envie seu voto: ");
			//Ler o voto
			BufferedReader inBrT = new BufferedReader(new InputStreamReader(System.in));
			msgString = inBrT.readLine();
			//Envia o voto para urna
			outPw = new PrintWriter(socket.getOutputStream(), true);
			outPw.println(msgString);
			
			//Recebimento do tamanho da foto
			dis = new DataInputStream(socket.getInputStream());
			imageSize = dis.readInt();
			msg = new byte[imageSize];
			
			//Recebimento da foto
			//dis = new DataInputStream(socket.getInputStream());
			bytesRead = dis.read(msg);
			bytesLeft = imageSize-bytesRead;
			current = bytesRead;
			while(bytesLeft != 0){
				bytesRead = dis.read(msg, current, msg.length-current);
				if(bytesRead < 0)System.out.println("bRead negativo");
				current += bytesRead;
				if(current < 0)System.out.println("current negativo");
				bytesLeft -= bytesRead;
				if(bytesLeft < 0)System.out.println("bytesLeft negativo");
			}
		
			//Mostra a imagem na tela
			windowImage.setIcon(new ImageIcon(ImageIO.read(new ByteArrayInputStream(msg))));
	        editorFrame.setVisible(true);
			editorFrame.pack();
	        editorFrame.setLocationRelativeTo(null);

			//Espera confirmação
			System.out.print("Confirme seu voto: \n1-Sim\n2-Nao\nOpcao: ");
			//inBr = new BufferedReader(new InputStreamReader(System.in));
			msgString = inBrT.readLine();
			outPw = new PrintWriter(socket.getOutputStream(), true);
			outPw.println(msgString);
			if(msgString.equals("1")){
				break;
			}
		}
		socket.close();
	}
}