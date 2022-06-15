package com.example.demo;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.models.Address;
import com.example.demo.models.Category;
import com.example.demo.models.Product;
import com.example.demo.models.Store;
import com.example.demo.models.User;
import com.example.demo.models.UserAddress;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.StoreRepository;
import com.example.demo.repository.UserAddressRepository;
import com.example.demo.repository.UserRepository;


@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}


	@Transactional
    @Bean
    public CommandLineRunner insertData(
		UserRepository userRepository, AddressRepository addressRepository, UserAddressRepository userAddressRepository, ProductRepository productRepository, 
		CategoryRepository categoryRepository, StoreRepository storeRepository ){
        return(args) -> {


			if (userRepository.findAll().size() == 0 && addressRepository.findAll().size() == 0 && storeRepository.findAll().size() == 0){
				// Users
				User u1 = new User("lucilia@gmail.com", "Lucília Santos", "santos", LocalDate.of(1948,1,8), "912 345 642", false, false);
				User u2 = new User("andre@gmail.com", "André Vaz", "vaz", LocalDate.of(1983,5,27), "962 531 971", true, false);
				User u3 = new User("duarte@gmail.com", "Duarte Henriques", "henriques", LocalDate.of(2003,12,4), "918 496 114", false, true);

				List<User> users = Arrays.asList(u1, u2, u3);
				for (User user : users) userRepository.save(user);

				// Addresses
				Address a1 = new Address("Portugal", "3880-345", "Ovar", "Rua 25 de Abril, nº704");
				Address a2 = new Address("Portugal", "4400-642", "Gaia","Rua José de Castro, nº9");
				Address a3 = new Address("Portugal", "3000-923", "Coimbra","Avenida São Nicolau, 2ªandar, 2ºesq");

				Address a4 = new Address("Portugal", "3800-764", "Aveiro", "Rua do Canatro, nº34");
				Address a5 = new Address("Portugal", "4000-173", "Porto","Rua Afonso Bernardes, nº87");
				Address a6 = new Address("Portugal", "3000-357", "Coimbra","Rua Infante Dão Henrique, nº234");

				List<Address> addresses = Arrays.asList(a1, a2, a3, a4, a5, a6);
				for (Address a : addresses) addressRepository.save(a);

				// Users_Addresses
				UserAddress ua1 = new UserAddress(u1,a1);
				UserAddress ua2 = new UserAddress(u2,a2);
				UserAddress ua3 = new UserAddress(u3,a3);

				List<UserAddress> userAdd = Arrays.asList(ua1, ua2, ua3);
				for (UserAddress ua : userAdd) userAddressRepository.save(ua);

				// Stores
				Store s1 = new Store("Coviran Aveiro", a4);
				Store s2 = new Store("Coviran Porto", a5);
				Store s3 = new Store("Coviran Coimbra", a6);

				List<Store> stores = Arrays.asList(s1, s2, s3);
				for (Store a : stores) storeRepository.save(a);
			}


			if (categoryRepository.findAll().size() == 0 && productRepository.findAll().size() == 0){
				// Category
				Category c1 = new Category("Peixaria", true);
				Category c2 = new Category("Charcutaria | Talho", true);
				Category c3 = new Category("Frutas e Legumes", true);
				Category c4 = new Category("Lácteos", true);
				Category c5 = new Category("Congelados", true);
				Category c6 = new Category("Alimentação", true);
				Category c7 = new Category("Gordices", true);
				Category c8 = new Category("Bebidas", true);
				Category c9 = new Category("Higiene Pessoal", true);

				List<Category> cat = Arrays.asList(c1, c2, c3, c4, c5, c6, c7, c8, c9);
				for (Category c : cat) categoryRepository.save(c);

				// Products
				//	- Peixaria
				Product p1 = new Product("Dourada Média Fresca", Float.valueOf("4.99"), "COVIRAN (700g)", true, c1);
				Product p2 = new Product("Robalo Médio Fresco",  Float.valueOf("3.59"), "COVIRAN (400g)", true, c1);
				Product p3 = new Product("Bacalhau Graúdo 1ª Noruega Seco", Float.valueOf("9.79"), "COVIRAN (2.5kg)", true, c1);

				//	- Charcutaria | Talho
				Product p4 = new Product("Fiambre perna extra", Float.valueOf("1.75"), "SICASAL (100g)",  true, c2);
				Product p5 = new Product("Fiambre Peito de Perú", Float.valueOf("1.12"), "CAMPOFRIO (11,20€/kg)", true, c2);
				Product p6 = new Product("Hambúrguer de Bovino", Float.valueOf("4.69"), "Talhos Rosa - emb. 4 uni (480 gr)", true, c2);

				//	- Frutas e Legumes
				Product p7 = new Product("Abacaxi Gold", Float.valueOf("1.19"), "Origem Costa Rica/Colômbia", true, c3);
				Product p8 = new Product("Banana importada", Float.valueOf("1.09"), "Origem Equador", true,  c3);
				Product p9 = new Product("Laranja Lane Late", Float.valueOf("0.95"), "Origem Australiana, rica em vitaminas", true, c3);
				Product p10 = new Product("Cenouras", Float.valueOf("0.55"), "Origem Nacional", true, c3);
				Product p11 = new Product("Cebola Nova", Float.valueOf("0.89"),"Origem Nacional", true,  c3);
				Product p12 = new Product("Tomate Cherry", Float.valueOf("0.99"), "Origem Nacional", true, c3);

				//	- Lácteos
				Product p13 = new Product("Iogurte Líquido Morango", Float.valueOf("1.99"), "YOGGI s/lactose (160g x 4uni.) ", true, c4);
				Product p14 = new Product("Queijo fatias original", Float.valueOf("2.30"),"TERRA NOSTRA (200g)", true,  c4);
				Product p15 = new Product("Leite Meio Grosso", Float.valueOf("0.77"),"Mimosa (1L)", true, c4);

				//	- Congelados
				Product p16 = new Product("Lombos Salmão", Float.valueOf("5.98"),"PESCANOVA (250g)", true,c5);
				Product p17 = new Product("Pizzas RISTORANTE", Float.valueOf("3.19"), "DR. OETKER (355g)", true,  c5);
				Product p18 = new Product("Gelado Baunilha", Float.valueOf("2.79"),"CARTE D'OR (1,7L)", true, c5);

				//	- Alimentação
				Product p19 = new Product("Massa Linguini", Float.valueOf("1.08"), "MILANEZA (500g)", true, c6);
				Product p20 = new Product("Salsichas de Frango", Float.valueOf("1.23"), "NOBRE (8 uni.)", true, c6);
				Product p21 = new Product("Azeite Subtil", Float.valueOf("2.39"), "GALLO (1L)", true,  c6);
				Product p22 = new Product("Arroz Basmati", Float.valueOf("1.59"),"CIGALA (1kg)", true, c6);
				Product p23 = new Product("Atum natural", Float.valueOf("0.99"), "BOM PETISCO (120g)", true, c6);

				//	- Gordices
				Product p24 = new Product("Chocolate Oreo", Float.valueOf("1.39"), "MILKA (100g)", true, c7);

				//	- Bebidas
				Product p25 = new Product("Água s/gás", Float.valueOf("0.53"), "FASTIO (1,5L)", true, c8);
				Product p26 = new Product("Refrigerante Laranja", Float.valueOf("1.19"),"SUMOL (1,5L)", true, c8);
				Product p27 = new Product("Cerveja branca", Float.valueOf("3.99"),"SUPER BOCK (33cl x 6 uni.)", true, c8);

				//	- Higiene Pessoal
				Product p28 = new Product("Detergente Louça", Float.valueOf("2.09"),"FAIRY (540ml)", true,  c9);
				Product p29 = new Product("Detergente Roupa", Float.valueOf("9.99"),"SKIP pó (46 doses)", true, c9);
				Product p30 = new Product("Rolo Cozinha", Float.valueOf("1.99"), "RENOVA extra XXL (1 rolo)", true,  c9);

				List<Product> products = Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22, p23, p24, p25, p26, p27, p28, p29, p30);
				for (Product p : products) productRepository.save(p);
			}

    	};
	}	

}
