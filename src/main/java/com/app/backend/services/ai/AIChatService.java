package com.app.backend.services.ai;

import com.app.backend.dtos.ai.ChatRequest;
import com.app.backend.dtos.ai.ChatResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class AIChatService {

    private final GroqAIService groqAIService;

    public ChatResponse chat(ChatRequest request) {
        try {
            String systemPrompt = loadSystemPrompt(request.getModule());
            if (systemPrompt == null) {
                return ChatResponse.builder()
                        .success(false)
                        .error("Módulo de chat no configurado: " + request.getModule())
                        .build();
            }

            String rawResponse = groqAIService.chatFreeText(systemPrompt, request.getMessage());
            if (rawResponse == null || rawResponse.isBlank()) {
                return ChatResponse.builder()
                        .success(false)
                        .error("El servicio de IA no respondió. Intenta nuevamente.")
                        .build();
            }

            return ChatResponse.builder()
                    .response(rawResponse.trim())
                    .module(request.getModule())
                    .success(true)
                    .build();

        } catch (Exception e) {
            log.error("[AIChatService] Error procesando chat para módulo '{}': {}", request.getModule(), e.getMessage());
            return ChatResponse.builder()
                    .success(false)
                    .error("Error al procesar tu consulta. Intenta nuevamente.")
                    .build();
        }
    }

    private String loadSystemPrompt(String module) {
        String path = "prompts/chat/" + module + ".txt";
        try {
            ClassPathResource resource = new ClassPathResource(path);
            try (InputStream is = resource.getInputStream()) {
                return new String(is.readAllBytes(), StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            log.error("[AIChatService] No se encontró prompt para módulo '{}' en {}", module, path);
            return null;
        }
    }
}