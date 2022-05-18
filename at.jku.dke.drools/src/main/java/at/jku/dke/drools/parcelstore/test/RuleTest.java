package at.jku.dke.drools.parcelstore.test;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

import at.jku.dke.drools.parcelstore.main.Paket;
import at.jku.dke.drools.parcelstore.main.Person;



class RuleTest {

	static KieSession kSession = null;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		kSession = kContainer.newKieSession("ksession-rules");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		kSession.destroy();
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testRuleLandOfRecipient() {

		System.out.println("Verification that recipient resides in a valid country. ");

		Person sender = new Person("Susi Sorglos", "AT", "Astraﬂe 1", "Linz", 4040);
		Person recipient = new Person("Max M¸ller", "JP", "Jstraﬂe 1", "Tokio", 1313);

		Paket shipment = new Paket(1, 100.0, 50.0, 60.0, 3.5, recipient, sender, true, 7000);
		FactHandle shipmentFactHandle = kSession.insert(shipment);
		kSession.fireAllRules();
		kSession.delete(shipmentFactHandle);

		assertEquals(-1.0, shipment.getVersandkosten());
	}

	@Test
	void testRuleLandOfSender() {

		System.out.println("Verification that sender resides in a valid country.");

		Person sender = new Person("Max M¸ller", "JP", "Jstraﬂe 1", "Tokio", 1313);
		Person recipient = new Person("Susi Sorglos", "AT", "Astraﬂe 1", "Linz", 4040);

		Paket shipment = new Paket(2, 100.0, 50.0, 60.0, 3.5, recipient, sender, true, 7000);
		FactHandle shipmentFactHandle = kSession.insert(shipment);
		kSession.fireAllRules();
		kSession.delete(shipmentFactHandle);

		assertEquals(-1.0, shipment.getVersandkosten());
	}

	@Test
	void testRuleMaxLength() {

		System.out.println("Verification that shipment does not exceed the maximum length of 190cm.");

		Person sender = new Person("Susi Sorglos", "AT", "Astraﬂe 1", "Linz", 4040);
		Person recipient = new Person("Hans Huber", "DE", "Dstraﬂe 2", "M¸nchen", 80331);

		Paket shipment = new Paket(3, 190.01, 90.0, 60.0, 3.5, recipient, sender, true, 7000);
		FactHandle shipmentFactHandle = kSession.insert(shipment);
		kSession.fireAllRules();
		kSession.delete(shipmentFactHandle);

		assertEquals(-1.0, shipment.getVersandkosten());
	}
	
	@Test
	void testRuleMaxWidth() {

		System.out.println("Verification that shipment does not exceed the maximum width of 130cm.");

		Person sender = new Person("Susi Sorglos", "AT", "Astraﬂe 1", "Linz", 4040);
		Person recipient = new Person("Hans Huber", "DE", "Dstraﬂe 2", "M¸nchen", 80331);

		Paket shipment = new Paket(4, 132.0, 131.0, 60.0, 3.5, recipient, sender, true, 7000);
		FactHandle shipmentFactHandle = kSession.insert(shipment);
		kSession.fireAllRules();
		kSession.delete(shipmentFactHandle);

		assertEquals(-1.0, shipment.getVersandkosten());
	}
	
	@Test
	void testRuleMaxHeight() {

		System.out.println("Verification that shipment does not exceed the maximum height of 90cm.");

		Person sender = new Person("Susi Sorglos", "AT", "Astraﬂe 1", "Linz", 4040);
		Person recipient = new Person("Hans Huber", "DE", "Dstraﬂe 2", "M¸nchen", 80331);

		Paket shipment = new Paket(5, 100.0, 99.0, 91.0, 3.5, recipient, sender, false, 7000);
		FactHandle shipmentFactHandle = kSession.insert(shipment);
		kSession.fireAllRules();
		kSession.delete(shipmentFactHandle);

		assertEquals(-1.0, shipment.getVersandkosten());
	}
	
