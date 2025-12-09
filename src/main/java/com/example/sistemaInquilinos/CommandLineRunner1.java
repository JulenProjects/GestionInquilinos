package com.example.sistemaInquilinos;

import com.example.sistemaInquilinos.entidad.*;
import com.example.sistemaInquilinos.repositorio.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CommandLineRunner1 {

    private final usuarioRepositorio usuarioRepositorio;
    private final inquilinoRespositorio inquilinoRepositorio;
    private final inmuebleRepositorio inmuebleRepositorio;
    private final pagosRepositorio pagosRepositorio;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner cargarDatosIniciales() {
        return args -> {

            // ============================
            // ✅ CREAR ADMIN SI NO EXISTE
            // ============================
            if (usuarioRepositorio.findByUsuario("adminPrincipal").isEmpty()) {

                usuario admin = usuario.builder()
                        .usuario("adminPrincipal")
                        .password(passwordEncoder.encode("admin1234"))
                        .role(Role.ADMIN)
                        .build();

                usuarioRepositorio.save(admin);

                System.out.println("✅ Admin creado automáticamente");
            } else {
                System.out.println("ℹ️ El admin ya existe");
            }

            // ============================
            // ✅ CREAR INQUILINOS
            // ============================
            if (inquilinoRepositorio.count() == 0) {

                inquilino i1 = new inquilino(null, "Carlos López", "12345678A", "600111222", "carlos@email.com");
                inquilino i2 = new inquilino(null, "Laura Pérez", "87654321B", "600333444", "laura@email.com");
                inquilino i3 = new inquilino(null, "Miguel Torres", "11223344C", "600555666", "miguel@email.com");

                inquilinoRepositorio.saveAll(List.of(i1, i2, i3));

                System.out.println("✅ Inquilinos creados");
            }

            // ============================
            // ✅ CREAR INMUEBLES
            // ============================
            if (inmuebleRepositorio.count() == 0) {

                List<inquilino> inquilinos = inquilinoRepositorio.findAll();

                inmueble m1 = new inmueble(
                        null,
                        "Calle Mayor 10",
                        "Madrid",
                        "28001",
                        900.0,
                        estadoInmueble.OCUPADO,
                        inquilinos.get(0),
                        null
                );

                inmueble m2 = new inmueble(
                        null,
                        "Avenida Sol 25",
                        "Valencia",
                        "46001",
                        750.0,
                        estadoInmueble.CON_DEUDA,
                        inquilinos.get(1),
                        null
                );

                inmueble m3 = new inmueble(
                        null,
                        "Calle Luna 5",
                        "Sevilla",
                        "41001",
                        600.0,
                        estadoInmueble.VACIO,
                        null,
                        null
                );

                inmuebleRepositorio.saveAll(List.of(m1, m2, m3));

                System.out.println("✅ Inmuebles creados");
            }

            // ============================
            // ✅ CREAR PAGOS
            // ============================
            if (pagosRepositorio.count() == 0) {

                List<inquilino> inquilinos = inquilinoRepositorio.findAll();
                List<inmueble> inmuebles = inmuebleRepositorio.findAll();

                pagos p1 = new pagos(
                        null,
                        2025,
                        1,
                        900.0,
                        0.0,
                        true,
                        inquilinos.get(0),
                        inmuebles.get(0)
                );

                pagos p2 = new pagos(
                        null,
                        2025,
                        1,
                        750.0,
                        750.0,
                        false,
                        inquilinos.get(1),
                        inmuebles.get(1)
                );

                pagos p3 = new pagos(
                        null,
                        2025,
                        2,
                        750.0,
                        1500.0,
                        false,
                        inquilinos.get(1),
                        inmuebles.get(1)
                );

                pagosRepositorio.saveAll(List.of(p1, p2, p3));

                System.out.println("✅ Pagos creados");
            }

            System.out.println("🚀 SISTEMA LISTO PARA SER PROBADO");
        };
    }
}
