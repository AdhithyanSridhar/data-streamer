package com.stream.sample;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ObserverPattern {

	public static void main(String[] args) {
		SpringApplication.run(ObserverPattern.class, args);
	}

}

@RestController
@RequestMapping("/msg")
class MessageController {

	@Autowired
	private NewPost post;

	@PostConstruct
	private void addNotificationListeners() {
		this.post.addImplementationsToObservers();
	}

	@GetMapping("/send/{message}")
	public void postMessage(@PathVariable String message) {
		post.postMessage(message);
	}

}

@Component
class NewPost {

	private String msg;
	private List<Observer> observers = new LinkedList<>();
	ListableBeanFactory beanFactory;

	@Autowired
	public NewPost(ListableBeanFactory beanFactory) {
		super();
		this.beanFactory = beanFactory;
	}

	public void addImplementationsToObservers() {
		Collection<Observer> implementations = beanFactory.getBeansOfType(Observer.class).values();
		observers.addAll(implementations);
	}

	public void postMessage(String msg) {
		this.msg = msg;

		System.out.println("regularStream - ordered");
		observers.parallelStream().forEach(observer -> {
//			sleep(500);
			observer.sendMessage(this.msg);
		});
	}

	private void sleep(long sleepTime) {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

interface Observer {
	public void sendMessage(String msg);
}

@Service
class FacebookNotifier implements Observer {
	@Override
	public void sendMessage(String msg) {
		System.out.println("sending notification message to FB: " + msg);
	}
}

@Service
class TwitterNotifier implements Observer {
	@Override
	public void sendMessage(String msg) {
		System.out.println("sending notification message to Twitter: " + msg);
	}
}

@Service
class LinkedInNotifier implements Observer {
	@Override
	public void sendMessage(String msg) {
		System.out.println("sending notification message to LinkedIn: " + msg);
	}
}

@Service
class YoutubeNotifier implements Observer {
	@Override
	public void sendMessage(String msg) {
		System.out.println("sending notification message to Youtube: " + msg);
	}
}

@Service
class GooglePlusNotifier implements Observer {
	@Override
	public void sendMessage(String msg) {
		System.out.println("sending notification message to GooglePlus: " + msg);
	}
}

@Service
class InstagramNotifier implements Observer {
	@Override
	public void sendMessage(String msg) {
		System.out.println("sending notification message to Instagram: " + msg);
	}
}

@Service
class PinterestNotifier implements Observer {
	@Override
	public void sendMessage(String msg) {
		System.out.println("sending notification message to Pinterest: " + msg);
	}
}

@Service
class RedditNotifier implements Observer {
	@Override
	public void sendMessage(String msg) {
		System.out.println("sending notification message to Reddit: " + msg);
	}
}