	@Test
	void testRuleMaxWeight() {
		
		System.out.println("Verification that shipment does not exceed the maximum weight of 50.00 KG.");

		Person sender = new Person("Susi Sorglos", "AT", "Astraﬂe 1", "Linz", 4040);
		Person recipient = new Person("Hans Huber", "DE", "Dstraﬂe 2", "M¸nchen", 80331);

		Paket shipment = new Paket(6, 10.0, 10.0, 10.0, 51.0, recipient, sender, false, 7000);		
		FactHandle shipmentFactHandle = kSession.insert(shipment);
		kSession.fireAllRules();
		kSession.delete(shipmentFactHandle);

		assertEquals(-1.0, shipment.getVersandkosten());
	}
	
	@Test
	void testRuleMaxBeltDimension() {
		
		System.out.println("Checking whether the shipment does not exceed the maximum belt dimension of 390cm.");

		Person sender = new Person("Susi Sorglos", "AT", "Astraﬂe 1", "Linz", 4040);
		Person recipient = new Person("Hans Huber", "DE", "Dstraﬂe 2", "M¸nchen", 80331);

		Paket shipment = new Paket(7, 160.0, 130.0, 80.0, 1.0, recipient, sender, false, 7000);		
		FactHandle shipmentFactHandle = kSession.insert(shipment);
		kSession.fireAllRules();
		kSession.delete(shipmentFactHandle);

		assertEquals(-1.0, shipment.getVersandkosten());
	}	
	
	@Test
	void testRuleWeightBelow10KgWithinEu() {
		
		System.out.println("Verification of the shipment of a parcel with less than 10 KG within the EU.");

		Person sender = new Person("Susi Sorglos", "AT", "Astraﬂe 1", "Linz", 4040);
		Person recipient = new Person("Hans Huber", "DE", "Dstraﬂe 2", "M¸nchen", 80331);

		Paket shipment = new Paket(8, 60.0, 30.0, 10.0, 9.99, recipient, sender, false, 7000);		
		FactHandle shipmentFactHandle = kSession.insert(shipment);
		kSession.fireAllRules();
		kSession.delete(shipmentFactHandle);

		assertEquals(9.0, shipment.getVersandkosten());
	}

	@Test
	void testRuleWeightBetween10And25KgWithinEu() {
		
		System.out.println("Verification of the shipment of a parcel with less than 25 KG and above 10 KG within the EU.");

		Person sender = new Person("Susi Sorglos", "AT", "Astraﬂe 1", "Linz", 4040);
		Person recipient = new Person("Hans Huber", "DE", "Dstraﬂe 2", "M¸nchen", 80331);

		Paket shipment = new Paket(9, 60.0, 30.0, 10.0, 10.01, recipient, sender, false, 7000);		
		FactHandle shipmentFactHandle = kSession.insert(shipment);
		kSession.fireAllRules();
		kSession.delete(shipmentFactHandle);

		assertEquals(19.0, shipment.getVersandkosten());
	}
	
	@Test
	void testRuleWeightBetween25And50KgWithinEu() {
		
		System.out.println("Verification of the shipment of a parcel with less than 50 KG and above 25 KG within the EU.");

		Person sender = new Person("Susi Sorglos", "AT", "Astraﬂe 1", "Linz", 4040);
		Person recipient = new Person("Hans Huber", "DE", "Dstraﬂe 2", "M¸nchen", 80331);

		Paket shipment = new Paket(10, 60.0, 30.0, 10.0, 25.01, recipient, sender, false, 7000);		
		FactHandle shipmentFactHandle = kSession.insert(shipment);
		kSession.fireAllRules();
		kSession.delete(shipmentFactHandle);

		assertEquals(29.0, shipment.getVersandkosten());
	}
	
