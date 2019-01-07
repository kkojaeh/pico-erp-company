package pico.erp.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pico.erp.company.address.CompanyAddressRequests;
import pico.erp.company.address.CompanyAddressService;
import pico.erp.shared.ApplicationInitializer;

@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection"})
@Configuration
public class OwnerInitializer implements ApplicationInitializer {

  @Autowired
  private CompanyService companyService;

  @Autowired
  private CompanyAddressService companyAddressService;

  @Override
  public void initialize() {
    CompanyRequests.CreateRequest owner = owner();
    if (!companyService.exists(owner.getId())) {
      companyService.create(owner);
      CompanyAddressRequests.CreateRequest ownerAddress = ownerAddress();
      companyAddressService.create(ownerAddress);
    }
  }

  @Bean
  @ConfigurationProperties("owner")
  public CompanyRequests.CreateRequest owner() {
    return new CompanyRequests.CreateRequest();
  }

  @Bean
  @ConfigurationProperties("owner.address")
  public CompanyAddressRequests.CreateRequest ownerAddress() {
    return new CompanyAddressRequests.CreateRequest();
  }

}
