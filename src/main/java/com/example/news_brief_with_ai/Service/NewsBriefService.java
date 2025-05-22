package com.example.news_brief_with_ai.Service;

import com.example.news_brief_with_ai.Client.NewsApiClient;
import com.example.news_brief_with_ai.DTO.NewsSummaryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NewsBriefService {
    private final NewsApiClient newsApiClient;

    @Autowired
    public NewsBriefService(NewsApiClient newsApiClient) {
        this.newsApiClient = newsApiClient;
    }

    public NewsSummaryResponse generateGeneralNewsBrief() {
        final newsApiResponse newsApiResponse=newsApiClient.getTopHeadlines();
    }
}
