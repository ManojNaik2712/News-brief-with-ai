package com.example.news_brief_with_ai.Service;

import com.example.news_brief_with_ai.Client.NewsApiClient;
import com.example.news_brief_with_ai.Client.OllamaClient;
import com.example.news_brief_with_ai.DTO.NewsApiResponse;
import com.example.news_brief_with_ai.DTO.NewsSummaryResponse;
import com.example.news_brief_with_ai.DTO.OllamaResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NewsBriefService {

    private final NewsApiClient newsApiClient;
    private final OllamaClient ollamaClient;

    @Autowired
    public NewsBriefService(NewsApiClient newsApiClient,OllamaClient ollamaClient) {
        this.newsApiClient = newsApiClient;
        this.ollamaClient=ollamaClient;
    }

    //Getting brief explanation of news
    public NewsSummaryResponse generateGeneralNewsBrief() {
        final NewsApiResponse newsApiResponse=newsApiClient.getTopHeadlines();

        log.info("newsApiReponse: {}",newsApiResponse.getArticles().size());

        final OllamaResponse ollamaResponse=ollamaClient.generateSummary(newsApiResponse.getArticles());
        return NewsSummaryResponse.builder()
                .summary(ollamaResponse.getResponse())
                .creadtedAt(java.time.LocalDateTime.now())
                .build();
    }
}
