package br.com.alexandre.email.dtos;

import java.io.Serializable;
import java.util.Date;

import com.google.gson.Gson;

import br.com.alexandre.email.entities.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String remetente;
	private String destinatario;
	private String corpo;
	private String assunto;
	private boolean enviado;
	private Date momentoEnvioSmtp;
	private String retorno;
	
	public EmailDTO(Email entity) {
		this.id = entity.getId();
		this.remetente = entity.getRemetente();
		this.destinatario = entity.getDestinatario();
		this.corpo = entity.getCorpo().getCorpo();
		this.assunto = entity.getAssunto();
		this.enviado = entity.isEnviado();
		this.momentoEnvioSmtp = entity.getMomentoEnvioSmtp();
		this.retorno = entity.getRetorno();
	}
	
	public EmailDTO(String dtoSerializado) {
		try {
			EmailDTO dto = new Gson().fromJson(dtoSerializado, EmailDTO.class);
			
			this.id = dto.getId();
			this.remetente = dto.getRemetente();
			this.destinatario = dto.getDestinatario();
			this.corpo = dto.getCorpo();
			this.assunto = dto.getAssunto();
			this.enviado = dto.isEnviado();
			this.momentoEnvioSmtp = dto.getMomentoEnvioSmtp();
			this.retorno = dto.getRetorno();
		} catch (Exception e) {
			return;
		}
	}
	
	public String getDtoParaLogAsString() {
		return "ID: " + this.id 
				+ " - Remetente: " + this.remetente
				+ " - Destinatario: " + this.destinatario 
				+ " - Assunto: " + this.assunto;
	}

}