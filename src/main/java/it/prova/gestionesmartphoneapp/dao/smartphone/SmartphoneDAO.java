package it.prova.gestionesmartphoneapp.dao.smartphone;

import it.prova.gestionesmartphoneapp.dao.IBaseDAO;
import it.prova.gestionesmartphoneapp.model.Smartphone;

public interface SmartphoneDAO extends IBaseDAO<Smartphone> {
	public void unlinkAppBySmartphone(Long idSmartphoneinput) throws Exception;
	public void uninstallApps(Smartphone smartphoneInstance) throws Exception;
}
