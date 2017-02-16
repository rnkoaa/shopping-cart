package com.ecommerce.shopping;

import com.ecommerce.shopping.domain.Product;
import com.ecommerce.shopping.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("local")
@RunWith(SpringRunner.class)
@SpringBootTest()
@Transactional
public class ShoppingCartApplicationTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void contextLoads() {
        assertThat(true).isTrue();
    }

    @Test
    public void testFindAll() {
        productRepository.deleteAll();

        final List<Product> all = productRepository.findAll();
        assertThat(all).hasSize(0);
    }

    @Test
    public void testInsertProduct() {
        Product product = Product.builder().name("Product 1").serialNumber("1267434").build();

        assertThat(product.getId()).isNull();

        product = productRepository.save(product);
        assertThat(product).isNotNull();
        assertThat(product.getId()).isGreaterThan(0L);

        System.out.println(product.getId());
    }

}
