package com.app.backend.config;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Slf4j
@Component
public class WhatsAppProcessStarter {

    private Process whatsappProcess;

    @EventListener(ApplicationReadyEvent.class)
    public void iniciarServicioWhatsApp() {
        try {
            File directorio = buscarDirectorioWhatsApp();

            if (directorio == null) {
                log.warn("No se encontró la carpeta whatsapp-service. El servicio NO se iniciará automáticamente.");
                return;
            }

            ProcessBuilder pb = new ProcessBuilder("node", "server.js");
            pb.directory(directorio);
            pb.inheritIO();

            whatsappProcess = pb.start();
            log.info("Servicio de WhatsApp iniciado automáticamente desde: {}", directorio);
        } catch (IOException e) {
            log.error("Error al iniciar el servicio de WhatsApp: {}", e.getMessage());
        }
    }

    private File buscarDirectorioWhatsApp() {
        File cwd = new File(System.getProperty("user.dir"));
        for (int i = 0; i < 3; i++) {
            File candidato = new File(cwd, "whatsapp-service");
            if (new File(candidato, "server.js").exists()) {
                return candidato;
            }
            cwd = cwd.getParentFile();
            if (cwd == null) break;
        }
        return null;
    }

    @PreDestroy
    public void detenerServicioWhatsApp() {
        if (whatsappProcess != null && whatsappProcess.isAlive()) {
            whatsappProcess.destroyForcibly();
            log.info("Servicio de WhatsApp detenido.");
        }
    }
}
