package com.example.news_brief_with_ai.Client;

import com.example.news_brief_with_ai.DTO.Article;
import com.example.news_brief_with_ai.DTO.OllamaRequest;
import com.example.news_brief_with_ai.DTO.OllamaResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class OllamaClient {

    @Value("${ollama.base.url}")
    private String ollamaUrl;

    @Value("${ollama.mistral.model}")
    private String aiModel;

    //Generating summary using AI
    public OllamaResponse generateSummary(List<Article> articles) {
        final RestTemplate restTemplate = new RestTemplate();

        final String prompt = getPrompt(articles);

        //Post request body
        final OllamaRequest requestPayload = OllamaRequest.builder()
                .model(aiModel)
                .prompt(prompt)
                .stream(false)
                .build();

        //Generating entity for post request
        final HttpEntity<OllamaRequest> entity = getHttpEntity(requestPayload);

        //Sending request to AI
        final ResponseEntity<OllamaResponse> response =
                restTemplate.postForEntity(ollamaUrl, entity, OllamaResponse.class);

        return response.getBody();
    }

    //Creates a HttpEntity which includes body and headers
    private HttpEntity<OllamaRequest> getHttpEntity(OllamaRequest requestPayload) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OllamaRequest> entity = new HttpEntity<>(requestPayload, headers);
        return entity;
    }

    //Creates a AI prompt
    private String getPrompt(List<Article> articles) {
        final StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("You are a news summarizer." +
                "Summarize the top global news stroies from today in a concise and infromative way. " +
                "Focus on major events, political developments, economic updates, and major technology or " +
                "science breakthrough. Keep the summary clear, objective, and easy to read, like a daily news brief. ");

        for (Article article : articles) {
            promptBuilder
                    .append("Title: ").append(article.getTitle()).append("\n")
                    .append("Description: ").append(article.getDescription()).append("\n")
                    .append("End of article\n\n");
        }
        final String prompt = promptBuilder.toString();
        return prompt;
    }
}
