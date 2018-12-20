package pico.erp.company;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.shared.TypeDefinitions;

public interface CompanyRequests {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CreateRequest {

    @Valid
    @NotNull
    CompanyId id;

    @Size(min = 1, max = TypeDefinitions.NAME_LENGTH)
    @NotNull
    String name;

    @Valid
    RegistrationNumber registrationNumber;

    @Size(max = TypeDefinitions.HUMAN_NAME_LENGTH)
    String representative;

    boolean supplier;

    boolean customer;

    boolean outsourcing;

    boolean enabled;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class UpdateRequest {

    @Valid
    @NotNull
    CompanyId id;

    @Size(min = 1, max = TypeDefinitions.NAME_LENGTH)
    @NotNull
    String name;

    @Valid
    RegistrationNumber registrationNumber;

    @Size(max = TypeDefinitions.HUMAN_NAME_LENGTH)
    String representative;

    boolean supplier;

    boolean customer;

    boolean outsourcing;

    boolean enabled;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  class DeleteRequest {

    @Valid
    @NotNull
    CompanyId id;
  }
}
