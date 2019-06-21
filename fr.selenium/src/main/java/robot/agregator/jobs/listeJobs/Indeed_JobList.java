package robot.agregator.jobs.listeJobs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import robot.agregator.jobs.loaders.MyDriver;

public class Indeed_JobList {
	
	public static ArrayList<Offre> listeIndeed = new ArrayList<>();
	
	public static void recupererOffres() {

		for (int i = 0; i < 3; i++) {
			
			// récupération des éléments
			List<WebElement> jobs = MyDriver.driver.findElements(By.cssSelector(".jobtitle.turnstileLink"));
			List<WebElement> jobSummary = MyDriver.driver.findElements(By.cssSelector(".summary"));
			List<WebElement> jobLocation = MyDriver.driver.findElements(By.cssSelector(".location"));
			List<WebElement> jobDate = MyDriver.driver.findElements(By.cssSelector(".date"));
			
			String link = jobs.get(i).getAttribute("href");
			String title = jobs.get(i).getAttribute("title").toLowerCase();
			String summary = jobSummary.get(i).getText();
			String location = jobLocation.get(i).getText();
			
			// récuperer la date sur le site 
			String publicationDate = jobDate.get(i).getText();
			
			//spliter le texte pour ne récuperer que le chiffre 
			String[] b = publicationDate.split(" ");
			String datepub = b[3];
			
			// transformer la date en long pour la soustraire à la date du jour
			long datepubli = Long.parseLong(datepub);
			LocalDate datpub = null;
						
				switch (b[4]) {
				    case "minutes":
					   datpub = LocalDate.now();
				    break;
			
				    case "heures":
					   datpub = LocalDate.now();
				    break;
				    
					case "jours":
						datpub = LocalDate.now().minusDays(datepubli);
					break;
						 
					case "mois":
						datepubli = datepubli*30;
						datpub = LocalDate.now().minusDays(datepubli);
					break;
				}
				
			// condition de filtre des offres
			if (title.contains("testeur")) {
			
				Offre offre = new Offre(title, link, summary, location, datpub);

				listeIndeed.add(offre);
			}
		
		}
		for (Offre offre : listeIndeed) {

			System.out.println(offre);
		}
	}
}
