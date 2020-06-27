package br.ce.wcaquino.tasks.functional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TasksTest {
	
	public WebDriver acessarAplicacao() throws MalformedURLException{
		String pathDriver = System.getProperty("user.dir");
		String pathFF = "\\src\\test\\resources\\drivers\\geckodriver.exe";
		
		/*String pathChrome = "\\src\\test\\resources\\drivers\\chromedriver.exe";
		//"src/test/resources/drivers/chromedriver.exe"
		System.setProperty("webdriver.chrome.driver", pathDriver + pathChrome);
		WebDriver driver = new ChromeDriver();*/
		
		System.setProperty("webdriver.gecko.driver", pathDriver + pathFF);
		
		/*DesiredCapabilities cap = DesiredCapabilities.firefox();
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		firefoxOptions.setHeadless(true);
		cap.merge(firefoxOptions);*/
		
		DesiredCapabilities cap=new DesiredCapabilities();
		cap.setBrowserName("chrome");
		cap.setPlatform(Platform.ANY);
		ChromeOptions options = new ChromeOptions();
		options.merge(cap);
		options.setHeadless(false);
		
		//Via local
		//WebDriver driver = new RemoteWebDriver(new URL("http://192.168.15.6:4444/wd/hub"), cap/options);
		
		//Via Docker
		WebDriver driver = new RemoteWebDriver(new URL("http://192.168.99.100:4444/wd/hub"), options);
		
		//firefoxOptions.setHeadless(true);
		//WebDriver driver = new FirefoxDriver(firefoxOptions);
		
		//Via local
		//driver.navigate().to("http://localhost:8001/tasks");
		
		//Via Docker
		driver.navigate().to("http://192.168.15.3:8001/tasks");
		//driver.navigate().to("http://192.168.99.100:9999/tasks");
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}
	
	@Test
	public void deveSalvarTarefaComSucesso() throws InterruptedException, MalformedURLException{
		WebDriver driver = acessarAplicacao();
		try{
		driver.findElement(By.id("addTodo")).click();
		driver.findElement(By.id("task")).sendKeys("Teste via Selenium....");
		driver.findElement(By.id("dueDate")).sendKeys("10/10/2025");
		driver.findElement(By.id("saveButton")).click();
		Thread.sleep(2000);
		String msgValidacao = driver.findElement(By.id("message")).getText();
		Assert.assertEquals("Success!", msgValidacao);
		}finally{
		driver.quit();
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaSemDescricao() throws InterruptedException, MalformedURLException{
		WebDriver driver = acessarAplicacao();
		try{
		driver.findElement(By.id("addTodo")).click();
		driver.findElement(By.id("dueDate")).sendKeys("10/10/2025");
		driver.findElement(By.id("saveButton")).click();
		Thread.sleep(2000);
		String msgValidacao = driver.findElement(By.id("message")).getText();
		Assert.assertEquals("Fill the task description", msgValidacao);
		}finally{
		driver.quit();
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaSemData() throws InterruptedException, MalformedURLException{
		WebDriver driver = acessarAplicacao();
		try{
		driver.findElement(By.id("addTodo")).click();
		driver.findElement(By.id("task")).sendKeys("Teste via Selenium....");
		driver.findElement(By.id("saveButton")).click();
		Thread.sleep(2000);
		String msgValidacao = driver.findElement(By.id("message")).getText();
		Assert.assertEquals("Fill the due date", msgValidacao);
		}finally{
		driver.quit();
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaComDataPassada() throws InterruptedException, MalformedURLException{
		WebDriver driver = acessarAplicacao();
		try{
		driver.findElement(By.id("addTodo")).click();
		driver.findElement(By.id("task")).sendKeys("Teste via Selenium....");
		driver.findElement(By.id("dueDate")).sendKeys("10/10/2000");
		driver.findElement(By.id("saveButton")).click();
		Thread.sleep(2000);
		String msgValidacao = driver.findElement(By.id("message")).getText();
		Assert.assertEquals("Due date must not be in past", msgValidacao);
		}finally{
		driver.quit();
		}
	}
}
