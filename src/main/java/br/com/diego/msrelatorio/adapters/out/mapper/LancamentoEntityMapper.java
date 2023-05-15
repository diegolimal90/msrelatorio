package br.com.diego.msrelatorio.adapters.out.mapper;

import br.com.diego.msrelatorio.adapters.out.repository.entities.LancamentoEntity;
import br.com.diego.msrelatorio.core.domain.Lancamento;
import br.com.diego.msrelatorio.core.domain.enums.TipoLancamentoEnum;

import java.util.Optional;

public class LancamentoEntityMapper {

    public static Lancamento to(LancamentoEntity mapper){
        return Optional.ofNullable(mapper).map(entity -> Lancamento.builder()
                .idLancamento(entity.getId())
                .valor(entity.getValor())
                .tipoLancamento(TipoLancamentoEnum.fromValue(entity.getTipoLancamento()))
                .dataLancamento(entity.getDataCriacao())
                .build()
        ).orElse(new Lancamento());
    }

    public static LancamentoEntity from(Lancamento mapper){
        return Optional.ofNullable(mapper).map(domain -> LancamentoEntity.builder()
                .id(domain.getIdLancamento())
                .valor(domain.getValor())
                .tipoLancamento(domain.getTipoLancamento().getValue())
                .build()
        ).orElse(new LancamentoEntity());
    }
}
