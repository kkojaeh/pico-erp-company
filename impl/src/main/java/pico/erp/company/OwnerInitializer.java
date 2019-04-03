package pico.erp.company;

import kkojaeh.spring.boot.component.SpringBootComponentReadyEvent;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pico.erp.company.address.CompanyAddressRequests;
import pico.erp.company.address.CompanyAddressService;

@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection"})
@Configuration
public class OwnerInitializer implements ApplicationListener<SpringBootComponentReadyEvent> {

  @Autowired
  private CompanyService companyService;

  @Autowired
  private CompanyAddressService companyAddressService;

  @Override
  public void onApplicationEvent(SpringBootComponentReadyEvent event) {
    val owner = owner();
    if (!companyService.exists(owner.getId())) {
      companyService.create(owner);
      val ownerAddress = ownerAddress();
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
