<<<<<<< HEAD:src/test/java/com/zerobase/CafeBom/productCategory/repository/ProductCategoryRepositoryTest.java
package com.zerobase.cafebom.productCategory.repository;
=======
package com.zerobase.cafebom.productcategory.repository;
>>>>>>> main:src/test/java/com/zerobase/cafebom/productcategory/repository/ProductCategoryRepositoryTest.java

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;
}