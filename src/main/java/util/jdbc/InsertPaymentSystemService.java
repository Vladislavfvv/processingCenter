package util.jdbc;

import model.PaymentSystem;
import service.PaymentSystemService;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class InsertPaymentSystemService {
    private static final Logger logger = Logger.getLogger(InsertCardStatusService.class.getName());
    private PaymentSystemService paymentSystemService;

    public InsertPaymentSystemService(PaymentSystemService paymentSystemService) {
        this.paymentSystemService = paymentSystemService;
    }

    public void insertPaymentSystems() {
        List<String> systems = Arrays.asList(
                "VISA International Service Association",
                "Mastercard",
                "JCB",
                "American Express",
                "Diners Club International",
                "China UnionPay"
        );

        for (String systemName : systems) {
            try {
                PaymentSystem paymentSystem = new PaymentSystem();
                paymentSystem.setPaymentSystemName(systemName);
                paymentSystemService.create(paymentSystem);
            } catch (Exception e) {
                logger.warning("Не удалось вставить paymentSystem '" + systemName + "': " + e.getMessage());
            }
        }
    }
}
