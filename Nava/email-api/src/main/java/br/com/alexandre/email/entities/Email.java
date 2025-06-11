package br.com.alexandre.email.entities;

import java.util.Date;

import br.com.alexandre.email.dtos.EmailDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_email")
@EqualsAndHashCode(of = "id")
public class Email {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 4000)
	private String remetente;
	@Column(length = 4000)
	private String destinatario;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "corpo_id")
	private EmailCorpo corpo;
	
	@Column(length = 4000)
	private String assunto;
	
	@Column(name = "is_enviado")
	private boolean isEnviado;
	
	@Column(name = "momento_envio_smtp")
	private Date momentoEnvioSmtp;
	@Column(name = "momento_inclusao")
	private Date momentoInclusao;
	@Column(name = "momento_ultima_alteracao")
	private Date momentoUltimaAlteracao;
	@Column(name = "momento_envio_para_fila")
	private Date momentoEnvioParaFila;
	
	@Column(length = 4000)
	private String retorno;
	
	@PrePersist
	private void prePersist() {
		this.momentoInclusao = new Date();
	}
	
	@PreUpdate
	private void preUpdate() {
		this.momentoUltimaAlteracao = new Date();
	}
	
	public Email(EmailDTO dto) {
		this.remetente = dto.getRemetente();
		this.destinatario = dto.getDestinatario();
		this.corpo = new EmailCorpo(dto.getCorpo());
		this.assunto = dto.getAssunto();
		this.isEnviado = dto.isEnviado();
		this.momentoEnvioSmtp = dto.getMomentoEnvioSmtp();
		this.retorno = dto.getRetorno();
	}
	
	public Email(EmailDTO dto, String situacao, Date momentoEnvioParaServidorFila) {
		this.remetente = dto.getRemetente();
		this.destinatario = dto.getDestinatario();
		this.corpo = new EmailCorpo(dto.getCorpo());
		this.assunto = dto.getAssunto();
		this.isEnviado = dto.isEnviado();
		this.momentoEnvioSmtp = dto.getMomentoEnvioSmtp();
		this.retorno = situacao;
		this.momentoEnvioParaFila = momentoEnvioParaServidorFila;
	}

}