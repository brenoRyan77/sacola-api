package com.ifood.sacolaApi.resource;

import com.ifood.sacolaApi.model.Item;
import com.ifood.sacolaApi.model.Sacola;
import com.ifood.sacolaApi.resource.dto.ItemDTO;
import com.ifood.sacolaApi.service.SacolaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ifood/sacolas")
@RequiredArgsConstructor
public class SacolaResource {

    private final SacolaService sacolaService;

    @PostMapping
    public Item incluirItemSacola(@RequestBody ItemDTO itemDTO){
        return sacolaService.incluirItem(itemDTO);
    }

    @GetMapping("/{id}")
    public Sacola verSacola(@PathVariable("id") Long id){
        return sacolaService.verSacola(id);
    }

    @PatchMapping("/fecharSacola/{sacolaId}")
    public Sacola fecharSacola(@PathVariable("sacolaId") Long sacolaId,
                               @RequestParam("formaPagamento") int formaPagamento){
        return sacolaService.fecharSacola(sacolaId, formaPagamento);
    }
}
