package tech.susheelkona.billsearch.services;

import tech.susheelkona.billsearch.model.NewsItem;
import tech.susheelkona.billsearch.services.cache.CachedEntity;

public interface NewsService extends Updatable {
    CachedEntity<NewsItem> getNewsItems();
}
