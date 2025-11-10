package RedBusBooking;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class RedBusbook {

	WebDriver wd;
	WebDriverWait wait;
	
	@BeforeTest
	public void setup() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		wd = new ChromeDriver(options);
		wd.get("https://www.redbus.in/");
		
		wait = new WebDriverWait(wd,Duration.ofSeconds(40));
	}
 
	@Test
	public void busBooking(){
		
		By fromBtnLocator = By.xpath("//div[contains(@class,\"srcDestWrapper\") and @role=\"button\"]");
		//WebElement sourceBtn = wd.findElement(fromBtnLocator);
		WebElement sourceBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(fromBtnLocator));
		sourceBtn.click();
		
		By searchSuggestionWrapper = By.xpath("//div[contains(@class,\"suggestionsWrapper\")]");
		wait.until(ExpectedConditions.visibilityOfElementLocated(searchSuggestionWrapper));
		
		WebElement searchCityElement = wd.switchTo().activeElement();
		wait.until(ExpectedConditions.visibilityOf(searchCityElement));
		searchCityElement.sendKeys("Delhi");
		//Thread.sleep(3000);
		By searchSuggestionSelectedCityLocator = By.xpath("//div[contains(@class,\"searchCategory\")]");
		List<WebElement> listOfSearchSuggestions = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(searchSuggestionSelectedCityLocator,1));
		
		WebElement searchedCityWrapperElement = listOfSearchSuggestions.get(0);
		
		List<WebElement> listOfSearchCities = searchedCityWrapperElement.findElements(By.xpath("//div[contains(@class,\"listItem\")]"));
		//listOfSearchCities.get(2).click();
		
		for(WebElement cities : listOfSearchCities) {
			if(cities.getText().contains("Akshardham")) {
				cities.click();
				break;
			}
		}
		
		
		//To
		WebElement destinationInputBox = wd.switchTo().activeElement();
		destinationInputBox.sendKeys("Jabalpur");
		
		By destinationCategoryLocator = By.xpath("//div[contains(@class,\"searchCategory\")]");
		List<WebElement> destinationCategoryList = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(destinationCategoryLocator, 2));
		
		WebElement destinationSearchedCategory = destinationCategoryList.get(0);
		
		List<WebElement> destinationSearchedCitiesList = destinationSearchedCategory.findElements(By.xpath("//div[contains(@class,\"listItem\")]"));
		
		for(WebElement destCities : destinationSearchedCitiesList) {
			if(destCities.getText().contains("Jabalpur ISBT")) {
				destCities.click();
				break;
			}
		}
		
		//selected calender
		By calenderInputLocator = By.xpath("//div[contains(@class,\"dateInputWrapper\")]");
		wd.findElement(calenderInputLocator).click();
		
		By calenderNextMonthArrowBtnLocator = By.xpath("//i[contains(@class,\"right\")]");	
		WebElement calenderNextMonthArrowBtnWebelement = wait.until(ExpectedConditions.elementToBeClickable(calenderNextMonthArrowBtnLocator));
		calenderNextMonthArrowBtnWebelement.click();
		
		By listOfDateLocator = By.xpath("//li[contains(@class,\"dateItem\")]");
		List<WebElement> listOfDatesWebelement = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(listOfDateLocator));
		
		for(WebElement date : listOfDatesWebelement) {
			if(date.getText().contains("8")) {
				date.click();
				break;
			}
		}
		
		//verifying if date selected 8th of august or not 
		String ExpectedDate = "08 Dec, 2025";
		String actualDateLocator = wd.findElement(By.xpath("//span[contains(@class,\"doj\")]")).getText();	
		Assert.assertEquals(ExpectedDate, actualDateLocator);
		
	   }
}
