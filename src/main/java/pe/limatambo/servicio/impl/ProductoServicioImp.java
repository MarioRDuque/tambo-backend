/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.limatambo.servicio.impl;

import java.util.List;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.limatambo.dao.GenericoDao;
import pe.limatambo.entidades.Producto;
import pe.limatambo.entidades.Productomedida;
import pe.limatambo.entidades.ProductomedidaPK;
import pe.limatambo.entidades.Unidadmedida;
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
    @Autowired
    private GenericoDao<Unidadmedida, Integer> unidaDao;
    @Autowired
    private GenericoDao<Productomedida, ProductomedidaPK> productoMedidaDao;

    public ProductoServicioImp(GenericoDao<Producto, Integer> genericoHibernate) {
        super(genericoHibernate);
    }

    @Override
    public BusquedaPaginada busquedaPaginada(Producto entidadBuscar, BusquedaPaginada busquedaPaginada, String idProducto, int idCategoria) {
        Criterio filtro;
        filtro = Criterio.forClass(Producto.class);
        filtro.add(Restrictions.eq("estado", Boolean.TRUE));
        if (idProducto!= null) {
            filtro.add(Restrictions.ilike("nombre", '%'+idProducto+'%'));
        }
        if (idCategoria>0) {
            filtro.add(Restrictions.eq("idcategoria.id", idCategoria));
        }
        busquedaPaginada.setTotalRegistros(productoDao.cantidadPorCriteria(filtro, "id"));
        busquedaPaginada.calcularCantidadDePaginas();
        busquedaPaginada.validarPaginaActual();
        filtro.calcularDatosParaPaginacion(busquedaPaginada);
        filtro.addOrder(Order.asc("nombre"));
        busquedaPaginada.setRegistros(productoDao.buscarPorCriteriaSinProyecciones(filtro));
//        List<Producto> pList = (List<Producto>) busquedaPaginada.getRegistros();
//        busquedaPaginada.setRegistros(pList);
        return busquedaPaginada;
    }

    @Override
    public Producto insertar(Producto entidad) throws GeneralException{
        entidad.setEstado(Boolean.TRUE);
        List<Productomedida> productoMedidas = entidad.getProductoMedidaList();
        entidad.setProductoMedidaList(null);
        entidad = productoDao.insertar(entidad);
        if(productoMedidas != null){
            for (Productomedida pm : productoMedidas) {
                ProductomedidaPK pk = new ProductomedidaPK(entidad.getId(), pm.getUnidadmedida().getId());
                pm.setProductomedidaPK(pk);
                productoMedidaDao.insertar(pm);
            }
        }
        entidad.setProductoMedidaList(productoMedidas);
        return entidad;
    }

    @Override
    public Producto actualizar(Producto producto) throws GeneralException {
        List<Productomedida> productoMedidas = producto.getProductoMedidaList();
        if(productoMedidas != null){
            productoMedidas.stream().forEach((pm) -> {
                ProductomedidaPK pk = new ProductomedidaPK(producto.getId(), pm.getUnidadmedida().getId());
                pm.setProductomedidaPK(pk);
                productoMedidaDao.actualizar(pm);
            });
        }
        return productoDao.actualizar(producto);
    }
    
}
