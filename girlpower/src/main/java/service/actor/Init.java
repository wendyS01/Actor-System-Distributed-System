package service.actor;

import service.girlpower.GPQService;

public class Init {
    private GPQService gpqService_instance;
    public Init(GPQService gpqService_instance) {
        this.gpqService_instance = gpqService_instance;
    }

    public GPQService getQuotationService() {
        return gpqService_instance;
    }

    public void setQuotationService(GPQService ddqService_instance) {
        this.gpqService_instance = ddqService_instance;
    }
}
