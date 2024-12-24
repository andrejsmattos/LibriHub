package br.com.andrejsmattos.librihub.model;

public enum Idioma {
    ES("Espanhol"),
    EN("Inglês"),
    FR("Francês"),
    PT("Português");

    private final String idiomaPortugues;

    Idioma(String idiomaPortugues) {
        this.idiomaPortugues = idiomaPortugues;
    }

    public static Idioma fromString(String texto) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.name().equalsIgnoreCase(texto) || idioma.getIdiomaPortugues().equalsIgnoreCase(texto)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Nenhum idioma encontrado com este nome: " + texto);
    }

    public String getIdiomaPortugues() {
        return idiomaPortugues;
    }

    @Override
    public String toString() {
        return idiomaPortugues;
    }
}
