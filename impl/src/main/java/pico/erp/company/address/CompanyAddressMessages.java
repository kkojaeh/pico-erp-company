package pico.erp.company.address;

import java.util.Collection;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import pico.erp.company.Company;
import pico.erp.shared.TypeDefinitions;
import pico.erp.shared.data.Address;
import pico.erp.shared.event.Event;

public interface CompanyAddressMessages {

  @Data
  class CreateRequest {

    @Valid
    @NotNull
    CompanyAddressId id;

    @Valid
    @NotNull
    Company company;

    @Size(min = 1, max = TypeDefinitions.NAME_LENGTH)
    @NotNull
    String name;

    @Size(max = TypeDefinitions.PHONE_NUMBER_LENGTH)
    String telephoneNumber;

    @Size(max = TypeDefinitions.PHONE_NUMBER_LENGTH)
    String faxNumber;

    @Valid
    @NotNull
    Address address;

    boolean enabled;

    boolean represented;
  }

  @Data
  class UpdateRequest {

    @Size(min = 1, max = TypeDefinitions.NAME_LENGTH)
    @NotNull
    String name;

    @Size(max = TypeDefinitions.PHONE_NUMBER_LENGTH)
    String telephoneNumber;

    @Size(max = TypeDefinitions.PHONE_NUMBER_LENGTH)
    String faxNumber;

    @Valid
    @NotNull
    Address address;

    boolean enabled;

    boolean represented;

  }

  @Data
  class DeleteRequest {

  }

  @Value
  class CreateResponse {

    Collection<Event> events;

  }

  @Value
  class UpdateResponse {

    Collection<Event> events;

  }

  @Value
  class DeleteResponse {

    Collection<Event> events;
  }

  @NoArgsConstructor
  @AllArgsConstructor
  @Data
  class PrepareImportRequest {

    CompanyAddress previous;
  }

  @Value
  class PrepareImportResponse {

    Collection<Event> events;

  }
}
