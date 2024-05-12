package guru.ysy.ollama.services;

import guru.ysy.ollama.model.Answer;
import guru.ysy.ollama.model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.stereotype.Service;

/**
 * @Author: Fred R. Zhen
 * @Date: 2024/5/12 21:58
 * @Email: fred.zhen@gmail.com
 */
@Service
@RequiredArgsConstructor
public class OllamaAiServiceImpl implements OllamaAiService {
    private final OllamaChatClient chatClient;
    @Override
    public Answer getAnswer(Question question) {
        Prompt prompt = new Prompt(question.question());
        ChatResponse response = chatClient.call(prompt);
        return new Answer(response.getResult().getOutput().getContent());
    }
}
