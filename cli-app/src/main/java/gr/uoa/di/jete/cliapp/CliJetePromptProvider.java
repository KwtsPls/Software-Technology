package gr.uoa.di.jete.cliapp;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;

import org.springframework.stereotype.Component;



@Component
public class CliJetePromptProvider implements PromptProvider{

    @Override
    public AttributedString getPrompt() {
        return new AttributedString("JETE-Cli:>",
                AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE)
        );
    }

}