	@Test
	void testRuleWeightBelow10KgFromEuToUaOrCh() {
		
		System.out.println("Verification of the shipment of a parcel with less than 10 KG from the EU to UA or CH.");

		Person senderEu = new Person("Susi Sorglos", "AT", "Astraﬂe 1", "Linz", 4040);
		Person recipientCh = new Person("Franz Fix", "CH", "Cstraﬂe 5", "Bern", 3000);
		Person recipientUa = new Person("Martha Mutig", "UA", "Dstraﬂe 2", "Donezk", 8008);

		Paket shipmentEu2Ch = new Paket(11, 60.0, 30.0, 10.0, 9.99, recipientCh, senderEu, false, 7000);		
		Paket shipmentEu2Ua = new Paket(12, 60.0, 30.0, 10.0, 9.99, recipientUa, senderEu, false, 7000);	
		FactHandle shipmentEu2ChFactHandle = kSession.insert(shipmentEu2Ch);
		FactHandle shipmentEu2UaFactHandle = kSession.insert(shipmentEu2Ua);
		kSession.fireAllRules();
		kSession.delete(shipmentEu2ChFactHandle);
		kSession.delete(shipmentEu2UaFactHandle);
		
		assertEquals(18.0, shipmentEu2Ch.getVersandkosten());
		assertEquals(18.0, shipmentEu2Ua.getVersandkosten());
	}

	@Test
	void testRuleWeightBelow10KgFromUaOrChToEu() {
		
		System.out.println("Verification of the shipment of a parcel with less than 10 KG from UA or CH to the EU.");

		Person recipientEu = new Person("Susi Sorglos", "AT", "Astraﬂe 1", "Linz", 4040);
		Person senderCh = new Person("Franz Fix", "CH", "Cstraﬂe 5", "Bern", 3000);
		Person senderUa = new Person("Martha Mutig", "UA", "Dstraﬂe 2", "Donezk", 8008);

		Paket shipmentCh2Eu = new Paket(13, 60.0, 30.0, 10.0, 9.99, recipientEu, senderCh, false, 7000);		
		Paket shipmentEu2Ua = new Paket(14, 60.0, 30.0, 10.0, 9.99, recipientEu, senderUa, false, 7000);	
		FactHandle shipmentCh2EuFactHandle = kSession.insert(shipmentCh2Eu);
		FactHandle shipmentUa2EuFactHandle = kSession.insert(shipmentEu2Ua);
		kSession.fireAllRules();
		kSession.delete(shipmentCh2EuFactHandle);
		kSession.delete(shipmentUa2EuFactHandle);
		
		assertEquals(18.0, shipmentCh2Eu.getVersandkosten());
		assertEquals(18.0, shipmentEu2Ua.getVersandkosten());
	}
	
	@Test
	void testRuleWeightBetween10And25KgFromEuToUaOrCh() {
		
		System.out.println("Verification of the shipment of a parcel with less than 25 KG and above 10 KG from the EU to UA or CH.");

		Person senderEu = new Person("Susi Sorglos", "AT", "Astraﬂe 1", "Linz", 4040);
		Person recipientCh = new Person("Franz Fix", "CH", "Cstraﬂe 5", "Bern", 3000);
		Person recipientUa = new Person("Martha Mutig", "UA", "Dstraﬂe 2", "Donezk", 8008);

		Paket shipmentEu2Ch = new Paket(15, 60.0, 30.0, 10.0, 10.01, recipientCh, senderEu, false, 7000);		
		Paket shipmentEu2Ua = new Paket(16, 60.0, 30.0, 10.0, 10.01, recipientUa, senderEu, false, 7000);	
		FactHandle shipmentEu2ChFactHandle = kSession.insert(shipmentEu2Ch);
		FactHandle shipmentEu2UaFactHandle = kSession.insert(shipmentEu2Ua);
		kSession.fireAllRules();
		kSession.delete(shipmentEu2ChFactHandle);
		kSession.delete(shipmentEu2UaFactHandle);
		
		assertEquals(36.0, shipmentEu2Ch.getVersandkosten());
		assertEquals(36.0, shipmentEu2Ua.getVersandkosten());
	}

