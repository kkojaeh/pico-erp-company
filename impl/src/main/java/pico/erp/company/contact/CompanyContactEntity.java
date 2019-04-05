package pico.erp.company.contact;


import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Index;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pico.erp.company.CompanyId;
import pico.erp.shared.TypeDefinitions;
import pico.erp.shared.data.Auditor;
import pico.erp.shared.data.Contact;

@Entity(name = "CompanyContact")
@Table(name = "CPN_COMPANY_CONTACT", indexes = {
  @Index(columnList = "COMPANY_ID")
})
@Data
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyContactEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "ID", length = TypeDefinitions.UUID_BINARY_LENGTH))
  })
  CompanyContactId id;

  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "COMPANY_ID", length = TypeDefinitions.ID_LENGTH))
  })
  CompanyId companyId;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "name", column = @Column(name = "NAME", length = TypeDefinitions.NAME_LENGTH)),
    @AttributeOverride(name = "email", column = @Column(name = "EMAIL", length =
      TypeDefinitions.EMAIL_LENGTH * 2)),
    @AttributeOverride(name = "telephoneNumber", column = @Column(name = "TELEPHONE_NUMBER", length =
      TypeDefinitions.PHONE_NUMBER_LENGTH * 2)),
    @AttributeOverride(name = "mobilePhoneNumber", column = @Column(name = "MOBILE_PHONE_NUMBER", length =
      TypeDefinitions.PHONE_NUMBER_LENGTH * 2)),
    @AttributeOverride(name = "faxNumber", column = @Column(name = "FAX_NUMBER", length =
      TypeDefinitions.PHONE_NUMBER_LENGTH * 2))
  })
  @NotNull
  Contact contact;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "CREATED_BY_ID", updatable = false, length = TypeDefinitions.ID_LENGTH)),
    @AttributeOverride(name = "name", column = @Column(name = "CREATED_BY_NAME", updatable = false, length = TypeDefinitions.NAME_LENGTH))
  })
  @CreatedBy
  Auditor createdBy;

  @Column(updatable = false)
  OffsetDateTime createdDate;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "LAST_MODIFIED_BY_ID", length = TypeDefinitions.ID_LENGTH)),
    @AttributeOverride(name = "name", column = @Column(name = "LAST_MODIFIED_BY_NAME", length = TypeDefinitions.NAME_LENGTH))
  })
  @LastModifiedBy
  Auditor lastModifiedBy;

  OffsetDateTime lastModifiedDate;

  @Column
  boolean enabled;

  @PrePersist
  private void onCreate() {
    createdDate = OffsetDateTime.now();
    lastModifiedDate = OffsetDateTime.now();
  }

  @PreUpdate
  private void onUpdate() {
    lastModifiedDate = OffsetDateTime.now();
  }

}
