package br.com.diego.msrelatorio.adapters.out.repository.entities;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("lancamento")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LancamentoEntity {

    @Id
    private String id;
    private BigDecimal valor;
    private String tipoLancamento;
    @CreatedDate
    private LocalDateTime dataCriacao;
}
