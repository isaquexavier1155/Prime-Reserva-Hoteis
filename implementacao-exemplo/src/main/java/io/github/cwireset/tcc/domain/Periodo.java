package io.github.cwireset.tcc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Embeddable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Periodo {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataHoraInicial;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataHoraFinal;

    /**
     * Foi necessário adicionar 2 horas na data final para garantir que a contagem aconteça de forma justa.
     * Do contrário a contagem sempre iria considerar menos de 1 dia, visto que o horário de entrada é as 14 e de saída as 12, nunca fechando
     * 1 dia completo.
     * @return
     */
    public Long quantidadeDiasNoPeriodo() {
        return Duration.between(dataHoraInicial, dataHoraFinal.plus(2, ChronoUnit.HOURS)).toDays();
    }

    public boolean dataFinalMaiorQueInicial() {
        return dataHoraFinal.isAfter(dataHoraInicial);
    }

}
