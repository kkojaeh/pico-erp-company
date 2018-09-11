package pico.erp.company.address;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface CompanyAddressExceptions {

  @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "company.address.already.exists.exception")
  class CompanyAddressAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;
  }

  @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "company.address.not.found.exception")
  class CompanyAddressNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

  }
}
