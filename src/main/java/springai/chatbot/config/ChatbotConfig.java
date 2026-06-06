package springai.chatbot.config;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatbotConfig {


    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder()
                .maxMessages(20) // Only remember the last 20 messages to save tokens
                .build();
    }

    @Bean
    public ChatClient chatbotClient(ChatClient.Builder builder,ChatMemory chatMemory){
        return builder.defaultSystem("You are a knowledgeable and polite software architecture assistant.")
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build(),new SimpleLoggerAdvisor())
                .build();
    }
}
