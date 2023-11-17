package ru.bekhterev.studentservicesoap.configuration;

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

@EnableKafka
@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    private final ConsumerFactory<Long, String> consumerFactory;
    private final ProducerFactory<Long, String> producerFactory;

    @Value("${kafka.topic.students}")
    private String studentsTopicName;

    @Bean
    public NewTopic topicStudents() {
        return new NewTopic(studentsTopicName, 1, (short) 1);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Long, String>> requestReplyListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Long, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setReplyTemplate(replyTemplate());
        return factory;
    }

    @Bean
    public KafkaTemplate<Long, String> replyTemplate() {
        return new KafkaTemplate<>(producerFactory);
    }
}
