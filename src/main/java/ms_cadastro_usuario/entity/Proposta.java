package ms_cadastro_usuario.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Proposta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "valor")
	private Double valorSolicitado;

	private Integer prazo;

	private Boolean aprovado;

	private boolean integrada;

	@Column(name = "obs")
	private String observacao;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "id_usuario", nullable = false)
	@JsonManagedReference
	private Usuario usuario;

	public Proposta() {

	}

	public Proposta(Long id, Double valorSolicitado, Integer prazo, Boolean aprovada, boolean integrada,
			String observacao, Usuario usuario) {
		super();
		this.id = id;
		this.valorSolicitado = valorSolicitado;
		this.prazo = prazo;
		this.aprovado = aprovada;
		this.integrada = integrada;
		this.observacao = observacao;
		this.usuario = usuario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getValorSolicitado() {
		return valorSolicitado;
	}

	public void setValorSolicitado(Double valorSolicitado) {
		this.valorSolicitado = valorSolicitado;
	}

	public Integer getPrazo() {
		return prazo;
	}

	public void setPrazo(Integer prazo) {
		this.prazo = prazo;
	}

	public Boolean getAprovado() {
		return aprovado;
	}

	public void setAprovado(Boolean aprovado) {
		this.aprovado = aprovado;
	}

	public boolean isIntegrada() {
		return integrada;
	}

	public void setIntegrada(boolean integrada) {
		this.integrada = integrada;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
