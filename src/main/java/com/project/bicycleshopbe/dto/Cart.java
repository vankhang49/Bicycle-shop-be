package com.project.bicycleshopbe.dto;

import com.project.bicycleshopbe.model.business.Bill;
import com.project.bicycleshopbe.model.business.BillItem;
import com.project.bicycleshopbe.model.business.CartItem;
import com.project.bicycleshopbe.model.business.Pricing;
import lombok.*;

import java.util.*;

@Setter
@Getter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    private Map<Pricing, Integer> itemsHashMap = new HashMap<>();;
    

    public Map<Pricing, Integer> getPricingIntegerHashMap() {
        return itemsHashMap;
    }

    private boolean checkItemInCart(Pricing Pricing, int quantity){
        for (Map.Entry<Pricing, Integer> entry : itemsHashMap.entrySet()) {
            if(entry.getKey().getPriceId().equals(Pricing.getPriceId())){
                return true;
            }
        }
        return false;
    }

    private Map.Entry<Pricing, Integer> selectItemInCart(Pricing Pricing){
        for (Map.Entry<Pricing, Integer> entry : itemsHashMap.entrySet()) {
            if(entry.getKey().getPriceId().equals(Pricing.getPriceId())){
                return entry;
            }
        }
        return null;
    }

    public void addPricing(Pricing Pricing, Integer amount){
        if (!checkItemInCart(Pricing, itemsHashMap.getOrDefault(Pricing, 0))){
            itemsHashMap.put(Pricing,amount);
        } else {
            Map.Entry<Pricing, Integer> itemEntry = selectItemInCart(Pricing);
            assert itemEntry != null;
            Integer newQuantity = itemEntry.getValue() + 1;
            itemsHashMap.replace(itemEntry.getKey(),newQuantity);
        }
    }

    public void setQuantityOfPricing(Pricing Pricing, String symbol) {
        for (Map.Entry<Pricing, Integer> entry : itemsHashMap.entrySet()){
            if (entry.getKey().getPriceId().equals(Pricing.getPriceId())){
                if (symbol.equals("-")){
                    entry.setValue(entry.getValue() - 1);
                } else if (symbol.equals("+")) {
                    entry.setValue(entry.getValue() + 1);
                } else {
                    entry.setValue(Integer.parseInt(symbol));
                }
            }
        }
    }

    public Integer countPricingQuantity(){
        Integer PricingQuantity = 0;
        for (Map.Entry<Pricing, Integer> entry : itemsHashMap.entrySet()) {
            PricingQuantity += entry.getValue();
        }
        return PricingQuantity;
    }

    public Integer countItemQuantity(){
        return itemsHashMap.size();
    }

    public Double countTotalPayment(){
        double payment = 0;
        for (Map.Entry<Pricing, Integer> entry : itemsHashMap.entrySet()) {
            payment += entry.getKey().getPrice() * (double) entry.getValue();
        }
        return payment;
    }

    public void deleteItemFromCart(Pricing Pricing){
        for (Map.Entry<Pricing, Integer> entry : itemsHashMap.entrySet()) {
            if (entry.getKey().getPriceId().equals(Pricing.getPriceId())){
                itemsHashMap.remove(entry.getKey());
            }
        }
    }
//    Set item to cart
    public Map<Pricing, Integer> setCartFromDB(List<CartItem> cartItems){
        for (CartItem cartItem: cartItems){
            itemsHashMap.put(cartItem.getPricing(), cartItem.getQuantity());
        }
        return itemsHashMap;
    }
//    Add cart to db
    public List<CartItem> getCartItemsFromDB(){
        List<CartItem> cartItems = new ArrayList<>();
        for (Map.Entry<Pricing, Integer> entry : itemsHashMap.entrySet()) {
            cartItems.add(new CartItem(entry.getKey(), entry.getValue()));
        }
        return cartItems;
    }

//    convert from cart to bill
    public Bill addCartToBill(Bill bill){
        Set<BillItem> billItems = new HashSet<>();
        for (Map.Entry<Pricing, Integer> entry : itemsHashMap.entrySet()) {
            billItems.add(new BillItem(entry.getKey(), entry.getValue()));
        }
        bill.setBillItems(billItems);
        return bill;
    }

}
