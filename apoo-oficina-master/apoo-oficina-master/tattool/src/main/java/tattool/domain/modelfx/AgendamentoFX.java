package tattool.domain.modelfx;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;

public class AgendamentoFX extends RecursiveTreeObject<AgendamentoFX> {
	private Integer id;

	public SimpleStringProperty hora;
	public SimpleStringProperty preco;
	public SimpleStringProperty cliente;
	public SimpleStringProperty duracao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SimpleStringProperty getHora() {
		return hora;
	}

	public void setHora(SimpleStringProperty hora) {
		this.hora = hora;
	}

	public SimpleStringProperty getPreco() {
		return preco;
	}

	public void setPreco(SimpleStringProperty preco) {
		this.preco = preco;
	}

	public SimpleStringProperty getCliente() {
		return cliente;
	}

	public void setCliente(SimpleStringProperty cliente) {
		this.cliente = cliente;
	}

	public SimpleStringProperty getDuracao() {
		return duracao;
	}

	public void setDuracao(SimpleStringProperty duracao) {
		this.duracao = duracao;
	}

}
