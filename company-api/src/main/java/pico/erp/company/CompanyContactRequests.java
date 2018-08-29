package pico.erp.company;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.company.data.CompanyContactId;
import pico.erp.company.data.CompanyId;
import pico.erp.shared.data.Contact;

public interface CompanyContactRequests {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CreateRequest {

    @Valid
    @NotNull
    CompanyContactId id;

    @Valid
    @NotNull
    CompanyId companyId;

    @Valid
    @NotNull
    Contact contact;

    boolean enabled;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class UpdateRequest {

    @Valid
    @NotNull
    CompanyContactId id;

    @Valid
    @NotNull
    Contact contact;

    boolean enabled;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  class DeleteRequest {

    @Valid
    @NotNull
    CompanyContactId id;
  }
}
