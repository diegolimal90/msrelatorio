package br.com.diego.msrelatorio.ports;

import br.com.diego.msrelatorio.core.domain.Lancamento;
import org.apache.commons.lang3.NotImplementedException;

import java.time.LocalDate;
import java.util.List;

public interface LancamentoBusinessPort {

    default List<Lancamento> buscarLancamentos(LocalDate data) {
        throw new NotImplementedException("METODO NÃO IMPLEMENTADO.");
    }
}
