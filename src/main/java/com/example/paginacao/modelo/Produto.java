package com.example.paginacao.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "tb_produto")
public class Produto {

    @Id
    @EqualsAndHashCode.Include
    @ToString.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;
    @Column(name = "descricao", columnDefinition = "TEXT", length = 500)
    private String descricao;

    @Column(name = "data_de_criacao", updatable = false, nullable = false)
    private LocalDateTime criacao;

    @PrePersist
    protected void onCreate() {
        this.criacao = LocalDateTime.now();
    }

    @Column(name = "preco", precision = 10, scale = 2)
    private BigDecimal preco;

    @ElementCollection
    @CollectionTable(
            name = "tb_produto_imagem",
            joinColumns = @JoinColumn(name = "produto_id")
    )
    @Column(name = "imagem_url")
    private List<String> imagens;


}
