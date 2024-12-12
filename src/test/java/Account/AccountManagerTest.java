package Account;

import example.account.AccountManager;
import example.account.AccountManagerImpl;
import example.account.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

public class AccountManagerTest {
    AccountManager accountManager =new AccountManagerImpl();

    @Test
   void givenMoneyAmount_whenAddingToCustomerBalance_thenSuccess()
    {
        Customer customer=new Customer();

        accountManager.deposit(customer,50);

        Assertions.assertEquals(50,customer.getBalance());
    }

   @Test
    void givenCustomerWithEnoughBalance_whenWithdraw_thenSucceed()
    {
        Customer customer=new Customer();
        customer.setBalance(50);

       String message= accountManager.withdraw(customer,30);

       Assertions.assertEquals("success",message);
        Assertions.assertEquals(20,customer.getBalance());
    }
    @Test
    void givenCustomerWithNotEnoughBalanceAndCreditNotAllowed_whenWithdraw_thenInsufficientAccountBalance()
    {
        Customer customer=new Customer();
        customer.setBalance(50);
        customer.setCreditAllowed(false);

        String message=accountManager.withdraw(customer,60);

        Assertions.assertEquals("insufficient account balance",message);
        Assertions.assertEquals(50,customer.getBalance());

    }
    @Test
    void givenCustomerWithNotEnoughBalanceWithCreditAllowedAndVip_whenWithdraw_thenSucceed()
    {
        Customer customer=new Customer();
        customer.setBalance(50);
        customer.setCreditAllowed(true);
        customer.setVip(true);

        String message=accountManager.withdraw(customer,60);

        Assertions.assertEquals("success",message);
        Assertions.assertEquals(-10,customer.getBalance());
    }

    @Test
    void givenCustomerWithNotEnoughBalanceWithCreditAllowedNotVipWithLimitSmallerThanMax_whenWithdraw_thenSucceed()
    {
        Customer customer=new Customer();
        customer.setBalance(50);
        customer.setCreditAllowed(true);
        customer.setVip(false);

        String message=accountManager.withdraw(customer,60);

        Assertions.assertEquals("success",message);
        Assertions.assertEquals(-10,customer.getBalance());
    }
    @Test
    void givenCustomerWithNotEnoughBalanceWithCreditAllowedNotVipWithLimitExceedsMax_whenWithdraw_thenMaxCreditExceeded()
    {
        Customer customer=new Customer();
        customer.setBalance(50);
        customer.setCreditAllowed(true);
        customer.setVip(false);

        String message=accountManager.withdraw(customer,1060);


        Assertions.assertEquals("maximum credit exceeded",message);
        Assertions.assertEquals(50,customer.getBalance());
    }


}
