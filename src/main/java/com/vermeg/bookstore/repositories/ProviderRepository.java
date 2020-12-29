package com.vermeg.bookstore.repositories;

//import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.vermeg.bookstore.entities.Provider;

@Repository
public interface ProviderRepository extends CrudRepository<Provider, Long> {
	
}
