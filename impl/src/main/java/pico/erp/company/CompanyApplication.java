package pico.erp.company;

import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import pico.erp.audit.AuditApi;
import pico.erp.audit.AuditConfiguration;
import pico.erp.company.CompanyApi.Roles;
import pico.erp.shared.ApplicationId;
import pico.erp.shared.ApplicationStarter;
import pico.erp.shared.Public;
import pico.erp.shared.SpringBootConfigs;
import pico.erp.shared.data.Role;
import pico.erp.shared.impl.ApplicationImpl;

@Slf4j
@SpringBootConfigs
public class CompanyApplication implements ApplicationStarter {

  public static final String CONFIG_NAME = "company/application";

  public static final String CONFIG_NAME_PROPERTY = "spring.config.name=company/application";

  public static final Properties DEFAULT_PROPERTIES = new Properties();

  static {
    DEFAULT_PROPERTIES.put("spring.config.name", CONFIG_NAME);
  }

  public static SpringApplication application() {
    return new SpringApplicationBuilder(CompanyApplication.class)
      .properties(DEFAULT_PROPERTIES)
      .web(false)
      .build();
  }

  public static void main(String[] args) {
    application().run(args);
  }

  @Bean
  @Public
  public AuditConfiguration auditConfiguration() {
    return AuditConfiguration.builder()
      .packageToScan("pico.erp.company")
      .entity(Roles.class)
      .build();
  }

  @Bean
  @Public
  public Role companyManagerRole() {
    return Roles.COMPANY_MANAGER;
  }

  @Override
  public Set<ApplicationId> getDependencies() {
    return Stream.of(AuditApi.ID).collect(Collectors.toSet());
  }

  @Override
  public ApplicationId getId() {
    return CompanyApi.ID;
  }

  @Override
  public boolean isWeb() {
    return false;
  }

  @Override
  public pico.erp.shared.Application start(String... args) {
    return new ApplicationImpl(application().run(args));
  }

}
