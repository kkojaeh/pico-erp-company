package pico.erp.company;

import java.util.LinkedList;
import java.util.List;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pico.erp.company.CompanyRequests.CreateRequest;
import pico.erp.company.address.CompanyAddressRequests;
import pico.erp.company.address.CompanyAddressService;
import pico.erp.company.contact.CompanyContactRequests;
import pico.erp.company.contact.CompanyContactService;
import pico.erp.shared.ApplicationInitializer;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Configuration
@Profile({"test", "default"})
public class TestDataInitializer implements ApplicationInitializer {

  @Autowired
  private CompanyService companyService;

  @Autowired
  private CompanyContactService companyContactService;

  @Autowired
  private CompanyAddressService companyAddressService;

  @Autowired
  private DataProperties dataProperties;

  @Override
  public void initialize() {
    dataProperties.companies.forEach(companyService::create);
    dataProperties.companyContacts.forEach(companyContactService::create);
    dataProperties.companyAddresses.forEach(companyAddressService::create);
  }

  @Data
  @Configuration
  @ConfigurationProperties("data")
  public static class DataProperties {

    List<CreateRequest> companies = new LinkedList<>();

    List<CompanyContactRequests.CreateRequest> companyContacts = new LinkedList<>();

    List<CompanyAddressRequests.CreateRequest> companyAddresses = new LinkedList<>();

  }

}
