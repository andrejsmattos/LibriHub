package br.com.andrejsmattos.librihub.service;

import br.com.andrejsmattos.librihub.exception.ConversaoJsonException;
import br.com.andrejsmattos.librihub.model.DadosLivro;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverteDados implements IConverteDados {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        try {
            JsonNode nodeOrigem = mapper.readTree(json);

            JsonNode nodeDestino;
            if (classe.equals(DadosLivro.class)) {
                nodeDestino = nodeOrigem.path("results").get(0);
            } else {
                throw new ConversaoJsonException("Erro ao converter JSON para " + classe.getSimpleName(), new Throwable());
            }
            return mapper.treeToValue(nodeDestino, classe);
        } catch (JsonProcessingException e) {
            throw new ConversaoJsonException("Erro ao converter JSON para " + classe.getSimpleName(), e);
        }
    }
}