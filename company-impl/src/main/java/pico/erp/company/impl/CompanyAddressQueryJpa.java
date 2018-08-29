package pico.erp.company.impl;

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
import pico.erp.company.CompanyAddressQuery;
import pico.erp.company.data.CompanyAddressView;
import pico.erp.company.data.CompanyId;
import pico.erp.company.impl.jpa.QCompanyAddressEntity;
import pico.erp.shared.ExtendedLabeledValue;
import pico.erp.shared.LabeledValue;
import pico.erp.shared.Public;
import pico.erp.shared.data.LabeledValuable;
import pico.erp.shared.jpa.QueryDslJpaSupport;

@Service
@Public
@Transactional(readOnly = true)
@Validated
public class CompanyAddressQueryJpa implements CompanyAddressQuery {

  private final QCompanyAddressEntity companyAddress = QCompanyAddressEntity.companyAddressEntity;

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  private QueryDslJpaSupport queryDslJpaSupport;


  @Override
  public List<? extends LabeledValuable> asLabels(CompanyId companyId, String keyword,
    long limit) {
    val query = new JPAQuery<LabeledValue>(entityManager);
    val select = Projections.bean(ExtendedLabeledValue.class,
      companyAddress.id.value.as("value"),
      companyAddress.company.name.concat(" / ").concat(companyAddress.name).as("label"),
      companyAddress.address.street.concat(" - ").concat(companyAddress.address.detail)
        .as("subLabel"),
      companyAddress.address.postalCode.as("stamp")
    );
    query.select(select);
    query.from(companyAddress);

    val builder = new BooleanBuilder();
    builder.and(companyAddress.company.id.eq(companyId));
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
      companyAddress.company.id.as("companyId"),
      companyAddress.company.name.as("companyName"),
      companyAddress.name,
      companyAddress.telephoneNumber,
      companyAddress.mobilePhoneNumber,
      companyAddress.address,
      companyAddress.enabled,
      companyAddress.createdBy,
      companyAddress.createdDate,
      companyAddress.lastModifiedBy,
      companyAddress.lastModifiedDate
    );

    query.select(select);
    query.from(companyAddress);
    query.join(companyAddress.company);

    val builder = new BooleanBuilder();

    if (filter.getCompanyId() != null) {
      builder.and(companyAddress.company.id.eq(filter.getCompanyId()));
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
