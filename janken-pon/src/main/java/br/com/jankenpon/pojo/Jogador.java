package br.com.jankenpon.pojo;

public class Jogador {
	private String nome;
	private Integer qtdeVitoria;
	private Integer qtdeDerrota;
	private Integer qtdeEmpate;

	public Jogador() {
		this.nome = "";
		this.qtdeVitoria = 0;
		this.qtdeDerrota = 0;
		this.qtdeEmpate = 0;
	}

	public Jogador(String nome) {
		this.nome = nome;
		this.qtdeVitoria = 0;
		this.qtdeDerrota = 0;
		this.qtdeEmpate = 0;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getQtdeVitoria() {
		return qtdeVitoria;
	}

	public void addQtdeVitoria() {
		this.qtdeVitoria++;
	}

	public Integer getQtdeDerrota() {
		return qtdeDerrota;
	}

	public void addQtdeDerrota() {
		this.qtdeDerrota++;
	}

	public Integer getQtdeEmpate() {
		return qtdeEmpate;
	}

	public void addQtdeEmpate() {
		this.qtdeEmpate++;
	}

}
