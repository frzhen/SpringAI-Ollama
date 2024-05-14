package guru.ysy.ollama.controllers;

import guru.ysy.ollama.model.*;
import guru.ysy.ollama.services.OllamaAiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Fred R. Zhen
 * @Date: 2024/5/13 00:17
 * @Email: fred.zhen@gmail.com
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Question Controller", description = "Endpoint for asking questions to Local Ollama Inference Endpoint")
public class QuestionController {

    private final OllamaAiService ollamaAiService;

    @Operation(summary = "Ask any question to get an answer in string")
    @PostMapping("/ask")
    public ResponseEntity<Answer> askQuestion(@RequestBody Question question) {
        return ResponseEntity.ok(ollamaAiService.getAnswer(question));
    }

    @Operation(summary = "Ask any question to get an answer in Chinese")
    @PostMapping("/ask/cn")
    public ResponseEntity<Answer> askCNQuestion(@RequestBody Question question) {
        return ResponseEntity.ok(ollamaAiService.getAnswerInChinese(question));
    }

    @Operation(summary = "Ask any question to get an answer in string in education mode")
    @PostMapping("/ask/edu")
    public ResponseEntity<Answer> askEduQuestion(@RequestBody Question question) {
        return ResponseEntity.ok(ollamaAiService.getAnswerEdu(question));
    }

    @Operation(summary = "Ask the name of a state or a country's capital")
    @PostMapping("/capital")
    public ResponseEntity<Answer> getCapitalName(@RequestBody GetCapitalRequest request) {
        return ResponseEntity.ok(ollamaAiService.getCapitalByString(request));
    }

    @Operation(summary = "Ask the name of a state or a country's capital in JSON format with AI")
    @PostMapping("/capital/json")
    public ResponseEntity<GetCapitalResponse> getCapitalNameByJson(@RequestBody GetCapitalRequest request) {
        return ResponseEntity.ok(ollamaAiService.getCapitalByJson(request));
    }

    @Operation(summary = "Ask the name of a state or a country's capital in details")
    @PostMapping("/capital/details")
    public ResponseEntity<Answer> getCapitalNameWithInfo(@RequestBody GetCapitalRequest request) {
        return ResponseEntity.ok(ollamaAiService.getCapitalWithInfoByString(request));
    }

    @Operation(summary = "Ask the name of a state or a country's capital in details in JSON format with AI")
    @PostMapping("/capital/details/json")
    public ResponseEntity<GetCapitalWithInfoResponse> getCapitalNameWithInfoByJson(@RequestBody GetCapitalRequest request) {
        return ResponseEntity.ok(ollamaAiService.getCapitalWithInfoByJson(request));
    }
}
