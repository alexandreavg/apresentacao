package br.com.alexandre.email.dtos;

import java.io.Serializable;
import java.util.Date;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailRespostaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private boolean enviado;
	private Date data;
	private String retorno;
	
	public EmailRespostaDTO(String dtoSerializado) {
		try {
			EmailRespostaDTO dto = new Gson().fromJson(dtoSerializado, EmailRespostaDTO.class);
			
			this.id = dto.getId();
			this.enviado = dto.isEnviado();
			this.data = dto.getData();
			this.retorno = dto.getRetorno();
		} catch (Exception e) {
			return;
		}
	}

}
