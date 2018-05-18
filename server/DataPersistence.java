package server;

import common.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class DataPersistence implements Runnable {

    private Server server;

    public DataPersistence(Server server) {
        this.server = server;
    }

    @Override
    public void run() {
        while(true) {
            if(!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(10000);
                    FileWriter writer = new FileWriter("data.txt", false);
                    for (Supplier s : server.getSuppliers()) {
                        writer.write("SUPPLIER:"+s.getName()+":"+s.getDistance() + "\r\n");
                    }
                    for (Ingredient i : server.getIngredients()) {
                        writer.write("INGREDIENT:"+i.getName()+":"+i.getUnit()+":"+i.getSupplier().getName()+":"
                                +i.getRestockThreshold()+":"+i.getRestockAmount()+"\r\n");
                    }
                    for (Dish d : server.getDishes()) {
                        writer.write("DISH:"+d.getName()+":"+d.getDescription()+":"+d.getPrice()+":"+
                        d.getRestockThreshold()+":"+d.getRestockAmount()+":");
                        int iter=0;
                        if(d.getRecipe().isEmpty()) {
                            writer.write("Not specified");
                        } else {
                            for (Map.Entry<Ingredient, Number> recipeItem : d.getRecipe().entrySet()) {
                                writer.write(recipeItem.getValue()+" * "+recipeItem.getKey().getName());
                                iter++;
                                if(iter<d.getRecipe().size()) writer.write(",");
                            }
                        }
                        writer.write("\r\n");
                    }
                    for (User u : server.getUsers()) {
                        writer.write("USER:"+u.getName()+":"+u.getPassword()+":"+u.getPostcode().getName() +":"+u.getPostcode().getCode()+"\r\n");
                    }
                    for (Postcode p : server.getPostcodes()) {
                        writer.write("POSTCODE:"+p.getCode()+":"+p.getDistance()+"\r\n");
                    }
                    for (Staff s : server.getStaff()) {
                        writer.write("STAFF:"+s.getName()+"\r\n");
                    }
                    for (Drone d : server.getDrones()) {
                        writer.write("DRONE:"+d.getSpeed()+"\r\n");
                    }
                    for (Order o : server.getOrders()) {
                        writer.write("ORDER:"+o.getName()+":");
                        int iter=0;
                        for (Map.Entry<Dish, Number> basketItem : o.getBasket().entrySet()) {
                            writer.write(basketItem.getValue()+" * "+basketItem.getKey().getName());
                            iter++;
                            if(iter<o.getBasket().size()) writer.write(",");
                        }
                        writer.write("\r\n");
                    }
                    for (Dish d : server.getDishes()) {
                        writer.write("STOCK:"+d.getName()+":"+d.getStock()+"\r\n");
                    }
                    for (Ingredient i : server.getIngredients()) {
                        writer.write("STOCK:"+i.getName()+":"+i.getStock()+"\r\n");
                    }
                    writer.close();
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            } else {
                break;
            }
        }
    }
}
