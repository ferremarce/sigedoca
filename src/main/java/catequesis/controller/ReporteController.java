/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catequesis.controller;

import catequesis.fachada.FichaFacade;
import catequesis.modelo.Ficha;
import catequesis.reportes.FuenteReporte;
import catequesis.reportes.JasperManager;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.JSFutil;

/**
 *
 * @author jmferreira
 */
@Named(value = "ReporteController")
@SessionScoped
public class ReporteController implements Serializable {
    
    @Inject
    FichaFacade fichaFacade;

    /**
     * Creates a new instance of ReporteController
     */
    public ReporteController() {
    }
    
    public void generarReporte(Integer id) throws IOException {
        JasperManager jm = new JasperManager();
        List<Ficha> lista = new ArrayList<>();
        if (id == 0) {
            lista = fichaFacade.findAll();
        } else {
            lista.add(fichaFacade.find(id));
        }
        
        String tipoReporte = "PDF";
        String idFuenteReporte = "1";
        FuenteReporte fr = new FuenteReporte(Integer.valueOf(idFuenteReporte));
        String reportSource = jm.getPathweb() + "reportes/template/" + fr.getNombreReporte();
        jm.generarReporte(reportSource, tipoReporte, lista);
    }
    
    public void generarReporteFicha(List<?> dataList) {
        JasperManager jm = new JasperManager();
        String tipoReporte = "PDF";
        String idFuenteReporte = "1";
        FuenteReporte fr = new FuenteReporte(Integer.valueOf(idFuenteReporte));
        String reportSource = jm.getPathweb() + "reportes/template/" + fr.getNombreReporte();
        if (dataList.size() > 0) {
            jm.generarReporte(reportSource, tipoReporte, dataList);
        } else {
            JSFutil.addErrorMessage("No hay datos para generar el reporte");
        }
        
    }
}
