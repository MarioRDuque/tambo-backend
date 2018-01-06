/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.limatambo.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 *
 * @author MarioMario
 */
@Data
@Entity
@Table(name = "producto")
public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "idpresentacion")
    private Integer idpresentacion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "costopromedio")
    private BigDecimal costopromedio;
    @Column(name = "precioventa")
    private BigDecimal precioventa;
    @Size(max = 12)
    @Column(name = "ctacontable")
    private String ctacontable;
    @Column(name = "stockmin")
    private BigDecimal stockmin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "afectoigv")
    private boolean afectoigv;
    @Size(max = 2147483647)
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "estado")
    private Boolean estado;
    @JoinColumn(name = "idunidad", referencedColumnName = "id")
    @ManyToOne
    private Unidadmedida idunidad;

    public Producto() {
    }

    public Producto(Integer id) {
        this.id = id;
    }

    public Producto(Integer id, String nombre, boolean afectoigv) {
        this.id = id;
        this.nombre = nombre;
        this.afectoigv = afectoigv;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "pe.limatambo.entidades.Producto[ id=" + id + " ]";
    }
    
}
