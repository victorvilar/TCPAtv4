package br.imd.ufrn.psi.TCP;

public class Candidato {

	private int id;
	private String nome;
	private String partido;
	private int qtdVotos = 0;
	
	public Candidato(){
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPartido() {
		return partido;
	}

	public void setPartido(String partido) {
		this.partido = partido;
	}

	public int getQtdVotos() {
		return qtdVotos;
	}

	public void setQtdVotos(int qtdVotos) {
		this.qtdVotos = qtdVotos;
	}

	public void incVotos(){
		this.qtdVotos++;
	}
	
}