	@Test
	void testRuleWeightBetween10And25KFromUaOrChToEu() {
		
		System.out.println("Verification of the shipment of a parcel with less than 25 KG and above 10 KG  from UA or CH to the EU.");

		Person recipientEu = new Person("Susi Sorglos", "AT", "Astraﬂe 1", "Linz", 4040);
		Person senderCh = new Person("Franz Fix", "CH", "Cstraﬂe 5", "Bern", 3000);
		Person senderUa = new Person("Martha Mutig", "UA", "Dstraﬂe 2", "Donezk", 8008);

		Paket shipmentCh2Eu = new Paket(17, 60.0, 30.0, 10.0, 10.01, recipientEu, senderCh, false, 7000);		
		Paket shipmentEu2Ua = new Paket(18, 60.0, 30.0, 10.0, 10.01, recipientEu, senderUa, false, 7000);	
		FactHandle shipmentCh2EuFactHandle = kSession.insert(shipmentCh2Eu);
		FactHandle shipmentUa2EuFactHandle = kSession.insert(shipmentEu2Ua);
		kSession.fireAllRules();
		kSession.delete(shipmentCh2EuFactHandle);
		kSession.delete(shipmentUa2EuFactHandle);
		
		assertEquals(36.0, shipmentCh2Eu.getVersandkosten());
		assertEquals(36.0, shipmentEu2Ua.getVersandkosten());
	}
	
	@Test
	void testRuleWeightAbove25KgFromEuToUaOrCh() {
		
		System.out.println("Verification of the shipment of a parcel with more than 25 KG from the EU to UA or CH.");

		Person senderEu = new Person("Susi Sorglos", "AT", "Astraﬂe 1", "Linz", 4040);
		Person recipientCh = new Person("Franz Fix", "CH", "Cstraﬂe 5", "Bern", 3000);
		Person recipientUa = new Person("Martha Mutig", "UA", "Dstraﬂe 2", "Donezk", 8008);

		Paket shipmentEu2Ch = new Paket(19, 60.0, 30.0, 10.0, 25.01, recipientCh, senderEu, false, 7000);		
		Paket shipmentEu2Ua = new Paket(20, 60.0, 30.0, 10.0, 25.01, recipientUa, senderEu, false, 7000);	
		FactHandle shipmentEu2ChFactHandle = kSession.insert(shipmentEu2Ch);
		FactHandle shipmentEu2UaFactHandle = kSession.insert(shipmentEu2Ua);
		kSession.fireAllRules();
		kSession.delete(shipmentEu2ChFactHandle);
		kSession.delete(shipmentEu2UaFactHandle);
		
		assertEquals(-1.0, shipmentEu2Ch.getVersandkosten());
		assertEquals(-1.0, shipmentEu2Ua.getVersandkosten());
	}

	@Test
	void testRuleWeightAbove25KgFromUaOrChToEu() {
		
		System.out.println("Verification of the shipment of a parcel with more than 25 KG from UA or CH to the EU.");

		Person recipientEu = new Person("Susi Sorglos", "AT", "Astraﬂe 1", "Linz", 4040);
		Person senderCh = new Person("Franz Fix", "CH", "Cstraﬂe 5", "Bern", 3000);
		Person senderUa = new Person("Martha Mutig", "UA", "Dstraﬂe 2", "Donezk", 8008);

		Paket shipmentCh2Eu = new Paket(21, 60.0, 30.0, 10.0, 25.01, recipientEu, senderCh, false, 7000);		
		Paket shipmentEu2Ua = new Paket(22, 60.0, 30.0, 10.0, 25.01, recipientEu, senderUa, false, 7000);	
		FactHandle shipmentCh2EuFactHandle = kSession.insert(shipmentCh2Eu);
		FactHandle shipmentUa2EuFactHandle = kSession.insert(shipmentEu2Ua);
		kSession.fireAllRules();
		kSession.delete(shipmentCh2EuFactHandle);
		kSession.delete(shipmentUa2EuFactHandle);
		
		assertEquals(-1.0, shipmentCh2Eu.getVersandkosten());
		assertEquals(-1.0, shipmentEu2Ua.getVersandkosten());
	}
	
