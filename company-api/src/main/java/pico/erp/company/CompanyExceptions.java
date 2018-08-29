package pico.erp.company;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface CompanyExceptions {

  @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "company.already.exists.exception")
  class AlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;
  }

  @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "company.not.found.exception")
  class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

  }

  @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "registration.number.already.exists.exception")
  class RegistrationNumberAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;
  }
}
