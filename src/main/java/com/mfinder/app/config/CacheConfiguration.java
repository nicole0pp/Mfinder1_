package com.mfinder.app.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.mfinder.app.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.mfinder.app.repository.UserRepository.USERS_BY_ID_CACHE);
            createCache(cm, com.mfinder.app.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.mfinder.app.domain.User.class.getName());
            createCache(cm, com.mfinder.app.domain.Authority.class.getName());
            createCache(cm, com.mfinder.app.domain.User.class.getName() + ".authorities");
            createCache(cm, com.mfinder.app.domain.Song.class.getName());
            createCache(cm, com.mfinder.app.domain.Artist.class.getName());
            createCache(cm, com.mfinder.app.domain.Artist.class.getName() + ".albums");
            createCache(cm, com.mfinder.app.domain.Artist.class.getName() + ".favoriteLists");
            createCache(cm, com.mfinder.app.domain.Artist.class.getName() + ".events");
            createCache(cm, com.mfinder.app.domain.Client.class.getName());
            createCache(cm, com.mfinder.app.domain.Client.class.getName() + ".favoriteLists");
            createCache(cm, com.mfinder.app.domain.Event.class.getName());
            createCache(cm, com.mfinder.app.domain.Event.class.getName() + ".artists");
            createCache(cm, com.mfinder.app.domain.Event.class.getName() + ".ratings");
            createCache(cm, com.mfinder.app.domain.Album.class.getName());
            createCache(cm, com.mfinder.app.domain.Album.class.getName() + ".songs");
            createCache(cm, com.mfinder.app.domain.FavoriteList.class.getName() + ".listDetails");
            createCache(cm, com.mfinder.app.domain.FavoriteList.class.getName());
            createCache(cm, com.mfinder.app.domain.ListDetails.class.getName());
            createCache(cm, com.mfinder.app.domain.ListDetails.class.getName() + ".favoriteList");
            createCache(cm, com.mfinder.app.domain.ListDetails.class.getName() + ".song");
            createCache(cm, com.mfinder.app.domain.RatingEvent.class.getName());
            createCache(cm, com.mfinder.app.domain.RatingEvent.class.getName() + ".event");
            createCache(cm, com.mfinder.app.domain.RatingSong.class.getName());
            createCache(cm, com.mfinder.app.domain.RatingSong.class.getName() + ".song");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
