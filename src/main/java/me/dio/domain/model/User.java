package me.dio.domain.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private Account account;

    @OneToOne(cascade = CascadeType.ALL)
    private Card card;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Feature> features;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<News> news;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }


    public static final class UserBuilder {
        private Long id;
        private String name;
        private Account account;
        private Card card;
        private List<Feature> features;
        private List<News> news;

        public UserBuilder() {
            this.account = new Account();
            this.card = new Card();
            this.features = new ArrayList<>();
            this.news = new ArrayList<>();
        }

        public UserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder addAccountId(Long id) {
            this.account.setId(id);
            return this;
        }

        public UserBuilder addAccountNumber(String number) {
            this.account.setNumber(number);
            return this;
        }

        public UserBuilder addAccountAgency(String agency) {
            this.account.setAgency(agency);
            return this;
        }

        public UserBuilder addAccountBalance(BigDecimal balance) {
            this.account.setBalance(balance);
            return this;
        }

        public UserBuilder addAccountLimit(BigDecimal limit) {
            this.account.setLimit(limit);
            return this;
        }

        public UserBuilder addCardId(Long id) {
            this.card.setId(id);
            return this;
        }

        public UserBuilder addCardNumber(String number) {
            this.card.setNumber(number);
            return this;
        }

        public UserBuilder addCardLimit(BigDecimal limit) {
            this.card.setLimit(limit);
            return this;
        }

        public UserBuilder addFeature(String icon, String description) {
            Feature feature = new Feature();
            feature.setIcon(icon);
            feature.setDescription(description);
            this.features.add(feature);
            return this;
        }

        public UserBuilder addNews(String icon, String description) {
            News news = new News();
            news.setIcon(icon);
            news.setDescription(description);
            this.news.add(news);
            return this;
        }

        public User build() {
            User user = new User();
            user.setId(id);
            user.setName(name);
            user.setAccount(account);
            user.setCard(card);
            user.setFeatures(features);
            user.setNews(news);
            return user;
        }
    }
}
