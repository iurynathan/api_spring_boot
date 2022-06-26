package com.api.energymarket.controllers;

import java.net.URL;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.energymarket.converterxlstojson.ExcelToJSONConverter;
import com.api.energymarket.models.CollectPricesModel;
import com.api.energymarket.services.CollectPricesService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/coletar-precos")
public class CollectPricesController {

  final CollectPricesService collectPricesService;
  Document documento = null;

  public CollectPricesController(CollectPricesService collectPricesService) {
    this.collectPricesService = collectPricesService;
  }

  @PostMapping
  public ResponseEntity<Object> coletarPrecos() throws IOException {
    documento = Jsoup.connect("https://www.ccee.org.br/precos/painel-precos").get();

    Elements elements = documento.select("#tipoPreco");
    String url = elements.select("option:nth-child(4)").attr("data-url");
    try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
      FileOutputStream fileOutputStream = new FileOutputStream("arquivo.xls")) {
        byte dataBuffer[] = new byte[1024];
        int bytesRead;
        while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
            fileOutputStream.write(dataBuffer, 0, bytesRead);
        }
    } catch (IOException e) {
        // handle exception
    }
    var collectPricesModel = new CollectPricesModel();
    File file = new File("arquivo.xls");
    JsonNode json = ExcelToJSONConverter.excelToJson(file);
    final JsonNode arrNode = new ObjectMapper().readTree(json.toString()).get("Test Sheet");
    int id = 0;
    for (JsonNode node : arrNode) {
      id++;
      collectPricesModel.setMes(node.get("MES").asText());
      collectPricesModel.setSudeste(node.get("SUDESTE").asDouble());
      collectPricesModel.setSul(node.get("SUL").asDouble());
      collectPricesModel.setNordeste(node.get("NORDESTE").asDouble());
      collectPricesModel.setNorte(node.get("NORTE").asDouble());
      collectPricesModel.setId(id);
      collectPricesService.save(collectPricesModel);
    }
    return ResponseEntity.status(HttpStatus.OK).body("Preços coletados com sucesso!");
  }

  @GetMapping
  public ResponseEntity<Object> getPrecos() {
    return ResponseEntity.status(HttpStatus.OK).body(collectPricesService.getAllPrices());
  }
}
