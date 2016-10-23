package br.com.diegogusava.togus.repository;

import br.com.diegogusava.togus.domain.Task;
import br.com.diegogusava.togus.domain.User;
import javaslang.Tuple;
import javaslang.Tuple2;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class UserRepository {

    @Inject
    private EntityManager em;

    public Set<User> findAll() {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<User> query = cb.createQuery(User.class);
        query.from(User.class);
        final List<User> users = em.createQuery(query).getResultList();
        return new HashSet<>(users);
    }

    public Optional<User> findById(Integer id) {
        return em.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findFirst();
    }

    public Optional<Tuple2<User, List<Task>>> findByIdComplete(Integer id) {

        findById(id).map( user -> {
            List<Task> tasks = em.createQuery("SELECT new Task(t.title, t.content) FROM Task t WHERE t.user.id = :userId", Task.class)
                    .setParameter("userId", user.getId())
                    .getResultList();

            return Tuple.of(user, tasks);
        });

        return Optional.empty();
    }

    @Transactional
    public User persist(User user) {
        em.persist(user);
        return user;
    }

    @Transactional
    public boolean delete(Integer id) {
        return em.createQuery("DELETE FROM User u WHERE u.id = :id")
                .setParameter("id", id)
                .executeUpdate() > 0;
    }

    @Transactional
    public User update(Integer id, User user) {
        final User userMerged = em.merge(new User(id, user.getName(), user.getEmail()));
        return userMerged;
    }
}
