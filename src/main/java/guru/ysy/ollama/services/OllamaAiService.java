package guru.ysy.ollama.services;

import guru.ysy.ollama.model.Answer;
import guru.ysy.ollama.model.Question;

/**
 * @Author: Fred R. Zhen
 * @Date: 2024/5/12 21:57
 * @Email: fred.zhen@gmail.com
 */
public interface OllamaAiService {

    Answer getAnswer(Question question);
}
