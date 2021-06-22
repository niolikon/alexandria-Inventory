package org.niolikon.alexandria.inventory.catalog.commons;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.niolikon.alexandria.inventory.catalog.commons.converter.PersonToPersonViewConverter;
import org.niolikon.alexandria.inventory.catalog.commons.dto.PersonRequest;
import org.niolikon.alexandria.inventory.catalog.commons.dto.PersonView;
import org.niolikon.alexandria.inventory.catalog.commons.entities.Person;
import org.niolikon.alexandria.inventory.system.MessageProvider;
import org.niolikon.alexandria.inventory.system.exceptions.EntityDuplicationException;
import org.niolikon.alexandria.inventory.system.exceptions.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    
    private final PersonRepository personRepo;
    private final PersonToPersonViewConverter personConverter;
    private final MessageProvider messageProvider;
    
    public PersonService(PersonRepository personRepo,
            PersonToPersonViewConverter personConverter,
            MessageProvider messageUtil) {
        this.personRepo = personRepo;
        this.personConverter = personConverter;
        this.messageProvider = messageUtil;
    }
    
    public Person findPersonOrThrow(Long id) {
        return personRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageProvider.getMessage("person.NotFound", id)));
    }
    
    public PersonView getPerson(Long id) {
        Person person = findPersonOrThrow(id);
        return personConverter.convert(person);
    }

    public Page<PersonView> findAllPersons(Pageable pageable) {
        Page<Person> persons = personRepo.findAll(pageable);
        List<PersonView> personViews = new ArrayList<>();
        persons.forEach(person -> {
            PersonView personView = personConverter.convert(person);
            personViews.add(personView);
        });
        return new PageImpl<>(personViews, pageable, persons.getTotalElements());
    }
    
    public PersonView create(PersonRequest req) {
        Person person = new Person();
        this.fetchFromRequest(person, req);

        try {
            Person personSaved = personRepo.save(person);
            return personConverter.convert(personSaved);
        } catch (DataIntegrityViolationException e) {
            throw new EntityDuplicationException(messageProvider.getMessage("person.Duplication", person.getName(), person.getSurname()));
        }
    }
    
    @Transactional
    public void delete(Long id) {
        try {
            personRepo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(messageProvider.getMessage("person.NotFound", id));
        }
    }
    
    public PersonView update(Person person, PersonRequest req) {
        Person personUpdated = this.fetchFromRequest(person, req);
        Person personSaved = personRepo.save(personUpdated);
        return personConverter.convert(personSaved);
    }
    
    private Person fetchFromRequest(Person person, PersonRequest req) {
        person.setName(req.getName());
        person.setSurname(req.getSurname());
        return person;
    }
}
