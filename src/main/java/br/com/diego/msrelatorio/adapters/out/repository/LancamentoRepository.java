package br.com.diego.msrelatorio.adapters.out.repository;

import br.com.diego.msrelatorio.adapters.out.repository.entities.LancamentoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface LancamentoRepository extends MongoRepository<LancamentoEntity, String> {

    List<LancamentoEntity> findByDataCriacaoBetween(LocalDateTime dataCriacao, LocalDateTime dataCriacao2);
}
