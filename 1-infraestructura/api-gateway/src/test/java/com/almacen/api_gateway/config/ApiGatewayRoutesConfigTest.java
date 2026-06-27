package com.almacen.api_gateway.config;

import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("unchecked")
class ApiGatewayRoutesConfigTest {

    @Test
    void debeExistirRutaMsVentas() {
        // ARRANGE: cargar el archivo application.yaml desde resources
        Yaml yaml = new Yaml();

        InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream("application.yaml");

        assertNotNull(inputStream, "No se encontró el archivo application.yaml");

        Map<String, Object> config = yaml.load(inputStream);

        // ACT: navegar dentro del YAML hasta llegar a las rutas del gateway
        Map<String, Object> spring = (Map<String, Object>) config.get("spring");
        Map<String, Object> cloud = (Map<String, Object>) spring.get("cloud");
        Map<String, Object> gateway = (Map<String, Object>) cloud.get("gateway");
        Map<String, Object> server = (Map<String, Object>) gateway.get("server");
        Map<String, Object> webflux = (Map<String, Object>) server.get("webflux");

        List<Map<String, Object>> routes =
                (List<Map<String, Object>>) webflux.get("routes");

        // ASSERT: verificar que exista la ruta ms-ventas con su path correcto
        boolean existeRutaMsVentas = routes.stream().anyMatch(route -> {
            String id = (String) route.get("id");
            List<String> predicates = (List<String>) route.get("predicates");

            return "ms-ventas".equals(id)
                    && predicates.contains("Path=/api/ventas/**");
        });

        assertTrue(existeRutaMsVentas,
                "Debe existir la ruta ms-ventas con Path=/api/ventas/**");

        // VERIFY: no aplica Mockito porque este test no usa mocks.
    }

    @Test
    void debeExistirRutaMsProductos() {
        // ARRANGE: cargar el archivo application.yaml desde resources
        Yaml yaml = new Yaml();

        InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream("application.yaml");

        assertNotNull(inputStream, "No se encontró el archivo application.yaml");

        Map<String, Object> config = yaml.load(inputStream);

        // ACT: navegar dentro del YAML hasta llegar a las rutas del gateway
        Map<String, Object> spring = (Map<String, Object>) config.get("spring");
        Map<String, Object> cloud = (Map<String, Object>) spring.get("cloud");
        Map<String, Object> gateway = (Map<String, Object>) cloud.get("gateway");
        Map<String, Object> server = (Map<String, Object>) gateway.get("server");
        Map<String, Object> webflux = (Map<String, Object>) server.get("webflux");

        List<Map<String, Object>> routes =
                (List<Map<String, Object>>) webflux.get("routes");

        // ASSERT: verificar que exista la ruta ms-productos con su path correcto
        boolean existeRutaMsProductos = routes.stream().anyMatch(route -> {
            String id = (String) route.get("id");
            List<String> predicates = (List<String>) route.get("predicates");

            return "ms-productos".equals(id)
                    && predicates.contains("Path=/api/productos/**");
        });

        assertTrue(existeRutaMsProductos,
                "Debe existir la ruta ms-productos con Path=/api/productos/**");

        // VERIFY: no aplica Mockito porque este test no usa mocks.
    }

    @Test
    void debeExistirRutaMsClientes() {
        // ARRANGE: cargar el archivo application.yaml desde resources
        Yaml yaml = new Yaml();

        InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream("application.yaml");

        assertNotNull(inputStream, "No se encontró el archivo application.yaml");

        Map<String, Object> config = yaml.load(inputStream);

        // ACT: navegar dentro del YAML hasta llegar a las rutas del gateway
        Map<String, Object> spring = (Map<String, Object>) config.get("spring");
        Map<String, Object> cloud = (Map<String, Object>) spring.get("cloud");
        Map<String, Object> gateway = (Map<String, Object>) cloud.get("gateway");
        Map<String, Object> server = (Map<String, Object>) gateway.get("server");
        Map<String, Object> webflux = (Map<String, Object>) server.get("webflux");

        List<Map<String, Object>> routes =
                (List<Map<String, Object>>) webflux.get("routes");

        // ASSERT: verificar que exista la ruta ms-clientes con su path correcto
        boolean existeRutaMsClientes = routes.stream().anyMatch(route -> {
            String id = (String) route.get("id");
            List<String> predicates = (List<String>) route.get("predicates");

            return "ms-clientes".equals(id)
                    && predicates.contains("Path=/api/clientes/**");
        });

        assertTrue(existeRutaMsClientes,
                "Debe existir la ruta ms-clientes con Path=/api/clientes/**");

        // VERIFY: no aplica Mockito porque este test no usa mocks.
    }

