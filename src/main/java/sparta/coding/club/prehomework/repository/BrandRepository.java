package sparta.coding.club.prehomework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.coding.club.prehomework.model.entity.Brand;

import java.math.BigInteger;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, BigInteger> {
    Optional<Brand> findByName(String name);
}
