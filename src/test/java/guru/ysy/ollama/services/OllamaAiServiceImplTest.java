package guru.ysy.ollama.services;

import guru.ysy.ollama.model.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @Author: Fred R. Zhen
 * @Date: 2024/5/12 22:07
 * @Email: fred.zhen@gmail.com
 */
@Order(21)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OllamaAiServiceImplTest {

    @Autowired
    OllamaAiService ollamaAiService;

    @Order(1)
    @Test
    @DisplayName("test Get Answer in String")
    void getAnswer() {
        Question question = new Question("Tell me about fibonacci sequence");

        System.out.println("Question: " + question.question());
        System.out.println("Got the answer:");

        Answer answer = ollamaAiService.getAnswer(question);
        assertThat(answer.answer()).isNotBlank();
        System.out.println(answer.answer());
    }

    @Order(2)
    @Test
    @DisplayName("test Get Answer with education field in String")
    void getAnswerEdu() {
        Question question = new Question("Tell me about fibonacci sequence");

        System.out.println("Question: " + question.question());
        System.out.println("Got the answer:");

        Answer answer = ollamaAiService.getAnswerEdu(question);
        assertThat(answer.answer()).isNotBlank();
        System.out.println(answer.answer());
    }

    @Order(3)
    @Test
    @DisplayName("test Get Capital name in String")
    void getCapitalByString() {
        GetCapitalRequest request = new GetCapitalRequest("China");

        System.out.printf("Got the capital answer for: %s%n", request.stateOrCountry());

        Answer answer = ollamaAiService.getCapitalByString(request);
        assertThat(answer.answer()).isNotBlank();
        System.out.println(answer.answer());
    }

    @Order(4)
    @Test
    @DisplayName("test Get Capital name by Json")
    void getCapitalByJson() {
        GetCapitalRequest request = new GetCapitalRequest("China");

        System.out.printf("Got the capital answer for: %s%n", request.stateOrCountry());

        GetCapitalResponse answer = ollamaAiService.getCapitalByJson(request);
        assertThat(answer.answer()).isNotBlank();
        System.out.println(answer.answer());
    }

    @Order(5)
    @Test
    @DisplayName("test Get Capital name with info in String")
    void getCapitalWithInfoByString() {
        GetCapitalRequest request = new GetCapitalRequest("China");

        System.out.printf("Got the capital answer with detail information for: %s%n", request.stateOrCountry());

        Answer answer = ollamaAiService.getCapitalWithInfoByString(request);
        assertThat(answer.answer()).isNotBlank();
        System.out.println(answer.answer());
    }

    @Order(6)
    @Test
    @DisplayName("test Get Capital name with info by Json")
    void getCapitalWithInfoByJson() {
        GetCapitalRequest request = new GetCapitalRequest("China");

        System.out.printf("Got the capital answer with JSON for: %s%n", request.stateOrCountry());

        GetCapitalWithInfoResponse answer = ollamaAiService.getCapitalWithInfoByJson(request);
        assertThat(answer).isNotNull();
        System.out.println(answer);
    }

    @Order(7)
    @Test
    @DisplayName("测试中文问题")
    void getAnswerInChinese() {
        Question question = new Question("你知道斐波那契数列吗？");
        System.out.println("问题: " + question.question());
        System.out.println("答案:");

        Answer answer = ollamaAiService.getAnswerInChinese(question);
        assertThat(answer.answer()).isNotBlank();
        System.out.println(answer.answer());
    }
}
