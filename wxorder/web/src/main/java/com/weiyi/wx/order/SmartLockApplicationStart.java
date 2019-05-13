package com.weiyi.wx.order;

import com.bstek.ureport.console.UReportServlet;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@ImportResource("classpath:context.xml")
public class SmartLockApplicationStart extends SpringBootServletInitializer {

    public static void main(String[] args)
    {
        SpringApplication.run(SmartLockApplicationStart.class, args);
    }

    @RequestMapping("/")
    public String  home()
    {
        return "hello word";
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        builder.sources(this.getClass());
        return super.configure(builder);
    }

    /*@Bean
    public ExitCodeGenerator exitCodeGenerator() {
        System.out.println("hedfjsakf++++++++++++++++++++++derfsdfsd");
        return () -> 0;
    }*/

    @Bean
    public ServletRegistrationBean buildUReportServlet(){
        return new ServletRegistrationBean(new UReportServlet(),"/ureport/*");
    }
}
