package com.trainingmug.ecommerce.productservice.config;

import com.trainingmug.ecommerce.productservice.security.JwtAuthenticationFilter; //Custom filter that checks for JWT tokens in incoming requests and validates them

/*ModelMapper oka object ni vere object type loki convert cheyadaniki help chesthundi.
Important: Idi database connection kaadu. MongoDB lo save kuda cheyyadu.
Similar names unna fields ni match chesi values ni copy cheyadaniki help chesthundi.*/

import org.modelmapper.ModelMapper; //Maps data between objects, such as DTOs and entities
import org.springframework.context.annotation.Bean; //Tells Spring to manage an object -- @Bean is used for ModelMapper, PasswordEncoder, and SecurityFilterChain
import org.springframework.context.annotation.Configuration; //Marks this class as a configuration class -- Spring processes this class to discover its @Bean methods and register their returned objects
import org.springframework.http.HttpMethod; //Represents HTTP request methods, such as GET, POST, PUT, and DELETE
import org.springframework.security.config.annotation.web.builders.HttpSecurity; //Lets you configure your application's web security.Your method uses it to set authentication rules, disable certain login mechanisms, configure sessions, and add the JWT filter.
import org.springframework.security.config.http.SessionCreationPolicy; //Defines how Spring Security will create and manage HTTP sessions.
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; //Implements PasswordEncoder and uses the BCrypt strong hashing function to encrypt passwords
import org.springframework.security.crypto.password.PasswordEncoder; //An interface that defines password-encoding operations, including encoding and matching passwords
import org.springframework.security.web.SecurityFilterChain; //Represents a chain of security filters that process incoming HTTP requests.
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; //A filter that processes authentication requests containing a username and password

//tells Spring that this class contains application configuration.
@Configuration
public class AppConfig {

    //tells Spring to manage the returned object.
    @Bean
    public ModelMapper getModelMapper() {

        //Creates a new instance of ModelMapper, which is used to map data between objects, such as DTOs and entities.
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        //Creates a new instance of BCryptPasswordEncoder, which is used to encrypt passwords using the BCrypt strong hashing function.
        return new BCryptPasswordEncoder();
    }

    @Bean
    //This method configures how incoming requests are secured.
    public SecurityFilterChain securityFilterChain(HttpSecurity http,JwtAuthenticationFilter jwtAuthenticationFilter)

        //Defines a bean that configures the security filter chain for the application. It takes an HttpSecurity object and a JwtAuthenticationFilter as parameters and returns a SecurityFilterChain object.
        throws Exception {
                            http //This starts the configuration chain using the HttpSecurity object.

                                /*CSRF is a type of cyberattack in which an attacker tricks a logged-in user's browser into sending an
                                unwanted request to a website where the user is already authenticated.*/

                                //CSRF - Cross-Site Request Forgery.Disables CSRF protection, which is not needed for stateless REST APIs.
                                .csrf(csrf -> csrf.disable())

                                //Disables the default login form provided by Spring Security, as we are using JWT-based authentication instead.
                                .formLogin(form -> form.disable())

                                //This disables HTTP Basic authentication, which normally sends a username and password through an HTTP authentication header.
                                .httpBasic(basic -> basic.disable())

                                //SessionCreationPolicy.STATELESS = Spring Security will not create or use an HTTP session. Each request must contain all the information needed for authentication, such as a JWT token.
                                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //This configures Spring Security not to maintain authentication through HTTP sessions.

                                //who can access which API?
                                .authorizeHttpRequests(auth -> auth
                                    .requestMatchers("/api/auth/**").permitAll()

                                     //Allows GET requests to product endpoints for both customers and admins
                                    .requestMatchers(HttpMethod.GET, "/api/products/**")
                                        .hasAnyAuthority("CUSTOMER", "ADMIN")

                                    //Allows all other requests to product endpoints only for admins
                                    .requestMatchers("/api/products/**")
                                        .hasAuthority("ADMIN")
                                        .anyRequest().authenticated()) ///Any other request must be authenticated, meaning the user must be logged in and have a valid JWT token.

                                //JWT filter can validate the token and establish authentication before authorization rules check the user's permissions.
                                .addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);

                        return http.build();
                            }

                        }