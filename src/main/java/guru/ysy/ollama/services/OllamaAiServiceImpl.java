package guru.ysy.ollama.services;

import guru.ysy.ollama.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.ai.parser.BeanOutputParser;
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

    @Value("classpath:templates/system-prompt-edu.st")
    private Resource systemPromptEduTemplate;

    @Value("classpath:templates/get-capital-String-prompt.st")
    private Resource getCapitalStringPromptTemplate;

    @Value("classpath:templates/get-capital-JSON-prompt.st")
    private Resource getCapitalJSONPromptTemplate;

    @Value("classpath:templates/get-capital-String-with-info.st")
    private Resource getCapitalStringWithInfoPromptTemplate;

    private final OllamaChatClient chatClient;

    @Override
    public Answer getAnswer(Question question) {
        Prompt prompt = new Prompt(question.question());
        ChatResponse response = chatClient.call(prompt);
        return new Answer(response.getResult().getOutput().getContent());
    }

    @Override
    public Answer getAnswerInChinese(Question question) {
        String systemChinesePrompt = """
                Your answer should be all in Simplified Chinese.
                """;
        String messageAddOnWithChinese = question.question() + systemChinesePrompt;

        Prompt prompt = new Prompt(messageAddOnWithChinese);
        ChatResponse response = chatClient.call(prompt);
        return new Answer(response.getResult().getOutput().getContent());
    }

    @Override
    public Answer getAnswerEdu(Question question) {
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemPromptEduTemplate);
        Message systemMessage = systemPromptTemplate.createMessage();

        PromptTemplate promptTemplate = new PromptTemplate(question.question());
        Message usrMessage = promptTemplate.createMessage();
        List<Message> messages = List.of(systemMessage, usrMessage);
        Prompt prompt = new Prompt(messages);
        ChatResponse response = chatClient.call(prompt);
        return new Answer(response.getResult().getOutput().getContent());
    }

    @Override
    public Answer getCapitalByString(GetCapitalRequest request) {
        ChatResponse response = chatClient.call(createPrompt(request, getCapitalStringPromptTemplate));
        return new Answer(response.getResult().getOutput().getContent());
    }

    @Override
    public GetCapitalResponse getCapitalByJson(GetCapitalRequest request) {
        BeanOutputParser<GetCapitalResponse> parser = new BeanOutputParser<>(GetCapitalResponse.class);
        String format = parser.getFormat();
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalJSONPromptTemplate);
        Prompt prompt = promptTemplate.create(Map.of(
                "stateOrCountry",
                request.stateOrCountry(),
                "format",
                format));
        ChatResponse response = chatClient.call(prompt);
        return parser.parse(response.getResult().getOutput().getContent());
    }

    @Override
    public Answer getCapitalWithInfoByString(GetCapitalRequest request) {
        ChatResponse response = chatClient.call(createPrompt(request, getCapitalStringWithInfoPromptTemplate));
        return new Answer(response.getResult().getOutput().getContent());
    }

    @Override
    public GetCapitalWithInfoResponse getCapitalWithInfoByJson(GetCapitalRequest request) {
        BeanOutputParser<GetCapitalWithInfoResponse> parser = new BeanOutputParser<>(
                GetCapitalWithInfoResponse.class);
        String format = parser.getFormat();
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalJSONPromptTemplate);
        Prompt prompt = promptTemplate.create(Map.of(
                "stateOrCountry",
                request.stateOrCountry(),
                "format",
                format));
        ChatResponse response = chatClient.call(prompt);
        return parser.parse(response.getResult().getOutput().getContent());
    }

    private Prompt createPrompt(GetCapitalRequest request, Resource resource) {
        PromptTemplate promptTemplate = new PromptTemplate(resource);
        return promptTemplate.create(Map.of("stateOrCountry", request.stateOrCountry()));
    }
}
