package guru.ysy.ollama.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.ysy.ollama.model.Answer;
import guru.ysy.ollama.model.GetCapitalRequest;
import guru.ysy.ollama.model.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fred R. Zhen
 * @Date: 2024/5/12 21:58
 * @Email: fred.zhen@gmail.com
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OllamaAiServiceImpl implements OllamaAiService {

    @Value("classpath:templates/get-capital-String-prompt.st")
    private Resource getCapitalStringPromptTemplate;

    @Value("classpath:templates/get-capital-JSON-prompt.st")
    private Resource getCapitalJSONPromptTemplate;

    @Value("classpath:templates/get-capital-String-with-info.st")
    private Resource getCapitalStringWithInfoPromptTemplate;

    @Value("classpath:templates/get-capital-JSON-with-info.st")
    private Resource getCapitalJSONWithInfoPromptTemplate;

    private final OllamaChatClient chatClient;

    private final ObjectMapper mapper;
    @Override
    public Answer getAnswer(Question question) {
        Prompt prompt = new Prompt(question.question());
        ChatResponse response = chatClient.call(prompt);
        return new Answer(response.getResult().getOutput().getContent());
    }

    @Override
    public Answer getCapitalByString(GetCapitalRequest request) {
        ChatResponse response = chatClient.call(createPrompt(request, getCapitalStringPromptTemplate));
        return new Answer(response.getResult().getOutput().getContent());
    }

    @Override
    public Answer getCapitalByJson(GetCapitalRequest request) {
        ChatResponse response = chatClient.call(createPrompt(request, getCapitalJSONPromptTemplate));
        List<String> properties = List.of("capital");
        return new Answer(parseResponseJsonToString(response, properties));
    }

    @Override
    public Answer getCapitalWithInfoByString(GetCapitalRequest request) {
        ChatResponse response = chatClient.call(createPrompt(request, getCapitalStringWithInfoPromptTemplate));
        return new Answer(response.getResult().getOutput().getContent());
    }

    @Override
    public Answer getCapitalWithInfoByJson(GetCapitalRequest request) {
        ChatResponse response = chatClient.call(createPrompt(request, getCapitalJSONWithInfoPromptTemplate));
        List<String> properties = List.of(
                "capital",
                "population-in-mil",
                "region",
                "language",
                "currency",
                "lat",
                "lng");
        return new Answer(parseResponseJsonToString(response, properties));
    }

    private Prompt createPrompt(GetCapitalRequest request, Resource resource) {
        PromptTemplate promptTemplate = new PromptTemplate(resource);
        return promptTemplate.create(Map.of("stateOrCountry", request.stateOrCountry()));
    }

    private String parseResponseJsonToString(ChatResponse response, List<String> properties) {
        final StringBuilder responseString = new StringBuilder();
        if (properties != null && !properties.isEmpty()) {
            try {
                JsonNode jsonNode = mapper.readTree(response.getResult().getOutput().getContent());
                for (String property : properties) {
                    responseString.append(property).append(" : ").append(jsonNode.get(property)).append("\n");
                }
                if (responseString.length() >= 2) {
                    responseString.delete(responseString.length() - 2, responseString.length());
                }
            } catch (JsonProcessingException e) {
                log.error("Ollama AI response format error:", e);
                responseString.append("Ollama AI response format error");
            }
        } else {
            responseString.append("There is a error in your response");
        }
        return responseString.toString();
    }

}
