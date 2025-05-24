package com.example.news_brief_with_ai.Client;

import com.example.news_brief_with_ai.DTO.NewsApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
public class NewsApiClient {

    @Value("${news.api.key}")
    private String apiKey;

    @Value("${news.api.base.url}")
    private String url;

    @Value("${news.api.default.country}")
    private String country;

    @Value("${news.api.url.content.top.headlines}")
    private String topHeadlineUrl;


    public NewsApiResponse getTopHeadlines() {
        final RestTemplate restTemplate = new RestTemplate();

        final String url = getTopHeadlinesUrl();

        final NewsApiResponse result = restTemplate.getForObject(url, NewsApiResponse.class);

        log.info("result: {}",result);
        return result;
    }

    private String getTopHeadlinesUrl() {
        final String baseUrl = this.url + this.topHeadlineUrl;

        final String urlWithParams = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("country", country)
                .queryParam("apiKey", apiKey)
                .toUriString();
        log.info("url: {}", urlWithParams);
        return urlWithParams;
    }
}
