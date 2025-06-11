package br.com.alexandre.email.services;

import br.com.alexandre.email.dtos.EmailDTO;

public interface EmailServico {
	
	EmailDTO enviar(EmailDTO dto) throws Exception;
	void salvarLogSucesso(Long id, String retorno);
	void salvarLogExcecao(Long id, String retorno);
	EmailDTO buscarPorId(Long id);
	
}
