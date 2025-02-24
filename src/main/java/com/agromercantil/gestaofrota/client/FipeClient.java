package com.agromercantil.gestaofrota.client;

import com.agromercantil.gestaofrota.integration.dto.FipePrice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "fipeClient", url = " https://deividfortuna.github.io/fipe/v2/")
public interface FipeClient {

    @GetMapping("/caminhoes/marcas")
    List<Object> getBrands();

    @GetMapping("/caminhoes/marcas/{marcaId}/modelos")
    ModelsResponse getModels(@PathVariable("marcaId") String marcaId);

    @GetMapping("/caminhoes/marcas/{marcaId}/modelos/{modeloId}/anos/{ano}")
    FipePrice getPrice(@PathVariable("marcaId") String marcaId,
                       @PathVariable("modeloId") String modeloId,
                       @PathVariable("ano") String ano);
}