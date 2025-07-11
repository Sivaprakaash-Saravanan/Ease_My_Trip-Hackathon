# easemytrip-hackathon
A test automation suite built for a hackathon, focusing on real-world scenarios such as cab booking, form validation, and hotel guest data extraction on EaseMyTrip. Powered by Java &amp; Selenium, the framework integrates TestNG, Maven, Jenkins, GitHub, Cucumber, and Allure Reports to deliver scalable, behavior-driven testing with rich analytics.

# 📘 Hackathon Project – EaseMyTrip Automation Suite

## 🛠️ Tech Stack
- Java
- Selenium WebDriver
- TestNG
- Maven
- Cucumber (BDD)
- Allure Reports
- Jenkins (CI/CD)
- GitHub

---

## 🎯 Project Objective

Automate real-world travel booking scenarios on [EaseMyTrip](https://www.easemytrip.com), focusing on UI validation, data extraction, and error-handling workflows. This project was built as part of a hackathon to demonstrate scalable test automation solutions.

---

## 🚗 Key Scenarios Automated

1. **Cab Booking Flow**
   - Book one-way outstation cab from Delhi to Manali.
   - Select future date/time (e.g., 23 Dec 2019, 6:30 AM).
   - Car type: SUV.
   - Extract and display the **lowest available charge**.

2. **Gift Card Validation**
   - Navigate to Group Gifting section.
   - Enter invalid email and form data.
   - Capture and assert the **warning/error message**.

3. **Hotel Booking Page**
   - Extract all available numbers of adult guests from the UI.
   - Store results in a collection and **print the list**.

---

## 🧪 Automation Capabilities

- Form validation and alert handling  
- Dropdown extraction  
- Web element collection and filtering  
- Page scrolling and navigation  
- Assertion checks and UI feedback capture  

---


---

## 📸 Reporting

- Allure Reports integrated for visual test insights  
- Cucumber BDD offers step-wise clarity for test scenarios  
- Jenkins Pipeline used for CI/CD execution (optional setup for team collaboration)

---

## 🧑‍🤝‍🧑 Collaboration

This was built as part of a team project during a hackathon. Contributions included:
- Designing test architecture  
- Implementing test cases  
- Debugging failed executions  
- Configuring CI pipeline  
- Reporting test outcomes  

---

## 🚀 How to Run Locally

1. Clone the repo: git clone https://github.com/yourusername/easemytrip-hackathon.git
2. Open the project in Eclipse or IntelliJ.
3. Install dependencies via Maven: mvn clean install
4. Run tests using TestNG or Cucumber runner.
5. Generate reports: mvn allure:serve
   


