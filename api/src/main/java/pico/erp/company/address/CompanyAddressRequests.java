package pico.erp.company.address;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.company.CompanyId;
import pico.erp.shared.TypeDefinitions;
import pico.erp.shared.data.Address;

public interface CompanyAddressRequests {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CreateRequest {

    @Valid
    @NotNull
    CompanyAddressId id;

    @Valid
    @NotNull
    CompanyId companyId;

    @Size(min = 1, max = TypeDefinitions.NAME_LENGTH)
    @NotNull
    String name;

    @Size(max = TypeDefinitions.PHONE_NUMBER_LENGTH)
    String telephoneNumber;

    @Size(max = TypeDefinitions.PHONE_NUMBER_LENGTH)
    String mobilePhoneNumber;

    @Valid
    @NotNull
    Address address;

    boolean enabled;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class UpdateRequest {

    @Valid
    @NotNull
    CompanyAddressId id;

    @Size(min = 1, max = TypeDefinitions.NAME_LENGTH)
    @NotNull
    String name;

    @Size(max = TypeDefinitions.PHONE_NUMBER_LENGTH)
    String telephoneNumber;

    @Size(max = TypeDefinitions.PHONE_NUMBER_LENGTH)
    String mobilePhoneNumber;

    @Valid
    @NotNull
    Address address;

    boolean enabled;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  class DeleteRequest {

    @Valid
    @NotNull
    CompanyAddressId id;
  }
}
