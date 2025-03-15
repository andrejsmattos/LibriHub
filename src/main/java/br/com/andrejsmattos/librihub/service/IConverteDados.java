package br.com.andrejsmattos.librihub.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
