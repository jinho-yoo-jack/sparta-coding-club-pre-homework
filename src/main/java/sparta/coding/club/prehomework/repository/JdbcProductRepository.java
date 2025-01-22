package sparta.coding.club.prehomework.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
@RequiredArgsConstructor
public class JdbcProductRepository {
    private final JdbcTemplate jdbcTemplate;

    public BigInteger findBrandIdByMinPriceGroupByBrand() {
        String sqlStatements = """
            SELECT brand_id from
            (SELECT brand_id, SUM(price) as total_price FROM product GROUP BY brand_id) t
             ORDER BY total_price
             LIMIT 1;
            """;
        return jdbcTemplate.queryForObject(sqlStatements, BigInteger.class);
    }
}
