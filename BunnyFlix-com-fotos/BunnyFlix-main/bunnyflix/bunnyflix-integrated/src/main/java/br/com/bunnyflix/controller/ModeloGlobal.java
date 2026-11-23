package br.com.bunnyflix.controller;

import br.com.bunnyflix.catalogo.CatalogoCategorias;
import br.com.bunnyflix.catalogo.CatalogoEspecies;
import br.com.bunnyflix.catalogo.CategoriaProduto;
import br.com.bunnyflix.catalogo.Especie;
import br.com.bunnyflix.catalogo.GrupoEspecies;
import br.com.bunnyflix.config.LojaInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Map;

/** Dados usados por todas as páginas: menu, rodapé, dados da loja e URL canônica. */
@ControllerAdvice
public class ModeloGlobal {

    @Autowired
    private LojaInfo lojaInfo;

    @ModelAttribute("loja")
    public LojaInfo loja() {
        return lojaInfo;
    }

    @ModelAttribute("grupos")
    public List<GrupoEspecies> grupos() {
        return CatalogoEspecies.grupos();
    }

    @ModelAttribute("todasEspecies")
    public List<Especie> todasEspecies() {
        return CatalogoEspecies.todas();
    }

    @ModelAttribute("mapaEspecies")
    public Map<String, Especie> mapaEspecies() {
        return CatalogoEspecies.porNome();
    }

    @ModelAttribute("categorias")
    public List<CategoriaProduto> categorias() {
        return CatalogoCategorias.todas();
    }

    @ModelAttribute("mapaCategorias")
    public Map<String, CategoriaProduto> mapaCategorias() {
        return CatalogoCategorias.porValor();
    }

    /** Endereço da página sem a query string. Páginas de erro não têm canônica. */
    @ModelAttribute("canonical")
    public String canonical(HttpServletRequest request) {
        if (request.getRequestURI().startsWith("/error")) {
            return null;
        }
        return ServletUriComponentsBuilder.fromRequest(request).replaceQuery(null).build().toUriString();
    }
}
