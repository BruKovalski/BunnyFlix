package br.com.bunnyflix.config;

import br.com.bunnyflix.catalogo.CatalogoCategorias;
import br.com.bunnyflix.catalogo.CatalogoEspecies;
import br.com.bunnyflix.catalogo.CategoriaProduto;
import br.com.bunnyflix.catalogo.Especie;
import br.com.bunnyflix.catalogo.GrupoEspecies;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Localiza as imagens do site. Primeiro procura a fotografia real em {@code static/img}
 * (img/hero.jpg, img/grupos/{slug}.jpg e img/especies/{slug}.jpg; também são aceitos .jpeg, .png e .webp).
 * Se a foto não existir, usa a ilustração que acompanha o projeto em {@code static/img/ilustracoes}.
 * Só devolve um caminho quando o arquivo existe, então a página nunca tenta carregar imagem inexistente.
 * Reinicie a aplicação depois de adicionar fotos.
 */
@Component("fotos")
public class FotosService {

    private static final String[] EXTENSOES = {"jpg", "jpeg", "webp", "png"};

    private final Map<String, String> caminhos = new HashMap<>();
    private final Map<String, String> ilustracoes = new HashMap<>();

    @PostConstruct
    void carregar() {
        registrar("hero");
        for (GrupoEspecies g : CatalogoEspecies.grupos()) {
            registrar("grupos/" + g.getSlug());
        }
        for (Especie e : CatalogoEspecies.todas()) {
            registrar("especies/" + e.getSlug());
        }
        registrarIlustracao("hero");
        for (GrupoEspecies g : CatalogoEspecies.grupos()) {
            registrarIlustracao("grupos/" + g.getSlug());
        }
        for (Especie e : CatalogoEspecies.todas()) {
            registrarIlustracao("especies/" + e.getSlug());
        }
        for (CategoriaProduto c : CatalogoCategorias.todas()) {
            registrarIlustracao("categorias/" + c.getSlug());
        }
    }

    private void registrarIlustracao(String base) {
        if (new ClassPathResource("static/img/ilustracoes/" + base + ".svg").exists()) {
            ilustracoes.put(base, "/img/ilustracoes/" + base + ".svg");
        }
    }

    private void registrar(String base) {
        for (String ext : EXTENSOES) {
            if (new ClassPathResource("static/img/" + base + "." + ext).exists()) {
                caminhos.put(base, "/img/" + base + "." + ext);
                return;
            }
        }
    }

    /** Foto do banner, ou a ilustração padrão se não houver foto. */
    public String hero() { return caminhos.getOrDefault("hero", ilustracoes.get("hero")); }
    /** Verdadeiro somente quando existe uma fotografia real do banner (e não a ilustração). */
    public boolean heroTemFoto() { return caminhos.containsKey("hero"); }
    public String grupo(String slug) { return caminhos.getOrDefault("grupos/" + slug, ilustracoes.get("grupos/" + slug)); }
    public String especie(String slug) { return caminhos.getOrDefault("especies/" + slug, ilustracoes.get("especies/" + slug)); }
    /** Ilustração do tipo de produto, usada quando o produto ainda não tem foto própria. */
    public String categoria(String slug) { return ilustracoes.get("categorias/" + slug); }
    /** Ilustração padrão (nunca a foto) de um grupo ou espécie, usada se a foto falhar ao carregar. */
    public String ilustracaoEspecie(String slug) { return ilustracoes.get("especies/" + slug); }
    public String ilustracaoGrupo(String slug) { return ilustracoes.get("grupos/" + slug); }
}
