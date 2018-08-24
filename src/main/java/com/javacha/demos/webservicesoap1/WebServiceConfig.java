package com.javacha.demos.webservicesoap1;

import java.util.List;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.server.endpoint.interceptor.PayloadValidatingInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

  @Bean
  public ServletRegistrationBean messageDispatcherServlet(ApplicationContext context) {
    MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
    messageDispatcherServlet.setApplicationContext(context);
    messageDispatcherServlet.setTransformWsdlLocations(true);
    return new ServletRegistrationBean(messageDispatcherServlet, "/ws/*");
  }

  @Bean(name = "students")
  public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema studentsSchema) {
    DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
    definition.setPortTypeName("StudentPort");
    definition.setTargetNamespace("http://demos.javacha.com/students");
    definition.setLocationUri("/ws");
    definition.setSchema(studentsSchema);
    return definition;
  }

  @Bean
  public XsdSchema studentsSchema() {
    return new SimpleXsdSchema(new ClassPathResource("student-details.xsd"));
  }
  
  
  /**
   * Agregar interceptor para validar campos de input
   * @param interceptors
   */
  
  @Override
  public void addInterceptors(List<EndpointInterceptor> interceptors) {
      PayloadValidatingInterceptor validatingInterceptor = new  CustomValidationInterceptor(); //PayloadValidatingInterceptor();
      validatingInterceptor.setValidateRequest(true);
      validatingInterceptor.setValidateResponse(true);
      validatingInterceptor.setXsdSchema(studentsSchema());
      interceptors.add(validatingInterceptor);
  }
  
}
