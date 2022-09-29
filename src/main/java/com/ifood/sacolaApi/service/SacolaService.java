package com.ifood.sacolaApi.service;

import com.ifood.sacolaApi.model.Item;
import com.ifood.sacolaApi.model.Sacola;
import com.ifood.sacolaApi.resource.dto.ItemDTO;

public interface SacolaService {

    Item incluirItem(ItemDTO itemDTO);
    Sacola verSacola(Long id);
    Sacola fecharSacola(Long id, int formaPagamento);

}
