package pico.erp.company;

import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pico.erp.company.data.CompanyView;
import pico.erp.shared.data.LabeledValuable;

public interface CompanyQuery {

  List<? extends LabeledValuable> asLabels(@NotNull String keyword, long limit);

  Page<CompanyView> retrieve(@NotNull CompanyView.Filter filter, @NotNull Pageable pageable);

}
