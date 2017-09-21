package model;

import java.sql.Date;

public class ActivityEventModel {	
	private String eventName;
	private Date serviceDate;
	private String githubComments;

	public ActivityEventModel(Date serviceDate, String eventName, String githubComments) {
		this.serviceDate = serviceDate;
		this.eventName = eventName;
		this.githubComments = githubComments;
	}

	public String getEventName() {
		return eventName;
	}

	public Date getServiceDate() {
		return serviceDate;
	}

	public String getGithubComments() {
		return githubComments;
	}
}