/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.punajut.ejbs;

import co.edu.uniandes.punajut.api.IItinerarioLogic;
import co.edu.uniandes.punajut.api.IViajeroLogic;
import co.edu.uniandes.punajut.api.IVisitaCiudadLogic;
import co.edu.uniandes.punajut.entities.ItinerarioEntity;
import co.edu.uniandes.punajut.entities.ViajeroEntity;
import co.edu.uniandes.punajut.exceptions.BusinessLogicException;
import co.edu.uniandes.punajut.persistence.ItinerarioPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;


/**
 *
 * @author mi.arevalo10
 */
@Stateless
public class ItinerarioLogic implements IItinerarioLogic
{
    private static final Logger logger = Logger.getLogger(ItinerarioLogic.class.getName());

    @Inject
    private ItinerarioPersistence persistence;

//    @Inject
//    private IVisitaCiudadLogic visitaCiudad;

    @Inject
    private IViajeroLogic viajeroLogic;

    @Override
    public List<ItinerarioEntity> getItinerarios(Long idViajero) throws BusinessLogicException{
        logger.info("Inicia proceso de consultar todos los itinerarios");
        ViajeroEntity v = viajeroLogic.getViajero(idViajero);
        List<ItinerarioEntity> itinerarios = v.getItiverarios();
        logger.info("Termina proceso de consultar todos los itinerarios de un viajero");
        return itinerarios;
    }


    @Override
    public ItinerarioEntity getItinerario(Long idItinerario, Long idViajero) throws BusinessLogicException {
        logger.log(Level.INFO, "Inicia proceso de consultar itinerario con id={0}", idItinerario);
        ItinerarioEntity itinerario = persistence.find(idItinerario, idViajero);
        if (itinerario == null) {
            logger.log(Level.SEVERE, "El itinerario con el id {0} no existe", idItinerario);
            throw new BusinessLogicException("El itinerario solicitado no existe");
        }
        logger.log(Level.INFO, "Termina proceso de consultar itinerario con id={0}", idItinerario);
        return itinerario;
    }

    @Override
    public ItinerarioEntity createItinerario(ItinerarioEntity entity) {
        logger.info("Inicia proceso de creación de un itinerario");
        persistence.create(entity);
        logger.info("Termina proceso de creación de un itinerario");
        return entity;
    }

    @Override
    public ItinerarioEntity updateItinerario(ItinerarioEntity entity) {
        logger.log(Level.INFO, "Inicia proceso de actualizar itinerario con id={0}", entity.getId());
        ItinerarioEntity newEntity = persistence.update(entity);
        logger.log(Level.INFO, "Termina proceso de actualizar itinerario con id={0}", entity.getId());
        return newEntity;
    }

    @Override
    public void deleteItinerario(Long id) {
        logger.log(Level.INFO, "Inicia proceso de borrar itinerario con id={0}", id);
        persistence.delete(id);
        logger.log(Level.INFO, "Termina proceso de borrar itinerario con id={0}", id);
    }

}