    @Test
    void debeExistirRutaMsUsuarios() {
        // ARRANGE: cargar el archivo application.yaml desde resources
        Yaml yaml = new Yaml();

        InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream("application.yaml");

        assertNotNull(inputStream, "No se encontró el archivo application.yaml");

        Map<String, Object> config = yaml.load(inputStream);

        // ACT: navegar dentro del YAML hasta llegar a las rutas del gateway
        Map<String, Object> spring = (Map<String, Object>) config.get("spring");
        Map<String, Object> cloud = (Map<String, Object>) spring.get("cloud");
        Map<String, Object> gateway = (Map<String, Object>) cloud.get("gateway");
        Map<String, Object> server = (Map<String, Object>) gateway.get("server");
        Map<String, Object> webflux = (Map<String, Object>) server.get("webflux");

        List<Map<String, Object>> routes =
                (List<Map<String, Object>>) webflux.get("routes");

        // ASSERT: verificar que exista la ruta ms-usuarios con su path correcto
        boolean existeRutaMsUsuarios = routes.stream().anyMatch(route -> {
                String id = (String) route.get("id");
                List<String> predicates = (List<String>) route.get("predicates");

                return "ms-usuarios".equals(id)
                        && predicates.contains("Path=/api/usuarios/**");
        });

        assertTrue(existeRutaMsUsuarios,
                "Debe existir la ruta ms-usuarios con Path=/api/usuarios/**");

        // VERIFY: no aplica Mockito porque este test no usa mocks.
    }

    @Test
    void debeExistirRutaMsAuth() {
        // ARRANGE: cargar el archivo application.yaml desde resources
        Yaml yaml = new Yaml();

        InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream("application.yaml");

        assertNotNull(inputStream, "No se encontró el archivo application.yaml");

        Map<String, Object> config = yaml.load(inputStream);

        // ACT: navegar dentro del YAML hasta llegar a las rutas del gateway
        Map<String, Object> spring = (Map<String, Object>) config.get("spring");
        Map<String, Object> cloud = (Map<String, Object>) spring.get("cloud");
        Map<String, Object> gateway = (Map<String, Object>) cloud.get("gateway");
        Map<String, Object> server = (Map<String, Object>) gateway.get("server");
        Map<String, Object> webflux = (Map<String, Object>) server.get("webflux");

        List<Map<String, Object>> routes =
                (List<Map<String, Object>>) webflux.get("routes");

        // ASSERT: verificar que exista la ruta ms-auth con su path correcto
        boolean existeRutaMsAuth = routes.stream().anyMatch(route -> {
                String id = (String) route.get("id");
                List<String> predicates = (List<String>) route.get("predicates");

                return "ms-auth".equals(id)
                        && predicates.contains("Path=/api/auth/**");
        });

        assertTrue(existeRutaMsAuth,
                "Debe existir la ruta ms-auth con Path=/api/auth/**");

        // VERIFY: no aplica Mockito porque este test no usa mocks.
    }

    @Test
    void debeExistirRutaMsStock() {
        // ARRANGE: cargar el archivo application.yaml desde resources
        Yaml yaml = new Yaml();

        InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream("application.yaml");

        assertNotNull(inputStream, "No se encontró el archivo application.yaml");

        Map<String, Object> config = yaml.load(inputStream);

        // ACT: navegar dentro del YAML hasta llegar a las rutas del gateway
        Map<String, Object> spring = (Map<String, Object>) config.get("spring");
        Map<String, Object> cloud = (Map<String, Object>) spring.get("cloud");
        Map<String, Object> gateway = (Map<String, Object>) cloud.get("gateway");
        Map<String, Object> server = (Map<String, Object>) gateway.get("server");
        Map<String, Object> webflux = (Map<String, Object>) server.get("webflux");

        List<Map<String, Object>> routes =
                (List<Map<String, Object>>) webflux.get("routes");

        // ASSERT: verificar que exista la ruta ms-stock con su path correcto
        boolean existeRutaMsStock = routes.stream().anyMatch(route -> {
                String id = (String) route.get("id");
                List<String> predicates = (List<String>) route.get("predicates");

                return "ms-stock".equals(id)
                        && predicates.contains("Path=/api/stock/**");
        });

        assertTrue(existeRutaMsStock,
                "Debe existir la ruta ms-stock con Path=/api/stock/**");

        // VERIFY: no aplica Mockito porque este test no usa mocks.
    }

}