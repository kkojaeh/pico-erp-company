package pico.erp.company.address;

import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pico.erp.company.CompanyId;
import pico.erp.shared.data.LabeledValuable;

public interface CompanyAddressQuery {

  List<? extends LabeledValuable> asLabels(@NotNull CompanyId companyId, @NotNull String keyword,
    long limit);

  Page<CompanyAddressView> retrieve(@NotNull CompanyAddressView.Filter filter,
    @NotNull Pageable pageable);

}
