package br.com.diego.msrelatorio.core.business;

import br.com.diego.msrelatorio.core.domain.Lancamento;
import br.com.diego.msrelatorio.ports.LancamentoBusinessPort;
import br.com.diego.msrelatorio.ports.LancamentoProviderPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class BuscarLancamentoBusiness implements LancamentoBusinessPort {

    @Autowired
    private LancamentoProviderPort lancamentoProviderPort;

    @Override
    public List<Lancamento> buscarLancamentos(LocalDate data) {
        var dataInicial = LocalDateTime.of(data, LocalTime.of(00,00, 00));
        var dataFinal = LocalDateTime.of(data, LocalTime.of(23,59, 59));

        return lancamentoProviderPort.buscarLancamentos(dataInicial, dataFinal);
    }
}
