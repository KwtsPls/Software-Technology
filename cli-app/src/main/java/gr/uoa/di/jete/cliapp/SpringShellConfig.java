package gr.uoa.di.jete.cliapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Configuration;

import org.jline.terminal.Terminal;

@Configuration
public class SpringShellConfig {

    @Bean
    public ShellHelper shellHelper(@Lazy Terminal terminal) {
        return new ShellHelper(terminal);
    }
}
