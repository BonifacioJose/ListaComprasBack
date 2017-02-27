package br.com.listacomprasback.genericdao.interfaces;

import java.io.Serializable;

/**
 *
 * @author Bonifácio
 */
public interface DatabaseEntity extends Serializable {
    
    public String[] pk();
    public boolean isNew();
}
