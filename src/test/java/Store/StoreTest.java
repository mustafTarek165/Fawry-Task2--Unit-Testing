package Store;


import example.account.AccountManager;
import example.account.AccountManagerImpl;
import example.account.Customer;
import example.store.Product;
import example.store.Store;
import example.store.StoreImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;


public class StoreTest {

    @Test
    void givenStoreWithNotEnoughProductStock_whenBuy_thenFail()
    {
        AccountManager accountManager= Mockito.mock(AccountManager.class);

       Store store=new StoreImpl(accountManager);

         Product product=new Product();
         Customer customer=new Customer();


        RuntimeException exception = Assertions.assertThrows(
                RuntimeException.class,
                () -> store.buy(product, customer),
                "Expected buy to throw, but it didn't"
        );
        Assertions.assertEquals("Product out of stock", exception.getMessage());
    }
    @Test
    void givenStoreWithEnoughProductStock_whenBuy_thenPaymentSuccess()
    {
        AccountManager accountManager= Mockito.mock(AccountManager.class);
        Store store=new StoreImpl(accountManager);

        Product product=new Product();
        product.setQuantity(5);
        Customer customer=new Customer();

        Mockito.when(accountManager.withdraw(ArgumentMatchers.any(),ArgumentMatchers.anyInt())).thenReturn("success");

        store.buy(product,customer);

        Assertions.assertEquals(4,product.getQuantity());

    }
    @Test
    void givenStoreWithEnoughProductStock_whenBuy_thenPaymentFail()
    {
        AccountManager accountManager= Mockito.mock(AccountManager.class);
        Store store=new StoreImpl(accountManager);

        Product product=new Product();
        product.setQuantity(5);
        Customer customer=new Customer();

        Mockito.when(accountManager.withdraw(ArgumentMatchers.any(),ArgumentMatchers.anyInt()))
                .thenReturn("Not success");


        RuntimeException exception = Assertions.assertThrows(
                RuntimeException.class,
                () -> store.buy(product, customer),
                "Expected buy to throw, but it didn't"
        );
        Assertions.assertEquals("Payment failure: Not success", exception.getMessage());

    }

}
