package pico.erp.company.contact;

import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pico.erp.company.CompanyId;
import pico.erp.shared.data.LabeledValuable;

public interface CompanyContactQuery {

  List<? extends LabeledValuable> asLabels(@NotNull CompanyId companyId, @NotNull String keyword,
    long limit);

  Page<CompanyContactView> retrieve(@NotNull CompanyContactView.Filter filter,
    @NotNull Pageable pageable);

}
