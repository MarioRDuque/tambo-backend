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
@Table(name = "pagotarjeta")
public class Pagotarjeta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "iddocumentocab")
    private int iddocumentocab;
    @Column(name = "idbanco")
    private Integer idbanco;
    @Column(name = "idtipotarjcred")
    private Integer idtipotarjcred;
    @Size(max = 10)
    @Column(name = "numoperacion")
    private String numoperacion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "importe")
    private BigDecimal importe;
    @Column(name = "estado")
    private Boolean estado;

    public Pagotarjeta() {
    }

    public Pagotarjeta(Integer id) {
        this.id = id;
    }

    public Pagotarjeta(Integer id, int iddocumentocab) {
        this.id = id;
        this.iddocumentocab = iddocumentocab;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Pagotarjeta)) {
            return false;
        }
        Pagotarjeta other = (Pagotarjeta) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "pe.limatambo.entidades.Pagotarjeta[ id=" + id + " ]";
    }
    
}
