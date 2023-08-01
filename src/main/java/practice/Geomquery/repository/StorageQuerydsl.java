package practice.Geomquery.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class StorageQuerydsl implements StorageCustomRepository {
    @PersistenceContext
    private EntityManager em;
    private final JPAQueryFactory queryFactory = new JPAQueryFactory(em);
}
