package io.github.wolches.tgbot.alkach.handlers.message.command;

import io.github.wolches.tgbot.alkach.domain.persistence.model.chat.Chat;
import io.github.wolches.tgbot.alkach.domain.persistence.model.chat.ChatUser;
import io.github.wolches.tgbot.alkach.service.common.ProcessService;
import io.github.wolches.tgbot.alkach.service.common.TextService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExecCommandHandler implements CommandHandler {

    private static final String EXEC_COMMAND = "/exec";

    private static final String OPERATION_NOT_PERMITTED_MESSAGE =   "Operation not permitted!";
    private static final String EXCEPTION_OCCURED_MESSAGE =         "An exception has occured, check logs for additional info.";
    private static final String EXECUTION_RESULT_MESSAGE =          "Execution result:" +
                                                                    "```\r\n%s```";

    private final TextService textService;
    private final ProcessService processService;

    @Override
    public String handle(Message message, Chat chat, ChatUser user) {
        try {
            if (user.getUser().getSettings().isAdmin()) {
                String[] commandArguments = textService.getCommandArguments(EXEC_COMMAND, message.getText());
                List<String> exec = Arrays.asList(commandArguments);
                Process process = processService.createProcess(exec);
                return String.format(EXECUTION_RESULT_MESSAGE, processService.getExecutionResult(process));
            } else {
                return OPERATION_NOT_PERMITTED_MESSAGE;
            }
        } catch (Throwable e) {
            log.error("An exception has occured when executing '{}'", message.getText(), e);
            return EXCEPTION_OCCURED_MESSAGE;
        }
    }

    @Override
    public String getCommandString() {
        return EXEC_COMMAND;
    }
}