	@Test
	void testRuleShippingInsuranceWithinEu() {
		
		System.out.println("Verification of the shipment insurance of a parcel within the EU.");

		Person senderEu = new Person("Susi Sorglos", "AT", "Astraﬂe 1", "Linz", 4040);
		Person recipientEu = new Person("Hans Huber", "DE", "Dstraﬂe 2", "M¸nchen", 80331);

		Paket shipmentEu = new Paket(23, 60.0, 30.0, 10.0, 9.99, recipientEu, senderEu, true, 100);		
		FactHandle shipmentEuFactHandle = kSession.insert(shipmentEu);
		kSession.fireAllRules();
		kSession.delete(shipmentEuFactHandle);
		
		assertEquals(6.0, shipmentEu.getVersicherungskosten());
	}
	
	@Test
	void testRuleShippingInsuranceFromEuToUaOrCh() {
		
		System.out.println("Verification of the shipment insurance of a parcel from the EU to UA or CH.");

		Person senderEu = new Person("Susi Sorglos", "AT", "Astraﬂe 1", "Linz", 4040);
		Person recipientCh = new Person("Franz Fix", "CH", "Cstraﬂe 5", "Bern", 3000);
		Person recipientUa = new Person("Martha Mutig", "UA", "Dstraﬂe 2", "Donezk", 8008);

		Paket shipmentEu2Ch = new Paket(24, 60.0, 30.0, 10.0, 9.99, recipientCh, senderEu, true, 100);		
		Paket shipmentEu2Ua = new Paket(25, 60.0, 30.0, 10.0, 9.99, recipientUa, senderEu, true, 100);	
		FactHandle shipmentEu2ChFactHandle = kSession.insert(shipmentEu2Ch);
		FactHandle shipmentEu2UaFactHandle = kSession.insert(shipmentEu2Ua);
		kSession.fireAllRules();
		kSession.delete(shipmentEu2ChFactHandle);
		kSession.delete(shipmentEu2UaFactHandle);
		
		assertEquals(20.0, shipmentEu2Ch.getVersicherungskosten());
		assertEquals(20.0, shipmentEu2Ua.getVersicherungskosten());
	}
	
	@Test
	void testRuleShippingInsuranceFromUaOrChToEu() {
		
		System.out.println("Verification of the shipment insurance of a parcel from UA or CH to the EU.");

		Person recipientEu = new Person("Susi Sorglos", "AT", "Astraﬂe 1", "Linz", 4040);
		Person senderCh = new Person("Franz Fix", "CH", "Cstraﬂe 5", "Bern", 3000);
		Person senderUa = new Person("Martha Mutig", "UA", "Dstraﬂe 2", "Donezk", 8008);

		Paket shipmentCh2Eu = new Paket(26, 60.0, 30.0, 10.0, 9.99, recipientEu, senderCh, true, 100);		
		Paket shipmentEu2Ua = new Paket(27, 60.0, 30.0, 10.0, 9.99, recipientEu, senderUa, true, 100);	
		FactHandle shipmentCh2EuFactHandle = kSession.insert(shipmentCh2Eu);
		FactHandle shipmentUa2EuFactHandle = kSession.insert(shipmentEu2Ua);
		kSession.fireAllRules();
		kSession.delete(shipmentCh2EuFactHandle);
		kSession.delete(shipmentUa2EuFactHandle);
		
		assertEquals(20.0, shipmentCh2Eu.getVersicherungskosten());
		assertEquals(20.0, shipmentEu2Ua.getVersicherungskosten());
	}
	
