/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catequesis.fachada;

import catequesis.modelo.Ficha;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jmferreira
 */
@Stateless
public class FichaFacade extends AbstractFacade<Ficha> {

    @PersistenceContext(unitName = "com.jmfa_catequesis_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FichaFacade() {
        super(Ficha.class);
    }
    
}
