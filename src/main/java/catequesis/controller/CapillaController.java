/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catequesis.controller;

import catequesis.fachada.CapillaFacade;
import catequesis.modelo.Capilla;
import catequesis.modelo.Diocesis;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.inject.Inject;
import util.JSFutil;
import util.JSFutil.PersistAction;

/**
 *
 * @author jmferreira
 */
@Named(value = "CapillaController")
@SessionScoped
public class CapillaController implements Serializable {
    
    private static final Logger LOG = Logger.getLogger(CapillaController.class.getName());
    ResourceBundle bundle = ResourceBundle.getBundle("propiedades.bundle", JSFutil.getmyLocale());
    
    @Inject
    CapillaFacade capillaFacade;
    
    private Capilla capilla;
    private List<Capilla> listaCapilla;
    private Diocesis tmpIdDiocesis;

    /**
     * Creates a new instance of CapillaController
     */
    public CapillaController() {
    }

    public Diocesis getTmpIdDiocesis() {
        return tmpIdDiocesis;
    }

    public void setTmpIdDiocesis(Diocesis tmpIdDiocesis) {
        this.tmpIdDiocesis = tmpIdDiocesis;
    }
    
    public Capilla getCapilla() {
        return capilla;
    }
    
    public void setCapilla(Capilla capilla) {
        this.capilla = capilla;
    }
    
    public List<Capilla> getListaCapilla() {
        return listaCapilla;
    }
    
    public void setListaCapilla(List<Capilla> listaCapilla) {
        this.listaCapilla = listaCapilla;
    }
    
    public String doListaForm() {
        this.listaCapilla = new ArrayList<>();
        return "/pages/ListarCapilla";
    }
    
    public String doCrearForm() {
        this.capilla = new Capilla();
        return "/pages/CrearCapilla";
    }
    
    public String doEditarForm(Integer id) {
        this.capilla = capillaFacade.find(id);
        return "/pages/CrearCapilla";
    }
    
    public String doRefrescar() {
        this.listaCapilla = capillaFacade.findAll();
        if (this.listaCapilla.isEmpty()) {
            JSFutil.addErrorMessage("No hay resultados...");
        } else {
            JSFutil.addSuccessMessage(this.listaCapilla.size() + " registros recuperados");
        }
        return "";
    }
    
    public String doGuardar() {
        if (this.capilla.getIdCapilla() != null) {
            persist(PersistAction.UPDATE);
        } else {
            persist(PersistAction.CREATE);
        }
        return doListaForm();
    }
    
    public String doBorrar(Integer id) {
        this.capilla = capillaFacade.find(id);
        persist(PersistAction.DELETE);
        return doListaForm();
    }
    
    private void persist(PersistAction persistAction) {
        try {
            if (persistAction.compareTo(PersistAction.CREATE) == 0) {
                capillaFacade.create(capilla);
            } else if (persistAction.compareTo(PersistAction.UPDATE) == 0) {
                capillaFacade.edit(capilla);
            } else if (persistAction.compareTo(PersistAction.DELETE) == 0) {
                capillaFacade.remove(capilla);
            }
            JSFutil.addSuccessMessage(this.bundle.getString("UpdateSuccess"));
        } catch (EJBException ex) {
            String msg = "";
            Throwable cause = ex.getCause();
            if (cause != null) {
                msg = cause.getLocalizedMessage();
            }
            if (msg.length() > 0) {
                JSFutil.addErrorMessage(msg);
            } else {
                JSFutil.addErrorMessage(ex, this.bundle.getString("UpdateError"));
            }
            LOG.log(Level.SEVERE, null, ex);
        }
    }
    
}
