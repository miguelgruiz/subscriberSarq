package com.gruposantander.subscribersarq.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ApiExceptionHandler {

	private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({NotFoundException.class})
	@ResponseBody
	public ErrorMessage notFoundRequest(HttpServletRequest request, Exception exception) {
		log.error(exception.getMessage());
		return new ErrorMessage(exception, request.getRequestURI());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({BadRequestException.class, org.springframework.dao.DuplicateKeyException.class, FieldInvalidException.class, 
			org.springframework.web.HttpRequestMethodNotSupportedException.class,
			org.springframework.web.bind.MethodArgumentNotValidException.class,
			org.springframework.http.converter.HttpMessageNotReadableException.class,
			org.apache.kafka.common.errors.SerializationException.class,})
	@ResponseBody
	public ErrorMessage badRequest(HttpServletRequest request, Exception exception) {
		log.error(exception.getMessage());
		return new ErrorMessage(exception, request.getRequestURI());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({org.springframework.web.client.HttpClientErrorException.class,})
	@ResponseBody
	public ErrorMessage exception(HttpClientErrorException exception, HttpServletRequest request) {
		log.error(exception.getResponseBodyAsString());
		exception.printStackTrace();
		return new ErrorMessage(new BadRequestException(exception.getResponseBodyAsString()), request.getRequestURI());
	}

	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler({ForbiddenException.class})
	@ResponseBody
	public ErrorMessage forbiddenRequest(Exception exception) {
		return new ErrorMessage(exception, "");
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({Exception.class,})
	@ResponseBody
	public ErrorMessage exception(Exception exception) {
		log.error(exception.getMessage());
		exception.printStackTrace();
		return new ErrorMessage("Server Error. " + exception.getMessage());
	}

}
