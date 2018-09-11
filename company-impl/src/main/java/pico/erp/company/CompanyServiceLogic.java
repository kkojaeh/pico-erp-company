package pico.erp.company;

import java.util.Optional;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.audit.AuditService;
import pico.erp.company.CompanyExceptions.AlreadyExistsException;
import pico.erp.company.CompanyExceptions.NotFoundException;
import pico.erp.company.CompanyExceptions.RegistrationNumberAlreadyExistsException;
import pico.erp.company.CompanyRequests.CreateRequest;
import pico.erp.company.CompanyRequests.DeleteRequest;
import pico.erp.company.CompanyRequests.UpdateRequest;
import pico.erp.company.data.CompanyData;
import pico.erp.company.data.CompanyId;
import pico.erp.company.data.RegistrationNumber;
import pico.erp.shared.Public;
import pico.erp.shared.event.EventPublisher;

@SuppressWarnings("Duplicates")
@Service
@Public
@Transactional
@Validated
public class CompanyServiceLogic implements CompanyService {

  @Autowired
  private CompanyRepository companyRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Autowired
  @Lazy
  private AuditService auditService;

  @Autowired
  private CompanyMapper mapper;

  @Value("${owner.id}")
  private String ownerId;

  @Override
  public CompanyData create(CreateRequest request) {
    if (companyRepository.exists(request.getId())) {
      throw new AlreadyExistsException();
    }

    if (request.getRegistrationNumber() != null) {
      if (companyRepository.exists(request.getRegistrationNumber())) {
        throw new RegistrationNumberAlreadyExistsException();
      }
    }
    val company = new Company();
    val response = company.apply(mapper.map(request));
    val created = companyRepository.create(company);
    auditService.commit(created);
    eventPublisher.publishEvents(response.getEvents());
    return mapper.map(created);
  }

  @Override
  public void delete(DeleteRequest request) {
    val company = companyRepository.findBy(request.getId())
      .orElseThrow(NotFoundException::new);
    val response = company.apply(mapper.map(request));
    companyRepository.deleteBy(request.getId());
    auditService.delete(company);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public boolean exists(CompanyId id) {
    return companyRepository.exists(id);
  }

  @Override
  public boolean exists(RegistrationNumber registrationNumber) {
    return companyRepository.exists(registrationNumber);
  }

  @Override
  public CompanyData get(CompanyId id) {
    return companyRepository.findBy(id)
      .map(mapper::map)
      .orElseThrow(NotFoundException::new);
  }

  @Override
  public CompanyData get(RegistrationNumber registrationNumber) {
    return companyRepository.findBy(registrationNumber)
      .map(mapper::map)
      .orElseThrow(NotFoundException::new);
  }

  @Override
  public CompanyData getOwner() {
    return get(CompanyId.from(ownerId));
  }

  @Override
  public void update(UpdateRequest request) {
    val company = companyRepository.findBy(request.getId())
      .orElseThrow(NotFoundException::new);

    if (!Optional.ofNullable(request.getRegistrationNumber())
      .equals(Optional.ofNullable(company.getRegistrationNumber()))) {
      if (companyRepository.exists(request.getRegistrationNumber())) {
        throw new RegistrationNumberAlreadyExistsException();
      }
    }

    val response = company.apply(mapper.map(request));
    companyRepository.update(company);
    auditService.commit(company);
    eventPublisher.publishEvents(response.getEvents());
  }
}
