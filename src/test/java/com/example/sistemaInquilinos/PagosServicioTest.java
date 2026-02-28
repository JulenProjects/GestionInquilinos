package com.example.sistemaInquilinos;

import com.example.sistemaInquilinos.entidad.estadoInmueble;
import com.example.sistemaInquilinos.entidad.pagos;
import com.example.sistemaInquilinos.repositorio.pagosRepositorio;
import com.example.sistemaInquilinos.servicio.pagosServicio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test unitario de pagosServicio (SIN base de datos y SIN arrancar Spring).
 * Aquí comprobamos el comportamiento del servicio simulando el repositorio con Mockito.
 */
@ExtendWith(MockitoExtension.class)
// Activa Mockito en JUnit 5 para que procese @Mock y @InjectMocks automáticamente.
class pagosServicioTest {

    @Mock
    // Crea una "copia falsa" (mock) del repositorio.
    // No accede a MySQL ni a JPA: solo finge respuestas.
    private pagosRepositorio pagosRepositorio;

    @InjectMocks
    // Crea una instancia REAL de pagosServicio e inyecta dentro el mock anterior.
    // Es como: new pagosServicio(pagosRepositorioMock)
    private pagosServicio servicio;

    @Test
        // Marca este método como un test que JUnit debe ejecutar.
    void listarPagos_devuelveLoQueDevuelveElRepositorio() {

        // ARRANGE (preparación):
        // Creamos una lista de ejemplo que queremos que el repositorio "finja" devolver.
        // solo ponemos dos pagos para verificar que devuelve coleccion,
        //podriamos poner mas pero es innecesario
        List<pagos> esperado = List.of(new pagos(), new pagos());

        // Le decimos al mock:
        // "cuando alguien llame a findAll(), devuelve esta lista"
        when(pagosRepositorio.findAll()).thenReturn(esperado);

        // ACT (acción):
        // Llamamos al método real del servicio.
        List<pagos> resultado = servicio.listarPagos();

        // ASSERT (comprobación):
        // Comprobamos que el servicio devuelve exactamente la misma lista.
        // assertSame = “mismo objeto” (muy estricto)
        //assertEquals = “mismo contenido” (lo normal en listas)
        assertEquals(esperado, resultado);
    }

    //optional.of = hay valor
    //optional.empty= no hay valor
    // orElse(null) = si esta vacio devuelve null
    @Test
    void buscarPagoPorId_siExiste_loDevuelve() {
        //Arrange(preparar)
        pagos esperado = new pagos();
        //preparar falso repo
        when(pagosRepositorio.findById(7)).thenReturn(Optional.of(esperado));
        //ACT (ejecutar)
        pagos resultado = servicio.buscarPagoPorId(7);
        //ASSERT(comprobar)
        assertSame(esperado, resultado);
    }

    @Test
    void buscarPagoPorId_siNoExiste_devuelveNull() {
        // ARRANGE: el repo falso dice "no hay pago con id 7"
        when(pagosRepositorio.findById(7)).thenReturn(Optional.empty());

        // ACT: llamo al método real del servicio
        pagos resultado = servicio.buscarPagoPorId(7);

        // ASSERT: si no existe, el servicio devuelve null
        assertNull(resultado);
    }

    @Test
    void calcularDeudaTotalPorInmueble_sumaSoloNoPagadosYNoVacio() {

        // ARRANGE: creo 3 pagos "falsos" (mocks) para controlar lo que devuelven
        pagos p1 = mock(pagos.class); // no pagado + inmueble ocupado => suma
        pagos p2 = mock(pagos.class); // pagado => no suma
        pagos p3 = mock(pagos.class); // no pagado + inmueble vacío => no suma

        // También mockeo inmueble porque el método llama a p.getInmueble().getEstado()
        var inm1 = mock(com.example.sistemaInquilinos.entidad.inmueble.class);
        var inm2 = mock(com.example.sistemaInquilinos.entidad.inmueble.class);
        var inm3 = mock(com.example.sistemaInquilinos.entidad.inmueble.class);

        // Configuro p1 (DEBE SUMAR 500)
        when(p1.getPagado()).thenReturn(false);
        when(p1.getPrecioAlquiler()).thenReturn(500.0);
        when(p1.getInmueble()).thenReturn(inm1);
        when(inm1.getEstado()).thenReturn(estadoInmueble.OCUPADO);

        // Configuro p2 (TAMBIEN SUMA porque NO está pagado)
        when(p2.getPagado()).thenReturn(false);
        when(p2.getPrecioAlquiler()).thenReturn(500.0);
        when(p2.getInmueble()).thenReturn(inm2);
        when(inm2.getEstado()).thenReturn(estadoInmueble.OCUPADO);

        // Configuro p3 (NO SUMA porque inmueble está VACIO)
        when(p3.getPagado()).thenReturn(false);
        when(p3.getInmueble()).thenReturn(inm3);
        when(inm3.getEstado()).thenReturn(estadoInmueble.VACIO);

        // El repo falso devuelve esos 3 pagos para el inmueble 10
        when(pagosRepositorio.findByInmuebleIdInmueble(10)).thenReturn(List.of(p1, p2, p3));

        // ACT: llamo al método real
        Double deuda = servicio.calcularDeudaTotalPorInmueble(10);

        // ASSERT: solo suma p1 => 500
        assertEquals(1000.0, deuda);
    }






}

