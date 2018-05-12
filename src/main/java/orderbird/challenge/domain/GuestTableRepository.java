package orderbird.challenge.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestTableRepository extends CrudRepository<GuestTable, Long> {

}
