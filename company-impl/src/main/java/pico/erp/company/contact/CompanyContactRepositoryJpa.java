package pico.erp.company.contact;

import java.util.Optional;
import java.util.stream.Stream;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pico.erp.company.CompanyId;

@Repository
interface CompanyContactEntityRepository extends
  CrudRepository<CompanyContactEntity, CompanyContactId> {

  @Query("SELECT cc FROM CompanyContact cc WHERE cc.company.id = :companyId")
  Stream<CompanyContactEntity> findAllBy(@Param("companyId") CompanyId companyId);

}

@Repository
@Transactional
public class CompanyContactRepositoryJpa implements CompanyContactRepository {

  @Autowired
  private CompanyContactMapper mapper;

  @Autowired
  private CompanyContactEntityRepository repository;

  @Override
  public CompanyContact create(CompanyContact companyContact) {
    val entity = mapper.entity(companyContact);
    val created = repository.save(entity);
    return mapper.domain(created);
  }

  @Override
  public void deleteBy(CompanyContactId id) {
    repository.delete(id);
  }

  @Override
  public boolean exists(CompanyContactId id) {
    return repository.exists(id);
  }

  @Override
  public Stream<CompanyContact> findAllBy(CompanyId companyId) {
    return repository.findAllBy(companyId)
      .map(mapper::domain);
  }

  @Override
  public Optional<CompanyContact> findBy(CompanyContactId id) {
    return Optional.ofNullable(repository.findOne(id))
      .map(mapper::domain);
  }

  @Override
  public void update(CompanyContact companyContact) {
    val entity = repository.findOne(companyContact.getId());
    mapper.pass(mapper.entity(companyContact), entity);
    repository.save(entity);
  }
}
