package com.upc.gym_atlas;

import com.upc.gym_atlas.entidades.Cliente;
import com.upc.gym_atlas.negocio.IClienteServicio;
import com.upc.gym_atlas.repositorio.IClienteRepositorio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.crypto.spec.OAEPParameterSpec;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class GymAtlasApplication {

	public static void main(String[] args) {

        SpringApplication.run(GymAtlasApplication.class, args);
	}

    @Bean
    CommandLineRunner init(IClienteServicio clienteServicio) {
        return args -> {

                // CLIENTE 1
                Cliente c1 = new Cliente();
                c1.setDni("12345678");                        // NOT NULL + UNIQUE
                c1.setNombres("Juan");                        // NOT NULL
                c1.setApellidos("Pérez Gómez");               // NOT NULL
                c1.setEmail("juan@example.com");
                c1.setTelefono("987654321");
                c1.setFechaNacimiento(LocalDate.of(1995, 5, 10));
                c1.setDireccion("Av. Siempre Viva 123, Lima");
                c1.setContactoEmergencia("María Pérez - 999888777");
                c1.setNotasMedicas("Alergia a penicilina");
                c1.setFotoUrl("https://example.com/fotos/juan.jpg");

                Cliente creado = clienteServicio.registrar(c1);
                System.out.println("Cliente creado: " + creado);

                // Listar todos los clientes
                System.out.println("Listado de clientes:");
                clienteServicio.listar().forEach(System.out::println);
        };
    }
}
