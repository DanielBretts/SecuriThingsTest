package com.SecuriThingsTest.testing;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class TestNGMain {

	eBrowser BROWSER_TYPE = eBrowser.CHROME;

	@Test
	public void createAccount() throws IOException, ParseException, InterruptedException, JSONException {
		WebDriver driver = WebDriverObject.getInstance(BROWSER_TYPE);
		driver.get("http://automationpractice.com/index.php");
		User u = new User();

		driver.findElement(By.className("login")).click();

		String email = u.getEmail();
		String password = u.getPassword();

		insertTextToTextfield("email_create", email, true);
		driver.findElement(By.id("SubmitCreate")).click();
		Thread.sleep(10000);
		// A condition that checks if the user is already registered to the website
		if (driver.findElements(By.id("create_account_error")).size() > 0) {
			insertTextToTextfield("email", email, true);
			insertTextToTextfield("passwd", password, true);
			driver.findElement(By.id("SubmitLogin")).click();
			Thread.sleep(2000);
			// This condition is important if the email that was entered is not valid
			if (driver.findElements(By.id("create_account_error")).size() > 0) {
				throw new JSONException("email");
			}
			return;
		}

		WebDriverWait waitForLoad = new WebDriverWait(driver, Duration.ofSeconds(10));
		waitForLoad.until(ExpectedConditions.textToBePresentInElementValue(By.id("email"), email));
		fillPersonalInformationSection(driver, u, password);

		// Address Section
		fillAddressSection(driver, u);

		// register button
		driver.findElement(By.id("submitAccount")).click();

	}

	private void fillAddressSection(WebDriver driver, User u) {
		Select objSelect = new Select(driver.findElement(By.id("id_country")));
		if (u.getIsUSA()) {
			Select stateSelect = new Select(driver.findElement(By.id("id_state")));
			String state = u.getStateUSA();
			String postalCode = u.getPostalCode();
			objSelect.selectByIndex(1);
			stateSelect.selectByVisibleText(state);
			insertTextToTextfield("postcode", postalCode, true);

		} else
			objSelect.selectByIndex(0);

		String[][] addressDetails = { { "company", u.getCompany() }, { "address1", u.getAddress() },
				{ "address2", u.getSecondAddress() }, { "city", u.getCity() }, { "other", u.getAdditionalInfo() },
				{ "phone", u.getHomePhone() }, { "phone_mobile", u.getMobilePhone() } };

		for (String[] section : addressDetails) {
			insertTextToTextfield(section[0], section[1], true);
		}
	}

	private void fillPersonalInformationSection(WebDriver driver, User u, String password) throws JSONException {
		String gender = u.getGender();
		String selectedGender;
		if (gender.equals("male")) {
			selectedGender = "id_gender1";
		} else if (gender.equals("female")) {
			selectedGender = "id_gender2";
		} else {
			throw new JSONException("gender");
		}
		driver.findElement(By.id(selectedGender)).click();
		insertTextToTextfield("passwd", password, true);
		insertTextToTextfield("customer_firstname", u.getFirstName(), true);
		insertTextToTextfield("customer_lastname", u.getLastName(), true);

		Hashtable<String, String> birthDate = u.getBirthDate();
		for (String str : birthDate.keySet()) {
			Select objSelect = new Select(driver.findElement(By.id(str + "s")));
			objSelect.selectByValue(birthDate.get(str));
		}
		if (u.getSignNewsletters())
			driver.findElement(By.id("newsletter")).click();
		if (u.getRecieveSpecialOffers())
			driver.findElement(By.id("optin")).click();
	}

	@Test(dependsOnMethods = { "createAccount" })
	public void addItemsToCart()
			throws FileNotFoundException, IOException, ParseException, InterruptedException, JSONException {
		WebDriver driver = WebDriverObject.getInstance(BROWSER_TYPE);
		driver.get("http://automationpractice.com/index.php");
		Order o = new Order();
		for (String item : o.getItemsWithQuantity().keySet()) {
			WebElement searchBar = driver.findElement(By.id("search_query_top"));
			searchBar.clear();
			searchBar.sendKeys(item, Keys.RETURN);
			// finding the first item from the search results
			WebElement ul = driver.findElement(By.xpath("//*[@id=\"center_column\"]/ul"));
			List<WebElement> lis = ul.findElements(By.tagName("li"));
			lis.get(0).findElement(By.tagName("a")).click();

			// adding the amount for one item
			for (int i = 0; i < o.getItemsWithQuantity().get(item) - 1; i++) {
				driver.findElement(By.xpath("//*[@id=\"quantity_wanted_p\"]/a[2]")).click();
			}

			// Sets the color for the item
			HashMap<String, String> colors = o.getItemsColor();
			String color = colors.get(item);
			WebElement colorsUl = driver.findElement(By.xpath("//*[@id=\"color_to_pick_list\"]"));
			List<WebElement> colorsLis = colorsUl.findElements(By.tagName("li"));

			// Selects the color
			boolean isColorExists = false;
			for (WebElement colorWE : colorsLis) {
				WebElement a = colorWE.findElement(By.tagName("a"));
				if (a.getAttribute("name").equals(color)) {
					isColorExists = true;
					a.click();
				}
			}
			if (!isColorExists) {
				throw new JSONException("color");
			}

			// Selects the size
			HashMap<String, String> size = o.getItemsSize();
			String sizeLetter = size.get(item);
			Select sizeSelect = new Select(driver.findElement(By.id("group_1")));
			sizeSelect.selectByVisibleText(sizeLetter);

			// Adding the item to cart
			driver.findElement(By.xpath("//*[@id='add_to_cart']/button")).click();
			Thread.sleep(5000);
		}

	}

	@Test(dependsOnMethods = { "addItemsToCart" })
	public void pay() throws FileNotFoundException, IOException, ParseException, InterruptedException, JSONException {
		Payment p = new Payment();
		WebDriver driver = WebDriverObject.getInstance(BROWSER_TYPE);
		goToCartPage(driver);

		// Checking if there are items in your cart
		doPaymentProcess(p, driver);

	}

	public void goToCartPage(WebDriver driver) {
		String cartLink = "http://automationpractice.com/index.php?controller=order";
		driver.get(cartLink);
	}

	public void doPaymentProcess(Payment p, WebDriver driver) throws JSONException {
		driver.findElement(By.xpath("//*[@id=\"center_column\"]/p[2]/a[1]")).click();

		String orderComment = p.getOrderComment();
		insertTextToTextfield("//*[@id=\"ordermsg\"]/textarea", orderComment, false);

		driver.findElement(By.xpath("//*[@id=\"center_column\"]/form/p/button")).click();
		driver.findElement(By.xpath("//*[@id=\"cgv\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"form\"]/p/button")).click();

		ePaymentMethod pm = p.getMethod();
		if (pm.equals(ePaymentMethod.BANK_WIRE)) {
			driver.findElement(By.xpath("//*[@id=\"HOOK_PAYMENT\"]/div[1]/div/p/a")).click();
		} else if (pm.equals(ePaymentMethod.CHECK)) {
			driver.findElement(By.xpath("//*[@id=\"HOOK_PAYMENT\"]/div[2]/div/p/a")).click();
		} else {
			throw new JSONException("payment");
		}

		driver.findElement(By.xpath("//*[@id=\"cart_navigation\"]/button")).click();
	}

	// A general function to insert any text to any text field by his ID or xPath
	public void insertTextToTextfield(String element, String text, Boolean isID) {
		WebDriver driver = WebDriverObject.getInstance(BROWSER_TYPE);
		WebElement TextField = null;
		if (!text.isBlank()) {
			if (isID) {
				TextField = driver.findElement(By.id(element));
			} else {
				TextField = driver.findElement(By.xpath(element));
			}
			TextField.sendKeys(text);
		}
	}

	@Test(dependsOnMethods = { "addItemsToCart" })
	public void closeDriver() {
		WebDriverObject.getInstance(BROWSER_TYPE).quit();
		System.out.println("Driver Finished");
	}

}
