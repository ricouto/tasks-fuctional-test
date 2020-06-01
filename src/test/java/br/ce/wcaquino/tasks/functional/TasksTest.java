package br.ce.wcaquino.tasks.functional;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class TasksTest {
	
	public WebDriver acessarAplicacao(){
		String pathDriver = System.getProperty("user.dir");
		String pathFF = "\\src\\test\\resources\\drivers\\geckodriver.exe";
		
		/*String pathChrome = "\\src\\test\\resources\\drivers\\chromedriver.exe";
		//"src/test/resources/drivers/chromedriver.exe"
		System.setProperty("webdriver.chrome.driver", pathDriver + pathChrome);
		WebDriver driver = new ChromeDriver();*/
		
		System.setProperty("webdriver.gecko.driver", pathDriver + pathFF);
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		firefoxOptions.setHeadless(false);
		WebDriver driver = new FirefoxDriver(firefoxOptions);
		
		
		driver.navigate().to("http://localhost:8001/tasks");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}
	
	@Test
	public void deveSalvarTarefaComSucesso() throws InterruptedException{
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
	public void naoDeveSalvarTarefaSemDescricao() throws InterruptedException{
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
	public void naoDeveSalvarTarefaSemData() throws InterruptedException{
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
	public void naoDeveSalvarTarefaComDataPassada() throws InterruptedException{
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
