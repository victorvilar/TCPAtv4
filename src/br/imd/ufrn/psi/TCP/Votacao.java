package br.imd.ufrn.psi.TCP;

import java.util.ArrayList;

public class Votacao {

	ArrayList<Candidato> listaCand = new ArrayList<Candidato>();
	int quantCand;
	
	public Votacao(){
		Candidato a1 = new Candidato();
		a1.setId(1);
		a1.setNome("Carol Mcbride");
		a1.setPartido("PT");
		listaCand.add(a1);
		
		Candidato a2 = new Candidato();
		a2.setId(2);
		a2.setNome("Daryl Reedus");
		a2.setPartido("PSDB");
		listaCand.add(a2);
		
		Candidato a3 = new Candidato();
		a3.setId(3);
		a3.setNome("Glenn Yeun");
		a3.setPartido("PSB");
		listaCand.add(a3);
		
		Candidato a4 = new Candidato();
		a4.setId(4);
		a4.setNome("Maggie Cohan");
		a4.setPartido("PR");
		listaCand.add(a4);
		
		Candidato a5 = new Candidato();
		a5.setId(5);
		a5.setNome("Michonne Gurira");
		a5.setPartido("PSB");
		listaCand.add(a5);
		
		Candidato a6 = new Candidato();
		a6.setId(6);
		a6.setNome("Rick Lincoln");
		a6.setPartido("PSB");
		listaCand.add(a6);

		Candidato a7 = new Candidato();
		a7.setId(7);
		a7.setNome("Tara Masterson");
		a7.setPartido("PSB");
		listaCand.add(a7);
		quantCand = listaCand.size();

		Candidato a8 = new Candidato();
		a8.setId(8);
		a8.setNome("Zombie");
		a8.setPartido("PSB");
		listaCand.add(a8);
		
		quantCand = listaCand.size();
	}

	public ArrayList<Candidato> getListaCand() {
		return listaCand;
	}

	public void setListaCand(ArrayList<Candidato> listaCand) {
		this.listaCand = listaCand;
	}

	public int getQuantCand() {
		return quantCand;
	}

	public void setQuantCand(int quantCand) {
		this.quantCand = quantCand;
	}

	public Candidato searchCandidato(int numero){
		for(Candidato elemento: listaCand){
			if(elemento.getId()==numero){
				return elemento;
			}
		}
		return null;
	}
}
