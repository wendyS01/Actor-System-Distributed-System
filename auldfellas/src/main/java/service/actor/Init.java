package service.actor;
import service.auldfellas.AFQService;

public class Init {
        private AFQService aFQService_instance;
        public Init(AFQService aFQService_instance) {
            this.aFQService_instance = aFQService_instance;
        }

    public AFQService getQuotationService() {
        return aFQService_instance;
    }

    public void setQuotationService(AFQService aFQService_instance) {
        this.aFQService_instance = aFQService_instance;
    }
}

