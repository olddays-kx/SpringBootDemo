package com.kuang.jwt.handler;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import com.kuang.jwt.exception.ApiAbandonedException;
import com.kuang.jwt.exception.EntityExistsException;
import com.kuang.jwt.exception.EntityNotFoundException;
import com.kuang.jwt.exception.ViolationException;
import com.kuang.jwt.vo.ApiErrorVO;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	// 400
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());

		final List<String> errors = new ArrayList<>();
		StringBuilder builder = new StringBuilder();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
			builder.append(error.getDefaultMessage()).append('\n');
		}
		for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
			builder.append(error.getDefaultMessage()).append('\n');
		}
		String message = builder.toString();
		if (message.length() > 1) {
			message = message.substring(0, message.length() - 1);
		}
		HttpStatus s = HttpStatus.BAD_REQUEST;
		final ApiErrorVO apiError = new ApiErrorVO(s.value(), message, errors);
		return handleExceptionInternal(ex, apiError, headers, s, request);
	}

	@Override
	protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());

		final List<String> errors = new ArrayList<>();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}
		HttpStatus s = HttpStatus.BAD_REQUEST;
		final ApiErrorVO apiError = new ApiErrorVO(s.value(), ex.getLocalizedMessage(), errors);
		return handleExceptionInternal(ex, apiError, headers, s, request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());

		final String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type " + ex.getRequiredType();

		HttpStatus s = HttpStatus.BAD_REQUEST;
		final ApiErrorVO apiError = new ApiErrorVO(s.value(), ex.getLocalizedMessage(), error);
		return new ResponseEntity<>(apiError, new HttpHeaders(), s);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());

		final String error = ex.getRequestPartName() + " part is missing";

		HttpStatus s = HttpStatus.BAD_REQUEST;
		final ApiErrorVO apiError = new ApiErrorVO(s.value(), ex.getLocalizedMessage(), error);
		return new ResponseEntity<>(apiError, new HttpHeaders(), s);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());

		final String error = ex.getParameterName() + " parameter is missing";

		HttpStatus s = HttpStatus.BAD_REQUEST;
		final ApiErrorVO apiError = new ApiErrorVO(s.value(), ex.getLocalizedMessage(), error);
		return new ResponseEntity<>(apiError, new HttpHeaders(), s);
	}

	@Override
	protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.info(ex.getClass().getName());

		HttpStatus s = HttpStatus.BAD_REQUEST;
		final ApiErrorVO apiError = new ApiErrorVO(s.value(), ex.getLocalizedMessage(), ex.getMessage());
		return new ResponseEntity<>(apiError, new HttpHeaders(), s);
	}

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex, final WebRequest request) {
		logger.info(ex.getClass().getName());

		final String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();

		HttpStatus s = HttpStatus.BAD_REQUEST;
		final ApiErrorVO apiError = new ApiErrorVO(s.value(), ex.getLocalizedMessage(), error);
		return new ResponseEntity<>(apiError, new HttpHeaders(), s);
	}


	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex, final WebRequest request) {
		logger.info(ex.getClass().getName());

		final List<String> errors = new ArrayList<>();
		for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage());
		}

		HttpStatus s = HttpStatus.BAD_REQUEST;
		final ApiErrorVO apiError = new ApiErrorVO(s.value(), ex.getLocalizedMessage(), errors);
		return new ResponseEntity<>(apiError, new HttpHeaders(), s);
	}

	// 403
	@ExceptionHandler({AccessDeniedException.class})
	protected ResponseEntity<Object> handleAccessDeniedException(final AccessDeniedException ex, final WebRequest request) {
		logger.info(ex.getClass().getName());

		HttpStatus s = HttpStatus.FORBIDDEN;
		final ApiErrorVO apiError = new ApiErrorVO(s.value(), "权限不足", "Access is denied");
		return new ResponseEntity<>(apiError, s);
	}

	// 404
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());

		final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

		HttpStatus s = HttpStatus.NOT_FOUND;
		final ApiErrorVO apiError = new ApiErrorVO(s.value(), ex.getLocalizedMessage(), error);
		return new ResponseEntity<>(apiError, new HttpHeaders(), s);
	}

	@ExceptionHandler({EntityNotFoundException.class})
	protected ResponseEntity<Object> handleEntityNotFoundException(final EntityNotFoundException ex, final WebRequest request) {
		logger.info(ex.getClass().getName());

		HttpStatus s = HttpStatus.NOT_FOUND;
		final ApiErrorVO apiError = new ApiErrorVO(s.value(), ex.getLocalizedMessage(), "EntityNotFound");
		return new ResponseEntity<>(apiError, s);
	}

	// 405
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());

		final StringBuilder builder = new StringBuilder();
		builder.append(ex.getMethod());
		builder.append(" method is not supported for this request. Supported methods are ");
		ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

		HttpStatus s = HttpStatus.METHOD_NOT_ALLOWED;
		final ApiErrorVO apiError = new ApiErrorVO(s.value(), ex.getLocalizedMessage(), builder.toString());
		return new ResponseEntity<>(apiError, new HttpHeaders(), s);
	}

	// 409
	@ExceptionHandler({EntityExistsException.class})
	protected ResponseEntity<Object> handleViolationException(final EntityExistsException ex, final WebRequest request) {
		logger.info(ex.getClass().getName());

		HttpStatus s = HttpStatus.CONFLICT;
		final ApiErrorVO apiError = new ApiErrorVO(s.value(), ex.getLocalizedMessage(), "Conflict");
		return new ResponseEntity<>(apiError, s);
	}

	// 410
	@ExceptionHandler({ApiAbandonedException.class})
	protected ResponseEntity<Object> handleApiAbandonedException(final ApiAbandonedException ex, final WebRequest request) {
		logger.info(ex.getClass().getName());

		HttpStatus s = HttpStatus.GONE;
		final ApiErrorVO apiError = new ApiErrorVO(s.value(), "您使用的版本过旧，请及时更新到最新版本!", "Gone");
		return new ResponseEntity<>(apiError, s);
	}

	// 415
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());

		final StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));

		HttpStatus s = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
		final ApiErrorVO apiError = new ApiErrorVO(s.value(), ex.getLocalizedMessage(), builder.substring(0, builder.length() - 2));
		return new ResponseEntity<>(apiError, new HttpHeaders(), s);
	}

	// 416
	/*@ExceptionHandler({RequestedRangeNotSatisfiableException.class})
	protected ResponseEntity<Object> handleRequestedRangeNotSatisfiable(final RequestedRangeNotSatisfiableException ex, final WebRequest request) {
		logger.info(ex.getClass().getName());

		HttpStatus s = HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE;
		final ApiErrorVO apiError = new ApiErrorVO(s.value(), ex.getLocalizedMessage(), "Requested Range Not Satisfiable");
		return new ResponseEntity<>(apiError, s);
	}*/

	// 422
	@ExceptionHandler({ViolationException.class})
	protected ResponseEntity<Object> handleViolationException(final ViolationException ex, final WebRequest request) {
		logger.info(ex.getClass().getName());

		HttpStatus s = HttpStatus.UNPROCESSABLE_ENTITY;
		final ApiErrorVO apiError = new ApiErrorVO(s.value(), ex.getLocalizedMessage(), "Unprocessable Entity");
		return new ResponseEntity<>(apiError, s);
	}

	// 500
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
		logger.info(ex.getClass().getName());
		logger.error("error", ex);

		HttpStatus s = HttpStatus.INTERNAL_SERVER_ERROR;
		final ApiErrorVO apiError = new ApiErrorVO(s.value(), ex.getLocalizedMessage(), "error occurred");
		return new ResponseEntity<>(apiError, new HttpHeaders(), s);
	}

}
