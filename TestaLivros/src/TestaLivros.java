import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestaLivros {
	private WebDriver driver;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		driver = new ChromeDriver();
	}

	@Test
	public void TestaLivros() throws Exception {
		// Abrindo o site da submarino
		driver.get("https://www.submarino.com.br/");
		driver.findElement(By.id("h_search-input")).click();
		driver.findElement(By.id("h_search-input")).clear();

		// Buscando os livros
		driver.findElement(By.id("h_search-input")).sendKeys("livros");

		// Abrindo o primeiro livro da pagina
		driver.findElement(By.id("h_search-btn")).click();
		driver.findElement(
				By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Refinar'])[1]/following::img[1]"))
				.click();
		driver.findElement(
				By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Autor(a)'])[1]/following::td[1]"))
				.click();

		// gravando o ISBN do livro
		String isbnSubmarino = driver
				.findElement(
						By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='ISBN'])[1]/following::span[1]"))
				.getText();

		// Salvando o nome do Autor no site da Submarino
		String autorSubmarino = driver
				.findElement(By
						.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Autor(a)'])[1]/following::span[1]"))
				.getText();

		// Abrindo o site da Livraria Cultura
		driver.get("https://www.livrariacultura.com.br/");

		// Buscando o livro pelo ISBN encontrado na submarino
		driver.findElement(By.id("Ntt-responsive")).click();
		driver.findElement(By.id("Ntt-responsive")).clear();
		driver.findElement(By.id("Ntt-responsive")).sendKeys(isbnSubmarino);

		// Selecionando o livro encontrado
		driver.findElement(By
				.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Papelaria & Etc'])[2]/following::input[2]"))
				.click();
		driver.findElement(By.linkText("Dois a Dois")).click();

		// Salvando o autor do livro encontrado na livraria cultura
		String autorCultura = driver.findElement(By.linkText("SPARKS, NICHOLAS")).getText();

		// Abrindo o site da Americanas
		driver.get("https://www.americanas.com.br/");
		driver.findElement(By.id("h_search-input")).click();
		driver.findElement(By.id("h_search-input")).clear();

		// Buscando o livro pelo ISBN salvo na submarino
		driver.findElement(By.id("h_search-input")).sendKeys(isbnSubmarino);
		driver.findElement(By.id("h_search-input")).sendKeys(Keys.ENTER);

		// Selecionando o livro encontrado
		driver.findElement(
				By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Refinar'])[1]/following::img[1]"))
				.click();
		driver.findElement(
				By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Autor(a)'])[1]/following::td[1]"))
				.click();

		// Salvando o autor do livro encontrado no site da Americanas
		String autorAmericanas = driver
				.findElement(By
						.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Autor(a)'])[1]/following::span[1]"))
				.getText();

		if (autorCultura.equals(autorSubmarino)) {
			System.out.println("O nome do autor do livro é igual nos dois sites!");
		} else {
			System.out.println("O nome do autor do livro é diferente no site da Livraria Cultura!");
		}

		if (autorSubmarino.equals(autorAmericanas)) {
			System.out.println("O nome do autor do livro é igual nos sites da Americanas e Submarino!");
		} else {
			System.out.println("O nome do autor do livro é diferente no site da Americanas!");
		}
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}
