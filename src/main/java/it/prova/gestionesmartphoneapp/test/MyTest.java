package it.prova.gestionesmartphoneapp.test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import it.prova.gestionesmartphoneapp.dao.EntityManagerUtil;
import it.prova.gestionesmartphoneapp.model.App;
import it.prova.gestionesmartphoneapp.model.Smartphone;
import it.prova.gestionesmartphoneapp.service.AppService;
import it.prova.gestionesmartphoneapp.service.MyServiceFactory;
import it.prova.gestionesmartphoneapp.service.SmartphoneService;

public class MyTest {

	public static void main(String[] args) {
		SmartphoneService smartphoneServiceInstance = MyServiceFactory.getSmartphoneServiceInstance();
		AppService appServiceInstance = MyServiceFactory.getAppServiceInstance();

		try {

			System.out.println("In tabella App ci sono " + appServiceInstance.listAll().size() + " elementi.");
			System.out.println(
					"In tabella Smartphone ci sono " + smartphoneServiceInstance.listAll().size() + " elementi.");
			System.out.println(
					"**************************** inizio batteria di test ********************************************");
			System.out.println(
					"*************************************************************************************************");

			// testInserimentoNuovoSmartphone(smartphoneServiceInstance);
			// testAggiornamentoVersioneOsSmartphone(smartphoneServiceInstance);
			// testInserimentoNuovaApp(appServiceInstance);
			// testModificaECheckDateApp(appServiceInstance);
			// testInstallaAppSuSmartphone(smartphoneServiceInstance, appServiceInstance);
			testRimozioneSmartphoneConAppInstallate(smartphoneServiceInstance);

			//testDisinstallaAppSuSmartphone(smartphoneServiceInstance, appServiceInstance);
			System.out.println(
					"****************************** fine batteria di test ********************************************");
			System.out.println(
					"*************************************************************************************************");
			System.out.println("In tabella App ci sono " + appServiceInstance.listAll().size() + " elementi.");
			System.out.println(
					"In tabella Smartphone ci sono " + smartphoneServiceInstance.listAll().size() + " elementi.");

		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			// questa è necessaria per chiudere tutte le connessioni quindi rilasciare il
			// main
			EntityManagerUtil.shutdown();
		}

	}

	private static void testInserimentoNuovoSmartphone(SmartphoneService smartphoneServiceInstance) throws Exception {
		System.out.println(".......testInserimentoNuovoSmartphone inizio.............");

		Smartphone smartphoneInstance = new Smartphone("Samsung", "Galaxys23", 800, 6);
		smartphoneServiceInstance.inserisciNuovo(smartphoneInstance);
		if (smartphoneInstance.getId() == null)
			throw new RuntimeException("testInserimentoNuovoSmartphone fallito ");

		System.out.println(".......testInserimentoNuovoSmartphone fine: PASSED.............");

	}

	private static void testAggiornamentoVersioneOsSmartphone(SmartphoneService smartphoneServiceInstance)
			throws Exception {
		System.out.println(".......testAggiornamentoVersioneOsSmartphone inizio.............");

		List<Smartphone> listaCellulari = smartphoneServiceInstance.listAll();
		Smartphone daAggiornare = listaCellulari.get(0);

		daAggiornare.setVersioneOS(8);

		smartphoneServiceInstance.aggiorna(daAggiornare);

		List<Smartphone> listaAtletiFinale = smartphoneServiceInstance.listAll();

		System.out.println(listaAtletiFinale.get(0));

		System.out.println(".......testAggiornamentoVersioneOsSmartphone fine: PASSED.............");

	}

	private static void testInserimentoNuovaApp(AppService appServiceInstance) throws Exception {
		System.out.println(".......testInserimentoNuovaApp inizio.............");

		App appInstance = new App("JustEat", LocalDate.parse("2022-04-08"), LocalDate.parse("2022-05-09"), 5);
		appServiceInstance.inserisciNuovo(appInstance);
		if (appInstance.getId() == null)
			throw new RuntimeException("testInserimentoNuovaApp fallito ");

		System.out.println(".......testInserimentoNuovaApp fine: PASSED.............");

	}

