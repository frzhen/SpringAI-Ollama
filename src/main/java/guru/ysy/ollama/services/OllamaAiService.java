package guru.ysy.ollama.services;

import guru.ysy.ollama.model.*;

/**
 * @Author: Fred R. Zhen
 * @Date: 2024/5/12 21:57
 * @Email: fred.zhen@gmail.com
 */
public interface OllamaAiService {

    Answer getAnswer(Question question);

    Answer getAnswerEdu(Question question);

    Answer getCapitalByString(GetCapitalRequest request);

    GetCapitalResponse getCapitalByJson(GetCapitalRequest request);

    Answer getCapitalWithInfoByString(GetCapitalRequest request);

    GetCapitalWithInfoResponse getCapitalWithInfoByJson(GetCapitalRequest request);
}
