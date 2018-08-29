package pico.erp.company.core;

import java.util.List;
import java.util.stream.Collectors;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.audit.AuditService;
import pico.erp.company.CompanyContactExceptions.AlreadyExistsException;
import pico.erp.company.CompanyContactExceptions.NotFoundException;
import pico.erp.company.CompanyContactRequests.CreateRequest;
import pico.erp.company.CompanyContactRequests.DeleteRequest;
import pico.erp.company.CompanyContactRequests.UpdateRequest;
import pico.erp.company.CompanyContactService;
import pico.erp.company.data.CompanyContactData;
import pico.erp.company.data.CompanyContactId;
import pico.erp.company.data.CompanyId;
import pico.erp.company.domain.CompanyContact;
import pico.erp.shared.Public;
import pico.erp.shared.event.EventPublisher;

@SuppressWarnings("Duplicates")
@Service
@Public
@Transactional
@Validated
public class CompanyContactServiceLogic implements CompanyContactService {

  @Autowired
  private CompanyContactRepository companyContactRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Autowired
  @Lazy
  private AuditService auditService;

  @Autowired
  private CompanyMapper mapper;

  @Override
  public CompanyContactData create(CreateRequest request) {
    if (companyContactRepository.exists(request.getId())) {
      throw new AlreadyExistsException();
    }
    val companyContact = new CompanyContact();
    val response = companyContact.apply(mapper.map(request));
    val created = companyContactRepository.create(companyContact);
    eventPublisher.publishEvents(response.getEvents());
    return mapper.map(created);
  }

  @Override
  public void delete(DeleteRequest request) {
    val companyContact = companyContactRepository.findBy(request.getId())
      .orElseThrow(NotFoundException::new);
    val response = companyContact.apply(mapper.map(request));
    companyContactRepository.deleteBy(request.getId());
    auditService.delete(companyContact);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public boolean exists(CompanyContactId id) {
    return companyContactRepository.exists(id);
  }

  @Override
  public CompanyContactData get(CompanyContactId id) {
    return companyContactRepository.findBy(id)
      .map(mapper::map)
      .orElseThrow(NotFoundException::new);
  }

  @Override
  public List<CompanyContactData> getAll(CompanyId companyId) {
    return companyContactRepository.findAllBy(companyId)
      .map(mapper::map)
      .collect(Collectors.toList());
  }

  @Override
  public void update(UpdateRequest request) {
    val companyContact = companyContactRepository.findBy(request.getId())
      .orElseThrow(NotFoundException::new);
    val response = companyContact.apply(mapper.map(request));
    companyContactRepository.update(companyContact);
    auditService.commit(companyContact);
    eventPublisher.publishEvents(response.getEvents());
  }
}
