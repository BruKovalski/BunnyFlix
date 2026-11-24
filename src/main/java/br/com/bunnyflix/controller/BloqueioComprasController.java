package br.com.bunnyflix.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * O BunnyFlix é apenas um catálogo de consulta. A aplicação nunca teve rotas de pedido no servidor
 * (o carrinho antigo existia só no navegador), mas estes caminhos ficam reservados e respondem
 * 410 (Gone) para qualquer método HTTP, impedindo que uma rota de compra seja criada por engano
 * ou que clientes antigos recebam uma resposta enganosa.
 */
@RestController
public class BloqueioComprasController {

    @RequestMapping({
        "/carrinho", "/carrinho/**",
        "/checkout", "/checkout/**",
        "/pedido", "/pedido/**",
        "/pedidos", "/pedidos/**",
        "/pagamento", "/pagamento/**",
        "/pagamentos", "/pagamentos/**",
        "/api/carrinho/**", "/api/checkout/**", "/api/pedido/**", "/api/pedidos/**",
        "/api/pagamento/**", "/api/pagamentos/**"
    })
    public ResponseEntity<Map<String, String>> comprasDesativadas() {
        return ResponseEntity.status(HttpStatus.GONE)
            .body(Map.of("erro", "As compras estão desativadas: o BunnyFlix é um catálogo de consulta."));
    }
}
