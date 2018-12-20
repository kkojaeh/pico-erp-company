package pico.erp.company.contact;

import java.util.Collection;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import pico.erp.company.Company;
import pico.erp.shared.data.Contact;
import pico.erp.shared.event.Event;

public interface CompanyContactMessages {

  @Data
  class CreateRequest {

    @Valid
    @NotNull
    CompanyContactId id;

    @Valid
    @NotNull
    Company company;

    @Valid
    @NotNull
    Contact contact;

    boolean enabled;
  }

  @Data
  class UpdateRequest {

    @Valid
    @NotNull
    Contact contact;

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

    CompanyContact previous;
  }

  @Value
  class PrepareImportResponse {

    Collection<Event> events;

  }
}
