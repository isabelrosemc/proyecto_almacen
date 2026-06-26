package com.almacen.api_gateway.config;

import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void debeExistirRutaMsCompras() {
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

        // ASSERT: verificar que exista la ruta ms-compras con su path correcto
        boolean existeRutaMsCompras = routes.stream().anyMatch(route -> {
                String id = (String) route.get("id");
                List<String> predicates = (List<String>) route.get("predicates");

                return "ms-compras".equals(id)
                        && predicates.contains("Path=/api/compras/**");
        });

        assertTrue(existeRutaMsCompras,
                "Debe existir la ruta ms-compras con Path=/api/compras/**");

        // VERIFY: no aplica Mockito porque este test no usa mocks.
    }

    @Test
    void debeExistirRutaMsPagos() {
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

        // ASSERT: verificar que exista la ruta ms-pagos con su path correcto
        boolean existeRutaMsPagos = routes.stream().anyMatch(route -> {
                String id = (String) route.get("id");
                List<String> predicates = (List<String>) route.get("predicates");

                return "ms-pagos".equals(id)
                        && predicates.contains("Path=/api/pagos/**");
        });

        assertTrue(existeRutaMsPagos,
                "Debe existir la ruta ms-pagos con Path=/api/pagos/**");

        // VERIFY: no aplica Mockito porque este test no usa mocks.
    }

    @Test
    void debeExistirRutaMsReportes() {
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

        // ASSERT: verificar que exista la ruta ms-reportes con su path correcto
        boolean existeRutaMsReportes = routes.stream().anyMatch(route -> {
                String id = (String) route.get("id");
                List<String> predicates = (List<String>) route.get("predicates");

                return "ms-reportes".equals(id)
                        && predicates.contains("Path=/api/reportes/**");
        });

        assertTrue(existeRutaMsReportes,
                "Debe existir la ruta ms-reportes con Path=/api/reportes/**");

        // VERIFY: no aplica Mockito porque este test no usa mocks.
    }

    @Test
    void debeExistirRutaMsCategorias() {
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

        // ASSERT: verificar que exista la ruta ms-categorias con su path correcto
        boolean existeRutaMsCategorias = routes.stream().anyMatch(route -> {
                String id = (String) route.get("id");
                List<String> predicates = (List<String>) route.get("predicates");

                return "ms-categorias".equals(id)
                        && predicates.contains("Path=/api/categorias/**");
        });

        assertTrue(existeRutaMsCategorias,
                "Debe existir la ruta ms-categorias con Path=/api/categorias/**");

        // VERIFY: no aplica Mockito porque este test no usa mocks.
    }

    @Test
    void debeExistirRutaMsProveedores() {
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

        // ASSERT: verificar que exista la ruta ms-proveedores con su path correcto
        boolean existeRutaMsProveedores = routes.stream().anyMatch(route -> {
                String id = (String) route.get("id");
                List<String> predicates = (List<String>) route.get("predicates");

                return "ms-proveedores".equals(id)
                        && predicates.contains("Path=/api/proveedores/**");
        });

        assertTrue(existeRutaMsProveedores,
                "Debe existir la ruta ms-proveedores con Path=/api/proveedores/**");

        // VERIFY: no aplica Mockito porque este test no usa mocks.
    }

    @Test
    void debeExistirRutaMsDetallesVentas() {
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

        // ASSERT: verificar que exista la ruta ms-detalles-ventas con su path correcto
        boolean existeRutaMsDetallesVentas = routes.stream().anyMatch(route -> {
                String id = (String) route.get("id");
                List<String> predicates = (List<String>) route.get("predicates");

                return "ms-detalles-ventas".equals(id)
                        && predicates.contains("Path=/api/detalles-ventas/**");
        });

        assertTrue(existeRutaMsDetallesVentas,
                "Debe existir la ruta ms-detalles-ventas con Path=/api/detalles-ventas/**");

        // VERIFY: no aplica Mockito porque este test no usa mocks.
    }

    @Test
    void debeTenerDoceRutasConfiguradas() {
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

        // ASSERT: verificar que existan 12 rutas configuradas
        assertEquals(12, routes.size(),
                "El API Gateway debe tener 12 rutas configuradas");

        // VERIFY: no aplica Mockito porque este test no usa mocks.
    }

    @Test
    void debeConfigurarPuerto8080() {
        // ARRANGE
        Yaml yaml = new Yaml();

        InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream("application.yaml");

        assertNotNull(inputStream, "No se encontró el archivo application.yaml");

        Map<String, Object> config = yaml.load(inputStream);

        // ACT
        Map<String, Object> server =
                (Map<String, Object>) config.get("server");

        Integer port = (Integer) server.get("port");

        // ASSERT
        assertEquals(8080, port,
                "El API Gateway debe ejecutarse en el puerto 8080");

        // VERIFY: no aplica Mockito porque este test no usa mocks.
    }

    @Test
    void debeConfigurarNombreAplicacionApiGateway() {
        // ARRANGE
        Yaml yaml = new Yaml();

        InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream("application.yaml");

        assertNotNull(inputStream, "No se encontró el archivo application.yaml");

        Map<String, Object> config = yaml.load(inputStream);

        // ACT
        Map<String, Object> spring =
                (Map<String, Object>) config.get("spring");

        Map<String, Object> application =
                (Map<String, Object>) spring.get("application");

        String name = (String) application.get("name");

        // ASSERT
        assertEquals("api-gateway", name,
                "El nombre de la aplicación debe ser api-gateway");

        // VERIFY: no aplica Mockito porque este test no usa mocks.
    }

    @Test
    void debeConfigurarUrlDeEureka() {
        // ARRANGE
        Yaml yaml = new Yaml();

        InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream("application.yaml");

        assertNotNull(inputStream, "No se encontró el archivo application.yaml");

        Map<String, Object> config = yaml.load(inputStream);

        // ACT
        Map<String, Object> eureka =
                (Map<String, Object>) config.get("eureka");

        Map<String, Object> client =
                (Map<String, Object>) eureka.get("client");

        Map<String, Object> serviceUrl =
                (Map<String, Object>) client.get("service-url");

        String defaultZone = (String) serviceUrl.get("defaultZone");

        // ASSERT
        assertEquals("http://localhost:8761/eureka", defaultZone,
                "La URL de Eureka debe ser http://localhost:8761/eureka");

        // VERIFY: no aplica Mockito porque este test no usa mocks.
    }

    @Test
    void debeConfigurarRewritePathEnRutaMsAuth() {
        // ARRANGE
        Yaml yaml = new Yaml();

        InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream("application.yaml");

        assertNotNull(inputStream, "No se encontró el archivo application.yaml");

        Map<String, Object> config = yaml.load(inputStream);

        // ACT
        Map<String, Object> spring = (Map<String, Object>) config.get("spring");
        Map<String, Object> cloud = (Map<String, Object>) spring.get("cloud");
        Map<String, Object> gateway = (Map<String, Object>) cloud.get("gateway");
        Map<String, Object> server = (Map<String, Object>) gateway.get("server");
        Map<String, Object> webflux = (Map<String, Object>) server.get("webflux");

        List<Map<String, Object>> routes =
                (List<Map<String, Object>>) webflux.get("routes");

        Map<String, Object> rutaAuth = routes.stream()
                .filter(route -> "ms-auth".equals(route.get("id")))
                .findFirst()
                .orElse(null);

        // ASSERT
        assertNotNull(rutaAuth, "Debe existir la ruta ms-auth");

        List<String> filters = (List<String>) rutaAuth.get("filters");

        assertNotNull(filters, "La ruta ms-auth debe tener filtros configurados");

        assertTrue(filters.contains("RewritePath=/api/auth/(?<remaining>.*), /auth/${remaining}"),
                "La ruta ms-auth debe tener configurado el filtro RewritePath correctamente");

        // VERIFY: no aplica Mockito porque este test no usa mocks.
    }

}