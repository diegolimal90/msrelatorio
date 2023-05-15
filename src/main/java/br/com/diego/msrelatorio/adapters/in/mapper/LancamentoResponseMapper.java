package br.com.diego.msrelatorio.adapters.in.mapper;

import br.com.diego.msrelatorio.core.domain.Lancamento;
import br.com.diego.msrelatorio.core.domain.enums.TipoLancamentoEnum;
import br.com.diego.msrelatorio.openapi.dto.LancamentoResponse;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.util.Optional;

public class LancamentoResponseMapper {

    public static Lancamento to(LancamentoResponse mapper){
        return Optional.ofNullable(mapper).map(request -> Lancamento.builder()
                .valor(BigDecimal.valueOf(request.getValor()))
                .tipoLancamento(TipoLancamentoEnum.fromValue(request.getTipoLancamento().getValue()))
                .build()
        ).orElse(new Lancamento());
    }

    public static LancamentoResponse from(Lancamento mapper){
        return Optional.ofNullable(mapper).map(domain -> LancamentoResponse.builder()
                .valor(domain.getValor().doubleValue())
                .tipoLancamento(LancamentoResponse.TipoLancamentoEnum.fromValue(domain.getTipoLancamento().getValue()))
                .dataLancamento(domain.getDataLancamento().atOffset(ZoneOffset.UTC))
                .build()
        ).orElse(new LancamentoResponse());
    }
}
