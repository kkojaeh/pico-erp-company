package pico.erp.company.address;

import static org.springframework.util.StringUtils.isEmpty;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.company.CompanyId;
import pico.erp.company.QCompanyEntity;
import pico.erp.shared.LabeledValue;
import pico.erp.shared.Public;
import pico.erp.shared.QExtendedLabeledValue;
import pico.erp.shared.data.LabeledValuable;
import pico.erp.shared.jpa.QueryDslJpaSupport;

@Service
@Public
@Transactional(readOnly = true)
@Validated
public class CompanyAddressQueryJpa implements CompanyAddressQuery {

  private final QCompanyAddressEntity companyAddress = QCompanyAddressEntity.companyAddressEntity;

  private final QCompanyEntity company = QCompanyEntity.companyEntity;

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  private QueryDslJpaSupport queryDslJpaSupport;


  @Override
  public List<? extends LabeledValuable> asLabels(CompanyId companyId, String keyword,
    long limit) {
    val query = new JPAQuery<LabeledValue>(entityManager);
    val select = new QExtendedLabeledValue(
      companyAddress.id.value.as("value"),
      company.name.concat(" / ").concat(companyAddress.name).as("label"),
      companyAddress.address.street.concat(" - ").concat(companyAddress.address.detail)
        .as("subLabel"),
      companyAddress.address.postalCode.as("stamp")
    );
    query.select(select);
    query.from(companyAddress);
    query.join(company)
      .on(companyAddress.companyId.eq(company.id));

    val builder = new BooleanBuilder();
    builder.and(companyAddress.companyId.eq(companyId));
    builder
      .and(companyAddress.name.likeIgnoreCase(queryDslJpaSupport.toLikeKeyword("%", keyword, "%")));
    query.where(builder);
    query.limit(limit);
    query.orderBy(companyAddress.name.asc());
    return query.fetch();
  }

  @Override
  public Page<CompanyAddressView> retrieve(CompanyAddressView.Filter filter,
    Pageable pageable) {
    val query = new JPAQuery<CompanyAddressView>(entityManager);
    val select = Projections.bean(CompanyAddressView.class,
      companyAddress.id,
      company.id.as("companyId"),
      company.name.as("companyName"),
      companyAddress.name,
      companyAddress.telephoneNumber,
      companyAddress.faxNumber,
      companyAddress.address,
      companyAddress.enabled,
      companyAddress.represented,
      companyAddress.createdBy,
      companyAddress.createdDate,
      companyAddress.lastModifiedBy,
      companyAddress.lastModifiedDate
    );

    query.select(select);
    query.from(companyAddress);
    query.join(company)
      .on(companyAddress.companyId.eq(company.id));

    val builder = new BooleanBuilder();

    if (filter.getCompanyId() != null) {
      builder.and(companyAddress.companyId.eq(filter.getCompanyId()));
    }

    if (!isEmpty(filter.getName())) {
      builder.and(
        companyAddress.name
          .likeIgnoreCase(queryDslJpaSupport.toLikeKeyword("%", filter.getName(), "%")));
    }
    if (filter.getEnabled() != null) {
      builder.and(companyAddress.enabled.eq(filter.getEnabled()));
    }
    query.where(builder);

    return queryDslJpaSupport.paging(query, pageable, select);
  }
}
