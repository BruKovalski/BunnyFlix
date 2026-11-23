package br.com.bunnyflix.util;

import java.text.Normalizer;
import java.util.Locale;

/** Utilitários de texto usados em URLs e na pesquisa. */
public final class Slug {

    private Slug() {}

    /** Remove acentos e converte para minúsculas ("Cães" vira "caes"). */
    public static String semAcentos(String texto) {
        if (texto == null) {
            return "";
        }
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
            .replaceAll("\\p{M}", "")
            .toLowerCase(Locale.ROOT);
    }

    /** Gera um trecho de URL legível ("Feno Premium de Timóteo" vira "feno-premium-de-timoteo"). */
    public static String de(String texto) {
        return semAcentos(texto)
            .replaceAll("[^a-z0-9]+", "-")
            .replaceAll("(^-+|-+$)", "");
    }
}
