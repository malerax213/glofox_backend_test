package com.glofox.dev.service;

import com.glofox.dev.model.Book;
import com.glofox.dev.model.Class;
import com.glofox.dev.repository.BookRepository;
import com.glofox.dev.repository.ClassRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class GeneralServiceImpl implements GeneralService {

    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Class createClass(Class aClass) throws Exception{
        if(classIsValidCheck(aClass)) {
            if(dateIsValid(aClass)){
                return classRepository.save(aClass);
            } else {
                throw new Exception("There is already a class on that day");
            }
        } else {
            throw new Exception("The class format is not valid");
        }
    }

    @Override
    public Book createBooking(Book book) throws Exception {
        if(bookIsValidCheck(book) && !doesTheBookExist(book)) {
            if(doesTheClassExist(book.getAClass())) {
                List<Integer> idList = classRepository.existsClassWithName(book.getAClass().getName());
                if(idList.size() == 1) {
                    Optional<Class> c = classRepository.findById(idList.get(0));
                    book.setAClass(c.get());
                    return bookRepository.save(book);
                }else{
                    throw new Exception("There are multiple records of the same Class");
                }
            } else {
                throw new Exception("The class you want to book does not exist");
            }
        } else {
            throw new Exception("The book format is not valid or the book already exists");
        }
    }

    private boolean classIsValidCheck(Class aClass) {
        if("".equalsIgnoreCase(aClass.getName()) || "".equalsIgnoreCase(aClass.getEnd_date().toString()) ||
                "".equalsIgnoreCase(aClass.getStart_date().toString())){
            return false;
        }
        return true;
    }

    private boolean bookIsValidCheck(Book book) {
        if("".equalsIgnoreCase(book.getName()) || "".equalsIgnoreCase(book.getDate().toString()) ||
                !classIsValidCheck(book.getAClass())){
            return false;
        }
        return true;
    }

    private boolean dateIsValid(Class aClass) {
        List<Integer> id1 = classRepository.existsClassWithDate(aClass.getStart_date());
        List<Integer> id2 = classRepository.existsClassWithDate(aClass.getEnd_date());

        if(id1.size() > 0 && id2.size() > 0){
            return false;
        }
        return true;
    }

    private boolean doesTheClassExist(Class aClass){
        List<Integer> classId = classRepository.existsClassWithName(aClass.getName());
        if(classId.size() > 0){
            return true;
        } else {
            return false;
        }
    }

    private boolean doesTheBookExist(Book book){
        List<Integer> ids = bookRepository.existsWithNameAndDate(book.getName(), book.getDate());
        if(ids.size() > 0){
            return true;
        } else {
            return false;
        }
    }

}
