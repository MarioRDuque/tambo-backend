/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.limatambo.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author MarioMario
 */
@Data
@Entity
@Table(name = "productomedida")
public class Productomedida implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProductomedidaPK productomedidaPK;
    @JoinColumn(name = "idunidadmedida", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Unidadmedida unidadmedida;
    @Column(name = "idproducto", insertable = false, updatable = false)
    private Integer idproducto;

    public Productomedida() {
    }

    public Productomedida(ProductomedidaPK productomedidaPK) {
        this.productomedidaPK = productomedidaPK;
    }

    public Productomedida(int idproducto, int idunidadmedida) {
        this.productomedidaPK = new ProductomedidaPK(idproducto, idunidadmedida);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productomedidaPK != null ? productomedidaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Productomedida)) {
            return false;
        }
        Productomedida other = (Productomedida) object;
        return !((this.productomedidaPK == null && other.productomedidaPK != null) || (this.productomedidaPK != null && !this.productomedidaPK.equals(other.productomedidaPK)));
    }

    @Override
    public String toString() {
        return "pe.limatambo.entidades.Productomedida[ productomedidaPK=" + productomedidaPK + " ]";
    }
    
}
