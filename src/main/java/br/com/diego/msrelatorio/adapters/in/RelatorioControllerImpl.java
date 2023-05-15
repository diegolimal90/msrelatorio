package br.com.diego.msrelatorio.adapters.in;

import br.com.diego.msrelatorio.adapters.in.mapper.LancamentoResponseMapper;
import br.com.diego.msrelatorio.ports.LancamentoBusinessPort;
import br.com.diego.msrelatorio.openapi.api.RelatorioApi;
import br.com.diego.msrelatorio.openapi.dto.LancamentoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RelatorioControllerImpl implements RelatorioApi {

    @Autowired
    private LancamentoBusinessPort lancamentoBusinessPort;

    @Override
    public ResponseEntity<List<LancamentoResponse>> buscarLancamentos(LocalDate data){
        var lancamentos = lancamentoBusinessPort.buscarLancamentos(data);
        return ResponseEntity.ok(lancamentos.stream().map(LancamentoResponseMapper::from)
                .collect(Collectors.toList()));
    }
}
