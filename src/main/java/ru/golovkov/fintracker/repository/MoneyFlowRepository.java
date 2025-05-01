package ru.golovkov.fintracker.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.golovkov.fintracker.model.MoneyFlow;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface MoneyFlowRepository extends JpaRepository<MoneyFlow, String> {

    Page<MoneyFlow> getAllByAccountId(String accountId, Pageable pageable);

    Page<MoneyFlow> getAllByAccountClientId(UUID clientId, Pageable pageable);

    Page<MoneyFlow> getAllByAccountClientIdAndCategoryId(UUID clientId, UUID categoryId, Pageable pageable);

    Page<MoneyFlow> getAllByAccountIdAndCategoryId(String accountId, UUID categoryId, Pageable pageable);

    @Query("""
                SELECT mf.category.name, SUM(mf.amount)
                FROM MoneyFlow mf
                WHERE mf.account.client.id = :clientId
                  AND (:from IS NULL OR mf.date >= :from)
                  AND (:to IS NULL OR mf.date <= :to)
                  AND mf.amount < 0
                GROUP BY mf.category.name
            """)
    List<Object[]> getOutflowsByCategory(
            @Param("clientId") UUID clientId,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );

    @Query("""
                SELECT
                    YEAR(mf.date),
                    MONTH(mf.date),
                    SUM(CASE WHEN mf.amount > 0 THEN mf.amount ELSE 0 END),
                    SUM(CASE WHEN mf.amount < 0 THEN mf.amount ELSE 0 END)
                FROM MoneyFlow mf
                WHERE mf.account.client.id = :clientId
                  AND (:from IS NULL OR mf.date >= :from)
                  AND (:to IS NULL OR mf.date <= :to)
                GROUP BY YEAR(mf.date), MONTH(mf.date)
            """)
    List<Object[]> getMonthlyInflowsAndOutflows(
            @Param("clientId") UUID clientId,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );

    @Query("""
                SELECT
                    SUM(CASE WHEN mf.amount > 0 THEN mf.amount ELSE 0 END) AS totalInflow,
                    SUM(CASE WHEN mf.amount < 0 THEN mf.amount ELSE 0 END) AS totalOutflow,
                    MIN(mf.date) AS minDate,
                    MAX(mf.date) AS maxDate
                FROM MoneyFlow mf
                WHERE mf.account.client.id = :clientId
                  AND (:from IS NULL OR mf.date >= :from)
                  AND (:to IS NULL OR mf.date <= :to)
            """)
    Object[] getTotalInflowAndOutflow(
            @Param("clientId") UUID clientId,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );

    @Query("""
                SELECT
                    YEAR(mf.date),
                    MONTH(mf.date),
                    SUM(mf.amount)
                FROM MoneyFlow mf
                WHERE mf.account.client.id = :clientId
                GROUP BY YEAR(mf.date), MONTH(mf.date)
            """)
    List<Object[]> getMonthlyNetChange(@Param("clientId") UUID clientId);
}