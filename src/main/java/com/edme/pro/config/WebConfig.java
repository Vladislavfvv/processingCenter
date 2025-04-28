package com.edme.pro.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//Этот класс настраивает, откуда можно делать запросы к моему серверу (разрешение кросс-доменных запросов, иначе называемое CORS)
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {//Позволяет настроить CORS-политику для твоего сервера.
        //Это нужно, чтобы браузеры не блокировали запросы между разными доменами.
        registry.addMapping("/**")//Применить CORS-правила ко всем путям (/** — все урлы)
                .allowedOrigins(//Разрешить принимать запросы только с этих доменов: мой продакшен-сайт и localhost для разработки.
                        "https://myfrontend.com",
                        "http://localhost:3000"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE")//Разрешить только эти HTTP-методы. Например, PATCH будет запрещен
                .allowCredentials(true); // Разрешает куки и заголовки авторизации
    }
}
