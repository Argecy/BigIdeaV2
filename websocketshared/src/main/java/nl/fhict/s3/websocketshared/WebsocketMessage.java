package nl.fhict.s3.websocketshared;

public class WebsocketMessage {

    public MessageOperation getOperation() {
        return operation;
    }

    public void setOperation(MessageOperation operation) {
        this.operation = operation;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private MessageOperation operation;
    private String content;

    public WebsocketMessage(){

    }

    public WebsocketMessage(MessageOperation operation, String content){
        this.operation = operation;
        this.content = content;
    }





}
