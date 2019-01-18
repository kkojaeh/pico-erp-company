package pico.erp.company;

import java.util.Collection;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import pico.erp.shared.TypeDefinitions;
import pico.erp.shared.event.Event;

public interface CompanyMessages {

  @Data
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

    @Size(max = TypeDefinitions.DESCRIPTION_LENGTH)
    String conditionDescription;

    @Size(max = TypeDefinitions.DESCRIPTION_LENGTH)
    String itemDescription;

    boolean supplier;

    boolean customer;

    boolean outsourcing;

    boolean enabled;

  }

  @Data
  class UpdateRequest {

    @Size(min = 1, max = TypeDefinitions.NAME_LENGTH)
    @NotNull
    String name;

    @Valid
    RegistrationNumber registrationNumber;

    @Size(max = TypeDefinitions.HUMAN_NAME_LENGTH)
    String representative;

    @Size(max = TypeDefinitions.DESCRIPTION_LENGTH)
    String conditionDescription;

    @Size(max = TypeDefinitions.DESCRIPTION_LENGTH)
    String itemDescription;

    boolean supplier;

    boolean customer;

    boolean outsourcing;

    boolean enabled;

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

    Company previous;
  }

  @Value
  class PrepareImportResponse {

    Collection<Event> events;

  }
}
