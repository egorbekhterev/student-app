package ru.bekhterev.studentservice.configuration;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.BatchMessagingMessageConverter;
import org.springframework.kafka.support.converter.MessagingMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@EnableKafka
@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    private final ConsumerFactory<Long, String> consumerFactory;
    private final ProducerFactory<Long, String> producerFactory;

    @Bean
    public NewTopic topicStudents() {
        return new NewTopic("students", 1, (short) 1);
    }

    @Bean
    public KafkaListenerContainerFactory<?> singleFactory() {
        ConcurrentKafkaListenerContainerFactory<Long, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setBatchListener(false);
        factory.setMessageConverter(new MessagingMessageConverter());
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<?> batchFactory() {
        ConcurrentKafkaListenerContainerFactory<Long, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setBatchListener(true);
        factory.setMessageConverter(new BatchMessagingMessageConverter(new MessagingMessageConverter()));
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<?> kafkaListenerContainerFactory() {
        return new ConcurrentKafkaListenerContainerFactory<>();
    }

    @Bean
    public KafkaTemplate<Long, String> kafkaTemplate() {
        KafkaTemplate<Long, String> template = new KafkaTemplate<>(producerFactory);
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }
}
