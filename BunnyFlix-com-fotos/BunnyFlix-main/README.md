# BunnyFlix

Catálogo online de produtos pet (somente consulta), feito com Spring Boot 3, Thymeleaf e banco H2 em memória.
Não existe carrinho, pedido nem pagamento. O catálogo é público; o login da área do Grêmio continua existindo,
com registro de acessos no banco, e não altera o catálogo.

## Como executar

Requisitos: Java 17+ e Maven.

```bash
cd bunnyflix/bunnyflix-integrated
mvn spring-boot:run
```

Abra http://localhost:8080

## Páginas

| Rota | Conteúdo |
|------|----------|
| `/` | Página inicial: banner, setores, espécies, tipos de produto, seleção da loja |
| `/catalogo` | Todos os produtos, com busca, filtros, ordenação e paginação |
| `/grupo/{slug}` | Setor (cães e gatos, pequenos mamíferos, aves, peixes) |
| `/especie/{slug}` | Uma espécie (ex.: `/especie/coelhos`) |
| `/produto/{id}-{slug}` | Página do produto |
| `/sobre`, `/contato` | Páginas institucionais |
| `/login` | Área do Grêmio (inalterada) |
| `/sitemap.xml`, `/robots.txt` | SEO |

Filtros disponíveis (combináveis): busca por texto (sem distinguir acentos), tipo de produto, faixa de preço e
ordenação (nome, menor preço, maior preço). Os filtros usam apenas dados que existem no banco.

## Compras desativadas

* Nenhum botão, página ou script de carrinho, checkout ou pagamento existe na interface.
* O projeto nunca teve rotas de pedido no servidor (o carrinho antigo ficava só no navegador). Mesmo assim,
  os caminhos `/carrinho`, `/checkout`, `/pedido(s)`, `/pagamento(s)` e `/api/...` equivalentes respondem
  `410 Gone` para qualquer método (`BloqueioComprasController`).

## Fotografias reais (pendente: precisam ser adicionadas por você)

Atalho: `python3 ferramentas/baixar_fotos.py` (na pasta `bunnyflix-integrated`, com internet) baixa fotos livres do
Wikimedia Commons para todos os nomes abaixo e gera `CREDITOS-FOTOS.md`. Confira cada foto depois e mostre os
créditos no site quando a licença for CC BY ou CC BY-SA.


O projeto não traz fotografias reais. Para que o site nunca fique com imagens vazias, ele inclui **ilustrações próprias**
em `static/img/ilustracoes/` (banner, 4 setores, 17 espécies e 5 tipos de produto). Elas são usadas automaticamente
quando não há foto; assim que você colocar uma foto com o nome abaixo, ela passa a ter prioridade. Produtos sem
`imagemUrl` mostram a ilustração do tipo de produto. As ilustrações podem ser regeneradas com
`python3 ferramentas/gerar_ilustracoes.py src/main/resources/static/img/ilustracoes`.

Coloque fotos próprias (ou com licença de uso) em
`src/main/resources/static/img/` com estes nomes (`.jpg`, `.jpeg`, `.png` ou `.webp`) e reinicie a aplicação:

* `hero.jpg` – foto do banner da página inicial
* `grupos/caes-e-gatos`, `grupos/pequenos-mamiferos`, `grupos/aves`, `grupos/peixes`
* `especies/` + `coelhos`, `porquinhos-da-india`, `hamsters`, `chinchilas`, `gerbils`, `ratos-twister`,
  `camundongos`, `caes`, `gatos`, `calopsitas`, `periquitos-australianos`, `agapornis`, `canarios`, `bettas`,
  `kinguios`, `guppies`, `neons`

Fotos de produtos: preencha o campo `imagemUrl` do produto (em `DataLoader` ou no banco). Use a foto do próprio
produto, não a de um animal. Recomenda-se redimensionar as imagens (cerca de 800 px de largura) antes de enviar.

## Modo escuro e animações

* Botão de sol/lua no cabeçalho alterna o tema. A escolha fica salva no navegador (`localStorage`, chave
  `bunnyflix-tema`); na primeira visita vale a preferência do sistema operacional. O tema é aplicado antes da
  primeira pintura, sem clarão.
* Animações em CSS/JS puro (sem bibliotecas): entrada do banner, revelação ao rolar, zoom nas imagens, menu móvel,
  transição entre temas. Com `prefers-reduced-motion` tudo isso é desligado.

## Dados da loja

Edite o bloco `loja.*` em `src/main/resources/application.properties` (e-mail, telefone, endereço, horário,
Instagram). Campos vazios não são exibidos; a página Contato só mostra o que estiver preenchido.

## Observações

* Produtos, preços e descrições em `DataLoader` são de demonstração. As avaliações (estrelas) foram retiradas
  da interface por serem valores inventados; a coluna continua no banco.
* O banco H2 é em memória: os dados voltam ao estado inicial a cada reinicialização.
* O console do H2 e o log de SQL vêm desativados. Reative só em desenvolvimento local.
* Pendência de segurança na área do Grêmio: as senhas ainda são gravadas em texto puro e a página de login exibe
  um usuário de teste. Antes de publicar, troque por senhas com hash e remova essa dica.
