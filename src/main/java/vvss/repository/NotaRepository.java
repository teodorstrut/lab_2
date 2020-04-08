package vvss.repository;

import vvss.domain.Nota;
import vvss.domain.Pair;
import vvss.validation.Validator;

public class NotaRepository extends AbstractCRUDRepository<Pair<String, String>, Nota> {
    public NotaRepository(Validator<Nota> validator) {
        super(validator);
    }
}
