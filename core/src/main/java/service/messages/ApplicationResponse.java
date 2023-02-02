package service.messages;

import service.core.ClientInfo;
import service.core.Quotation;

import java.util.ArrayList;
import java.util.List;

public class ApplicationResponse implements MySerializable {
    private int id;
    private ClientInfo clientInfo;
    public List<Quotation> quotations;

    public ApplicationResponse(int id, ClientInfo clientInfo) {
        this.id = id;
        this.clientInfo = clientInfo;
        this.quotations = new ArrayList<Quotation>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public List<Quotation> getQuotations() {
        return quotations;
    }

    public void setQuotations(List<Quotation> quotations) {
        this.quotations = quotations;
    }
}
