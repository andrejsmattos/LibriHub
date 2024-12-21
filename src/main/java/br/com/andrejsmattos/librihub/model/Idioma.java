package br.com.andrejsmattos.librihub.model;

public enum Idioma {
    ES("Espanhol"),
    EN("Inglês"),
    FR("Francês"),
    PT("Português");

    private String idiomaPortuges;

    Idioma(String idiomaPortuges) {
        this.idiomaPortuges = idiomaPortuges;
    }

    public static Idioma fromString(String texto) {
        for (Idioma idioma: Idioma.values()) {
            if (idioma.name().equalsIgnoreCase(texto)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Nenhum idioma encontrado com este nome: " + texto);
    }

    @Override
    public String toString() {
        return idiomaPortuges;
    }
}