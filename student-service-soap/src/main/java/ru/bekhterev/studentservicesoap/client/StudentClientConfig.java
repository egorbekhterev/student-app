package ru.bekhterev.studentservicesoap.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class StudentClientConfig {

    @Value("${server.port}")
    private String serverPort;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("ru.bekhterev.studentservicesoap");
        return marshaller;
    }

    @Bean
    public StudentClient studentClient(Jaxb2Marshaller marshaller) {
        StudentClient client = new StudentClient();
        client.setDefaultUri("http://localhost:" + serverPort + "/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