	private static void testModificaECheckDateApp(AppService appServiceInstance) throws Exception {
		System.out.println(".......testModificaECheckDateApp inizio.............");

		App appInstance = new App("Microsoft Office", LocalDate.parse("2022-09-24"), LocalDate.parse("2022-10-25"), 8);
		appServiceInstance.inserisciNuovo(appInstance);
		if (appInstance.getId() == null)
			throw new RuntimeException("testModificaECheckDateApp fallito ");

		// ora mi salvo da parte le date di creazione ed update
		LocalDateTime createDateTimeIniziale = appInstance.getCreateDateTime();
		LocalDateTime updateDateTimeIniziale = appInstance.getUpdateDateTime();

		// Se ho errori continui commentare le due righe successive!

		if (!createDateTimeIniziale.equals(updateDateTimeIniziale))
			throw new RuntimeException("testModificaECheckDateApp fallito: le date non coincidono ");

		// ora modifico il record
		appInstance.setVersione(9);
		appServiceInstance.aggiorna(appInstance);

		// se la nuova data aggiornamento risulta precedente a quella iniziale: errore
		if (appInstance.getUpdateDateTime().isAfter(updateDateTimeIniziale))
			throw new RuntimeException("testModificaECheckDateApp fallito: le date di modifica sono disallineate ");

		// la data creazione deve essere uguale a quella di prima
		if (!appInstance.getCreateDateTime().equals(createDateTimeIniziale))
			throw new RuntimeException("testModificaECheckDateApp fallito: la data di creazione è cambiata ");

		System.out.println(".......testModificaECheckDateApp fine: PASSED.............");
	}

	private static void testInstallaAppSuSmartphone(SmartphoneService smartphoneServiceInstance,
			AppService appServiceInstance) throws Exception {
		System.out.println("........ testInstallaAppSuSmartphone inizio........");

		List<Smartphone> listaCellulari = smartphoneServiceInstance.listAll();
		Smartphone daCollegare = listaCellulari.get(0);

		if (daCollegare.getId() == null)
			throw new RuntimeException("testInstallaAppSuSmartphone fallito: non riesco a trovare nessun cellulare! ");

		List<App> elencoApp = appServiceInstance.listAll();
		App daInstallare = elencoApp.get(0 + 1);

		smartphoneServiceInstance.aggiungiApp(daCollegare, daInstallare);

		System.out.println("...........testInstallaAppSuSmartphone fine: PASSED...........");

	}

	private static void testDisinstallaAppSuSmartphone(SmartphoneService smartphoneServiceInstance,
			AppService appServiceInstance) throws Exception {
		System.out.println("........ testDisinstallaAppSuSmartphone inizio........");

		List<Smartphone> listaCellulari = smartphoneServiceInstance.listAll();
		Smartphone daScollegare = listaCellulari.get(0);

		if (daScollegare.getId() == null)
			throw new RuntimeException(
					"testDisinstallaAppSuSmartphone fallito: non riesco a trovare nessun cellulare! ");

		List<App> elencoApp = appServiceInstance.listAll();
		App daDisinstallare = elencoApp.get(0);

		smartphoneServiceInstance.disinstallaAppDaCellulare(daDisinstallare.getId());

		System.out.println("...........testDisinstallaAppSuSmartphone fine: PASSED...........");

	}
	
	
	
	private static void testRimozioneSmartphoneConAppInstallate(SmartphoneService smartphoneServiceInstance) throws Exception{
		System.out.println("........ testRimozioneSmartphoneConAppInstallate inizio........");
		
		List<Smartphone> listaCellulari = smartphoneServiceInstance.listAll();
		Smartphone daEliminare = listaCellulari.get(0);

		if (daEliminare.getId() == null)
			throw new RuntimeException(
					"testRimozioneSmartphoneConAppInstallate fallito: non riesco a trovare nessun cellulare! ");
		
		smartphoneServiceInstance.rimuoviSmartphoneConAppInstallate(daEliminare);
		
		System.out.println("...........testRimozioneSmartphoneConAppInstallate fine: PASSED..............");

	}
}
