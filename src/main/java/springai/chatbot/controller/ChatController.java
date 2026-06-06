package springai.chatbot.controller;


import lombok.AllArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@AllArgsConstructor
public class ChatController {
    private final ChatClient chatClient;

    @GetMapping("/chat-health")
    public String hello(){
        return "hello";
    }
    @GetMapping("/chat")
    public String chat(@RequestParam String message,@RequestParam(defaultValue = "default-session") String sessionId){
        return this.chatClient.prompt().user(message)
                .advisors(context->context.param("chat_memory_conversation_id", sessionId))
                .call()
                .content();
    }
    @GetMapping(value = "/chat-stream",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatStream(
            @RequestParam String message,
            @RequestParam(defaultValue = "default-session") String sessionId) {

        return this.chatClient.prompt()
                .user(message)
                .advisors(context -> context.param("chat_memory_conversation_id", sessionId))
                .stream()
                .content();
    }
}