	@Test
	void testRuleExtraFeeForLargeShipments() {
		
		System.out.println("Verification of the shipment extra free for pracels with a belt dimension above 300cm.");

		Person senderEu = new Person("Susi Sorglos", "AT", "Astraﬂe 1", "Linz", 4040);
		Person recipientEu = new Person("Hans Huber", "DE", "Dstraﬂe 2", "M¸nchen", 80331);

		Paket shipmentEu = new Paket(28, 120.0, 80.0, 50.0, 9.99, recipientEu, senderEu, true, 100);		
		FactHandle shipmentEuFactHandle = kSession.insert(shipmentEu);
		kSession.fireAllRules();
		kSession.delete(shipmentEuFactHandle);
		
		assertEquals(16.50, shipmentEu.getVersandkosten());
	}
	
	@Test
	void testRuleAuctionInsuranceWithinEu() {
		
		System.out.println("Verification of the insurance auction for shipments with a value below 100.");

		Person senderEu = new Person("Susi Sorglos", "AT", "Astraﬂe 1", "Linz", 4040);
		Person recipientEu = new Person("Hans Huber", "DE", "Dstraﬂe 2", "M¸nchen", 80331);

		Paket shipmentEu = new Paket(29, 120.0, 80.0, 50.0, 9.99, recipientEu, senderEu, true, 99);		
		FactHandle shipmentEuFactHandle = kSession.insert(shipmentEu);
		kSession.fireAllRules();
		kSession.delete(shipmentEuFactHandle);
		
		assertEquals(0.99, shipmentEu.getVersicherungskosten());
	}
	
	@Test
	void testRuleCustomDutyFromChToUa() {
		
		System.out.println("Verification of extra custom duty for shipments from CH to UA.");

		Person senderCh = new Person("Franz Fix", "CH", "Cstraﬂe 5", "Bern", 3000);
		Person recipientUa = new Person("Martha Mutig", "UA", "Dstraﬂe 2", "Donezk", 8008);

		Paket shipmentCh2Ua = new Paket(30, 60.0, 30.0, 10.0, 9.99, recipientUa, senderCh, true, 100);
		FactHandle shipmentCh2UaFactHandle = kSession.insert(shipmentCh2Ua);
		kSession.fireAllRules();
		kSession.delete(shipmentCh2UaFactHandle);
		
		assertEquals(18.0 + 100 * 0.25, shipmentCh2Ua.getVersandkosten());
	}
	
	@Test
	void testRuleAuctionReducedFeeForSecondParcel() throws ParseException {
		
		System.out.println("Verification of reduced fee for all other parcels except the first parcel form a sender.");
		System.out.println("WARNING: To succeed this test change rule's attributes \"date-effective\" to \"01-may-2021\"!");
		
		Person sender = new Person("Susi Sorglos", "AT", "Astraﬂe 1", "Linz", 4040);
		Person recipient1 = new Person("Hans Huber", "DE", "Dstraﬂe 2", "M¸nchen", 80331);
		Person recipient2 = new Person("Tina Toscana", "DE", "Istraﬂe 1", "Berlin", 20100);

		Paket shipment1 = new Paket(31, 10.0, 20.0, 30.0, 9.99, recipient1, sender, true, 400);
		Paket shipment2 = new Paket(32, 10.0, 20.0, 30.0, 9.99, recipient2, sender, true, 400);
		Paket shipment3 = new Paket(33, 10.0, 20.0, 30.0, 9.99, recipient2, sender, true, 400);
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		shipment1.setDatum(formatter.parse("10-05-2021"));
		shipment2.setDatum(formatter.parse("11-05-2021"));
		shipment3.setDatum(formatter.parse("11-05-2021"));
				
		FactHandle shipment1FactHandle = kSession.insert(shipment1);
		FactHandle shipment2FactHandle = kSession.insert(shipment2);
		FactHandle shipment3FactHandle = kSession.insert(shipment3);
		
		kSession.fireAllRules();
		kSession.delete(shipment1FactHandle);
		kSession.delete(shipment2FactHandle);
		kSession.delete(shipment3FactHandle);
			
		assertEquals(9.00, shipment1.getVersandkosten());	
		assertEquals(9.00 * 0.8, shipment2.getVersandkosten());
		assertEquals(9.00 * 0.8, shipment3.getVersandkosten());
	}
}
