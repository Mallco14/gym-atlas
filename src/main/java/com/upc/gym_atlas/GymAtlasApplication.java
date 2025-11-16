package com.upc.gym_atlas;

import com.upc.gym_atlas.entidades.Cliente;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GymAtlasApplication {

	public static void main(String[] args) {

        SpringApplication.run(GymAtlasApplication.class, args);
	}
    @Bean
    CommandLineRunner init(IClienteServicio clienteServicio) {
        return args -> {
            // Crear un cliente de prueba
            Cliente c = new Cliente();
            c.setNombre("Juan Pérez");
            c.setDni("12345678");
            c.setTelefono("987654321");
            c.setActivo(true);

            Cliente creado = clienteServicio.registrar(c);
            System.out.println("Cliente creado: " + creado);

            // Listar todos los clientes
            System.out.println("Listado de clientes:");
            clienteServicio.listar().forEach(System.out::println);
        };
    }
}
