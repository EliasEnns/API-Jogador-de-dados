package com.Elias.Jogador_de_Dados.Controllers;

import com.Elias.Jogador_de_Dados.Services.DadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DadoController {

    @Autowired
    private DadoService Dado;

    @GetMapping("/sobre")
    public ResponseEntity<Map<String, String>> sobre() {
        Map<String, String> response = new HashMap<>();
        response.put("estudante", "Elias Enns");
        response.put("projeto", "Jogador de Dados");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/rolar")
    public ResponseEntity<String> rolar(@RequestBody Map<String, String> request) {
        String diceSides = request.get("diceSides");
        String numDice = request.get("numDice");
        String roll = Dado.rollDice(diceSides, numDice);
        return ResponseEntity.ok(roll);
    }

    @GetMapping("/consulta")
    public ResponseEntity<List<String>> consulta() {
        List<String> rolls = Dado.getAllLRolls();
        return ResponseEntity.ok(rolls);
    }
}