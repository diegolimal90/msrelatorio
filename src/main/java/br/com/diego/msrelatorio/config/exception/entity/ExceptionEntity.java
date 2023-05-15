package br.com.diego.msrelatorio.config.exception.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionEntity implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private Integer status;
    private String msg;
    private Long timestamp;
}