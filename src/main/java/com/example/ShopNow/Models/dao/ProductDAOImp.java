package com.example.ShopNow.Models.dao;

import com.example.ShopNow.Models.Product;
import com.example.ShopNow.Models.User;
import com.example.ShopNow.Services.JsonReaderService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
@Repository
public class ProductDAOImp implements ProductDAO{
    @Autowired
    private JsonReaderService jsonReaderService;
    EntityManager entityManager;
    public UserDAO userDAO;
    @Autowired
    public  ProductDAOImp(EntityManager entityManager, UserDAO userDAO){

        this.entityManager=entityManager;
        this.userDAO=userDAO;
    }
    @Transactional
    public List<Product> loadAllProducts() throws IOException {
        Map<String, String> productTypes = new HashMap<>();
        Map<String, User> vendors = new HashMap<>();
        productTypes.put("gaming", "gaming");
        productTypes.put("desktop", "desktop");
        productTypes.put("pcComp", "computer-components");
        productTypes.put("kidFashion", "kid-fashion");
        productTypes.put("laptops", "laptops");
        productTypes.put("menFashion", "men-fashion");
        productTypes.put("mobile", "mobile");
        productTypes.put("networking", "networking");
        productTypes.put("tablets", "tablets");
        productTypes.put("womenFashion", "women-fashion");
        List<Product> allProds=new ArrayList<>();
        for (Map.Entry<String, String> entry : productTypes.entrySet()) {
            List<Product> prods=jsonReaderService.readUsersFromFile("src/main/resources/data/"+entry.getValue()+".json");
            allProds.addAll(prods);
        }
        for(Product prod: allProds){
            prod.setCreatedAt(LocalDateTime.now());
            if(!vendors.containsKey(prod.getVendor())){
                User seller=new User();
                String sellerName=prod.getVendor();
                seller.setUsername(sellerName);
                seller.setEmail(sellerName+"@gmail.com");
                seller.setPassword("12345678");
                seller.setCreatedAt(LocalDateTime.now());
                seller.setEnabled(1);
                seller.setVendor(true);
                userDAO.save(seller);
                vendors.put(sellerName,seller);
                prod.setSellerUser(seller);
            }else{
                prod.setSellerUser(vendors.get(prod.getVendor()));
            }

            entityManager.persist(prod);
        }
        return  allProds;
    }

    public Product getProdById(int id){
        return entityManager.find(Product.class,id);
    }
    @Transactional
    public  void  updateProduct(Product prod){ entityManager.merge(prod);}

    public List<Product> getAllProductByCat(String prodType){
        if(!prodType.equals("all")){
            List<Product> allProds=entityManager.createQuery("FROM Product WHERE categoryName = :theCat").setParameter("theCat",prodType).getResultList();
            return allProds;
        }else{
            List<Product> allProds=entityManager.createQuery("FROM Product").getResultList();
            return allProds;
        }
    }

    public List<Product> getRelatedProd(String subCategory){
        List<Product> prods=entityManager.createQuery("FROM Product WHERE subcategoryName = :subC").setParameter("subC",subCategory).setMaxResults(5).getResultList();
        return  prods;
    }

    @Transactional
    public boolean updateProdQt(int prodId,int quatityToRemove){
        Product prod=this.getProdById(prodId);
        if(prod !=null){
            int prodQt=Integer.parseInt(prod.getQuantity());
            if(prodQt>= quatityToRemove){
                int newQt=prodQt-quatityToRemove;
                prod.setQuantity(String.valueOf(newQt));
                entityManager.merge(prod);
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }


    public List<Product> searchProds(String search){
        List<Product> prods=entityManager.createQuery("FROM Product WHERE name LIKE :searchC  Or description LIKE :searchC").setParameter("searchC", "%" + search + "%").getResultList();
        return prods;
    }

    public <T> List<T> shuffleList(List<T> list) {
        List<T> shuffled = new ArrayList<>(list);
        Random random = new Random();

        for (int i = shuffled.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            // Swap elements
            T temp = shuffled.get(i);
            shuffled.set(i, shuffled.get(j));
            shuffled.set(j, temp);
        }

        return shuffled;
    }


    public List<Product> searchForProdsByFiler(String filter,String value){
        List<Product> prods;
        if(filter.equals("category")){
            prods=entityManager.createQuery("FROM Product WHERE categoryName = :search").setParameter("search",value).getResultList();

        } else if (filter.equals("price")) {
            String[] priceRange = value.split("-");
            Double minPrice=Double.parseDouble(priceRange[0]) ;
            Double maxPrice=Double.parseDouble(priceRange[1]);
            String jpql = "FROM Product p " +
                    "WHERE CAST(REPLACE(p.price, ',', '') AS double) >= :minP " +
                    "AND CAST(REPLACE(p.price, ',', '') AS double) <= :maxP";
            prods=entityManager.createQuery(jpql ).setParameter("minP",minPrice).setParameter("maxP",maxPrice).getResultList();

        }else if (filter.equals("brand")) {
            prods=entityManager.createQuery("FROM Product WHERE brandName = :brand").setParameter("brand",value).getResultList();
        }else if (filter.equals("popular")) {
            prods=entityManager.createQuery("FROM Product WHERE reviewRates >= :rate").setParameter("rate",2.0).getResultList();
        }else if (filter.equals("sales")) {
            prods=entityManager.createQuery("FROM Product WHERE newPrice IS NOT NULL").getResultList();
        }else if (filter.equals("bestSeller")) {
            String jpql = "FROM Product p " +
                    "WHERE CAST(REPLACE(p.quantity, ',', '') AS integer) <= 5 ";
            prods=entityManager.createQuery(jpql ).getResultList();
        }else if (filter.equals("new-arrivals")) {
            LocalDateTime threeWeeksAgo = LocalDateTime.now().minusWeeks(4);
            String jpql = "FROM Product p " +
                    "WHERE p.createdAt >= :threeWeeksAgo " +
                    "ORDER BY p.createdAt DESC";

            prods = entityManager.createQuery(jpql, Product.class)
                    .setParameter("threeWeeksAgo", threeWeeksAgo )
                    .getResultList();
        }else if (filter.equals("all")) {
            prods=entityManager.createQuery("FROM Product").getResultList();
            Collections.shuffle(prods);
        }else{
            prods=new ArrayList<>();
        }
        return prods;
    }
    @Transactional
    public  void  upDatabase(){
        List<Product> allProds=entityManager.createQuery("FROM Product").getResultList();
        for(int i =0;i<allProds.size();i++){
            if(i <= 100){
                allProds.get(i).setCreatedAt(LocalDateTime.now().minusMonths(3));
            } else if (i>100 && i<=200) {
                allProds.get(i).setCreatedAt(LocalDateTime.now().minusMonths(2));
            }
            else if (i>100 && i<=300) {
                allProds.get(i).setCreatedAt(LocalDateTime.now().minusMonths(1));
            }else{
                allProds.get(i).setCreatedAt(LocalDateTime.now().minusWeeks(3));
            }
            entityManager.merge(allProds.get(i));
        }
    }
}
