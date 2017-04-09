package br.com.medibot.conversation;

import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ConversationHelper {

    public MessageResponse teste(String message, Map<String, Object> context) {
        String username = "ed9986b6-0a36-4232-bda0-65bea2025108";
        String password = "iUTecUPeA445";
        String workspaceId = "59e631a5-0d3e-4957-9f58-144e75408c59";
        ConversationService service = new ConversationService(ConversationService.VERSION_DATE_2017_02_03);
        service.setUsernameAndPassword(username, password);

        MessageRequest req = new MessageRequest.Builder().inputText(message).context(context).build();
        MessageResponse resp = service.message(workspaceId, req).execute();
        return resp;
    }
}
