package pico.erp.company;

import com.coreoz.windmill.Windmill;
import com.coreoz.windmill.exports.config.ExportHeaderMapping;
import com.coreoz.windmill.exports.exporters.excel.ExportExcelConfig;
import com.coreoz.windmill.files.FileSource;
import com.coreoz.windmill.imports.Parsers;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Stream;
import kkojaeh.spring.boot.component.ComponentBean;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.validation.annotation.Validated;
import pico.erp.company.address.CompanyAddress;
import pico.erp.company.address.CompanyAddressId;
import pico.erp.company.address.CompanyAddressMessages;
import pico.erp.company.address.CompanyAddressRepository;
import pico.erp.company.contact.CompanyContact;
import pico.erp.company.contact.CompanyContactId;
import pico.erp.company.contact.CompanyContactMessages;
import pico.erp.company.contact.CompanyContactRepository;
import pico.erp.shared.data.Address;
import pico.erp.shared.data.Contact;
import pico.erp.shared.data.ContentInputStream;
import pico.erp.shared.event.EventPublisher;

@Component
@ComponentBean
@Validated
@Transactional
public class CompanyTransporterImpl implements CompanyTransporter {

  @Autowired
  private EventPublisher eventPublisher;

  @Autowired
  private CompanyRepository companyRepository;

  @Autowired
  private CompanyContactRepository companyContactRepository;

  @Autowired
  private CompanyAddressRepository companyAddressRepository;

  @Autowired
  private CompanyMapper companyMapper;

