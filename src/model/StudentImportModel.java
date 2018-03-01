package model;

public class StudentImportModel implements Comparable<StudentImportModel> {
	private int clientID;
	private String lastName, firstName, githubName, startDate, homeLocString;
	private int homeLocation, gender, gradYear, isInMasterDb;

	// Additional fields for importing contacts to SalesForce
	private String gradYearString;
	private String genderString;
	private String birthDate;
	private String currGrade;
	private String email, mobilePhone, homePhone, address, schoolName, tShirtSize, financialAidPercent, grantInfo;
	private String membership, passOnFile, leaveReason, hearAboutUs, whoToThank;
	private String emergContactName, emergContactPhone, emergContactEmail;
	private String accountMgrNames, accountMgrPhones, accountMgrEmails, dependentNames;
	private int completedVisits, futureVisits;
	private boolean signedWaiver, stopEmail, financialAid;

	public StudentImportModel(int clientID, String lastName, String firstName, String githubName, String gender,
			String startDate, String homeLocation, String gradYear) {

		// Pike13 import data
		this.clientID = clientID;
		this.lastName = lastName;
		this.firstName = firstName;
		this.githubName = parseGithubName(githubName);
		this.gender = GenderModel.convertStringToGender(gender);
		this.startDate = startDate;

		this.homeLocation = LocationModel.convertStringToLocation(homeLocation);
		this.homeLocString = LocationModel.convertLocationToString(this.homeLocation);

		if (gradYear.equals("") || gradYear.equals("\"\""))
			this.gradYear = 0;
		else {
			try {
				this.gradYear = Integer.parseInt(gradYear);

			} catch (NumberFormatException e) {
				this.gradYear = 0;
			}
		}

		isInMasterDb = 1;
	}

	public StudentImportModel(int clientID, String lastName, String firstName, String githubName, int gender,
			String startDate, int homeLocation, int gradYear, int isInMasterDb) {

		// Database format being converted for comparison purposes
		this.clientID = clientID;
		this.lastName = lastName;
		this.firstName = firstName;
		this.githubName = parseGithubName(githubName);
		this.gender = gender;
		this.startDate = startDate;
		this.homeLocation = homeLocation;
		this.gradYear = gradYear;
		this.isInMasterDb = isInMasterDb;
	}

	public StudentImportModel(int clientID, String firstName, String lastName, String gender, String birthDate,
			String currGrade, String gradYear, String startDate, String homeLocation, String email, String mobilePhone,
			String address, String schoolName, String githubName, int completedVisits, int futureVisits,
			String tShirtSize, boolean signedWaiver, String membership, String passOnFile, boolean stopEmail,
			boolean financialAid, String financialAidPercent, String grantInfo, String leaveReason, String hearAboutUs,
			String whoToThank, String emergContactName, String emergContactPhone, String emergContactEmail,
			String homePhone, String accountMgrNames, String accountMgrPhones, String accountMgrEmails,
			String dependentNames) {

		this.clientID = clientID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.genderString = gender;
		this.birthDate = birthDate;
		this.currGrade = currGrade;
		this.gradYearString = gradYear;
		this.startDate = startDate;
		this.homeLocString = homeLocation;
		this.email = email;
		this.mobilePhone = mobilePhone;
		this.address = address;
		this.schoolName = schoolName;
		this.githubName = parseGithubName(githubName);
		this.completedVisits = completedVisits;
		this.futureVisits = futureVisits;
		this.tShirtSize = tShirtSize;
		this.signedWaiver = signedWaiver;
		this.membership = membership;
		this.passOnFile = passOnFile;
		this.stopEmail = stopEmail;
		this.financialAid = financialAid;
		this.financialAidPercent = financialAidPercent;
		this.grantInfo = grantInfo;
		this.leaveReason = leaveReason;
		this.hearAboutUs = hearAboutUs;
		this.whoToThank = whoToThank;
		this.emergContactName = emergContactName;
		this.emergContactPhone = emergContactPhone;
		this.emergContactEmail = emergContactEmail;
		this.accountMgrNames = accountMgrNames;
		this.accountMgrPhones = accountMgrPhones;
		this.accountMgrEmails = accountMgrEmails;
		this.homePhone = homePhone;
		if (dependentNames == null)
			this.dependentNames = "";
		else
			this.dependentNames = dependentNames;
	}

	private String parseGithubName(String githubName) {
		if (githubName == null || githubName.equals("") || githubName.equals("\"\""))
			githubName = "";
		else {
			int index = githubName.indexOf('(');
			if (index != -1)
				githubName = githubName.substring(0, index);
			githubName.trim();
		}
		return githubName;
	}

	public String toString() {
		return firstName + " " + lastName + " (" + clientID + ")";
	}

	public int getClientID() {
		return clientID;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getGithubName() {
		return githubName;
	}

	public int getGender() {
		return gender;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getHomeLocAsString() {
		return homeLocString;
	}

	public int getHomeLocation() {
		return homeLocation;
	}

	public int getGradYear() {
		return gradYear;
	}

	public int getIsInMasterDb() {
		return isInMasterDb;
	}

	public String getHomeLocString() {
		return homeLocString;
	}

	public String getGradYearString() {
		return gradYearString;
	}

	public String getGenderString() {
		return genderString;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public String getCurrGrade() {
		return currGrade;
	}

	public String getEmail() {
		return email;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public String getAddress() {
		return address;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public String gettShirtSize() {
		return tShirtSize;
	}

	public String getFinancialAidPercent() {
		return financialAidPercent;
	}

	public String getGrantInfo() {
		return grantInfo;
	}

	public String getLeaveReason() {
		if (leaveReason.equals(""))
			return " ";
		else
			return leaveReason;
	}

	public String getHearAboutUs() {
		return hearAboutUs;
	}

	public String getWhoToThank() {
		return whoToThank;
	}

	public String getEmergContactName() {
		return emergContactName;
	}

	public String getEmergContactPhone() {
		return emergContactPhone;
	}

	public String getEmergContactEmail() {
		return emergContactEmail;
	}

	public int getCompletedVisits() {
		return completedVisits;
	}

	public int getFutureVisits() {
		return futureVisits;
	}

	public boolean isSignedWaiver() {
		return signedWaiver;
	}

	public String getMembership() {
		return membership;
	}

	public String getPassOnFile() {
		return passOnFile;
	}

	public boolean isStopEmail() {
		return stopEmail;
	}

	public boolean isFinancialAid() {
		return financialAid;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public String getAccountMgrNames() {
		return accountMgrNames;
	}

	public String getAccountMgrPhones() {
		return accountMgrPhones;
	}

	public String getAccountMgrEmails() {
		return accountMgrEmails;
	}

	public String getDependentNames() {
		return dependentNames;
	}

	@Override
	public int compareTo(StudentImportModel other) {
		if (clientID < other.getClientID())
			return -1;

		else if (clientID > other.getClientID())
			return 1;

		// Client ID matches
		else if (lastName.equals(other.getLastName()) && firstName.equals(other.getFirstName())
				&& githubName.equals(other.getGithubName()) && startDate.equals(other.getStartDate())
				&& homeLocation == other.getHomeLocation() && gender == other.getGender()
				&& gradYear == other.getGradYear() && isInMasterDb == other.getIsInMasterDb())
			return 0;

		else {
			// Client ID matches but data does not
			return -2;
		}
	}

	public String displayAll() {
		return (clientID + ": " + firstName + " " + lastName + " (" + gender + "), github: " + githubName + ", home: "
				+ homeLocString + ", start: " + startDate + ", grad: " + gradYear + ", " + isInMasterDb);
	}
}
