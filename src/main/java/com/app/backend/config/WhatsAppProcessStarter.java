package com.app.backend.config;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

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

            // Matar cualquier proceso previo que use el puerto 7070
            liberarPuerto(7071);

            ProcessBuilder pb = new ProcessBuilder("node", "server.js");
            pb.directory(directorio);
            pb.redirectErrorStream(true); // Combinar stderr con stdout

            whatsappProcess = pb.start();
            log.info("Servicio de WhatsApp iniciado automáticamente desde: {}", directorio);

            // Leer logs del proceso en un hilo separado
            Thread logThread = new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(whatsappProcess.getInputStream()))) {
                    String linea;
                    while ((linea = reader.readLine()) != null) {
                        log.info("[WhatsApp-Service] {}", linea);
                    }
                } catch (Exception e) {
                    // El proceso terminó
                }
            });
            logThread.setDaemon(true);
            logThread.setName("whatsapp-log-reader");
            logThread.start();

        } catch (Exception e) {
            log.error("Error al iniciar el servicio de WhatsApp: {}", e.getMessage());
        }
    }

    private void liberarPuerto(int puerto) {
        try {
            // Buscar PIDs que usan el puerto
            Process netstat = Runtime.getRuntime().exec(
                    new String[]{"cmd", "/c", "netstat -ano | findstr :" + puerto + " | findstr LISTENING"});
            BufferedReader reader = new BufferedReader(new InputStreamReader(netstat.getInputStream()));
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.trim().split("\\s+");
                if (partes.length >= 5) {
                    String pid = partes[partes.length - 1];
                    if (!"0".equals(pid)) {
                        log.info("Puerto {} en uso por PID {}, terminando proceso...", puerto, pid);
                        try {
                            Runtime.getRuntime().exec(new String[]{"taskkill", "/PID", pid, "/F"}).waitFor();
                        } catch (Exception e) {
                            log.warn("No se pudo terminar PID {}: {}", pid, e.getMessage());
                        }
                    }
                }
            }
            netstat.waitFor();
            // Esperar a que el SO libere el puerto
            Thread.sleep(1500);
        } catch (Exception e) {
            log.debug("No hay procesos previos en el puerto {}", puerto);
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
        // También matar por puerto por si acaso
        try {
            liberarPuerto(7071);
        } catch (Exception ignored) {}
    }
}
