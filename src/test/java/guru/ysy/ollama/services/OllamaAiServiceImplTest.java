package guru.ysy.ollama.services;

import guru.ysy.ollama.model.Answer;
import guru.ysy.ollama.model.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @Author: Fred R. Zhen
 * @Date: 2024/5/12 22:07
 * @Email: fred.zhen@gmail.com
 */
@SpringBootTest
class OllamaAiServiceImplTest {

    @Autowired
    OllamaAiService ollamaAiService;

    @Test
    void getAnswer() {
        Question question = new Question("Tell me a funny joke about dog");

        System.out.println("Question: " + question.question());
        System.out.println("Got the answer:");

        Answer answer = ollamaAiService.getAnswer(question);
        assertThat(answer.answer()).isNotBlank();
        System.out.println(answer.answer());
    }
}
