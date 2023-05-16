package br.com.diego.msrelatorio.adpters.out;

import br.com.diego.msrelatorio.adapters.out.providers.LancamentoProvider;
import br.com.diego.msrelatorio.adapters.out.repository.LancamentoRepository;
import br.com.diego.msrelatorio.adapters.out.repository.entities.LancamentoEntity;
import br.com.diego.msrelatorio.config.exception.DataBaseException;
import br.com.diego.msrelatorio.core.domain.enums.TipoLancamentoEnum;
import com.mongodb.MongoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class LancamentoProviderTest {

    @Mock
    private LancamentoRepository lancamentoRepository;

    @InjectMocks
    private LancamentoProvider lancamentoProvider;

    @Test
    public void buscarLancamentosSuccess() {
        List<LancamentoEntity> entities = List.of(LancamentoEntity.builder()
                .id("123456")
                .tipoLancamento(TipoLancamentoEnum.CREDITO.getValue())
                .dataCriacao(LocalDateTime.now())
                .valor(BigDecimal.valueOf(200.00))
                .build());

        Mockito.when(lancamentoRepository.findByDataCriacaoBetween(
                any(), any())).thenReturn(entities);

        var result = lancamentoProvider.buscarLancamentos(LocalDateTime.now(), LocalDateTime.now());


        Assertions.assertNotNull(result);
        Assertions.assertEquals(Objects.requireNonNull(result).size(), entities.size());
    }

    @Test
    public void buscarLancamentosException() {
        List<LancamentoEntity> entities = List.of(LancamentoEntity.builder()
                .id("123456")
                .tipoLancamento(TipoLancamentoEnum.CREDITO.getValue())
                .dataCriacao(LocalDateTime.now())
                .valor(BigDecimal.valueOf(200.00))
                .build());

        Mockito.doThrow(MongoException.class).when(lancamentoRepository).findByDataCriacaoBetween(
                any(), any());

        Assertions.assertThrows(DataBaseException.class, () -> lancamentoProvider.buscarLancamentos(LocalDateTime.now(), LocalDateTime.now()));
    }
}
