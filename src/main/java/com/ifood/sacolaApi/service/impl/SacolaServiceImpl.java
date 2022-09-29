package com.ifood.sacolaApi.service.impl;

import com.ifood.sacolaApi.enumeration.FormaPagamento;
import com.ifood.sacolaApi.model.Item;
import com.ifood.sacolaApi.model.Restaurante;
import com.ifood.sacolaApi.model.Sacola;
import com.ifood.sacolaApi.repository.ItemRepository;
import com.ifood.sacolaApi.repository.ProdutoRepository;
import com.ifood.sacolaApi.repository.SacolaRepository;
import com.ifood.sacolaApi.resource.dto.ItemDTO;
import com.ifood.sacolaApi.service.SacolaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SacolaServiceImpl implements SacolaService {

    private final SacolaRepository sacolaRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemRepository itemRepository;

    @Override
    public Item incluirItem(ItemDTO itemDTO) {

        Sacola sacola = verSacola(itemDTO.getIdSacola());

        if(sacola.isFechada()){
            throw new RuntimeException("Esta sacola está fechada.");
        }

        Item itemParaSerInserido = Item.builder()
                .quantidade(itemDTO.getQuantidade())
                .sacola(sacola)
                .produto(produtoRepository.findById(itemDTO.getProdutoId()).orElseThrow(
                        ()->{
                            throw new RuntimeException("Esse produto já existe!");
                        }
                ))
                .build();

        List<Item> itensSacola = sacola.getItens();
        if(itensSacola.isEmpty()){
            itensSacola.add(itemParaSerInserido);
        } else{
            Restaurante restauranteAtual = itensSacola.get(0).getProduto().getRestaurante();
            Restaurante restaurantDoItem = itemParaSerInserido.getProduto().getRestaurante();

            if (restauranteAtual.equals(restaurantDoItem)){
                itensSacola.add(itemParaSerInserido);
            } else{
                throw new RuntimeException("Impossível adicionar produtos de restaurantes diferente." +
                        " Feche a sacola ou limpe-a.");
            }
        }


        List<Double> valorItens = new ArrayList<>();

        for(Item itemDaSacola : itensSacola){
            double valorTotal = itemDaSacola.getProduto().getValorUnitario() * itemDaSacola.getQuantidade();
            valorItens.add(valorTotal);
        }

        double valorTotalSacola = valorItens.stream()
                .mapToDouble(valorTotalItens -> valorTotalItens)
                .sum();
        sacola.setValoresTotal(valorTotalSacola);
        sacolaRepository.save(sacola);
        return itemParaSerInserido;
    }

    @Override
    public Sacola verSacola(Long id) {
        return sacolaRepository.findById(id).orElseThrow(
                () -> {
                    throw new RuntimeException("Essa sacola não existe");
                }
        );
    }

    @Override
    public Sacola fecharSacola(Long id, int numeroFormaPagamento) {

        Sacola sacola = verSacola(id);

        if(sacola.getItens().isEmpty()){
            throw new RuntimeException("Inclua itens na sacola!");
        }

        FormaPagamento formaPagamento =
                numeroFormaPagamento == 0 ? FormaPagamento.DINHEIRO
                        : FormaPagamento.MAQUINETA;
        sacola.setFormaPagamento(formaPagamento);
        sacola.setFechada(true);
        return sacolaRepository.save(sacola);
    }
}
