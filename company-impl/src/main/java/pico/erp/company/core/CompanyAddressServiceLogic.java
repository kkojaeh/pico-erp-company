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
import pico.erp.company.CompanyAddressExceptions.CompanyAddressAlreadyExistsException;
import pico.erp.company.CompanyAddressExceptions.CompanyAddressNotFoundException;
import pico.erp.company.CompanyAddressRequests.CreateRequest;
import pico.erp.company.CompanyAddressRequests.DeleteRequest;
import pico.erp.company.CompanyAddressRequests.UpdateRequest;
import pico.erp.company.CompanyAddressService;
import pico.erp.company.data.CompanyAddressData;
import pico.erp.company.data.CompanyAddressId;
import pico.erp.company.data.CompanyId;
import pico.erp.company.domain.CompanyAddress;
import pico.erp.shared.Public;
import pico.erp.shared.event.EventPublisher;

@SuppressWarnings("Duplicates")
@Service
@Public
@Transactional
@Validated
public class CompanyAddressServiceLogic implements CompanyAddressService {

  @Autowired
  private CompanyAddressRepository companyAddressRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Autowired
  @Lazy
  private AuditService auditService;

  @Autowired
  private CompanyMapper mapper;

  @Override
  public CompanyAddressData create(CreateRequest request) {
    if (companyAddressRepository.exists(request.getId())) {
      throw new CompanyAddressAlreadyExistsException();
    }
    val companyAddress = new CompanyAddress();
    val response = companyAddress.apply(mapper.map(request));
    val created = companyAddressRepository.create(companyAddress);
    eventPublisher.publishEvents(response.getEvents());
    return mapper.map(created);
  }

  @Override
  public void delete(DeleteRequest request) {
    val companyAddress = companyAddressRepository.findBy(request.getId())
      .orElseThrow(CompanyAddressNotFoundException::new);
    val response = companyAddress.apply(mapper.map(request));
    companyAddressRepository.deleteBy(request.getId());
    auditService.delete(companyAddress);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public boolean exists(CompanyAddressId id) {
    return companyAddressRepository.exists(id);
  }

  @Override
  public CompanyAddressData get(CompanyAddressId id) {
    return companyAddressRepository.findBy(id)
      .map(mapper::map)
      .orElseThrow(CompanyAddressNotFoundException::new);
  }

  @Override
  public List<CompanyAddressData> getAll(CompanyId companyId) {
    return companyAddressRepository.findAllBy(companyId)
      .map(mapper::map)
      .collect(Collectors.toList());
  }

  @Override
  public void update(UpdateRequest request) {
    val companyAddress = companyAddressRepository.findBy(request.getId())
      .orElseThrow(CompanyAddressNotFoundException::new);
    val response = companyAddress.apply(mapper.map(request));
    companyAddressRepository.update(companyAddress);
    auditService.commit(companyAddress);
    eventPublisher.publishEvents(response.getEvents());
  }
}
