package at.jku.dke.drools.parcelstore.main;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class Main {
	public static final void main(String[] args) {
		// load up the knowledge base
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		KieSession kSession = kContainer.newKieSession("ksession-rules");

		Person person1 = new Person("Susi Sorglos", "AT", "Astraﬂe 1", "Linz", 4040);
		Person person2 = new Person("Franz Fix", "CH", "Cstraﬂe 5", "Bern", 3000);
		Person person3 = new Person("Hans Huber", "DE", "Dstraﬂe 2", "M¸nchen", 80331);
		Person person4 = new Person("Martha Mutig", "UA", "Dstraﬂe 2", "Donezk", 8008);
		Person person5 = new Person("Tina Toscana", "DE", "Istraﬂe 1", "Berlin", 20100);
		Person person6 = new Person("Bernd Test", "UA", "Astraﬂe 1", "Kiew", 40403);
		Person person7 = new Person("Georg Frˆhlich", "UA", "Astraﬂe 1", "Odessa", 43040);
		Person person8 = new Person("Max M¸ller", "JP", "Jstraﬂe 1", "Tokio", 1313);
	}
}
