package service.actor;

import service.dodgydrivers.DDQService;

public class Init {
    private DDQService ddqService_instance;
    public Init(DDQService ddqService_instance) {
        this.ddqService_instance = ddqService_instance;
    }

    public DDQService getQuotationService() {
        return ddqService_instance;
    }

    public void setQuotationService(DDQService ddqService_instance) {
        this.ddqService_instance = ddqService_instance;
    }
}


