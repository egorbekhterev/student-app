package ru.bekhterev.rservice.configuration;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

import java.time.Duration;

@EnableKafka
@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    private final ConsumerFactory<Long, String> consumerFactory;
    private final ProducerFactory<Long, String> producerFactory;

    @Value("${kafka.topic.users}")
    private String usersTopicName;

    @Value("${kafka.timeout}")
    private long timeout;

    @Bean
    public NewTopic topicUsers() {
        return new NewTopic(usersTopicName, 1, (short) 1);
    }

    @Bean
    public KafkaTemplate<Long, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public ReplyingKafkaTemplate<Long, String, String> replyKafkaTemplate(
            ProducerFactory<Long, String> pf, KafkaMessageListenerContainer<Long, String> container) {
        ReplyingKafkaTemplate<Long, String, String> replyingKafkaTemplate = new ReplyingKafkaTemplate<>(pf, container);
        replyingKafkaTemplate.setDefaultReplyTimeout(Duration.ofMillis(timeout));
        return replyingKafkaTemplate;
    }

    @Bean
    public KafkaMessageListenerContainer<Long, String> replyContainer(ConsumerFactory<Long, String> cf) {
        ContainerProperties containerProperties = new ContainerProperties(usersTopicName);
        return new KafkaMessageListenerContainer<>(cf, containerProperties);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Long, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Long, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setReplyTemplate(kafkaTemplate());
        return factory;
    }
}
