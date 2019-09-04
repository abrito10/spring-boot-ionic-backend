package com.abrito10.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.abrito10.cursomc.dao.ClienteDAO;
import com.abrito10.cursomc.domain.Cliente;
import com.abrito10.cursomc.dto.ClienteDTO;
import com.abrito10.cursomc.resources.exception.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteDAO repo;
	
	@Override
	public void initialize(ClienteUpdate ann) {
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		//pegar o id da url
		Map<String,String> map = (Map<String,String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
		
		List<FieldMessage> list = new ArrayList<>();
		
		Cliente cliAux= repo.findByEmail(objDto.getEmail());
		if(cliAux != null && !cliAux.getId().equals(uriId)) {
			list.add(new FieldMessage("email","CNPJ ja existente"));			
		}
				
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
		}
		return list.isEmpty();
	}
}