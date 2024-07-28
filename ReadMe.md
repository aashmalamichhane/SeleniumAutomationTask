
# Hamro Bazar UI Automation
This project automates the HamroBazar website using Selenium WebDriver, integrated with TestNG and CSV handling via OpenCSV. The Page Object Model (POM) design pattern is employed to organize the site into three sections: Homepage, Product, and Filter.

## Table of Contents

1. [Features](#Features)
2. [Prerequisites](#Prerequisites)
3. [Installation](#Installation)
4. [Running Application](#Running-Application)
5. [Sample Configuration](#Sample-Configuration)
6. [Sample Test Data](#Sample-Test-Data)

## Features
- Automated product search and filtering
- CSV file generation for search results and storage in Search_Result.csv with tabular view in Search_Result.html
- ExtentReports for report generation stored in the test-output/ExtentReports folder.
- Screenshots of Failed testcases stored in the test-output/ExtentReports/Screenshots folder.
- Configuration settings retrieved from config.properties.
- Test data fetched from testdata.properties

## Prerequisites
- Java JDK 21
- Maven

## Installation
1. Clone this repository:

   git clone https://github.com/aashmalamichhane/SeleniumAutomationTask.git

2.  Navigate to the project directory:
    cd HamroBazarAutomationTask
3.  Install dependencies using Maven:
    mvn clean install

## Running Application
Run testng.xml file in src/main/resources folder.

## Sample Configuration
- url = https://hamrobazaar.com/
- browser = chrome
- Note : we can change the browser name as required from config.properties.

## Sample Test Data
- searchItemName = Monitor
- locationName = Newroad
- exactLocationName = naya sadak newroad, New Road, Kathmandu
- lowToHighSortValue = Low to High (Price)