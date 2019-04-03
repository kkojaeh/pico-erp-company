package pico.erp.company.contact;

import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
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

  @Query("SELECT cc FROM CompanyContact cc WHERE cc.companyId = :companyId")
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
    val entity = mapper.jpa(companyContact);
    val created = repository.save(entity);
    return mapper.jpa(created);
  }

  @Override
  public void deleteBy(CompanyContactId id) {
    repository.deleteById(id);
  }

  @Override
  public boolean exists(CompanyContactId id) {
    return repository.existsById(id);
  }

  @Override
  public Stream<CompanyContact> findAllBy(CompanyId companyId) {
    return repository.findAllBy(companyId)
      .map(mapper::jpa);
  }

  @Override
  public Optional<CompanyContact> findBy(CompanyContactId id) {
    return repository.findById(id)
      .map(mapper::jpa);
  }

  @Override
  public Stream<CompanyContact> getAll() {
    return StreamSupport.stream(
      repository.findAll().spliterator(), false
    ).map(mapper::jpa);
  }

  @Override
  public void update(CompanyContact companyContact) {
    val entity = repository.findById(companyContact.getId()).get();
    mapper.pass(mapper.jpa(companyContact), entity);
    repository.save(entity);
  }
}
