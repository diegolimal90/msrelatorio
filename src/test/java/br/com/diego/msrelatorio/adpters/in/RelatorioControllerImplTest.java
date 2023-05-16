package br.com.diego.msrelatorio.adpters.in;


import br.com.diego.msrelatorio.adapters.in.RelatorioControllerImpl;
import br.com.diego.msrelatorio.core.domain.Lancamento;
import br.com.diego.msrelatorio.core.domain.enums.TipoLancamentoEnum;
import br.com.diego.msrelatorio.ports.LancamentoBusinessPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class RelatorioControllerImplTest {

    @Mock
    private LancamentoBusinessPort lancamentoBusinessPort;
    @InjectMocks
    private RelatorioControllerImpl relatorioController;

    @Test
    public void buscarRelatorioSuccess(){
        var lancamentoList = List.of(Lancamento.builder()
                .tipoLancamento(TipoLancamentoEnum.CREDITO)
                .dataLancamento(LocalDateTime.now())
                .valor(BigDecimal.valueOf(200.00))
                .build());

        Mockito.when(lancamentoBusinessPort.buscarLancamentos(any())).thenReturn(lancamentoList);

        var responseEntity = relatorioController.buscarLancamentos(LocalDate.now());

        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(Objects.requireNonNull(responseEntity.getBody()).size(), lancamentoList.size());
    }
}
