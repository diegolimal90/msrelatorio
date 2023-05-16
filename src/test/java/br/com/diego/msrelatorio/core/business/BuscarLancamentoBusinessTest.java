package br.com.diego.msrelatorio.core.business;

import br.com.diego.msrelatorio.adapters.out.providers.LancamentoProvider;
import br.com.diego.msrelatorio.adapters.out.repository.entities.LancamentoEntity;
import br.com.diego.msrelatorio.core.domain.Lancamento;
import br.com.diego.msrelatorio.core.domain.enums.TipoLancamentoEnum;
import br.com.diego.msrelatorio.ports.LancamentoBusinessPort;
import br.com.diego.msrelatorio.ports.LancamentoProviderPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class BuscarLancamentoBusinessTest {

    @Mock
    private LancamentoProviderPort lancamentoProviderPort;
    @InjectMocks
    private BuscarLancamentoBusiness buscarLancamentoBusiness;

    @Test
    void buscarLancamentoSuccess(){
        List<Lancamento> entities = List.of(Lancamento.builder()
                .idLancamento("123456")
                .tipoLancamento(TipoLancamentoEnum.CREDITO)
                .dataLancamento(LocalDateTime.now())
                .valor(BigDecimal.valueOf(200.00))
                .build());
        Mockito.when(lancamentoProviderPort.buscarLancamentos(any(), any())).thenReturn(entities);

        var result = buscarLancamentoBusiness.buscarLancamentos(LocalDate.now());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(Objects.requireNonNull(result).size(), entities.size());

    }
}
