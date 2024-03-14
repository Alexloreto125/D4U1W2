package org.example;

import com.github.javafaker.Faker;
import org.example.entitites.Customer;
import org.example.entitites.Order;
import org.example.entitites.Product;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
public class Application {

    public static void main(String[] args) {




        Supplier<Integer> randomNumbersSupplier= () -> {
            Random rndm= new Random();
            return rndm.nextInt(1,10);
        };
//        Customer clienti = new Customer("Aldo",50);
        Supplier<Customer> clientiSupplier= () -> {
            Faker faker = new Faker();
            return new Customer(faker.superhero().name(),randomNumbersSupplier.get() );
        };

        Supplier<Product> productSupplier= () -> {
          Faker faker= new Faker();
        return new Product(faker.food().ingredient(), faker.commerce().department(), faker.number().randomDouble(2,1,100));
        };
        List<Customer> clienti= new ArrayList<>();
        for (int i= 0; i<4;i++) {
            clienti.add(clientiSupplier.get());
        }

//        List<Order> orderList= new ArrayList<>();

    //!ESERCIZIO 1
        System.out.println("ESERCIZIO 1");

        Map<Customer, List<Order>> ordersByCustomer = clienti.stream()
                .map(customer ->{
                    Order order= new Order(customer);
                    int numProduct= randomNumbersSupplier.get();
                    for (int i= 0; i< numProduct; i++){
                        order.addProduct(productSupplier.get());
                    }
                    return order;

                }).collect(Collectors.groupingBy(order -> order.getCustomer()));


        Map<Customer, Integer> customerIndexMap= new HashMap<>();
        for (int i= 0; i< clienti.size();i++){
            customerIndexMap.put(clienti.get(i),i);
        }

        ordersByCustomer.forEach((customer, orders) -> {
            int index = customerIndexMap.get(customer);

            System.out.println("Cliente numero: " + index);
            System.out.println("Nome del cliente: " + customer.getName());
            System.out.println("Ordini:");

            for (Order order : orders) {
                System.out.println("   - ID ordine: " + order.getId());
                System.out.println("     Stato: " + order.getStatus());
                System.out.println("     Data ordine: " + order.getOrderDate());
                System.out.println("     Data consegna: " + order.getDeliveryDate());
                System.out.println("     Prodotti:");

                for (Product product : order.getProducts()) {
                    System.out.println("       " + product);
                }
            }
        });
        //! ESERCIZIO 2
//
            Map<Customer,Double> totalSalesByCustomer= new HashMap<>();

            ordersByCustomer.forEach((customer, orders) -> {
                double totalSales = orders.stream().flatMap(order -> order.getProducts().stream()).mapToDouble(Product::getPrice).sum();
                totalSalesByCustomer.put(customer,totalSales);
            });

        System.out.println("Totale vendite per cliente: ");
        totalSalesByCustomer.forEach((customer,totalSales) -> {
            System.out.println(customer.getName()+ ": "+ totalSales+ "€");
        });

        //!ESERCIZIO 3

       Map<Customer,Product> maxProduct= new HashMap<>();
       ordersByCustomer.forEach((customer, orders) -> {
           Product maxProductSale= orders.stream().flatMap(order -> order.getProducts().stream()).max(Comparator.comparingDouble(Product::getPrice)).orElse(null);
           maxProduct.put(customer,maxProductSale);
       });

        System.out.println("Il prodotto più costoso per cliente è: ");

        maxProduct.forEach((customer, product) -> {
        if (product != null){
            System.out.println(customer.getName()+ " -> "+ product.getName()+": "+ product.getPrice()+"€");
        } else{
            System.out.println(customer.getName()+ " Nessun prodotto");
        }
        });

            //!Esercizio 4




    }
}
