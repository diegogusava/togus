package br.com.diegogusava.togus.repository;

import br.com.diegogusava.togus.domain.Task;
import br.com.diegogusava.togus.domain.User;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TaskRepository {

    @Inject
    private EntityManager em;

    public Set<Task> findAll() {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Task> query = cb.createQuery(Task.class);
        Root<Task> root = query.from(Task.class);
        final List<Task> users = em.createQuery(query).getResultList();
        return new HashSet<>(users);
    }


    @Transactional
    public Task persist(Task task) {
        em.persist(task);
        return task;
    }

    @Transactional
    public boolean update(Integer taskId, Task task) {
        int rows = em.createQuery("UPDATE Task t SET t.content =:content, t.title =:title WHERE t.id =:taskId")
                .setParameter("title", task.getTitle())
                .setParameter("content", task.getContent())
                .setParameter("taskId", taskId)
                .executeUpdate();
        return rows > 0;
    }

    @Transactional
    public boolean delete(Integer taskId) {
        int rows = em.createQuery("DELETE FROM Task t WHERE t.id =:taskId")
                .setParameter("taskId", taskId)
                .executeUpdate();
        return rows > 0;
    }

}
