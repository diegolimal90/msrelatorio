package br.com.diego.msrelatorio.core.domain;

import br.com.diego.msrelatorio.core.domain.enums.TipoLancamentoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lancamento {

    private String idLancamento;
    private BigDecimal valor;
    private TipoLancamentoEnum tipoLancamento;
    private LocalDateTime dataLancamento;
}
