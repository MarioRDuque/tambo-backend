/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.limatambo.servicio.impl;

import java.util.Date;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.limatambo.dao.GenericoDao;
import pe.limatambo.dto.PedidoDTO;
import pe.limatambo.entidades.Detallepedido;
import pe.limatambo.entidades.Pedido;
import pe.limatambo.entidades.Usuario;
import pe.limatambo.excepcion.GeneralException;
import pe.limatambo.servicio.PedidoServicio;
import pe.limatambo.util.BusquedaPaginada;
import pe.limatambo.util.Criterio;
import pe.limatambo.util.LimatamboUtil;
import pe.limatambo.util.Mensaje;
/**
 *
 * @author dev-out-03
 */

@Service
@Transactional
public class PedidoServicioImp extends GenericoServicioImpl<Pedido, Integer> implements PedidoServicio {

    private final Logger loggerServicio = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private GenericoDao<Pedido, Integer> pedidoDao;
    @Autowired
    private GenericoDao<Detallepedido, Integer> pedidoDetalleDao;

    public PedidoServicioImp(GenericoDao<Pedido, Integer> genericoHibernate) {
        super(genericoHibernate);
    }

    @Override
    public Pedido guardar(Pedido pedido) {
        if(pedido != null){
            pedido.setEstado(Boolean.TRUE);
            pedido = pedidoDao.insertar(pedido);
            loggerServicio.info(pedido.getId()+"");
        }else{
            throw new GeneralException("Evento nulo", Mensaje.CAMPO_OBLIGATORIO_VACIO, loggerServicio);
        }
        return pedido;
    }

    @Override
    public BusquedaPaginada busquedaPaginada(Pedido entidadBuscar, BusquedaPaginada busquedaPaginada, Integer idPedido, Date desde, Date hasta) {
        Criterio filtro;
        filtro = Criterio.forClass(Pedido.class);
        filtro.add(Restrictions.eq("estado", Boolean.TRUE));
        filtro.createAlias("idcliente", "cliente", JoinType.LEFT_OUTER_JOIN);
        filtro.createAlias("cliente.idpersona", "persona", JoinType.LEFT_OUTER_JOIN);
        if (idPedido!= null && idPedido>0) {
            filtro.add(Restrictions.eq("id", idPedido));
        }
        if (LimatamboUtil.sonNoNulos(desde, hasta)) {
            if (desde.before(hasta)) {
                filtro.add(Restrictions.between("fechapedido", desde, hasta));
            }else{
                throw new GeneralException("Las fechas son insconsistentes", Mensaje.ERROR_GENERAL, loggerServicio);
            }
        }
        busquedaPaginada.setTotalRegistros(pedidoDao.cantidadPorCriteria(filtro, "id"));
        busquedaPaginada.calcularCantidadDePaginas();
        busquedaPaginada.validarPaginaActual();
        filtro.setProjection(Projections.projectionList()
                .add(Projections.property("id"), "codigo")
                .add(Projections.property("descripcion"), "descripcion")
                .add(Projections.property("fechapedido"), "fechaEntrega")
                .add(Projections.property("direccion"), "direccion")
                .add(Projections.property("persona.nombrecompleto"), "cliente"));
        filtro.calcularDatosParaPaginacion(busquedaPaginada);
        filtro.addOrder(Order.desc("id"));
        busquedaPaginada.setRegistros(pedidoDao.proyeccionPorCriteria(filtro, PedidoDTO.class));
        return busquedaPaginada;
    }

//    @Override
//    public EventoDTO obtenerDTO(Long id) {
//        EventoDTO dto = new EventoDTO();
//        String[] lineas;
//        Evento Ev = obtener(Evento.class, id);
//        if (Ev!=null) {
//            AgenciaPK pkAgencia = new AgenciaPK(CodigoCmacEnum.CMAC_PAITA.getCodigo(), Ev.getCCodage());
//            Agencia agencia = agenciaDao.obtener(Agencia.class, pkAgencia);
//            dto.setId(Ev.getNIdeven());
//            dto.setFactor(Ev.getNIdfact()!=null ? Ev.getNIdfact().getCDenomi() : "");
//            dto.setDescripcion(Ev.getCDescor());
//            dto.setDescLarga(Ev.getCDeslar());
//            dto.setEvidencia(Ev.getCEviden());
//            dto.setTipoEvento(Ev.getNIdstev()!=null ? Ev.getNIdstev().getCDenomi(): "");
//            dto.setRiesgo(Ev.getNIdtipr()!=null ? Ev.getNIdtipr().getCTipori(): "");
//            dto.setProcesoOrigen(Ev.getNIdacti()!=null ? Ev.getNIdacti().getCDescri() : "");
//            dto.setAgencia(agencia!=null ? agencia.getCDesage(): "");
//            dto.setFechaOcurrencia(Ev.getDFocurr());
//            dto.setFechaCreacion(Ev.getAufecins());
//            dto.setUsuarioRegistra(Ev.getUserId()!=null ? Ev.getUserId().getCNomusu() : Ev.getCPerreg());
//            lineas = new String[Ev.getEventoSubLineasDeNegocioList().size()];
//            for (int i=0; i<Ev.getEventoSubLineasDeNegocioList().size() ;i++) {
//                lineas[i] = Ev.getEventoSubLineasDeNegocioList().get(i).getNIdslne().getCSublne();
//            }
//            dto.setLineaNegocio(lineas);
//        }
//        return dto;
//    }

    @Override
    public Pedido actualizar(Pedido pedido, Usuario usuario) {
        if(pedido.getId()> 0){
            pedido = pedidoDao.actualizar(pedido);
//            if(pedido.getNIdeven() > 0 && usuario!=null){
//                historicoDeCambiosEventosServicio.guardarHistorico(evento, usuario);
//            }
        }else{
            throw new GeneralException("Evento nulo", Mensaje.CAMPO_OBLIGATORIO_VACIO, loggerServicio);
        }
        return pedido;
    }
    
}
