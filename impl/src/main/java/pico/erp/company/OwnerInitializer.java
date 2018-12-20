package pico.erp.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pico.erp.company.CompanyRequests.CreateRequest;
import pico.erp.shared.ApplicationInitializer;

@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
public class OwnerInitializer implements ApplicationInitializer {

  @Autowired
  private CompanyService companyService;

  @Override
  public void initialize() {
    CreateRequest owner = owner();
    if (!companyService.exists(owner.getId())) {
      companyService.create(owner);
    }
  }

  @Bean
  @ConfigurationProperties("owner")
  public CreateRequest owner() {
    return new CreateRequest();
  }

}
