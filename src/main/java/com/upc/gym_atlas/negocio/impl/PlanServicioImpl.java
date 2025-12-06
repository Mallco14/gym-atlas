package com.upc.gym_atlas.negocio.impl;

import com.upc.gym_atlas.entidades.Plan;
import com.upc.gym_atlas.entidades.enums.EstadoPlan;
import com.upc.gym_atlas.negocio.IPlanServicio;
import com.upc.gym_atlas.repositorio.PlanRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PlanServicioImpl implements IPlanServicio {

    private final PlanRepositorio planRepositorio;

    public PlanServicioImpl(PlanRepositorio planRepositorio) {
        this.planRepositorio = planRepositorio;
    }

    @Override
    public Plan registrar(Plan plan) {
        //Si no se envia estado, por defecto activo
        if(plan.getEstado() == null){
            plan.setEstado(EstadoPlan.activo);
        }
        // accesoClases y accesoMaquinas ya tienen defaults en la entidad
        return planRepositorio.save(plan);
    }

    @Override
    public List<Plan> listar() {
        return planRepositorio.findAll();
    }

    @Override
    public Optional<Plan> obtenerPorId(Integer idPlan) {
        return planRepositorio.findById(idPlan);
    }
    @Override
    public Plan actualizar(Integer idPlan, Plan datos) {
        return planRepositorio.findById(idPlan)
                .map(existente -> {
                    existente.setNombrePlan(datos.getNombrePlan());
                    existente.setDescripcion(datos.getDescripcion());
                    existente.setPrecio(datos.getPrecio());
                    existente.setDuracionDias(datos.getDuracionDias());
                    existente.setAccesoClases(datos.getAccesoClases());
                    existente.setAccesoMaquinas(datos.getAccesoMaquinas());
                    // Puedes decidir si quieres permitir cambiar el estado aquí o solo con cambiarEstado():
                    if (datos.getEstado() != null) {
                        existente.setEstado(datos.getEstado());
                    }
                    return planRepositorio.save(existente);
                })
                .orElse(null);
    }
    @Override
    public void eliminar(Integer idPlan) {
        planRepositorio.deleteById(idPlan);
    }

    @Override
    public Plan cambiarEstado(Integer idPlan, EstadoPlan nuevoEstado) {
        return planRepositorio.findById(idPlan)
                .map(existente -> {
                    existente.setEstado(nuevoEstado);
                    return planRepositorio.save(existente);
                })
                .orElse(null);
    }

    @Override
    public List<Plan> listarPorEstado(EstadoPlan estado) {
        return planRepositorio.findByEstado(estado);
    }

    @Override
    public List<Plan> buscarPorNombre(String nombreParcial) {
        if (nombreParcial == null || nombreParcial.trim().isEmpty()) {
            return planRepositorio.findAll();
        }
        return planRepositorio.findByNombrePlanContainingIgnoreCase(nombreParcial.trim());
    }

    @Override
    public List<Plan> buscarPorPrecioMax(BigDecimal precioMaximo) {
        return planRepositorio.findByPrecioLessThanEqual(precioMaximo);
    }

    @Override
    public List<Plan> buscarPorRangoPrecio(BigDecimal precioMinimo, BigDecimal precioMaximo) {
        return planRepositorio.findByPrecioBetween(precioMinimo, precioMaximo);
    }

    @Override
    public List<Plan> buscarPorDuracionMinima(Integer duracionMinima) {
        return planRepositorio.findByDuracionDiasGreaterThanEqual(duracionMinima);
    }
}
