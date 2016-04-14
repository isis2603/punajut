/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.rest.punajut.dtos;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * Clase que contiene los eventos que visitará el viajero en una ciudad determinada
 * @author ls.hernandez10
 */
@XmlRootElement
public class EventoViajeroDTO
{
    /**
     * Evento que cumple con la restriccion de horario de la ciudad y que el viajero ha seleccionado para realizar
     */
    private Long id;
    private String descripcion;
    private String lugar;
    private String tipo;

    private EventoDTO evento;

    //Constructor por defecto
//    public EventoViajeroDTO()
//    {
//
//    }

//    /**
//     * Constructor con parámetros
//     * @param pEvento
//     */
//    public EventoViajeroDTO(EventoDTO pEvento)
//    {
//        super();
//        evento = pEvento;
//    }

    public EventoDTO getEvento() {
        return evento;
    }

    public void setEvento(EventoDTO evento) {
        this.evento = evento;
    }

    @Override
    public String toString()
    {
        return "Tipo:" + evento.getTipo();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Long getId() {
        return id;
    }

    public String getLugar() {
        return lugar;
    }

    public String getTipo() {
        return tipo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }



}

