package br.com.diego.msrelatorio.core.domain.enums;

import lombok.Getter;

@Getter
public enum TipoLancamentoEnum {

    DEBITO("DEBITO"),
    CREDITO("CREDITO");

    private String value;

    TipoLancamentoEnum(String value) {
        this.value = value;
    }

    public static TipoLancamentoEnum fromValue(String value){
        for (TipoLancamentoEnum enumItem : TipoLancamentoEnum.values()) {
            if (enumItem.value.equals(value)) {
                return enumItem;
            }
        }
        throw new IllegalArgumentException("Tipo de lançamento inválido '" + value + "'");
    }
}
