package br.com.diego.msrelatorio.adapters.out.providers;

import br.com.diego.msrelatorio.adapters.out.mapper.LancamentoEntityMapper;
import br.com.diego.msrelatorio.adapters.out.repository.LancamentoRepository;
import br.com.diego.msrelatorio.adapters.out.repository.entities.LancamentoEntity;
import br.com.diego.msrelatorio.config.exception.DataBaseException;
import br.com.diego.msrelatorio.core.domain.Lancamento;
import br.com.diego.msrelatorio.ports.LancamentoProviderPort;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LancamentoProvider implements LancamentoProviderPort {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Override
    public List<Lancamento> buscarLancamentos(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        try {
            List<LancamentoEntity> entities = lancamentoRepository.findByDataCriacaoBetween(
                    dataInicial, dataFinal);
            return entities.stream().map(LancamentoEntityMapper::to)
                    .collect(Collectors.toList());
        }catch (MongoException ex){
            throw new DataBaseException("Falha na persistência dos dados!", ex.getCause());
        }
    }
}
