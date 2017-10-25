/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.limatambo.servicio.impl;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.limatambo.dao.GenericoDao;
import pe.limatambo.entidades.Producto;
import pe.limatambo.excepcion.GeneralException;
import pe.limatambo.servicio.ProductoServicio;
import pe.limatambo.util.BusquedaPaginada;
import pe.limatambo.util.Criterio;
/**
 *
 * @author dev-out-03
 */

@Service
@Transactional
public class ProductoServicioImp extends GenericoServicioImpl<Producto, Integer> implements ProductoServicio {

    private final Logger loggerServicio = LoggerFactory.getLogger(getClass());
    @Autowired
    private GenericoDao<Producto, Integer> productoDao;

    public ProductoServicioImp(GenericoDao<Producto, Integer> genericoHibernate) {
        super(genericoHibernate);
    }

    @Override
    public BusquedaPaginada busquedaPaginada(Producto entidadBuscar, BusquedaPaginada busquedaPaginada, String idProducto) {
        Criterio filtro;
        filtro = Criterio.forClass(Producto.class);
        filtro.add(Restrictions.eq("estado", Boolean.TRUE));
        if (idProducto!= null) {
            filtro.add(Restrictions.eq("id", idProducto));
        }
        busquedaPaginada.setTotalRegistros(productoDao.cantidadPorCriteria(filtro, "id"));
        busquedaPaginada.calcularCantidadDePaginas();
        busquedaPaginada.validarPaginaActual();
        filtro.calcularDatosParaPaginacion(busquedaPaginada);
        filtro.addOrder(Order.desc("id"));
        busquedaPaginada.setRegistros(productoDao.buscarPorCriteriaSinProyecciones(filtro));
        return busquedaPaginada;
    }

    @Override
    public Producto insertar(Producto entidad) throws GeneralException{
        entidad.setEstado(Boolean.TRUE);
        return productoDao.insertar(entidad);
    }
    
}