  @SneakyThrows
  @Override
  public ContentInputStream exportExcel(ExportRequest request) {
    val locale = LocaleContextHolder.getLocale();
    Stream<Company> companies =
      request.isEmpty() ? Stream.empty() : companyRepository.getAll();
    val workbook = new XSSFWorkbook();
    Windmill
      .export(() -> companies.iterator())
      .withHeaderMapping(
        new ExportHeaderMapping<Company>()
          .add("id", e -> e.getId().getValue())
          .add("name", e -> e.getName())
          .add("registrationNumber",
            e -> Optional.ofNullable(e.getRegistrationNumber()).map(no -> no.getValue())
              .orElse(null))
          .add("representative", e -> e.getRepresentative())
          .add("conditionDescription", e -> e.getConditionDescription())
          .add("itemDescription", e -> e.getItemDescription())
          .add("supplier", e -> e.isSupplier() + "")
          .add("customer", e -> e.isCustomer() + "")
          .add("outsourcing", e -> e.isOutsourcing() + "")
          .add("enabled", e -> e.isEnabled() + "")
      )
      .asExcel(
        ExportExcelConfig.fromWorkbook(workbook).build("companies")
      )
      .write();

    Stream<CompanyAddress> addresses =
      request.isEmpty() ? Stream.empty() : companyAddressRepository.getAll();

    Windmill
      .export(() -> addresses.iterator())
      .withHeaderMapping(
        new ExportHeaderMapping<CompanyAddress>()
          .add("id", e -> e.getId().getValue())
          .add("companyId", e -> e.getCompany().getId().getValue())
          .add("name", e -> e.getName())
          .add("telephoneNumber", e -> e.getTelephoneNumber())
          .add("faxNumber", e -> e.getFaxNumber())
          .add("address[postalCode]", e -> e.getAddress().getPostalCode())
          .add("address[street]", e -> e.getAddress().getStreet())
          .add("address[detail]", e -> e.getAddress().getDetail())
          .add("enabled", e -> e.isEnabled() + "")
          .add("represented", e -> e.isRepresented() + "")
      )
      .asExcel(
        ExportExcelConfig.fromWorkbook(workbook).build("company-addresses")
      )
      .write();

    Stream<CompanyContact> contacts =
      request.isEmpty() ? Stream.empty() : companyContactRepository.getAll();

    Windmill
      .export(() -> contacts.iterator())
      .withHeaderMapping(
        new ExportHeaderMapping<CompanyContact>()
          .add("id", e -> e.getId().getValue())
          .add("companyId", e -> e.getCompany().getId().getValue())
          .add("contact[name]", e -> e.getContact().getName())
          .add("contact[email]", e -> e.getContact().getEmail())
          .add("contact[telephoneNumber]", e -> e.getContact().getTelephoneNumber())
          .add("contact[mobilePhoneNumber]", e -> e.getContact().getMobilePhoneNumber())
          .add("contact[faxNumber]", e -> e.getContact().getFaxNumber())
          .add("enabled", e -> e.isEnabled() + "")
      )
      .asExcel(
        ExportExcelConfig.fromWorkbook(workbook).build("company-contacts")
      )
      .write();

    @Cleanup
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    workbook.write(baos);
    val bytes = baos.toByteArray();
    return ContentInputStream.builder()
      .name(
        String.format("companies-%s.%s",
          DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now()),
          ContentInputStream.XLSX_CONTENT_EXTENSION
        )
      )
      .contentType(ContentInputStream.XLSX_CONTENT_TYPE)
      .contentLength(bytes.length)
      .inputStream(new ByteArrayInputStream(bytes))
      .build();
  }

  @SneakyThrows
  @Override
  public void importExcel(ImportRequest request) {
    val bytes = StreamUtils.copyToByteArray(request.getInputStream());
    Stream<Company> companies = Parsers.xlsx("companies")
      .trimValues()
      .parse(FileSource.of(bytes))
      .skip(1)
      .map(row -> Company.builder()
        .id(CompanyId.from(row.cell("id").asString()))
        .name(row.cell("name").asString())
        .registrationNumber(
          Optional.ofNullable(row.cell("registrationNumber").asString())
            .map(value -> RegistrationNumber.from(value))
            .orElse(null)
        )
        .representative(row.cell("representative").asString())
        .conditionDescription(row.cell("conditionDescription").asString())
        .itemDescription(row.cell("itemDescription").asString())
        .supplier(Boolean.valueOf(row.cell("supplier").asString()))
        .customer(Boolean.valueOf(row.cell("customer").asString()))
        .outsourcing(Boolean.valueOf(row.cell("outsourcing").asString()))
        .enabled(Boolean.valueOf(row.cell("enabled").asString()))
        .build()
      );
    companies.forEach(company -> {
      val previous = companyRepository.findBy(company.getId()).orElse(null);
      val response = company.apply(new CompanyMessages.PrepareImportRequest(previous));
      if (previous == null) {
        companyRepository.create(company);
      } else if (request.isOverwrite()) {
        companyRepository.update(company);
      }
      eventPublisher.publishEvents(response.getEvents());
    });
    Stream<CompanyAddress> addresses = Parsers.xlsx("company-addresses")
      .trimValues()
      .parse(FileSource.of(bytes))
      .skip(1)
      .map(row -> CompanyAddress.builder()
        .id(CompanyAddressId.from(row.cell("id").asString()))
        .company(companyMapper.map(CompanyId.from(row.cell("companyId").asString())))
        .name(row.cell("name").asString())
        .telephoneNumber(row.cell("telephoneNumber").asString())
        .faxNumber(row.cell("faxNumber").asString())
        .enabled(Boolean.valueOf(row.cell("enabled").asString()))
        .represented(Boolean.valueOf(row.cell("represented").asString()))
        .address(
          new Address(
            row.cell("address[postalCode]").asString(),
            row.cell("address[street]").asString(),
            row.cell("address[detail]").asString()
          )
        )
        .build()
      );
    addresses.forEach(address -> {
      val previous = companyAddressRepository.findBy(address.getId()).orElse(null);
      val response = address.apply(new CompanyAddressMessages.PrepareImportRequest(previous));
      if (previous == null) {
        companyAddressRepository.create(address);
      } else if (request.isOverwrite()) {
        companyAddressRepository.update(address);
      }
      eventPublisher.publishEvents(response.getEvents());
    });

    Stream<CompanyContact> contacts = Parsers.xlsx("company-contacts")
      .trimValues()
      .parse(FileSource.of(bytes))
      .skip(1)
      .map(row -> CompanyContact.builder()
        .id(CompanyContactId.from(row.cell("id").asString()))
        .company(companyMapper.map(CompanyId.from(row.cell("companyId").asString())))
        .enabled(Boolean.valueOf(row.cell("enabled").asString()))
        .contact(
          new Contact(
            row.cell("contact[name]").asString(),
            row.cell("contact[email]").asString(),
            row.cell("contact[telephoneNumber]").asString(),
            row.cell("contact[mobilePhoneNumber]").asString(),
            row.cell("contact[faxNumber]").asString()
          )
        )
        .build()
      );
    contacts.forEach(contact -> {
      val previous = companyContactRepository.findBy(contact.getId()).orElse(null);
      val response = contact.apply(new CompanyContactMessages.PrepareImportRequest(previous));
      if (previous == null) {
        companyContactRepository.create(contact);
      } else if (request.isOverwrite()) {
        companyContactRepository.update(contact);
      }
      eventPublisher.publishEvents(response.getEvents());
    });

  }
}
