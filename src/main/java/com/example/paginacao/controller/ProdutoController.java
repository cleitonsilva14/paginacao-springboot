package com.example.paginacao.controller;

import com.example.paginacao.modelo.Produto;
import com.example.paginacao.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/produto")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;


    //http://localhost:8282/api/produto?page=0&size=5
    @GetMapping
    public List<Produto> getAll(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "orderBy", defaultValue = "id") String orderBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        Sort sort = Sort.by(sortDirection, orderBy);

        Page<Produto> produtos = produtoRepository.findAll(PageRequest.of(page, size, Sort.by(sortDirection, orderBy)));

        return produtos.getContent();
    }

    // http://localhost:8282/api/produto/paginado?page=1&sort=preco&size=2
    @GetMapping("/paginado")
    public ResponseEntity<Page<Produto>> findAll(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(produtoRepository.findAll(pageable));
    }


    @PostMapping
    public ResponseEntity<List<Produto>> save(@RequestBody List<Produto> produtos){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(produtoRepository.saveAll(produtos));
    }



}
