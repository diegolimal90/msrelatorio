package br.com.diego.msrelatorio.ports;

import br.com.diego.msrelatorio.core.domain.Lancamento;
import org.apache.commons.lang3.NotImplementedException;

import java.time.LocalDateTime;
import java.util.List;

public interface LancamentoProviderPort {

    default List<Lancamento> buscarLancamentos(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        throw new NotImplementedException("METODO NÃO IMPLEMENTADO.");
    }
}
