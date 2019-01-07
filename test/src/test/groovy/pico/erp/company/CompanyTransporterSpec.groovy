package pico.erp.company

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.company.address.CompanyAddressId
import pico.erp.company.address.CompanyAddressService
import pico.erp.company.contact.CompanyContactId
import pico.erp.company.contact.CompanyContactService
import pico.erp.shared.IntegrationConfiguration
import spock.lang.Specification

@SpringBootTest(classes = [IntegrationConfiguration])
@Transactional
@Rollback
@ActiveProfiles("test")
@Configuration
@ComponentScan("pico.erp.config")
class CompanyTransporterSpec extends Specification {

  @Autowired
  CompanyTransporter companyTransporter

  @Autowired
  CompanyService companyService

  @Autowired
  CompanyAddressService companyAddressService

  @Autowired
  CompanyContactService companyContactService

  @Value("classpath:company-import-data.xlsx")
  Resource importData

  def "export"() {
    when:
    def inputStream = companyTransporter.exportExcel(
      new CompanyTransporter.ExportRequest(
        empty: false
      )
    )

    then:
    inputStream.contentLength > 0
  }

  def "import - 덮어쓴다"() {
    when:
    companyTransporter.importExcel(
      new CompanyTransporter.ImportRequest(
        inputStream: importData.getInputStream(),
        overwrite: true
      )
    )
    def previous = companyService.get(CompanyId.from("CLI0"))
    def created = companyService.get(CompanyId.from("NEW"))
    def createdAddress = companyAddressService.get(CompanyAddressId.from("6783f4ce-80a2-4313-b43b-7cab32b302f1"))
    def createdContact = companyContactService.get(CompanyContactId.from("ad1428df-cf34-4139-90db-070b3428ec8b"))
    then:
    previous.name == "(주)클리x"
    previous.registrationNumber == RegistrationNumber.from("2118623450")
    previous.representative == "한*2"
    created.name == "테스트사"
    created.registrationNumber == RegistrationNumber.from("0000000005")
    created.representative == "테스트 대표자2"
    createdAddress.companyId == CompanyId.from("NEW")
    createdAddress.name == "테스트"
    createdAddress.faxNumber == "+821011111113"
    createdAddress.telephoneNumber == "+821011111112"
    createdAddress.address.postalCode == "13497"
    createdAddress.address.street == "경기도 성남시 분당구 장미로 43"
    createdAddress.address.detail == "야탑리더스 411호"
    createdAddress.enabled == true

    createdContact.companyId == CompanyId.from("NEW")
    createdContact.contact.name == "테스트 담당자"
    createdContact.contact.email == "customer2@kd-ace.co.kr"
    createdContact.contact.telephoneNumber == "+821011111112"
    createdContact.contact.mobilePhoneNumber == "+821011111113"
    createdContact.contact.faxNumber == "+821011111114"
    createdContact.enabled == true
  }

  def "import - 덮어쓰지 않는다"() {
    when:
    companyTransporter.importExcel(
      new CompanyTransporter.ImportRequest(
        inputStream: importData.getInputStream(),
        overwrite: false
      )
    )
    def previous = companyService.get(CompanyId.from("CLI0"))
    then:
    previous.name != "(주)클리x"
    previous.registrationNumber != RegistrationNumber.from("2118623450")
    previous.representative != "한*2"
  }
}
