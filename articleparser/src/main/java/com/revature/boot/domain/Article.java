package com.revature.boot.domain;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "articles")
public class Article {
	@Id @GeneratedValue(generator="articles_id_seq", strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Pattern(regexp="[a-zA-Z]+")
	@javax.validation.constraints.Size(min=2, max=15)
	@NotBlank
	private String title;
	private String body;
	private String author;
	private String publisher;
	private String date_created;
	private String link;
	private String summary;
	
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getDate_created() {
		return date_created;
	}
	public void setDate_created(String date_created) {
		this.date_created = date_created;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
	public String getSummary() {
		return summary;
	}
	
	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String textSearch(String text, String searchWord) {
        int numHits = 0;
        int currentIndex = 0;

        String retJSON = "{  \"" + searchWord + "\" : { \n";

        boolean keepGoing = true;
        while (keepGoing) {
            int indexOf = text.indexOf(searchWord, currentIndex);

            if (indexOf == -1) {
                keepGoing = false;
                retJSON += " \n} \n}";
            } else {
                if (numHits > 0) {
                    retJSON += ",\n";
                }

                numHits += 1;
                currentIndex = indexOf + 1; // use currentIndex like a pointer, have to advance it past the substr else
                                            // inf loop
                retJSON += "\t\"hit" + numHits + "\": \"" + indexOf + "\"";
            }
        }
        if (numHits > 0)
            return retJSON;
        else
            return "\"no hits\"";
    }

}
