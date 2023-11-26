package com.example.essentialinternship.service.clas;

import com.example.essentialinternship.exeptions.RepositoryException;
import com.example.essentialinternship.models.Users;
import com.example.essentialinternship.repositories.UsersRepository;
import com.example.essentialinternship.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class UsersServiceClas implements UsersService {
    private final UsersRepository usersRepository;

    @Autowired
    public UsersServiceClas(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public Users saveUser(Users user) {
        if (isSaveAble(user) && usersRepository.findByEmail(user.getEmail())==null)
           return usersRepository.save(user);
        else
            throw new RepositoryException("Not valid user");
    }

    private boolean isSaveAble(Users user){
        return
                 user.getAddress() != null
                && user.getContactNumber() != null
                && user.getEmail() != null
                && user.getFirstName() != null
                && user.getLastName() != null
                && user.getDateOfBirth() != null
                && (usersRepository.findByEmail(user.getEmail())==null)
                && (usersRepository.findByPassword(user.getPassword())==null);
    }
    @Override
    public Users findById(Integer id) {
        if(usersRepository.findById(id).isPresent())
            return  usersRepository.findById(id).get();
            else
                throw new RepositoryException("User not find by id");
    }

    @Override
    public Users findByEmail(String email) {
        if(usersRepository.findByEmail(email)!=null)
        return usersRepository.findByEmail(email);
        else
            throw new RepositoryException("User not find bt email");
    }

    @Override
    public Users updateUser(Users user,boolean verifyMessage) {
        if(findById(user.getUserId())!=null)
        {
            return usersRepository.save((
                    doUpdateAble(user,
                    findById(user.getUserId())
                    )));
        }
        else
            throw new RepositoryException("Not valid updateAble user");
    }

    private Users doUpdateAble(Users newUser,Users oldUser) {
        if(newUser.getPassword()!=null && !newUser.getPassword().equals(oldUser.getPassword()))
          oldUser.setPassword(newUser.getPassword());
        if(newUser.getEmail()!=null && !newUser.getEmail().equals(oldUser.getEmail()))
            oldUser.setEmail(newUser.getEmail());
        if(newUser.getAddress()!=null && !newUser.getAddress().equals(oldUser.getAddress()))
            oldUser.setAddress(newUser.getAddress());
        if(newUser.getFirstName()!=null && !newUser.getFirstName().equals(oldUser.getFirstName()))
            oldUser.setFirstName(newUser.getFirstName());
        if(newUser.getLastName()!=null && !newUser.getLastName().equals(oldUser.getLastName()))
            oldUser.setLastName(newUser.getLastName());
        if(newUser.getDateOfBirth()!=null && !newUser.getDateOfBirth().equals(oldUser.getDateOfBirth()))
            oldUser.setDateOfBirth(newUser.getDateOfBirth());
        if(newUser.getContactNumber()!=null && !newUser.getContactNumber().equals(oldUser.getContactNumber()))
            oldUser.setContactNumber(newUser.getContactNumber());
        return oldUser;
    }

    @Override
    public Boolean deleteUser(Integer id) {
        try {
            usersRepository.deleteById(id);
            return true;
        }
        catch (RepositoryException e){
            throw e;
        }
    }

}

