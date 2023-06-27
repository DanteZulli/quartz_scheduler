package com.zullid.quartz_scheduler.service;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class PetService {

    private Logger log = LoggerFactory.getLogger(getClass());

    public static final long EXECUTION_TIME = 5000L;

    private AtomicInteger count = new AtomicInteger();

    // El método callPetProcess() realiza el procesamiento del Job específico
    // del servicio PetService.
    // Realiza acciones como obtener los datos de las mascotas,
    // esperar una cierta cantidad de tiempo y actualizar un contador.
    public void callPetProcess() {

        log.info("El trabajo empezo...");
        getPets();
        try {
            Thread.sleep(EXECUTION_TIME);
        } catch (InterruptedException e) {
            log.error("Error al ejecutar el trabajo :( -->", e);
        } finally {
            count.incrementAndGet();
            log.info("El trabajo finalizo :D");
        }
    }

    // Un método simple para registrar la cantidad de veces que fue disparado el Job
    public int getNumberOfInvocations() {
        return count.get();
    }

    // Importo variables definidas en application.properties para no "hardcodearlas"
    // Existen las variables USER_NAME y PASSWORD por si se requiere acceder a una
    // API Privada
    @Value("${pet.endpoint}")
    private String ENDPOINT;
    @Value("${pet.resource.pets}")
    private String URL_PETS;
    @Value("${pet.username}")
    private String USER_NAME;
    @Value("${pet.password}")
    private String PASSWORD;
    @Value("${pet.parameter.status}")
    private String STATUS_PARAMETR;

    @Value("${pet.iso.date.format}")

    // El método getPets() realiza una solicitud HTTP a la API de Mascotas.
    // En donde:
    // - Se crea un objeto HttpHeaders y se configura para aceptar un tipo de
    // contenido JSON.
    // - Se construye una URL utilizando UriComponentsBuilder con una dirección base
    // (ENDPOINT), la url del recurso a consultar (URL_PETS) y un parámetro de consulta (status).
    // - Se realiza una solicitud GET utilizando RestTemplate a la URL construida y se recibe una respuesta de tipo Object[]
    // - Se obtiene el cuerpo de la respuesta, que contiene un arreglo de objetos pets
    private void getPets() {
        log.info("El servicio getPets fue llamado...");
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ENDPOINT + URL_PETS)
                    .queryParam("status", STATUS_PARAMETR);
            String fullUrl = builder.build().toString();
            ResponseEntity<Object[]> response = new RestTemplate().getForEntity(fullUrl, Object[].class);
            Object[] pets = response.getBody();
            log.info("El servicio finalizo correctamente.");
            log.info("Cantidad de Pets Activos: " + pets.length);
            log.info("Pet Aleatoria: " + pets[ThreadLocalRandom.current().nextInt(0, pets.length)]);
        } catch (RestClientException e) {
            log.error("Error al ejecutar", e);
        }
    }

}